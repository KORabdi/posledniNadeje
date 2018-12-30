/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.jpa.dao;

import B6B32EAR.Forex.jpa.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import B6B32EAR.Forex.jpa.entities.Position;
import B6B32EAR.Forex.jpa.entities.Price;
import B6B32EAR.Forex.jpa.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author zero
 */
@Transactional
@Repository
public class PriceJpaController  extends BaseDao<Price> {

    public PriceJpaController() {
        super(Price.class);
    }
    public void create(Price price) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Position fkposition = price.getFkposition();
            if (fkposition != null) {
                fkposition = em.getReference(fkposition.getClass(), fkposition.getIdposition());
                price.setFkposition(fkposition);
            }
            em.persist(price);
            if (fkposition != null) {
                fkposition.getPriceSet().add(price);
                fkposition = em.merge(fkposition);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Price price) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Price persistentPrice = em.find(Price.class, price.getIdprice());
            Position fkpositionOld = persistentPrice.getFkposition();
            Position fkpositionNew = price.getFkposition();
            if (fkpositionNew != null) {
                fkpositionNew = em.getReference(fkpositionNew.getClass(), fkpositionNew.getIdposition());
                price.setFkposition(fkpositionNew);
            }
            price = em.merge(price);
            if (fkpositionOld != null && !fkpositionOld.equals(fkpositionNew)) {
                fkpositionOld.getPriceSet().remove(price);
                fkpositionOld = em.merge(fkpositionOld);
            }
            if (fkpositionNew != null && !fkpositionNew.equals(fkpositionOld)) {
                fkpositionNew.getPriceSet().add(price);
                fkpositionNew = em.merge(fkpositionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = price.getIdprice();
                if (findPrice(id) == null) {
                    throw new NonexistentEntityException("The price with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Price price;
            try {
                price = em.getReference(Price.class, id);
                price.getIdprice();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The price with id " + id + " no longer exists.", enfe);
            }
            Position fkposition = price.getFkposition();
            if (fkposition != null) {
                fkposition.getPriceSet().remove(price);
                fkposition = em.merge(fkposition);
            }
            em.remove(price);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Price> findPriceEntities() {
        return findPriceEntities(true, -1, -1);
    }

    public List<Price> findPriceEntities(int maxResults, int firstResult) {
        return findPriceEntities(false, maxResults, firstResult);
    }

    private List<Price> findPriceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Price.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Price findPrice(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Price.class, id);
        } finally {
            em.close();
        }
    }

    public int getPriceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Price> rt = cq.from(Price.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
