package com.nitsoft.ecommerce.dao;

import com.nitsoft.ecommerce.model.Category;
import com.nitsoft.ecommerce.model.Product;
import com.nitsoft.ecommerce.model.ProductCategory;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class ProductImpl extends AbstractImpl<Product> {

    public List<Product> findAllByCategoryId(int categoryId) {
        Session session = null;
        List T = null;
        try {
            session = this.getSession();
            Query query = session.createQuery("SELECT p FROM " + Product.class.getName() + " p, " + Category.class.getName() + " c, " + ProductCategory.class.getName() + " pc WHERE pc.categoryId = :categoryId AND pc.categoryId = c.categoryId AND pc.productId = p.productId");
            query.setParameter("categoryId", categoryId);

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
