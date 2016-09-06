/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.dao;

import com.nitsoft.ecommerce.model.Session;
import com.nitsoft.ecommerce.model.User;
import com.nitsoft.util.Constant;
import com.nitsoft.util.DateUtil;
import java.util.Date;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.nitsoft.ecommerce.config.AppConfig;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserSessionImpl extends AbstractImpl<Session> {
    
    @Autowired  AppConfig appConfig;
    
    public Session findByUser (String userId) {
        org.hibernate.Session session = this.getSession();
        try {
            Query query;
            List<Session> l = new ArrayList<Session>();
            query = session.createQuery("from Session where userId = :userId").setParameter("userId", userId);
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

    public boolean createUserSession(Session session) {
        try {
            this.save(session);
            return true;
        } catch (Exception e) {
            EventLogManager.getInstance().error(e);
            return false;
        }
    }

}
