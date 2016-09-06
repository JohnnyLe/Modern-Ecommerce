package com.nitsoft.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_attribute_details")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ProductAttributeDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "product_id")
    @JsonIgnore
    private int productId;

    @Id
    @Basic(optional = false)
    @Column(name = "attribute_id")
    private int attributeId;

    @Column(name = "value_string")
    private String valueString;

    @Column(name = "value_numberic")
    private Double valueNumberic;

    public ProductAttributeDetail() {
    }

    public ProductAttributeDetail(int productId, int attributeId) {
        this.productId = productId;
        this.attributeId = attributeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(int attributeId) {
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
