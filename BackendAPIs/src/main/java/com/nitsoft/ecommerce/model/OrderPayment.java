/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author VS9 X64Bit
 */
@Entity
@Table(name = "order_payments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderPayment.findAll", query = "SELECT o FROM OrderPayment o"),
    @NamedQuery(name = "OrderPayment.findByOrderId", query = "SELECT o FROM OrderPayment o WHERE o.orderPaymentPK.orderId = :orderId"),
    @NamedQuery(name = "OrderPayment.findByPaymentId", query = "SELECT o FROM OrderPayment o WHERE o.orderPaymentPK.paymentId = :paymentId"),
    @NamedQuery(name = "OrderPayment.findByTransactionId", query = "SELECT o FROM OrderPayment o WHERE o.transactionId = :transactionId"),
    @NamedQuery(name = "OrderPayment.findByStatus", query = "SELECT o FROM OrderPayment o WHERE o.status = :status")})
public class OrderPayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderPaymentPK orderPaymentPK;
    @Basic(optional = false)
    @Column(name = "transaction_id")
    private String transactionId;
    @Basic(optional = false)
    @Column(name = "status")
    private int status;

    public OrderPayment() {
    }

    public OrderPayment(OrderPaymentPK orderPaymentPK) {
        this.orderPaymentPK = orderPaymentPK;
    }

    public OrderPayment(OrderPaymentPK orderPaymentPK, String transactionId, int status) {
        this.orderPaymentPK = orderPaymentPK;
        this.transactionId = transactionId;
        this.status = status;
    }

    public OrderPayment(int orderId, int paymentId) {
        this.orderPaymentPK = new OrderPaymentPK(orderId, paymentId);
    }

    public OrderPaymentPK getOrderPaymentPK() {
        return orderPaymentPK;
    }

    public void setOrderPaymentPK(OrderPaymentPK orderPaymentPK) {
        this.orderPaymentPK = orderPaymentPK;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderPaymentPK != null ? orderPaymentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderPayment)) {
            return false;
        }
        OrderPayment other = (OrderPayment) object;
        if ((this.orderPaymentPK == null && other.orderPaymentPK != null) || (this.orderPaymentPK != null && !this.orderPaymentPK.equals(other.orderPaymentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.OrderPayment[ orderPaymentPK=" + orderPaymentPK + " ]";
    }
    
}
