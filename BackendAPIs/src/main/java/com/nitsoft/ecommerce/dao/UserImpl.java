/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.dao;

import com.nitsoft.ecommerce.model.User;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class UserImpl extends AbstractImpl<User>{
    public List<User> getUser() {
        Session session = this.getSession();
        List<User> listUser = new ArrayList<User>();
        try {
            String sql = "from " + User.class.getName();
            Query query = session.createQuery(sql);
            listUser = query.list();
            return listUser;
        } catch (Exception e) {
//            EventLogManager.getInstance().error(e);
e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}
