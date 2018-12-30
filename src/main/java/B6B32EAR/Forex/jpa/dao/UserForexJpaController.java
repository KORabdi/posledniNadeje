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
import B6B32EAR.Forex.jpa.entities.Forex;
import B6B32EAR.Forex.jpa.entities.User;
import B6B32EAR.Forex.jpa.entities.UserForex;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

/**
 *
 * @author zero
 */
@Transactional
@Repository
public class UserForexJpaController extends BaseDao<UserForex> {

    public UserForexJpaController() {
        super(UserForex.class);
    }

    public void create(UserForex userForex) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Forex fkforex = userForex.getFkforex();
            if (fkforex != null) {
                fkforex = em.getReference(fkforex.getClass(), fkforex.getIdforex());
                userForex.setFkforex(fkforex);
            }
            User fkuser = userForex.getFkuser();
            if (fkuser != null) {
                fkuser = em.getReference(fkuser.getClass(), fkuser.getIduser());
                userForex.setFkuser(fkuser);
            }
            em.persist(userForex);
            if (fkforex != null) {
                fkforex.getUserForexSet().add(userForex);
                fkforex = em.merge(fkforex);
            }
            if (fkuser != null) {
                fkuser.getUserForexSet().add(userForex);
                fkuser = em.merge(fkuser);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserForex userForex) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserForex persistentUserForex = em.find(UserForex.class, userForex.getIduserForex());
            Forex fkforexOld = persistentUserForex.getFkforex();
            Forex fkforexNew = userForex.getFkforex();
            User fkuserOld = persistentUserForex.getFkuser();
            User fkuserNew = userForex.getFkuser();
            if (fkforexNew != null) {
                fkforexNew = em.getReference(fkforexNew.getClass(), fkforexNew.getIdforex());
                userForex.setFkforex(fkforexNew);
            }
            if (fkuserNew != null) {
                fkuserNew = em.getReference(fkuserNew.getClass(), fkuserNew.getIduser());
                userForex.setFkuser(fkuserNew);
            }
            userForex = em.merge(userForex);
            if (fkforexOld != null && !fkforexOld.equals(fkforexNew)) {
                fkforexOld.getUserForexSet().remove(userForex);
                fkforexOld = em.merge(fkforexOld);
            }
            if (fkforexNew != null && !fkforexNew.equals(fkforexOld)) {
                fkforexNew.getUserForexSet().add(userForex);
                fkforexNew = em.merge(fkforexNew);
            }
            if (fkuserOld != null && !fkuserOld.equals(fkuserNew)) {
                fkuserOld.getUserForexSet().remove(userForex);
                fkuserOld = em.merge(fkuserOld);
            }
            if (fkuserNew != null && !fkuserNew.equals(fkuserOld)) {
                fkuserNew.getUserForexSet().add(userForex);
                fkuserNew = em.merge(fkuserNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = userForex.getIduserForex();
                if (findUserForex(id) == null) {
                    throw new NonexistentEntityException("The userForex with id " + id + " no longer exists.");
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
            UserForex userForex;
            try {
                userForex = em.getReference(UserForex.class, id);
                userForex.getIduserForex();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userForex with id " + id + " no longer exists.", enfe);
            }
            Forex fkforex = userForex.getFkforex();
            if (fkforex != null) {
                fkforex.getUserForexSet().remove(userForex);
                fkforex = em.merge(fkforex);
            }
            User fkuser = userForex.getFkuser();
            if (fkuser != null) {
                fkuser.getUserForexSet().remove(userForex);
                fkuser = em.merge(fkuser);
            }
            em.remove(userForex);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserForex> findUserForexEntities() {
        return findUserForexEntities(true, -1, -1);
    }

    public List<UserForex> findUserForexEntities(int maxResults, int firstResult) {
        return findUserForexEntities(false, maxResults, firstResult);
    }

    private List<UserForex> findUserForexEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserForex.class));
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

    public UserForex findUserForex(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserForex.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserForexCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserForex> rt = cq.from(UserForex.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
