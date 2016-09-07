/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    @NamedQuery(name = "SupplierProduct.findByProductId", query = "SELECT s FROM SupplierProduct s WHERE s.productId = :productId"),
    @NamedQuery(name = "SupplierProduct.findBySupplierId", query = "SELECT s FROM SupplierProduct s WHERE s.supplierId = :supplierId")})
public class SupplierProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "product_id")
    private int productId;

    @Id
    @Basic(optional = false)
    @Column(name = "supplier_id")
    private int supplierId;

    public SupplierProduct() {
    }

    public SupplierProduct(int productId, int supplierId) {
        this.productId
                = productId;
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

}
