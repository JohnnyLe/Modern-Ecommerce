package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "products")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findByCompanyId", query = "SELECT p FROM Product p WHERE p.companyId = :companyId"),
    @NamedQuery(name = "Product.findByRank", query = "SELECT p FROM Product p WHERE p.rank = :rank"),
    @NamedQuery(name = "Product.findByCreatedOn", query = "SELECT p FROM Product p WHERE p.createdOn = :createdOn"),
    @NamedQuery(name = "Product.findByUpdatedOn", query = "SELECT p FROM Product p WHERE p.updatedOn = :updatedOn"),
    @NamedQuery(name = "Product.findBySku", query = "SELECT p FROM Product p WHERE p.sku = :sku"),
    @NamedQuery(name = "Product.findByType", query = "SELECT p FROM Product p WHERE p.type = :type"),
    @NamedQuery(name = "Product.findByHasOptions", query = "SELECT p FROM Product p WHERE p.hasOptions = :hasOptions"),
    @NamedQuery(name = "Product.findByRequiredOptions", query = "SELECT p FROM Product p WHERE p.requiredOptions = :requiredOptions")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "product_id")
    private Integer productId;
    @Basic(optional = false)
    @Column(name = "company_id")
    private int companyId;
    @Basic(optional = false)
    @Column(name = "rank")
    private int rank;
    @Basic(optional = false)
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
    @Basic(optional = false)
    @Column(name = "sku")
    private String sku;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Column(name = "has_options")
    private int hasOptions;
    @Basic(optional = false)
    @Column(name = "required_options")
    private int requiredOptions;

    public Product() {
    }

    public Product(Integer id) {
        this.productId = id;
    }

    public Product(Integer id, int companyId, int rank, Date createdOn, String sku, String type, int hasOptions, int requiredOptions) {
        this.productId = id;
        this.companyId = companyId;
        this.rank = rank;
        this.createdOn = createdOn;
        this.sku = sku;
        this.type = type;
        this.hasOptions = hasOptions;
        this.requiredOptions = requiredOptions;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer id) {
        this.productId = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHasOptions() {
        return hasOptions;
    }

    public void setHasOptions(int hasOptions) {
        this.hasOptions = hasOptions;
    }

    public int getRequiredOptions() {
        return requiredOptions;
    }

    public void setRequiredOptions(int requiredOptions) {
        this.requiredOptions = requiredOptions;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productId != null ? productId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the productId fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productId == null && other.productId != null) || (this.productId != null && !this.productId.equals(other.productId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.Product[ id=" + productId + " ]";
    }

}
