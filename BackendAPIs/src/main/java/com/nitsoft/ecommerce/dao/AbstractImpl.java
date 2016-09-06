/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.dao;

import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import com.nitsoft.manager.HibernateSessionManager;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

/**
 *
 * @param <T>
 */
@Component
public abstract class AbstractImpl<T> {

    protected Session getSession() {
        return HibernateSessionManager.getSessionFactory().openSession();
    }

    public void save(T entity) {
        Session hibernateSession = this.getSession();
        Transaction trans = hibernateSession.beginTransaction();
        try {
            hibernateSession.saveOrUpdate(entity);
            trans.commit();
        } catch (HibernateException e) {
            trans.rollback();
            EventLogManager.getInstance().error(e);
        } finally {
            hibernateSession.close();
        }
    }
    
    public void save(List<T> entites) {
        Session hibernateSession = this.getSession();
        Transaction trans = hibernateSession.beginTransaction();
        try {
            for (T entity : entites) {
                hibernateSession.saveOrUpdate(entity);
            }
            trans.commit();
        } catch (HibernateException e) {
            System.out.print(e);
            trans.rollback();
            EventLogManager.getInstance().error(e);
        } finally {
            hibernateSession.close();
        }
    }

    public void merge(T entity) {
        Session hibernateSession = this.getSession();
        hibernateSession.merge(entity);
    }

    public void delete(T entity) {
        Session hibernateSession = this.getSession();
        Transaction trans = hibernateSession.beginTransaction();
        try {
            hibernateSession.delete(entity);
            trans.commit();
        } catch (HibernateException e) {
            // We should rollback
            trans.rollback();
        } finally {
            hibernateSession.close();
        }
    }
    
    public T findByID(Class clazz, int id) {
        Session hibernateSession = this.getSession();
        T t = null;
        try {
            t = (T) hibernateSession.get(clazz, id);
        } catch (HibernateException e) {
            EventLogManager.getInstance().log(e.getMessage());
        } finally {
            hibernateSession.close();
        }
        return t;
    }
    
    public T findByID(Class clazz, String id) {
        Session hibernateSession = this.getSession();
        T t = null;
        try {
            t = (T) hibernateSession.get(clazz, id);
        } catch (HibernateException e) {
            EventLogManager.getInstance().log(e.getMessage());
        } finally {
            hibernateSession.close();
        }
        return t;
    }

    public List findAll(Class clazz) {
        Session session = null;
        List T = null;
        try {
            session = this.getSession();
            Query query = session.createQuery("from " + clazz.getName());
            T = query.list();
        } catch (HibernateException e) {
            EventLogManager.getInstance().log(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return T;
    }

    /**
     *
     * @param clazz
     * @param proName
     * @param proValue
     * @param status
     * @return
     */
    public List findAll(Class clazz, String proName, String proValue, int status) {
        Session session = null;
        List T = null;
        try {
            session = this.getSession();
            Query query = session.createQuery("from " + clazz.getName() + " where lower(" + proName + ") =:prop and status=:status");
            query.setParameter("prop", proValue);
            query.setParameter("status", status);
            T = query.list();
        } catch (HibernateException e) {
            EventLogManager.getInstance().log(e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return T;
    }
}
