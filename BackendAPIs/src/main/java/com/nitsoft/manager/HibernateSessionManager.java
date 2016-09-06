/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.manager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionManager {

    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            sessionFactory = new Configuration().configure("/hibernate.cfg.xml").buildSessionFactory();
            return sessionFactory;
        } catch (Throwable ex) {
            System.err.println("Session Factory Initialization error" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdownConnection() {
        getSessionFactory().close();
    }
}
