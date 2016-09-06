/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VS9 X64Bit
 */
@Entity
@Table(name = "product_categories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductCategory.findAll", query = "SELECT p FROM ProductCategory p"),
    @NamedQuery(name = "ProductCategory.findByProductId", query = "SELECT p FROM ProductCategory p WHERE p.productCategoryPK.productId = :productId"),
    @NamedQuery(name = "ProductCategory.findByCategoryId", query = "SELECT p FROM ProductCategory p WHERE p.productCategoryPK.categoryId = :categoryId")})
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductCategoryPK productCategoryPK;

    public ProductCategory() {
    }

    public ProductCategory(ProductCategoryPK productCategoryPK) {
        this.productCategoryPK = productCategoryPK;
    }

    public ProductCategory(int productId, int categoryId) {
        this.productCategoryPK = new ProductCategoryPK(productId, categoryId);
    }

    public ProductCategoryPK getProductCategoryPK() {
        return productCategoryPK;
    }

    public void setProductCategoryPK(ProductCategoryPK productCategoryPK) {
        this.productCategoryPK = productCategoryPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productCategoryPK != null ? productCategoryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductCategory)) {
            return false;
        }
        ProductCategory other = (ProductCategory) object;
        if ((this.productCategoryPK == null && other.productCategoryPK != null) || (this.productCategoryPK != null && !this.productCategoryPK.equals(other.productCategoryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.ProductCategory[ productCategoryPK=" + productCategoryPK + " ]";
    }
    
}
