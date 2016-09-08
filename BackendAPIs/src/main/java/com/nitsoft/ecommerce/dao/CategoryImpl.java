/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.dao;

import com.nitsoft.ecommerce.model.Session;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.nitsoft.ecommerce.config.AppConfig;
import com.nitsoft.ecommerce.model.Category;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import com.nitsoft.util.Constant;
import java.util.ArrayList;
import java.util.List;
import static org.hibernate.criterion.Expression.sql;

@Component
public class CategoryImpl extends AbstractImpl<Session> {
    
    @Autowired  AppConfig appConfig;
    
    public Session findByCategory (String categoryId) {
        org.hibernate.Session session = this.getSession();
        try {
            Query query;
            List<Session> l = new ArrayList<Session>();
            query = session.createQuery("from Session where userId = :userId").setParameter("userId", categoryId);
            l = query.list();
            Session s = (l.size() > 0) ? l.get(0) : null;
            return s;
        }
        catch(HibernateException e) {
            EventLogManager.getInstance().error(e);
            return null;
        }
        finally {
            session.close();
        }
        
    }

    public boolean createCategory(Session session) {
        try {
            this.save(session);
            return true;
        } catch (Exception e) {
            EventLogManager.getInstance().error(e);
            return false;
        }
    }
    
    public List<Category> doSearchSortUser(String categoryId, int pageSize, int pageNumber) {
        org.hibernate.Session session = this.getSession();
        List<Category> listUser = null;
        try {
            Query query = session.createQuery("from Session where categoryId = :categoryId").setParameter("category", categoryId);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);
            listUser = query.list();
            return listUser;
        } catch (HibernateException e) {
            EventLogManager.getInstance().error(e);
            return null;
        } finally {
            session.close();
        }
    }
}
