package com.nitsoft.ecommerce.repository.specification;

import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.database.model.ProductAttributeDetail;
import com.nitsoft.ecommerce.database.model.ProductCategory;
import com.nitsoft.ecommerce.database.model.User;
import com.nitsoft.util.Constant;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification implements Specification<User> {
    
    private final long companyId;
    private final String searchKey;
    private final int sortCase;
    private final boolean isAscSort;
    private final String userId;

    public UserSpecification(String userId, long companyId, String searchKey, int sortCase, boolean isAscSort) {
        this.companyId = companyId;
        this.searchKey = searchKey;
        this.sortCase = sortCase;
        this.isAscSort = isAscSort;
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new LinkedList<>();

        // filter by company id
        if (companyId != -1) {
            predicates.add(cb.equal(root.get("companyId"), companyId));
        }

        // filter by product name [search key]
        if (searchKey != null && !searchKey.trim().isEmpty()) {
            String wrapSearch = "%" + searchKey.trim() + "%";
            Predicate firstName = cb.like(root.<String>get("firstName"), wrapSearch);
            Predicate lastName = cb.like(root.<String>get("lastName"), wrapSearch);
            Predicate email = cb.like(root.<String>get("email"), wrapSearch);
            Predicate search = cb.or(firstName, lastName, email);
            predicates.add(search);
        }
        predicates.add(cb.notEqual(root.get("userId"), userId));
        predicates.add(cb.equal(root.get("status"), Constant.USER_STATUS.ACTIVE.getStatus()));
        
        // sort
        Path orderClause;
        switch (sortCase) {
            case Constant.SORT_BY_FIRST_NAME:
                orderClause = root.get("firstName");
                break;
            case Constant.SORT_BY_LAST_NAME:
                orderClause = root.get("lastName");
                break;
            case Constant.SORT_BY_EMAIL_ADDRESS:
                orderClause = root.get("email");
                break;
            case Constant.SORT_BY_CREATE_DATE:
                orderClause = root.get("createDate");
                break;
            default: // sort by product name
                orderClause = root.get("firstName");
                break;
        }

        if (isAscSort) {
            cq.orderBy(cb.asc(orderClause));
        } else {
            cq.orderBy(cb.desc(orderClause));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
