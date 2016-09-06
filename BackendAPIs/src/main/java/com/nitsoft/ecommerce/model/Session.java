/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vkloan
 */
@Entity
@Table(name = "session")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Session.findAll", query = "SELECT s FROM Session s")})
public class Session implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "session_id")
    private String sessionId;
    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;    
    @Column(name = "team_id")
    private String teamId;
    @Basic(optional = false)
    @Column(name = "login_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDate;
    @Basic(optional = false)
    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    @Column(name = "return_url")
    private String returnUrl;
    @Lob
    @Column(name = "session_data")
    private String sessionData;

    public Session() {
    }

    public Session(String sessionIs) {
        this.sessionId = sessionIs;
    }

    public Session(String sessionId, String userId, String teamId, Date loginDate, Date expirationDate) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.teamId = teamId;
        this.loginDate = loginDate;
        this.expirationDate = expirationDate;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getSessionData() {
        return sessionData;
    }

    public void setSessionData(String sessionData) {
        this.sessionData = sessionData;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sessionId != null ? sessionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Session)) {
            return false;
        }
        Session other = (Session) object;
        if ((this.sessionId == null && other.sessionId != null) || (this.sessionId != null && !this.sessionId.equals(other.sessionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "";
    }

}
