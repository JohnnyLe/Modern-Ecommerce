/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Column;
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
@Table(name = "product_attribute_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductAttributeDetail.findAll", query = "SELECT p FROM ProductAttributeDetail p"),
    @NamedQuery(name = "ProductAttributeDetail.findByProductId", query = "SELECT p FROM ProductAttributeDetail p WHERE p.productAttributeDetailPK.productId = :productId"),
    @NamedQuery(name = "ProductAttributeDetail.findByAttributeId", query = "SELECT p FROM ProductAttributeDetail p WHERE p.productAttributeDetailPK.attributeId = :attributeId"),
    @NamedQuery(name = "ProductAttributeDetail.findByValueString", query = "SELECT p FROM ProductAttributeDetail p WHERE p.valueString = :valueString"),
    @NamedQuery(name = "ProductAttributeDetail.findByValueNumberic", query = "SELECT p FROM ProductAttributeDetail p WHERE p.valueNumberic = :valueNumberic")})
public class ProductAttributeDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductAttributeDetailPK productAttributeDetailPK;
    @Column(name = "value_string")
    private String valueString;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value_numberic")
    private Double valueNumberic;

    public ProductAttributeDetail() {
    }

    public ProductAttributeDetail(ProductAttributeDetailPK productAttributeDetailPK) {
        this.productAttributeDetailPK = productAttributeDetailPK;
    }

    public ProductAttributeDetail(int productId, int attributeId) {
        this.productAttributeDetailPK = new ProductAttributeDetailPK(productId, attributeId);
    }

    public ProductAttributeDetailPK getProductAttributeDetailPK() {
        return productAttributeDetailPK;
    }

    public void setProductAttributeDetailPK(ProductAttributeDetailPK productAttributeDetailPK) {
        this.productAttributeDetailPK = productAttributeDetailPK;
    }

    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public Double getValueNumberic() {
        return valueNumberic;
    }

    public void setValueNumberic(Double valueNumberic) {
        this.valueNumberic = valueNumberic;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productAttributeDetailPK != null ? productAttributeDetailPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductAttributeDetail)) {
            return false;
        }
        ProductAttributeDetail other = (ProductAttributeDetail) object;
        if ((this.productAttributeDetailPK == null && other.productAttributeDetailPK != null) || (this.productAttributeDetailPK != null && !this.productAttributeDetailPK.equals(other.productAttributeDetailPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.ProductAttributeDetail[ productAttributeDetailPK=" + productAttributeDetailPK + " ]";
    }
    
}
