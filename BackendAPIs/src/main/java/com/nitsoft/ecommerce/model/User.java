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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")})
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_id")
    private String userId;    
    @Column(name = "team_id")
    private String teamId;
    @Basic(optional = false)
    @Column(name = "role_id")
    private int roleId;
    @Basic(optional = false)
    @Column(name = "mail_address")
    private String mailAddress;
    @Basic(optional = false)
    @Column(name = "password_hash")
    private String passwordHash;
    @Basic(optional = false)
    @Column(name = "status")
    private int status;
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "session_time_out")
    private int sessionTimeOut;
    @Basic(optional = false)
    @Column(name = "activation_code")
    private String activationCode;
    @Basic(optional = false)
    @Column(name = "activation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activationDate;
    @Basic(optional = false)
    @Column(name = "num_of_disp_notify")
    private String numOfDispNotify;
    @Basic(optional = false)
    @Column(name = "piriod_of_disp_notify")
    private int piriodOfDispNotify;
    @Basic(optional = false)
    @Column(name = "time_zone")
    private int timeZone;
    @Basic(optional = false)
    @Column(name = "signup_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date signupDate;
    @Column(name = "last_activity")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActivity;
    @Basic(optional = false)
    @Column(name = "status_notify")
    private String statusNotify;
    @Basic(optional = false)
    @Column(name = "notification_flag")
    private String notificationFlag;
    @Column(name = "card_info_id")
    private String cardInfoId;
    @Column(name = "landing_page")
    private Integer landingPage;
    @Basic(optional = false)
    @Column(name = "lang")
    private String lang;
    @Basic(optional = false)
    @Column(name = "bookend_user_id")
    private String bookendUserId;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
    }

    public User(String userId, String teamId, int roleId, String mailAddress, String passwordHash, int status, String firstName, String lastName, int sessionTimeOut, String activationCode, Date activationDate, String numOfDispNotify, int piriodOfDispNotify, int timeZone, Date signupDate, String statusNotify, String notificationFlag, String lang) {
        this.userId = userId;
        this.teamId = teamId;
        this.roleId = roleId;
        this.mailAddress = mailAddress;
        this.passwordHash = passwordHash;
        this.status = status;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sessionTimeOut = sessionTimeOut;
        this.activationCode = activationCode;
        this.activationDate = activationDate;
        this.numOfDispNotify = numOfDispNotify;
        this.piriodOfDispNotify = piriodOfDispNotify;
        this.timeZone = timeZone;
        this.signupDate = signupDate;
        this.statusNotify = statusNotify;
        this.notificationFlag = notificationFlag;
        this.lang = lang;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(int sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public String getNumOfDispNotify() {
        return numOfDispNotify;
    }

    public void setNumOfDispNotify(String numOfDispNotify) {
        this.numOfDispNotify = numOfDispNotify;
    }

    public int getPiriodOfDispNotify() {
        return piriodOfDispNotify;
    }

    public void setPiriodOfDispNotify(int piriodOfDispNotify) {
        this.piriodOfDispNotify = piriodOfDispNotify;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public Date getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(Date signupDate) {
        this.signupDate = signupDate;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getStatusNotify() {
        return statusNotify;
    }

    public void setStatusNotify(String statusNotify) {
        this.statusNotify = statusNotify;
    }

    public String getNotificationFlag() {
        return notificationFlag;
    }

    public void setNotificationFlag(String notificationFlag) {
        this.notificationFlag = notificationFlag;
    }

    public String getCardInfoId() {
        return cardInfoId;
    }

    public void setCardInfoId(String cardInfoId) {
        this.cardInfoId = cardInfoId;
    }

    public Integer getLandingPage() {
        return landingPage;
    }

    public void setLandingPage(Integer landingPage) {
        this.landingPage = landingPage;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getBookendUserId() {
        return bookendUserId;
    }

    public void setBookendUserId(String bookendUserId) {
        this.bookendUserId = bookendUserId;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "";
    }
    
}
