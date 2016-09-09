package com.nitsoft.ecommerce.dao;

import com.nitsoft.ecommerce.model.Product;
import com.nitsoft.ecommerce.model.ProductCategory;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import com.nitsoft.util.Constant;
import java.util.HashMap;
import java.util.Iterator;
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
            Query query = session.createQuery("SELECT p FROM " + Product.class.getName() + " p, " + ProductCategory.class.getName() + " pc WHERE pc.categoryId = :categoryId AND pc.productId = p.productId");
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

    public List<Product> findAllByCompanyId(int companyId) {
        Session session = null;
        List T = null;
        try {
            session = this.getSession();
            Query query = session.createQuery("SELECT p FROM " + Product.class.getName() + " p WHERE p.companyId = :companyId");
            query.setParameter("companyId", companyId);

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

    public List<Product> findAllByCompanyIdAndCategoryId(int companyId, int categoryId) {
        Session session = null;
        List T = null;
        try {
            session = this.getSession();
            Query query = session.createQuery("SELECT p FROM " + Product.class.getName() + " p, " + ProductCategory.class.getName() + " pc WHERE p.companyId = :companyId AND p.productId = pc.productId AND pc.categoryId = :categoryId");
            query.setParameter("companyId", companyId);
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

    public List<Product> doFilterSearchSortPagingProduct(int companyId, int categoryId, int attributeId, String searchKey, double minPrice, double maxPrice, int sortCase, boolean isAscSort, int pageSize, int pageNumber) {
        // init sql query parameter mapping
        HashMap<String, Object> queryParamMapper = new HashMap<>();

        // init sql query
        StringBuilder selectClause = new StringBuilder("SELECT p FROM Product p");
        StringBuilder whereClause = new StringBuilder(" WHERE ");
        boolean isConditionExisted = false;

        // filter by company id
        if (companyId != -1) {
            isConditionExisted = addCondition(whereClause, "p.companyId = :companyId", isConditionExisted);
            queryParamMapper.put("companyId", companyId);
        }

        // filter by category id
        if (categoryId != -1) {
            selectClause.append(", ProductCategory pc");
            isConditionExisted = addCondition(whereClause, "pc.categoryId = :categoryId AND pc.productId = p.productId", isConditionExisted);
            queryParamMapper.put("categoryId", categoryId);
        }

        // filter by product attribute id
        if (attributeId != -1) {
            selectClause.append(", ProductAttributeDetail pad");
            isConditionExisted = addCondition(whereClause, "pad.attributeId = :attributeId AND pad.productId = p.productId", isConditionExisted);
            queryParamMapper.put("attributeId", attributeId);
        }

        // filter by product name [search key]
        if (searchKey != null && !searchKey.trim().isEmpty()) {
            addCondition(whereClause, "p.name LIKE :searchName", isConditionExisted);
            queryParamMapper.put("searchName", "%" + searchKey.trim() + "%");
        }

        // validate price range
        if (minPrice < 0) {
            minPrice = 0;
        }

        if (minPrice < maxPrice) {
            whereClause.append("AND (p.salePrice BETWEEN :minPrice AND :maxPrice) ");
            queryParamMapper.put("minPrice", minPrice);
            queryParamMapper.put("maxPrice", maxPrice);
        } else if (minPrice == maxPrice) {
            whereClause.append("AND p.salePrice = :minPrice ");
            queryParamMapper.put("minPrice", minPrice);
        }

        // sort
        switch (sortCase) {
            case Constant.SORT_BY_PRODUCT_PRICE:
                whereClause.append("ORDER BY p.salePrice ");
                break;
            default: // sort by product name
                whereClause.append("ORDER BY p.name ");
        }

        // sort type
        whereClause.append(isAscSort ? "ASC" : "DESC");

        // query sql
        Session session = null;
        List T = null;
        try {
            session = this.getSession();

            Query query = session.createQuery(selectClause.append(whereClause).toString());

            // set parameter
            Iterator<String> paramKey = queryParamMapper.keySet().iterator();
            String key;
            while (paramKey.hasNext()) {
                key = paramKey.next();
                query.setParameter(key, queryParamMapper.get(key));
            }

            // paging results
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);

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

    private boolean addCondition(StringBuilder whereClause, String condition, boolean isConditionExisted) {
        if (whereClause.length() == 0) {
            whereClause.append(" WHERE ");
        } else if (isConditionExisted) {
            whereClause.append("AND ").append(condition).append(" ");
        } else {
            whereClause.append(condition).append(" ");
            isConditionExisted = true;
        }

        return isConditionExisted;
    }
}
