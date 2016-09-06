package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_payments")
public class OrderPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "order_id")
    private int orderId;

    @Id
    @Basic(optional = false)
    @Column(name = "payment_id")
    private int paymentId;

    @Basic(optional = false)
    @Column(name = "transaction_id")
    private String transactionId;
    @Basic(optional = false)
    @Column(name = "status")
    private int status;

    public OrderPayment() {
    }

    public OrderPayment(int orderId, int paymentId, String transactionId, int status) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.transactionId = transactionId;
        this.status = status;
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

}
