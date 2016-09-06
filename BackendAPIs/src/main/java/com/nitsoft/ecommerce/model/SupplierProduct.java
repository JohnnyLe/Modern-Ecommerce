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
@Table(name = "supplier_products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupplierProduct.findAll", query = "SELECT s FROM SupplierProduct s"),
    @NamedQuery(name = "SupplierProduct.findByProductId", query = "SELECT s FROM SupplierProduct s WHERE s.supplierProductPK.productId = :productId"),
    @NamedQuery(name = "SupplierProduct.findBySupplierId", query = "SELECT s FROM SupplierProduct s WHERE s.supplierProductPK.supplierId = :supplierId")})
public class SupplierProduct implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SupplierProductPK supplierProductPK;

    public SupplierProduct() {
    }

    public SupplierProduct(SupplierProductPK supplierProductPK) {
        this.supplierProductPK = supplierProductPK;
    }

    public SupplierProduct(int productId, int supplierId) {
        this.supplierProductPK = new SupplierProductPK(productId, supplierId);
    }

    public SupplierProductPK getSupplierProductPK() {
        return supplierProductPK;
    }

    public void setSupplierProductPK(SupplierProductPK supplierProductPK) {
        this.supplierProductPK = supplierProductPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierProductPK != null ? supplierProductPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupplierProduct)) {
            return false;
        }
        SupplierProduct other = (SupplierProduct) object;
        if ((this.supplierProductPK == null && other.supplierProductPK != null) || (this.supplierProductPK != null && !this.supplierProductPK.equals(other.supplierProductPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.SupplierProduct[ supplierProductPK=" + supplierProductPK + " ]";
    }
    
}
