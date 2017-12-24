package com.nitsoft.ecommerce.repository.specification;

import com.nitsoft.ecommerce.database.model.Category;
import com.nitsoft.util.Constant;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

@Component
public class CategorySpecifications {

    public Specification<Category> doFilterSearchSort(long companyId, String keyword, final int sortKey) {
        return (Root<Category> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {

            List<Predicate> predicates = new LinkedList<>();

            // search condition

            predicates.add(cb.equal(root.<Long>get("companyId"), companyId));
            predicates.add(cb.equal(root.<Integer>get("status"), Constant.STATUS.ACTIVE_STATUS.getValue()));

            if (keyword != null && !"".equals(keyword)) {
                predicates.add(cb.like(root.<String>get("name"), "%" + keyword + "%"));
            }

            // sort
            // Create orderClause from sort column
            Path orderClause;
            Order order;
            switch (sortKey) {
                case 1:
                    orderClause = root.get("name");
                    order = cb.asc(orderClause);
                    break;
                case -1:
                    orderClause = root.get("name");
                    order = cb.desc(orderClause);
                    break;
                default:
                    orderClause = root.get("name");
                    order = cb.asc(orderClause);
                    break;
            }

            cq.orderBy(order);

            // return the create query
            return cb.and(predicates.toArray(new Predicate[]{}));
        };
    }

}
