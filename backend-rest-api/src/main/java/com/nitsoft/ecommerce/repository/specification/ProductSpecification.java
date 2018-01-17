package com.nitsoft.ecommerce.repository.specification;

import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.database.model.ProductAttributeDetail;
import com.nitsoft.ecommerce.database.model.ProductCategory;
import com.nitsoft.util.Constant;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification implements Specification<Product> {

    private final long companyId;
    private final long categoryId;
    private final long attributeId;
    private final String searchKey;
    private double minPrice;
    private final double maxPrice;
    private int minRank;
    private final int maxRank;
    private final int sortCase;
    private final boolean isAscSort;

    public ProductSpecification(long companyId, long categoryId, long attributeId, String searchKey, double minPrice, double maxPrice, int minRank, int maxRank, int sortCase, boolean isAscSort) {
        this.companyId = companyId;
        this.categoryId = categoryId;
        this.attributeId = attributeId;
        this.searchKey = searchKey;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minRank = minRank;
        this.maxRank = maxRank;
        this.sortCase = sortCase;
        this.isAscSort = isAscSort;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        List<Predicate> predicates = new LinkedList<>();
        //filter by sattus
        predicates.add(cb.equal(root.get("status"), Constant.STATUS.ACTIVE_STATUS.getValue()));
        // filter by company id
        if (companyId != -1) {
            predicates.add(cb.equal(root.get("companyId"), companyId));
        }
        // filter by category id
        if (categoryId != -1) {
            Root<ProductCategory> proCatRoot = cq.from(ProductCategory.class);
            predicates.add(cb.equal(proCatRoot.get("id").get("categoryId"), categoryId));
            predicates.add(cb.equal(root.get("productId"), proCatRoot.get("id").get("productId")));
        }
        // filter by product attribute id
        if (attributeId != -1) {
            Root<ProductAttributeDetail> padRoot = cq.from(ProductAttributeDetail.class);
            predicates.add(cb.equal(padRoot.get("attributeId"), attributeId));
            predicates.add(cb.equal(root.get("productId"), padRoot.get("productId")));
        }
        // filter by product name [search key]
        if (searchKey != null && !searchKey.trim().isEmpty()) {
            predicates.add(cb.like(root.<String>get("name"), "%" + searchKey.trim() + "%"));
        }
        // validate price range
        if (minPrice < 0) {
            minPrice = 0;
        }

        if (minPrice < maxPrice) {
            // minPrice <= price <= maxPrice
            predicates.add(cb.between(root.<Double>get("salePrice"), minPrice, maxPrice));
        } else if (minPrice > 0) {
            if (maxPrice == -1) {
                // price >= minPrice
                predicates.add(cb.greaterThanOrEqualTo(root.<Double>get("salePrice"), minPrice));
            } else if (minPrice == maxPrice) {
                // price == minPrice
                predicates.add(cb.equal(root.get("salePrice"), minPrice));
            }
        }
        // validate rank
        if (minRank < 0) {
            minRank = 0;
        }

        if (minRank < maxRank) {
            predicates.add(cb.between(root.<Integer>get("rank"), minRank, maxRank));
        } else if (minRank > 0) {
            if (maxRank == -1) {
                predicates.add(cb.greaterThanOrEqualTo(root.<Integer>get("rank"), minRank));
            } else if (minRank == maxRank) {
                predicates.add(cb.equal(root.get("rank"), minRank));
            }
        }
        // sort
        Path orderClause;
        switch (sortCase) {
            case Constant.SORT_BY_PRODUCT_NAME:
                orderClause = root.get("name");
                break;
            case Constant.SORT_BY_PRODUCT_PRICE:
                orderClause = root.get("salePrice");
                break;
            case Constant.SORT_BY_PRODUCT_QUANTITY:
                orderClause = root.get("quantity");
                break;
            case Constant.SORT_BY_PRODUCT_CREATE_DATE:
                orderClause = root.get("createdOn");
                break;
            default: // sort by product name
                orderClause = root.get("createdOn");
        }

        if (isAscSort) {
            cq.orderBy(cb.asc(orderClause));
        } else {
            cq.orderBy(cb.desc(orderClause));
        }

        return cb.and(predicates.toArray(new Predicate[]{}));
    }

}
