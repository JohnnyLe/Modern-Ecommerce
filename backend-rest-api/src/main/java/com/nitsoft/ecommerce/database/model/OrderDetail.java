/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.database.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VS9 X64Bit
 */
@Entity
@Table(name = "order_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderDetail.findAll", query = "SELECT o FROM OrderDetail o"),
    @NamedQuery(name = "OrderDetail.findById", query = "SELECT o FROM OrderDetail o WHERE o.id = :id"),
    @NamedQuery(name = "OrderDetail.findByParentId", query = "SELECT o FROM OrderDetail o WHERE o.parentId = :parentId"),
    @NamedQuery(name = "OrderDetail.findByOrderId", query = "SELECT o FROM OrderDetail o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "OrderDetail.findByProductId", query = "SELECT o FROM OrderDetail o WHERE o.productId = :productId"),
    @NamedQuery(name = "OrderDetail.findByCreatedAt", query = "SELECT o FROM OrderDetail o WHERE o.createdAt = :createdAt"),
    @NamedQuery(name = "OrderDetail.findByUpdatedAt", query = "SELECT o FROM OrderDetail o WHERE o.updatedAt = :updatedAt"),
    @NamedQuery(name = "OrderDetail.findByIsVirtual", query = "SELECT o FROM OrderDetail o WHERE o.isVirtual = :isVirtual"),
    @NamedQuery(name = "OrderDetail.findBySku", query = "SELECT o FROM OrderDetail o WHERE o.sku = :sku"),
    @NamedQuery(name = "OrderDetail.findByName", query = "SELECT o FROM OrderDetail o WHERE o.name = :name"),
    @NamedQuery(name = "OrderDetail.findByFreeShipping", query = "SELECT o FROM OrderDetail o WHERE o.freeShipping = :freeShipping"),
    @NamedQuery(name = "OrderDetail.findByWeight", query = "SELECT o FROM OrderDetail o WHERE o.weight = :weight"),
    @NamedQuery(name = "OrderDetail.findByQuantity", query = "SELECT o FROM OrderDetail o WHERE o.quantity = :quantity"),
    @NamedQuery(name = "OrderDetail.findByPrice", query = "SELECT o FROM OrderDetail o WHERE o.price = :price"),
    @NamedQuery(name = "OrderDetail.findByBasePrice", query = "SELECT o FROM OrderDetail o WHERE o.basePrice = :basePrice"),
    @NamedQuery(name = "OrderDetail.findByRowTotal", query = "SELECT o FROM OrderDetail o WHERE o.rowTotal = :rowTotal"),
    @NamedQuery(name = "OrderDetail.findByBaseRowTotal", query = "SELECT o FROM OrderDetail o WHERE o.baseRowTotal = :baseRowTotal"),
    @NamedQuery(name = "OrderDetail.findByRowWeight", query = "SELECT o FROM OrderDetail o WHERE o.rowWeight = :rowWeight"),
    @NamedQuery(name = "OrderDetail.findByProductType", query = "SELECT o FROM OrderDetail o WHERE o.productType = :productType")})
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "parent_id")
    private Integer parentId;
    @Basic(optional = false)
    @Column(name = "order_id")
    private int orderId;
    @Basic(optional = false)
    @Column(name = "product_id")
    private int productId;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "is_virtual")
    private Short isVirtual;
    @Column(name = "sku")
    private String sku;
    @Column(name = "name")
    private String name;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "free_shipping")
    private Short freeShipping;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "weight")
    private BigDecimal weight;
    @Column(name = "quantity")
    private BigDecimal quantity;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "base_price")
    private BigDecimal basePrice;
    @Column(name = "row_total")
    private BigDecimal rowTotal;
    @Column(name = "base_row_total")
    private BigDecimal baseRowTotal;
    @Column(name = "row_weight")
    private BigDecimal rowWeight;
    @Column(name = "product_type")
    private String productType;

    public OrderDetail() {
    }

    public OrderDetail(Integer id) {
        this.id = id;
    }

    public OrderDetail(Integer id, int orderId, int productId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public Short getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Short isVirtual) {
        this.isVirtual = isVirtual;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Short getFreeShipping() {
        return freeShipping;
    }

    public void setFreeShipping(Short freeShipping) {
        this.freeShipping = freeShipping;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(BigDecimal rowTotal) {
        this.rowTotal = rowTotal;
    }

    public BigDecimal getBaseRowTotal() {
        return baseRowTotal;
    }

    public void setBaseRowTotal(BigDecimal baseRowTotal) {
        this.baseRowTotal = baseRowTotal;
    }

    public BigDecimal getRowWeight() {
        return rowWeight;
    }

    public void setRowWeight(BigDecimal rowWeight) {
        this.rowWeight = rowWeight;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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
        if (!(object instanceof OrderDetail)) {
            return false;
        }
        OrderDetail other = (OrderDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.OrderDetail[ id=" + id + " ]";
    }
    
}
