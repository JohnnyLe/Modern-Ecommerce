package com.nitsoft.ecommerce.database.model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "reviews")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r"),
    @NamedQuery(name = "Review.findByReviewId", query = "SELECT r FROM Review r WHERE r.reviewId = :reviewId"),
    @NamedQuery(name = "Review.findByUserId", query = "SELECT r FROM Review r WHERE r.userId = :userId"),
    @NamedQuery(name = "Review.findByProductId", query = "SELECT r FROM Review r WHERE r.productId = :productId"),
    @NamedQuery(name = "Review.findByCompanyId", query = "SELECT r FROM Review r WHERE r.companyId = :companyId"),
    @NamedQuery(name = "Review.findByRank", query = "SELECT r FROM Review r WHERE r.rank = :rank"),
    @NamedQuery(name = "Review.findByComment", query = "SELECT r FROM Review r WHERE r.comment = :comment")})
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "review_id")
    private Integer reviewId;

    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;

    @Basic(optional = false)
    @Column(name = "product_id")
    private int productId;

    @Basic(optional = false)
    @Column(name = "company_id")
    private long companyId;

    @Basic(optional = false)
    @Column(name = "rank")
    private int rank;

    @Basic(optional = false)
    @Column(name = "comment")
    private String comment;

    @Basic(optional = false)
    @Column(name = "create_date")
    private Date createDate;

    public Review() {
    }

    public Review(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Review(Integer reviewId, String userId, int productId, long companyId, int rank, String comment, Date createDate) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.productId = productId;
        this.companyId = companyId;
        this.rank = rank;
        this.comment = comment;
        this.createDate = createDate;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reviewId != null ? reviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.reviewId == null && other.reviewId != null) || (this.reviewId != null && !this.reviewId.equals(other.reviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.Review[ reviewId=" + reviewId + " ]";
    }

}
