package com.nitsoft.ecommerce.database.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "product_attribute_details")
@IdClass(ProductAttributeDetailPK.class)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductAttributeDetail.findAll", query = "SELECT p FROM ProductAttributeDetail p"),
    @NamedQuery(name = "ProductAttributeDetail.findByProductId", query = "SELECT p FROM ProductAttributeDetail p WHERE p.productId = :productId"),
    @NamedQuery(name = "ProductAttributeDetail.findByAttributeId", query = "SELECT p FROM ProductAttributeDetail p WHERE p.attributeId = :attributeId"),
    @NamedQuery(name = "ProductAttributeDetail.findByValueString", query = "SELECT p FROM ProductAttributeDetail p WHERE p.valueString = :valueString"),
    @NamedQuery(name = "ProductAttributeDetail.findByValueNumberic", query = "SELECT p FROM ProductAttributeDetail p WHERE p.valueNumberic = :valueNumberic")})
public class ProductAttributeDetail implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "product_id")
    private Long productId;

    @Id
    @Basic(optional = false)
    @Column(name = "attribute_id")
    private Long attributeId;

    @Column(name = "value_string")
    private String valueString;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value_numberic")
    private Double valueNumberic;

    public ProductAttributeDetail() {
    }

    public ProductAttributeDetail(long productId, long attributeId) {
        this.productId = productId;
        this.attributeId = attributeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Long attributeId) {
        this.attributeId = attributeId;
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

}
