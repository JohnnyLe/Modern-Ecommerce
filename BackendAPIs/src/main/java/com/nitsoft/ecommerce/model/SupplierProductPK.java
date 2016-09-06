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
public class SupplierProductPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "product_id")
    private int productId;
    @Basic(optional = false)
    @Column(name = "supplier_id")
    private int supplierId;

    public SupplierProductPK() {
    }

    public SupplierProductPK(int productId, int supplierId) {
        this.productId = productId;
        this.supplierId = supplierId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productId;
        hash += (int) supplierId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupplierProductPK)) {
            return false;
        }
        SupplierProductPK other = (SupplierProductPK) object;
        if (this.productId != other.productId) {
            return false;
        }
        if (this.supplierId != other.supplierId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.SupplierProductPK[ productId=" + productId + ", supplierId=" + supplierId + " ]";
    }
    
}
