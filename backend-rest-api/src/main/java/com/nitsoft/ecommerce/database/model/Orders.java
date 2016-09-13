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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByUserId", query = "SELECT o FROM Orders o WHERE o.userId = :userId"),
    @NamedQuery(name = "Orders.findByCompanyId", query = "SELECT o FROM Orders o WHERE o.companyId = :companyId"),
    @NamedQuery(name = "Orders.findByCreatedAt", query = "SELECT o FROM Orders o WHERE o.createdAt = :createdAt"),
    @NamedQuery(name = "Orders.findByUpdatedAt", query = "SELECT o FROM Orders o WHERE o.updatedAt = :updatedAt"),
    @NamedQuery(name = "Orders.findByIsActive", query = "SELECT o FROM Orders o WHERE o.isActive = :isActive"),
    @NamedQuery(name = "Orders.findByIsVirtual", query = "SELECT o FROM Orders o WHERE o.isVirtual = :isVirtual"),
    @NamedQuery(name = "Orders.findByIsMultiShipping", query = "SELECT o FROM Orders o WHERE o.isMultiShipping = :isMultiShipping"),
    @NamedQuery(name = "Orders.findByStatus", query = "SELECT o FROM Orders o WHERE o.status = :status"),
    @NamedQuery(name = "Orders.findByItemsCount", query = "SELECT o FROM Orders o WHERE o.itemsCount = :itemsCount"),
    @NamedQuery(name = "Orders.findByItemsQuantity", query = "SELECT o FROM Orders o WHERE o.itemsQuantity = :itemsQuantity"),
    @NamedQuery(name = "Orders.findByGrandTotal", query = "SELECT o FROM Orders o WHERE o.grandTotal = :grandTotal"),
    @NamedQuery(name = "Orders.findByBaseGrandTotal", query = "SELECT o FROM Orders o WHERE o.baseGrandTotal = :baseGrandTotal"),
    @NamedQuery(name = "Orders.findByCheckoutComment", query = "SELECT o FROM Orders o WHERE o.checkoutComment = :checkoutComment"),
    @NamedQuery(name = "Orders.findByCustomerEmail", query = "SELECT o FROM Orders o WHERE o.customerEmail = :customerEmail"),
    @NamedQuery(name = "Orders.findByCustomerPrefix", query = "SELECT o FROM Orders o WHERE o.customerPrefix = :customerPrefix"),
    @NamedQuery(name = "Orders.findByCustomerFirstname", query = "SELECT o FROM Orders o WHERE o.customerFirstname = :customerFirstname"),
    @NamedQuery(name = "Orders.findByCustomerMiddlename", query = "SELECT o FROM Orders o WHERE o.customerMiddlename = :customerMiddlename"),
    @NamedQuery(name = "Orders.findByCustomerLastname", query = "SELECT o FROM Orders o WHERE o.customerLastname = :customerLastname"),
    @NamedQuery(name = "Orders.findByCustomerSuffix", query = "SELECT o FROM Orders o WHERE o.customerSuffix = :customerSuffix"),
    @NamedQuery(name = "Orders.findByCustomerDob", query = "SELECT o FROM Orders o WHERE o.customerDob = :customerDob"),
    @NamedQuery(name = "Orders.findByCustomerIsGuest", query = "SELECT o FROM Orders o WHERE o.customerIsGuest = :customerIsGuest"),
    @NamedQuery(name = "Orders.findByRemoteIp", query = "SELECT o FROM Orders o WHERE o.remoteIp = :remoteIp"),
    @NamedQuery(name = "Orders.findByCustomerGender", query = "SELECT o FROM Orders o WHERE o.customerGender = :customerGender"),
    @NamedQuery(name = "Orders.findBySubtotal", query = "SELECT o FROM Orders o WHERE o.subtotal = :subtotal"),
    @NamedQuery(name = "Orders.findByBaseSubtotal", query = "SELECT o FROM Orders o WHERE o.baseSubtotal = :baseSubtotal"),
    @NamedQuery(name = "Orders.findByIsChanged", query = "SELECT o FROM Orders o WHERE o.isChanged = :isChanged")})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;
    
    @Basic(optional = false)
    @Column(name = "company_id")
    private Long companyId;
    
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    
    @Column(name = "is_active")
    private Short isActive;
    
    @Column(name = "is_virtual")
    private Short isVirtual;
    
    @Column(name = "is_multi_shipping")
    private Short isMultiShipping;
    
    @Basic(optional = false)
    @Column(name = "status")
    private int status;
    
    @Column(name = "items_count")
    private Integer itemsCount;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "items_quantity")
    private BigDecimal itemsQuantity;
    
    @Column(name = "grand_total")
    private BigDecimal grandTotal;
    
    @Column(name = "base_grand_total")
    private BigDecimal baseGrandTotal;
    
    @Column(name = "checkout_comment")
    private String checkoutComment;
    
    @Column(name = "customer_email")
    private String customerEmail;
    
    @Column(name = "customer_prefix")
    private String customerPrefix;
    
    @Column(name = "customer_firstname")
    private String customerFirstname;
    
    @Column(name = "customer_middlename")
    private String customerMiddlename;
    
    @Column(name = "customer_lastname")
    private String customerLastname;
    
    @Column(name = "customer_suffix")
    private String customerSuffix;
    
    @Column(name = "customer_dob")
    @Temporal(TemporalType.TIMESTAMP)
    private Date customerDob;
    
    @Column(name = "customer_is_guest")
    private Short customerIsGuest;
    
    @Column(name = "remote_ip")
    private String remoteIp;
    
    @Column(name = "customer_gender")
    private String customerGender;
    
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    
    @Column(name = "base_subtotal")
    private BigDecimal baseSubtotal;
    
    @Column(name = "is_changed")
    private Integer isChanged;

    public Orders() {
    }

    public Orders(Long id) {
        this.id = id;
    }

    public Orders(Long id, String userId, Long companyId, Date createdAt, Date updatedAt, int status) {
        this.id = id;
        this.userId = userId;
        this.companyId = companyId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public Short getIsActive() {
        return isActive;
    }

    public void setIsActive(Short isActive) {
        this.isActive = isActive;
    }

    public Short getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Short isVirtual) {
        this.isVirtual = isVirtual;
    }

    public Short getIsMultiShipping() {
        return isMultiShipping;
    }

    public void setIsMultiShipping(Short isMultiShipping) {
        this.isMultiShipping = isMultiShipping;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(Integer itemsCount) {
        this.itemsCount = itemsCount;
    }

    public BigDecimal getItemsQuantity() {
        return itemsQuantity;
    }

    public void setItemsQuantity(BigDecimal itemsQuantity) {
        this.itemsQuantity = itemsQuantity;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(BigDecimal grandTotal) {
        this.grandTotal = grandTotal;
    }

    public BigDecimal getBaseGrandTotal() {
        return baseGrandTotal;
    }

    public void setBaseGrandTotal(BigDecimal baseGrandTotal) {
        this.baseGrandTotal = baseGrandTotal;
    }

    public String getCheckoutComment() {
        return checkoutComment;
    }

    public void setCheckoutComment(String checkoutComment) {
        this.checkoutComment = checkoutComment;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPrefix() {
        return customerPrefix;
    }

    public void setCustomerPrefix(String customerPrefix) {
        this.customerPrefix = customerPrefix;
    }

    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public void setCustomerFirstname(String customerFirstname) {
        this.customerFirstname = customerFirstname;
    }

    public String getCustomerMiddlename() {
        return customerMiddlename;
    }

    public void setCustomerMiddlename(String customerMiddlename) {
        this.customerMiddlename = customerMiddlename;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public String getCustomerSuffix() {
        return customerSuffix;
    }

    public void setCustomerSuffix(String customerSuffix) {
        this.customerSuffix = customerSuffix;
    }

    public Date getCustomerDob() {
        return customerDob;
    }

    public void setCustomerDob(Date customerDob) {
        this.customerDob = customerDob;
    }

    public Short getCustomerIsGuest() {
        return customerIsGuest;
    }

    public void setCustomerIsGuest(Short customerIsGuest) {
        this.customerIsGuest = customerIsGuest;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getBaseSubtotal() {
        return baseSubtotal;
    }

    public void setBaseSubtotal(BigDecimal baseSubtotal) {
        this.baseSubtotal = baseSubtotal;
    }

    public Integer getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(Integer isChanged) {
        this.isChanged = isChanged;
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
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.Orders[ id=" + id + " ]";
    }
    
}
