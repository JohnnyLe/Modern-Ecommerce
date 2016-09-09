package com.nitsoft.ecommerce.database.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "order_payments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderPayment.findAll", query = "SELECT o FROM OrderPayment o"),
    @NamedQuery(name = "OrderPayment.findByOrderId", query = "SELECT o FROM OrderPayment o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "OrderPayment.findByPaymentId", query = "SELECT o FROM OrderPayment o WHERE o.paymentId = :paymentId"),
    @NamedQuery(name = "OrderPayment.findByTransactionId", query = "SELECT o FROM OrderPayment o WHERE o.transactionId = :transactionId"),
    @NamedQuery(name = "OrderPayment.findByStatus", query = "SELECT o FROM OrderPayment o WHERE o.status = :status")})
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

    public OrderPayment(String transactionId, int status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public OrderPayment(int orderId, int paymentId) {
        this.orderId = orderId;
        this.paymentId
                = paymentId;
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
