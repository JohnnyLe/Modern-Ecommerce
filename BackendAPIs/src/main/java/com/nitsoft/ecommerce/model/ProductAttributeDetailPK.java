/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author VS9 X64Bit
 */
@Embeddable
public class ProductAttributeDetailPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "product_id")
    private int productId;
    @Basic(optional = false)
    @Column(name = "attribute_id")
    private int attributeId;

    public ProductAttributeDetailPK() {
    }

    public ProductAttributeDetailPK(int productId, int attributeId) {
        this.productId = productId;
        this.attributeId = attributeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
        this.attributeId = attributeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productId;
        hash += (int) attributeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductAttributeDetailPK)) {
            return false;
        }
        ProductAttributeDetailPK other = (ProductAttributeDetailPK) object;
        if (this.productId != other.productId) {
            return false;
        }
        if (this.attributeId != other.attributeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.ProductAttributeDetailPK[ productId=" + productId + ", attributeId=" + attributeId + " ]";
    }
    
}
