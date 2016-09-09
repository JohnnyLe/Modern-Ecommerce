package com.nitsoft.ecommerce.database.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "order_reports")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderReport.findAll", query = "SELECT o FROM OrderReport o"),
    @NamedQuery(name = "OrderReport.findByReportId", query = "SELECT o FROM OrderReport o WHERE o.reportId = :reportId"),
    @NamedQuery(name = "OrderReport.findByOrderId", query = "SELECT o FROM OrderReport o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "OrderReport.findByStatus", query = "SELECT o FROM OrderReport o WHERE o.status = :status"),
    @NamedQuery(name = "OrderReport.findByNote", query = "SELECT o FROM OrderReport o WHERE o.note = :note")})
public class OrderReport implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "report_id")
    private Integer reportId;
    
    @Basic(optional = false)
    @Column(name = "order_id")
    private int orderId;
    
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;
    
    @Column(name = "note")
    private String note;

    public OrderReport() {
    }

    public OrderReport(Integer reportId) {
        this.reportId = reportId;
    }

    public OrderReport(Integer reportId, int orderId, boolean status, String note) {
        this.reportId = reportId;
        this.orderId = orderId;
        this.status = status;
        this.note = note;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reportId != null ? reportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderReport)) {
            return false;
        }
        OrderReport other = (OrderReport) object;
        if ((this.reportId == null && other.reportId != null) || (this.reportId != null && !this.reportId.equals(other.reportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.OrderReport[ reportId=" + reportId + " ]";
    }
    
}
