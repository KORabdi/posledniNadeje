/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.jpa.dao;

import B6B32EAR.Forex.jpa.dao.exceptions.IllegalOrphanException;
import B6B32EAR.Forex.jpa.dao.exceptions.NonexistentEntityException;
import B6B32EAR.Forex.jpa.entities.Algorithm;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import B6B32EAR.Forex.jpa.entities.Logger;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author zero
 */
public class AlgorithmJpaController implements Serializable {

    /*    @PersistenceContext(unitName = "forex")
    protected EntityManager entityManager;
*/
    
    public AlgorithmJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Algorithm algorithm) {
        if (algorithm.getLoggerSet() == null) {
            algorithm.setLoggerSet(new HashSet<Logger>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Logger> attachedLoggerSet = new HashSet<Logger>();
            for (Logger loggerSetLoggerToAttach : algorithm.getLoggerSet()) {
                loggerSetLoggerToAttach = em.getReference(loggerSetLoggerToAttach.getClass(), loggerSetLoggerToAttach.getIdlogger());
                attachedLoggerSet.add(loggerSetLoggerToAttach);
            }
            algorithm.setLoggerSet(attachedLoggerSet);
            em.persist(algorithm);
            for (Logger loggerSetLogger : algorithm.getLoggerSet()) {
                Algorithm oldFkalgorithmOfLoggerSetLogger = loggerSetLogger.getFkalgorithm();
                loggerSetLogger.setFkalgorithm(algorithm);
                loggerSetLogger = em.merge(loggerSetLogger);
                if (oldFkalgorithmOfLoggerSetLogger != null) {
                    oldFkalgorithmOfLoggerSetLogger.getLoggerSet().remove(loggerSetLogger);
                    oldFkalgorithmOfLoggerSetLogger = em.merge(oldFkalgorithmOfLoggerSetLogger);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Algorithm algorithm) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Algorithm persistentAlgorithm = em.find(Algorithm.class, algorithm.getIdalgorithm());
            Set<Logger> loggerSetOld = persistentAlgorithm.getLoggerSet();
            Set<Logger> loggerSetNew = algorithm.getLoggerSet();
            List<String> illegalOrphanMessages = null;
            for (Logger loggerSetOldLogger : loggerSetOld) {
                if (!loggerSetNew.contains(loggerSetOldLogger)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Logger " + loggerSetOldLogger + " since its fkalgorithm field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<Logger> attachedLoggerSetNew = new HashSet<Logger>();
            for (Logger loggerSetNewLoggerToAttach : loggerSetNew) {
                loggerSetNewLoggerToAttach = em.getReference(loggerSetNewLoggerToAttach.getClass(), loggerSetNewLoggerToAttach.getIdlogger());
                attachedLoggerSetNew.add(loggerSetNewLoggerToAttach);
            }
            loggerSetNew = attachedLoggerSetNew;
            algorithm.setLoggerSet(loggerSetNew);
            algorithm = em.merge(algorithm);
            for (Logger loggerSetNewLogger : loggerSetNew) {
                if (!loggerSetOld.contains(loggerSetNewLogger)) {
                    Algorithm oldFkalgorithmOfLoggerSetNewLogger = loggerSetNewLogger.getFkalgorithm();
                    loggerSetNewLogger.setFkalgorithm(algorithm);
                    loggerSetNewLogger = em.merge(loggerSetNewLogger);
                    if (oldFkalgorithmOfLoggerSetNewLogger != null && !oldFkalgorithmOfLoggerSetNewLogger.equals(algorithm)) {
                        oldFkalgorithmOfLoggerSetNewLogger.getLoggerSet().remove(loggerSetNewLogger);
                        oldFkalgorithmOfLoggerSetNewLogger = em.merge(oldFkalgorithmOfLoggerSetNewLogger);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = algorithm.getIdalgorithm();
                if (findAlgorithm(id) == null) {
                    throw new NonexistentEntityException("The algorithm with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Algorithm algorithm;
            try {
                algorithm = em.getReference(Algorithm.class, id);
                algorithm.getIdalgorithm();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The algorithm with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Logger> loggerSetOrphanCheck = algorithm.getLoggerSet();
            for (Logger loggerSetOrphanCheckLogger : loggerSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Algorithm (" + algorithm + ") cannot be destroyed since the Logger " + loggerSetOrphanCheckLogger + " in its loggerSet field has a non-nullable fkalgorithm field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(algorithm);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Algorithm> findAlgorithmEntities() {
        return findAlgorithmEntities(true, -1, -1);
    }

    public List<Algorithm> findAlgorithmEntities(int maxResults, int firstResult) {
        return findAlgorithmEntities(false, maxResults, firstResult);
    }

    private List<Algorithm> findAlgorithmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Algorithm.class));
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

    public Algorithm findAlgorithm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Algorithm.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlgorithmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Algorithm> rt = cq.from(Algorithm.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
