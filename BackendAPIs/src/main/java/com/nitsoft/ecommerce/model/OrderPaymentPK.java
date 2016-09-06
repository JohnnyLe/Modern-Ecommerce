/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author VS9 X64Bit
 */
@Embeddable
public class OrderPaymentPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "order_id")
    private int orderId;
    @Basic(optional = false)
    @Column(name = "payment_id")
    private int paymentId;

    public OrderPaymentPK() {
    }

    public OrderPaymentPK(int orderId, int paymentId) {
        this.orderId = orderId;
        this.paymentId = paymentId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderId;
        hash += (int) paymentId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderPaymentPK)) {
            return false;
        }
        OrderPaymentPK other = (OrderPaymentPK) object;
        if (this.orderId != other.orderId) {
            return false;
        }
        if (this.paymentId != other.paymentId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.OrderPaymentPK[ orderId=" + orderId + ", paymentId=" + paymentId + " ]";
    }
    
}
