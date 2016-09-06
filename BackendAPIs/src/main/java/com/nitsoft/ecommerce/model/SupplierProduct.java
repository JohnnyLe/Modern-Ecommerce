package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "supplier_products")
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

}
