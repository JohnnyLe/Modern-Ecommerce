package com.nitsoft.ecommerce.database.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Table(name = "users")
@XmlRootElement
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;
    
    @Basic(optional = false)
    @Column(name = "company_id")
    private Long companyId;
    
    @Basic(optional = false)
    @Column(name = "role_id")
    private int roleId;
    
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Basic(optional = false)
    @Column(name = "password_hash")
    private String passwordHash;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Basic(optional = false)
    @Column(name = "status")
    private int status;
    
    @JsonIgnore
    @Basic(optional = false)
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    
    @JsonIgnore
    @Basic(optional = false)
    @Column(name = "salt")
    private String salt;
    
}
