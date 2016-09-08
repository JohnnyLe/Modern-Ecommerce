/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.database.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "product_attributes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductAttribute.findAll", query = "SELECT p FROM ProductAttribute p"),
    @NamedQuery(name = "ProductAttribute.findByAttributeId", query = "SELECT p FROM ProductAttribute p WHERE p.attributeId = :attributeId"),
    @NamedQuery(name = "ProductAttribute.findByCompanyId", query = "SELECT p FROM ProductAttribute p WHERE p.companyId = :companyId"),
    @NamedQuery(name = "ProductAttribute.findByName", query = "SELECT p FROM ProductAttribute p WHERE p.name = :name")})
public class ProductAttribute implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "attribute_id")
    private Long attributeId;
    @Basic(optional = false)
    @Column(name = "company_id")
    private int companyId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public ProductAttribute() {
    }

    public ProductAttribute(Long attributeId) {
        this.attributeId = attributeId;
    }

    public ProductAttribute(Long attributeId, int companyId, String name) {
        this.attributeId = attributeId;
        this.companyId = companyId;
        this.name = name;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attributeId != null ? attributeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductAttribute)) {
            return false;
        }
        ProductAttribute other = (ProductAttribute) object;
        if ((this.attributeId == null && other.attributeId != null) || (this.attributeId != null && !this.attributeId.equals(other.attributeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.ProductAttribute[ attributeId=" + attributeId + " ]";
    }

}
