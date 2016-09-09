package com.nitsoft.ecommerce.database.model;

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
@Table(name = "order_addresses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderAddress.findAll", query = "SELECT o FROM OrderAddress o"),
    @NamedQuery(name = "OrderAddress.findById", query = "SELECT o FROM OrderAddress o WHERE o.id = :id"),
    @NamedQuery(name = "OrderAddress.findByOrderId", query = "SELECT o FROM OrderAddress o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "OrderAddress.findByAdressId", query = "SELECT o FROM OrderAddress o WHERE o.adressId = :adressId"),
    @NamedQuery(name = "OrderAddress.findByCreatedAt", query = "SELECT o FROM OrderAddress o WHERE o.createdAt = :createdAt"),
    @NamedQuery(name = "OrderAddress.findByUpdatedAt", query = "SELECT o FROM OrderAddress o WHERE o.updatedAt = :updatedAt"),
    @NamedQuery(name = "OrderAddress.findByRegionId", query = "SELECT o FROM OrderAddress o WHERE o.regionId = :regionId"),
    @NamedQuery(name = "OrderAddress.findByRegion", query = "SELECT o FROM OrderAddress o WHERE o.region = :region"),
    @NamedQuery(name = "OrderAddress.findByPostcode", query = "SELECT o FROM OrderAddress o WHERE o.postcode = :postcode"),
    @NamedQuery(name = "OrderAddress.findByPrefix", query = "SELECT o FROM OrderAddress o WHERE o.prefix = :prefix"),
    @NamedQuery(name = "OrderAddress.findBySuffix", query = "SELECT o FROM OrderAddress o WHERE o.suffix = :suffix")})
public class OrderAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "order_id")
    private int orderId;
    
    @Basic(optional = false)
    @Column(name = "adress_id")
    private int adressId;
    
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(name = "region_id")
    private Integer regionId;
    
    @Column(name = "region")
    private String region;
    
    @Column(name = "postcode")
    private String postcode;
    
    @Column(name = "prefix")
    private String prefix;
    
    @Column(name = "suffix")
    private String suffix;

    public OrderAddress() {
    }

    public OrderAddress(Integer id) {
        this.id = id;
    }

    public OrderAddress(Integer id, int orderId, int adressId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.adressId = adressId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getAdressId() {
        return adressId;
    }

    public void setAdressId(int adressId) {
        this.adressId = adressId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderAddress)) {
            return false;
        }
        OrderAddress other = (OrderAddress) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.OrderAddress[ id=" + id + " ]";
    }
    
}
