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

import B6B32EAR.Forex.jpa.entities.*;

import java.util.HashSet;
import java.util.Set;

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
public class ForexJpaController extends BaseDao<Forex>  {

    public ForexJpaController() {
        super(Forex.class);
    }


    public void create(Forex forex) {
        if (forex.getUserForexSet() == null) {
            forex.setUserForexSet(new HashSet<UserForex>());
        }
        if (forex.getPositionSet() == null) {
            forex.setPositionSet(new HashSet<Position>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Currency currencytwo = forex.getCurrencytwo();
            if (currencytwo != null) {
                currencytwo = em.getReference(currencytwo.getClass(), currencytwo.getIdcurrency());
                forex.setCurrencytwo(currencytwo);
            }
            Currency currencyone = forex.getCurrencyone();
            if (currencyone != null) {
                currencyone = em.getReference(currencyone.getClass(), currencyone.getIdcurrency());
                forex.setCurrencyone(currencyone);
            }
            Set<UserForex> attachedUserForexSet = new HashSet<UserForex>();
            for (UserForex userForexSetUserForexToAttach : forex.getUserForexSet()) {
                userForexSetUserForexToAttach = em.getReference(userForexSetUserForexToAttach.getClass(), userForexSetUserForexToAttach.getIduserForex());
                attachedUserForexSet.add(userForexSetUserForexToAttach);
            }
            forex.setUserForexSet(attachedUserForexSet);
            Set<Position> attachedPositionSet = new HashSet<Position>();
            for (Position positionSetPositionToAttach : forex.getPositionSet()) {
                positionSetPositionToAttach = em.getReference(positionSetPositionToAttach.getClass(), positionSetPositionToAttach.getIdposition());
                attachedPositionSet.add(positionSetPositionToAttach);
            }
            forex.setPositionSet(attachedPositionSet);
            em.persist(forex);
            if (currencytwo != null) {
                currencytwo.getForexSet().add(forex);
                currencytwo = em.merge(currencytwo);
            }
            if (currencyone != null) {
                currencyone.getForexSet().add(forex);
                currencyone = em.merge(currencyone);
            }
            for (UserForex userForexSetUserForex : forex.getUserForexSet()) {
                Forex oldFkforexOfUserForexSetUserForex = userForexSetUserForex.getFkforex();
                userForexSetUserForex.setFkforex(forex);
                userForexSetUserForex = em.merge(userForexSetUserForex);
                if (oldFkforexOfUserForexSetUserForex != null) {
                    oldFkforexOfUserForexSetUserForex.getUserForexSet().remove(userForexSetUserForex);
                    oldFkforexOfUserForexSetUserForex = em.merge(oldFkforexOfUserForexSetUserForex);
                }
            }
            for (Position positionSetPosition : forex.getPositionSet()) {
                Forex oldFkforexOfPositionSetPosition = positionSetPosition.getFkforex();
                positionSetPosition.setFkforex(forex);
                positionSetPosition = em.merge(positionSetPosition);
                if (oldFkforexOfPositionSetPosition != null) {
                    oldFkforexOfPositionSetPosition.getPositionSet().remove(positionSetPosition);
                    oldFkforexOfPositionSetPosition = em.merge(oldFkforexOfPositionSetPosition);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Forex forex) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Forex persistentForex = em.find(Forex.class, forex.getIdforex());
            Currency currencytwoOld = persistentForex.getCurrencytwo();
            Currency currencytwoNew = forex.getCurrencytwo();
            Currency currencyoneOld = persistentForex.getCurrencyone();
            Currency currencyoneNew = forex.getCurrencyone();
            Set<UserForex> userForexSetOld = persistentForex.getUserForexSet();
            Set<UserForex> userForexSetNew = forex.getUserForexSet();
            Set<Position> positionSetOld = persistentForex.getPositionSet();
            Set<Position> positionSetNew = forex.getPositionSet();
            List<String> illegalOrphanMessages = null;
            for (UserForex userForexSetOldUserForex : userForexSetOld) {
                if (!userForexSetNew.contains(userForexSetOldUserForex)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserForex " + userForexSetOldUserForex + " since its fkforex field is not nullable.");
                }
            }
            for (Position positionSetOldPosition : positionSetOld) {
                if (!positionSetNew.contains(positionSetOldPosition)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Position " + positionSetOldPosition + " since its fkforex field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (currencytwoNew != null) {
                currencytwoNew = em.getReference(currencytwoNew.getClass(), currencytwoNew.getIdcurrency());
                forex.setCurrencytwo(currencytwoNew);
            }
            if (currencyoneNew != null) {
                currencyoneNew = em.getReference(currencyoneNew.getClass(), currencyoneNew.getIdcurrency());
                forex.setCurrencyone(currencyoneNew);
            }
            Set<UserForex> attachedUserForexSetNew = new HashSet<UserForex>();
            for (UserForex userForexSetNewUserForexToAttach : userForexSetNew) {
                userForexSetNewUserForexToAttach = em.getReference(userForexSetNewUserForexToAttach.getClass(), userForexSetNewUserForexToAttach.getIduserForex());
                attachedUserForexSetNew.add(userForexSetNewUserForexToAttach);
            }
            userForexSetNew = attachedUserForexSetNew;
            forex.setUserForexSet(userForexSetNew);
            Set<Position> attachedPositionSetNew = new HashSet<Position>();
            for (Position positionSetNewPositionToAttach : positionSetNew) {
                positionSetNewPositionToAttach = em.getReference(positionSetNewPositionToAttach.getClass(), positionSetNewPositionToAttach.getIdposition());
                attachedPositionSetNew.add(positionSetNewPositionToAttach);
            }
            positionSetNew = attachedPositionSetNew;
            forex.setPositionSet(positionSetNew);
            forex = em.merge(forex);
            if (currencytwoOld != null && !currencytwoOld.equals(currencytwoNew)) {
                currencytwoOld.getForexSet().remove(forex);
                currencytwoOld = em.merge(currencytwoOld);
            }
            if (currencytwoNew != null && !currencytwoNew.equals(currencytwoOld)) {
                currencytwoNew.getForexSet().add(forex);
                currencytwoNew = em.merge(currencytwoNew);
            }
            if (currencyoneOld != null && !currencyoneOld.equals(currencyoneNew)) {
                currencyoneOld.getForexSet().remove(forex);
                currencyoneOld = em.merge(currencyoneOld);
            }
            if (currencyoneNew != null && !currencyoneNew.equals(currencyoneOld)) {
                currencyoneNew.getForexSet().add(forex);
                currencyoneNew = em.merge(currencyoneNew);
            }
            for (UserForex userForexSetNewUserForex : userForexSetNew) {
                if (!userForexSetOld.contains(userForexSetNewUserForex)) {
                    Forex oldFkforexOfUserForexSetNewUserForex = userForexSetNewUserForex.getFkforex();
                    userForexSetNewUserForex.setFkforex(forex);
                    userForexSetNewUserForex = em.merge(userForexSetNewUserForex);
                    if (oldFkforexOfUserForexSetNewUserForex != null && !oldFkforexOfUserForexSetNewUserForex.equals(forex)) {
                        oldFkforexOfUserForexSetNewUserForex.getUserForexSet().remove(userForexSetNewUserForex);
                        oldFkforexOfUserForexSetNewUserForex = em.merge(oldFkforexOfUserForexSetNewUserForex);
                    }
                }
            }
            for (Position positionSetNewPosition : positionSetNew) {
                if (!positionSetOld.contains(positionSetNewPosition)) {
                    Forex oldFkforexOfPositionSetNewPosition = positionSetNewPosition.getFkforex();
                    positionSetNewPosition.setFkforex(forex);
                    positionSetNewPosition = em.merge(positionSetNewPosition);
                    if (oldFkforexOfPositionSetNewPosition != null && !oldFkforexOfPositionSetNewPosition.equals(forex)) {
                        oldFkforexOfPositionSetNewPosition.getPositionSet().remove(positionSetNewPosition);
                        oldFkforexOfPositionSetNewPosition = em.merge(oldFkforexOfPositionSetNewPosition);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = forex.getIdforex();
                if (findForex(id) == null) {
                    throw new NonexistentEntityException("The forex with id " + id + " no longer exists.");
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
            Forex forex;
            try {
                forex = em.getReference(Forex.class, id);
                forex.getIdforex();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The forex with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<UserForex> userForexSetOrphanCheck = forex.getUserForexSet();
            for (UserForex userForexSetOrphanCheckUserForex : userForexSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Forex (" + forex + ") cannot be destroyed since the UserForex " + userForexSetOrphanCheckUserForex + " in its userForexSet field has a non-nullable fkforex field.");
            }
            Set<Position> positionSetOrphanCheck = forex.getPositionSet();
            for (Position positionSetOrphanCheckPosition : positionSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Forex (" + forex + ") cannot be destroyed since the Position " + positionSetOrphanCheckPosition + " in its positionSet field has a non-nullable fkforex field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Currency currencytwo = forex.getCurrencytwo();
            if (currencytwo != null) {
                currencytwo.getForexSet().remove(forex);
                currencytwo = em.merge(currencytwo);
            }
            Currency currencyone = forex.getCurrencyone();
            if (currencyone != null) {
                currencyone.getForexSet().remove(forex);
                currencyone = em.merge(currencyone);
            }
            em.remove(forex);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Forex> findForexEntities() {
        return findForexEntities(true, -1, -1);
    }

    public List<Forex> findForexEntities(int maxResults, int firstResult) {
        return findForexEntities(false, maxResults, firstResult);
    }

    private List<Forex> findForexEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Forex.class));
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

    public Forex findForex(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Forex.class, id);
        } finally {
            em.close();
        }
    }

    public int getForexCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Forex> rt = cq.from(Forex.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
