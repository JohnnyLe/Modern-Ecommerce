package com.nitsoft.ecommerce.database.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Table(name = "orders")
@XmlRootElement
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
    @Column(name = "adress_id")
    private Long adressId;
    
    @Basic(optional = false)
    @Column(name = "payment_id")
    private Long paymentId;
    
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
    
}
