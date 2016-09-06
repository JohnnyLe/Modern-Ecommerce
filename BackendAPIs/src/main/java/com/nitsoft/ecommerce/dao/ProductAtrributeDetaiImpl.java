package com.nitsoft.ecommerce.dao;

import com.nitsoft.ecommerce.model.ProductAttributeDetail;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class ProductAtrributeDetaiImpl extends AbstractImpl<ProductAttributeDetail> {

    public List<ProductAttributeDetail> findByProductId(int productId) {
        Session session = null;
        List T = null;
        try {
            session = this.getSession();
            Query query = session.createQuery("FROM ProductAttributeDetail p WHERE p.productAttributeDetailPK.productId = :productId");
            query.setParameter("productId", productId);

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
