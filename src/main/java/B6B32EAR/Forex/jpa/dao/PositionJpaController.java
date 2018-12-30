/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.jpa.dao;

import B6B32EAR.Forex.jpa.dao.exceptions.IllegalOrphanException;
import B6B32EAR.Forex.jpa.dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import B6B32EAR.Forex.jpa.entities.Forex;
import B6B32EAR.Forex.jpa.entities.User;
import B6B32EAR.Forex.jpa.entities.Price;
import java.util.HashSet;
import java.util.Set;
import B6B32EAR.Forex.jpa.entities.Logger;
import B6B32EAR.Forex.jpa.entities.Position;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
public class PositionJpaController extends BaseDao<Position> {

    public PositionJpaController() {
        super(Position.class);
    }

    public void create(Position position) {
        if (position.getPriceSet() == null) {
            position.setPriceSet(new HashSet<Price>());
        }
        if (position.getLoggerSet() == null) {
            position.setLoggerSet(new HashSet<Logger>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Forex fkforex = position.getFkforex();
            if (fkforex != null) {
                fkforex = em.getReference(fkforex.getClass(), fkforex.getIdforex());
                position.setFkforex(fkforex);
            }
            User fkuser = position.getFkuser();
            if (fkuser != null) {
                fkuser = em.getReference(fkuser.getClass(), fkuser.getIduser());
                position.setFkuser(fkuser);
            }
            Set<Price> attachedPriceSet = new HashSet<Price>();
            for (Price priceSetPriceToAttach : position.getPriceSet()) {
                priceSetPriceToAttach = em.getReference(priceSetPriceToAttach.getClass(), priceSetPriceToAttach.getIdprice());
                attachedPriceSet.add(priceSetPriceToAttach);
            }
            position.setPriceSet(attachedPriceSet);
            Set<Logger> attachedLoggerSet = new HashSet<Logger>();
            for (Logger loggerSetLoggerToAttach : position.getLoggerSet()) {
                loggerSetLoggerToAttach = em.getReference(loggerSetLoggerToAttach.getClass(), loggerSetLoggerToAttach.getIdlogger());
                attachedLoggerSet.add(loggerSetLoggerToAttach);
            }
            position.setLoggerSet(attachedLoggerSet);
            em.persist(position);
            if (fkforex != null) {
                fkforex.getPositionSet().add(position);
                fkforex = em.merge(fkforex);
            }
            if (fkuser != null) {
                fkuser.getPositionSet().add(position);
                fkuser = em.merge(fkuser);
            }
            for (Price priceSetPrice : position.getPriceSet()) {
                Position oldFkpositionOfPriceSetPrice = priceSetPrice.getFkposition();
                priceSetPrice.setFkposition(position);
                priceSetPrice = em.merge(priceSetPrice);
                if (oldFkpositionOfPriceSetPrice != null) {
                    oldFkpositionOfPriceSetPrice.getPriceSet().remove(priceSetPrice);
                    oldFkpositionOfPriceSetPrice = em.merge(oldFkpositionOfPriceSetPrice);
                }
            }
            for (Logger loggerSetLogger : position.getLoggerSet()) {
                Position oldFkpositionOfLoggerSetLogger = loggerSetLogger.getFkposition();
                loggerSetLogger.setFkposition(position);
                loggerSetLogger = em.merge(loggerSetLogger);
                if (oldFkpositionOfLoggerSetLogger != null) {
                    oldFkpositionOfLoggerSetLogger.getLoggerSet().remove(loggerSetLogger);
                    oldFkpositionOfLoggerSetLogger = em.merge(oldFkpositionOfLoggerSetLogger);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Position position) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Position persistentPosition = em.find(Position.class, position.getIdposition());
            Forex fkforexOld = persistentPosition.getFkforex();
            Forex fkforexNew = position.getFkforex();
            User fkuserOld = persistentPosition.getFkuser();
            User fkuserNew = position.getFkuser();
            Set<Price> priceSetOld = persistentPosition.getPriceSet();
            Set<Price> priceSetNew = position.getPriceSet();
            Set<Logger> loggerSetOld = persistentPosition.getLoggerSet();
            Set<Logger> loggerSetNew = position.getLoggerSet();
            List<String> illegalOrphanMessages = null;
            for (Logger loggerSetOldLogger : loggerSetOld) {
                if (!loggerSetNew.contains(loggerSetOldLogger)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Logger " + loggerSetOldLogger + " since its fkposition field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkforexNew != null) {
                fkforexNew = em.getReference(fkforexNew.getClass(), fkforexNew.getIdforex());
                position.setFkforex(fkforexNew);
            }
            if (fkuserNew != null) {
                fkuserNew = em.getReference(fkuserNew.getClass(), fkuserNew.getIduser());
                position.setFkuser(fkuserNew);
            }
            Set<Price> attachedPriceSetNew = new HashSet<Price>();
            for (Price priceSetNewPriceToAttach : priceSetNew) {
                priceSetNewPriceToAttach = em.getReference(priceSetNewPriceToAttach.getClass(), priceSetNewPriceToAttach.getIdprice());
                attachedPriceSetNew.add(priceSetNewPriceToAttach);
            }
            priceSetNew = attachedPriceSetNew;
            position.setPriceSet(priceSetNew);
            Set<Logger> attachedLoggerSetNew = new HashSet<Logger>();
            for (Logger loggerSetNewLoggerToAttach : loggerSetNew) {
                loggerSetNewLoggerToAttach = em.getReference(loggerSetNewLoggerToAttach.getClass(), loggerSetNewLoggerToAttach.getIdlogger());
                attachedLoggerSetNew.add(loggerSetNewLoggerToAttach);
            }
            loggerSetNew = attachedLoggerSetNew;
            position.setLoggerSet(loggerSetNew);
            position = em.merge(position);
            if (fkforexOld != null && !fkforexOld.equals(fkforexNew)) {
                fkforexOld.getPositionSet().remove(position);
                fkforexOld = em.merge(fkforexOld);
            }
            if (fkforexNew != null && !fkforexNew.equals(fkforexOld)) {
                fkforexNew.getPositionSet().add(position);
                fkforexNew = em.merge(fkforexNew);
            }
            if (fkuserOld != null && !fkuserOld.equals(fkuserNew)) {
                fkuserOld.getPositionSet().remove(position);
                fkuserOld = em.merge(fkuserOld);
            }
            if (fkuserNew != null && !fkuserNew.equals(fkuserOld)) {
                fkuserNew.getPositionSet().add(position);
                fkuserNew = em.merge(fkuserNew);
            }
            for (Price priceSetOldPrice : priceSetOld) {
                if (!priceSetNew.contains(priceSetOldPrice)) {
                    priceSetOldPrice.setFkposition(null);
                    priceSetOldPrice = em.merge(priceSetOldPrice);
                }
            }
            for (Price priceSetNewPrice : priceSetNew) {
                if (!priceSetOld.contains(priceSetNewPrice)) {
                    Position oldFkpositionOfPriceSetNewPrice = priceSetNewPrice.getFkposition();
                    priceSetNewPrice.setFkposition(position);
                    priceSetNewPrice = em.merge(priceSetNewPrice);
                    if (oldFkpositionOfPriceSetNewPrice != null && !oldFkpositionOfPriceSetNewPrice.equals(position)) {
                        oldFkpositionOfPriceSetNewPrice.getPriceSet().remove(priceSetNewPrice);
                        oldFkpositionOfPriceSetNewPrice = em.merge(oldFkpositionOfPriceSetNewPrice);
                    }
                }
            }
            for (Logger loggerSetNewLogger : loggerSetNew) {
                if (!loggerSetOld.contains(loggerSetNewLogger)) {
                    Position oldFkpositionOfLoggerSetNewLogger = loggerSetNewLogger.getFkposition();
                    loggerSetNewLogger.setFkposition(position);
                    loggerSetNewLogger = em.merge(loggerSetNewLogger);
                    if (oldFkpositionOfLoggerSetNewLogger != null && !oldFkpositionOfLoggerSetNewLogger.equals(position)) {
                        oldFkpositionOfLoggerSetNewLogger.getLoggerSet().remove(loggerSetNewLogger);
                        oldFkpositionOfLoggerSetNewLogger = em.merge(oldFkpositionOfLoggerSetNewLogger);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = position.getIdposition();
                if (findPosition(id) == null) {
                    throw new NonexistentEntityException("The position with id " + id + " no longer exists.");
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
            Position position;
            try {
                position = em.getReference(Position.class, id);
                position.getIdposition();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The position with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Logger> loggerSetOrphanCheck = position.getLoggerSet();
            for (Logger loggerSetOrphanCheckLogger : loggerSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Position (" + position + ") cannot be destroyed since the Logger " + loggerSetOrphanCheckLogger + " in its loggerSet field has a non-nullable fkposition field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Forex fkforex = position.getFkforex();
            if (fkforex != null) {
                fkforex.getPositionSet().remove(position);
                fkforex = em.merge(fkforex);
            }
            User fkuser = position.getFkuser();
            if (fkuser != null) {
                fkuser.getPositionSet().remove(position);
                fkuser = em.merge(fkuser);
            }
            Set<Price> priceSet = position.getPriceSet();
            for (Price priceSetPrice : priceSet) {
                priceSetPrice.setFkposition(null);
                priceSetPrice = em.merge(priceSetPrice);
            }
            em.remove(position);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Position> findPositionEntities() {
        return findPositionEntities(true, -1, -1);
    }

    public List<Position> findPositionEntities(int maxResults, int firstResult) {
        return findPositionEntities(false, maxResults, firstResult);
    }

    private List<Position> findPositionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Position.class));
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

    public Position findPosition(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Position.class, id);
        } finally {
            em.close();
        }
    }

    public int getPositionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Position> rt = cq.from(Position.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
