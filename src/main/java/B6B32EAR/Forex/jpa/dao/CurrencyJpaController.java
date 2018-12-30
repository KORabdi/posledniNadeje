/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package B6B32EAR.Forex.jpa.dao;

import B6B32EAR.Forex.jpa.dao.exceptions.IllegalOrphanException;
import B6B32EAR.Forex.jpa.dao.exceptions.NonexistentEntityException;
import B6B32EAR.Forex.jpa.entities.Currency;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import B6B32EAR.Forex.jpa.entities.Forex;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
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
public class CurrencyJpaController  extends BaseDao<Currency> {

    public CurrencyJpaController() {
        super(Currency.class);
    }
    public void create(Currency currency) {
        if (currency.getForexSet() == null) {
            currency.setForexSet(new HashSet<Forex>());
        }
        if (currency.getForexSet1() == null) {
            currency.setForexSet1(new HashSet<Forex>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Forex> attachedForexSet = new HashSet<Forex>();
            for (Forex forexSetForexToAttach : currency.getForexSet()) {
                forexSetForexToAttach = em.getReference(forexSetForexToAttach.getClass(), forexSetForexToAttach.getIdforex());
                attachedForexSet.add(forexSetForexToAttach);
            }
            currency.setForexSet(attachedForexSet);
            Set<Forex> attachedForexSet1 = new HashSet<Forex>();
            for (Forex forexSet1ForexToAttach : currency.getForexSet1()) {
                forexSet1ForexToAttach = em.getReference(forexSet1ForexToAttach.getClass(), forexSet1ForexToAttach.getIdforex());
                attachedForexSet1.add(forexSet1ForexToAttach);
            }
            currency.setForexSet1(attachedForexSet1);
            em.persist(currency);
            for (Forex forexSetForex : currency.getForexSet()) {
                Currency oldCurrencytwoOfForexSetForex = forexSetForex.getCurrencytwo();
                forexSetForex.setCurrencytwo(currency);
                forexSetForex = em.merge(forexSetForex);
                if (oldCurrencytwoOfForexSetForex != null) {
                    oldCurrencytwoOfForexSetForex.getForexSet().remove(forexSetForex);
                    oldCurrencytwoOfForexSetForex = em.merge(oldCurrencytwoOfForexSetForex);
                }
            }
            for (Forex forexSet1Forex : currency.getForexSet1()) {
                Currency oldCurrencyoneOfForexSet1Forex = forexSet1Forex.getCurrencyone();
                forexSet1Forex.setCurrencyone(currency);
                forexSet1Forex = em.merge(forexSet1Forex);
                if (oldCurrencyoneOfForexSet1Forex != null) {
                    oldCurrencyoneOfForexSet1Forex.getForexSet1().remove(forexSet1Forex);
                    oldCurrencyoneOfForexSet1Forex = em.merge(oldCurrencyoneOfForexSet1Forex);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Currency currency) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Currency persistentCurrency = em.find(Currency.class, currency.getIdcurrency());
            Set<Forex> forexSetOld = persistentCurrency.getForexSet();
            Set<Forex> forexSetNew = currency.getForexSet();
            Set<Forex> forexSet1Old = persistentCurrency.getForexSet1();
            Set<Forex> forexSet1New = currency.getForexSet1();
            List<String> illegalOrphanMessages = null;
            for (Forex forexSetOldForex : forexSetOld) {
                if (!forexSetNew.contains(forexSetOldForex)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Forex " + forexSetOldForex + " since its currencytwo field is not nullable.");
                }
            }
            for (Forex forexSet1OldForex : forexSet1Old) {
                if (!forexSet1New.contains(forexSet1OldForex)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Forex " + forexSet1OldForex + " since its currencyone field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Set<Forex> attachedForexSetNew = new HashSet<Forex>();
            for (Forex forexSetNewForexToAttach : forexSetNew) {
                forexSetNewForexToAttach = em.getReference(forexSetNewForexToAttach.getClass(), forexSetNewForexToAttach.getIdforex());
                attachedForexSetNew.add(forexSetNewForexToAttach);
            }
            forexSetNew = attachedForexSetNew;
            currency.setForexSet(forexSetNew);
            Set<Forex> attachedForexSet1New = new HashSet<Forex>();
            for (Forex forexSet1NewForexToAttach : forexSet1New) {
                forexSet1NewForexToAttach = em.getReference(forexSet1NewForexToAttach.getClass(), forexSet1NewForexToAttach.getIdforex());
                attachedForexSet1New.add(forexSet1NewForexToAttach);
            }
            forexSet1New = attachedForexSet1New;
            currency.setForexSet1(forexSet1New);
            currency = em.merge(currency);
            for (Forex forexSetNewForex : forexSetNew) {
                if (!forexSetOld.contains(forexSetNewForex)) {
                    Currency oldCurrencytwoOfForexSetNewForex = forexSetNewForex.getCurrencytwo();
                    forexSetNewForex.setCurrencytwo(currency);
                    forexSetNewForex = em.merge(forexSetNewForex);
                    if (oldCurrencytwoOfForexSetNewForex != null && !oldCurrencytwoOfForexSetNewForex.equals(currency)) {
                        oldCurrencytwoOfForexSetNewForex.getForexSet().remove(forexSetNewForex);
                        oldCurrencytwoOfForexSetNewForex = em.merge(oldCurrencytwoOfForexSetNewForex);
                    }
                }
            }
            for (Forex forexSet1NewForex : forexSet1New) {
                if (!forexSet1Old.contains(forexSet1NewForex)) {
                    Currency oldCurrencyoneOfForexSet1NewForex = forexSet1NewForex.getCurrencyone();
                    forexSet1NewForex.setCurrencyone(currency);
                    forexSet1NewForex = em.merge(forexSet1NewForex);
                    if (oldCurrencyoneOfForexSet1NewForex != null && !oldCurrencyoneOfForexSet1NewForex.equals(currency)) {
                        oldCurrencyoneOfForexSet1NewForex.getForexSet1().remove(forexSet1NewForex);
                        oldCurrencyoneOfForexSet1NewForex = em.merge(oldCurrencyoneOfForexSet1NewForex);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = currency.getIdcurrency();
                if (findCurrency(id) == null) {
                    throw new NonexistentEntityException("The currency with id " + id + " no longer exists.");
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
            Currency currency;
            try {
                currency = em.getReference(Currency.class, id);
                currency.getIdcurrency();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The currency with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Set<Forex> forexSetOrphanCheck = currency.getForexSet();
            for (Forex forexSetOrphanCheckForex : forexSetOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Currency (" + currency + ") cannot be destroyed since the Forex " + forexSetOrphanCheckForex + " in its forexSet field has a non-nullable currencytwo field.");
            }
            Set<Forex> forexSet1OrphanCheck = currency.getForexSet1();
            for (Forex forexSet1OrphanCheckForex : forexSet1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Currency (" + currency + ") cannot be destroyed since the Forex " + forexSet1OrphanCheckForex + " in its forexSet1 field has a non-nullable currencyone field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(currency);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Currency> findCurrencyEntities() {
        return findCurrencyEntities(true, -1, -1);
    }

    public List<Currency> findCurrencyEntities(int maxResults, int firstResult) {
        return findCurrencyEntities(false, maxResults, firstResult);
    }

    private List<Currency> findCurrencyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Currency.class));
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

    public Currency findCurrency(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Currency.class, id);
        } finally {
            em.close();
        }
    }

    public int getCurrencyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Currency> rt = cq.from(Currency.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
