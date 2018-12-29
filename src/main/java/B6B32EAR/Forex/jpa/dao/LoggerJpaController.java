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
import B6B32EAR.Forex.jpa.entities.Algorithm;
import B6B32EAR.Forex.jpa.entities.Logger;
import B6B32EAR.Forex.jpa.entities.Position;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author zero
 */
public class LoggerJpaController implements Serializable {

    public LoggerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Logger logger) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Algorithm fkalgorithm = logger.getFkalgorithm();
            if (fkalgorithm != null) {
                fkalgorithm = em.getReference(fkalgorithm.getClass(), fkalgorithm.getIdalgorithm());
                logger.setFkalgorithm(fkalgorithm);
            }
            Position fkposition = logger.getFkposition();
            if (fkposition != null) {
                fkposition = em.getReference(fkposition.getClass(), fkposition.getIdposition());
                logger.setFkposition(fkposition);
            }
            em.persist(logger);
            if (fkalgorithm != null) {
                fkalgorithm.getLoggerSet().add(logger);
                fkalgorithm = em.merge(fkalgorithm);
            }
            if (fkposition != null) {
                fkposition.getLoggerSet().add(logger);
                fkposition = em.merge(fkposition);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Logger logger) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Logger persistentLogger = em.find(Logger.class, logger.getIdlogger());
            Algorithm fkalgorithmOld = persistentLogger.getFkalgorithm();
            Algorithm fkalgorithmNew = logger.getFkalgorithm();
            Position fkpositionOld = persistentLogger.getFkposition();
            Position fkpositionNew = logger.getFkposition();
            if (fkalgorithmNew != null) {
                fkalgorithmNew = em.getReference(fkalgorithmNew.getClass(), fkalgorithmNew.getIdalgorithm());
                logger.setFkalgorithm(fkalgorithmNew);
            }
            if (fkpositionNew != null) {
                fkpositionNew = em.getReference(fkpositionNew.getClass(), fkpositionNew.getIdposition());
                logger.setFkposition(fkpositionNew);
            }
            logger = em.merge(logger);
            if (fkalgorithmOld != null && !fkalgorithmOld.equals(fkalgorithmNew)) {
                fkalgorithmOld.getLoggerSet().remove(logger);
                fkalgorithmOld = em.merge(fkalgorithmOld);
            }
            if (fkalgorithmNew != null && !fkalgorithmNew.equals(fkalgorithmOld)) {
                fkalgorithmNew.getLoggerSet().add(logger);
                fkalgorithmNew = em.merge(fkalgorithmNew);
            }
            if (fkpositionOld != null && !fkpositionOld.equals(fkpositionNew)) {
                fkpositionOld.getLoggerSet().remove(logger);
                fkpositionOld = em.merge(fkpositionOld);
            }
            if (fkpositionNew != null && !fkpositionNew.equals(fkpositionOld)) {
                fkpositionNew.getLoggerSet().add(logger);
                fkpositionNew = em.merge(fkpositionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = logger.getIdlogger();
                if (findLogger(id) == null) {
                    throw new NonexistentEntityException("The logger with id " + id + " no longer exists.");
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
            Logger logger;
            try {
                logger = em.getReference(Logger.class, id);
                logger.getIdlogger();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logger with id " + id + " no longer exists.", enfe);
            }
            Algorithm fkalgorithm = logger.getFkalgorithm();
            if (fkalgorithm != null) {
                fkalgorithm.getLoggerSet().remove(logger);
                fkalgorithm = em.merge(fkalgorithm);
            }
            Position fkposition = logger.getFkposition();
            if (fkposition != null) {
                fkposition.getLoggerSet().remove(logger);
                fkposition = em.merge(fkposition);
            }
            em.remove(logger);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Logger> findLoggerEntities() {
        return findLoggerEntities(true, -1, -1);
    }

    public List<Logger> findLoggerEntities(int maxResults, int firstResult) {
        return findLoggerEntities(false, maxResults, firstResult);
    }

    private List<Logger> findLoggerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Logger.class));
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

    public Logger findLogger(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Logger.class, id);
        } finally {
            em.close();
        }
    }

    public int getLoggerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Logger> rt = cq.from(Logger.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
