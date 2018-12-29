package B6B32EAR.Forex.jpa.dao;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Parameter;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
@Transactional
public abstract class BaseDao<T> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> type;
    
    protected EntityManager getEntityManager(){
        return em;
    }
    
    protected BaseDao(Class<T> type) {
        this.type = type;
    }

    @Override
    public T find(Integer id) {
        Objects.requireNonNull(id);
        return em.find(type, id);
    }

    @Override
    public List<T> findAll() {
        try {
            return em.createQuery("SELECT e FROM " + type.getSimpleName() + " e", type).getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<T> findBy(HashMap<String, String> hmap) {
        String query = "SELECT e FROM " + type.getSimpleName() + " e WHERE 1=1";
        Set set = hmap.entrySet();
        Iterator iterator = set.iterator();
        
        while(iterator.hasNext()) {
           Map.Entry mentry = (Map.Entry)iterator.next();
           query = query + " AND e." + mentry.getKey() + "=:" + mentry.getKey();
        }
        
        set = hmap.entrySet();
        iterator = set.iterator();
        TypedQuery emQuery = em.createQuery(query, type);
        
        while(iterator.hasNext()) {
           Map.Entry mentry = (Map.Entry)iterator.next();
           emQuery.setParameter((String) mentry.getKey(), mentry.getValue());
        }
        
        List<T> temp = emQuery.getResultList();
        return temp.isEmpty() ? null : temp;
    }
    
    @Override
    public void persist(T entity) {
        Objects.requireNonNull(entity);
        try {
            em.persist(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void persist(Collection<T> entities) {
        Objects.requireNonNull(entities);
        if (entities.isEmpty()) {
            return;
        }
        try {
            entities.forEach(this::persist);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override @Transactional
    public T update(T entity) {
        Objects.requireNonNull(entity);
        try {
            return em.merge(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void remove(T entity) {
        Objects.requireNonNull(entity);
        try {
            final T toRemove = em.merge(entity);
            if (toRemove != null) {
                em.remove(toRemove);
            }
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public boolean exists(Integer id) {
        return id != null && em.find(type, id) != null;
    }
}
