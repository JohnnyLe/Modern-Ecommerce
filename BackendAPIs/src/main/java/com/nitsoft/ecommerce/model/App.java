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
@Table(name = "app")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "App.findAll", query = "SELECT a FROM App a"),
    @NamedQuery(name = "App.findByThemeId", query = "SELECT a FROM App a WHERE a.appPK.themeId = :themeId"),
    @NamedQuery(name = "App.findByCompanyId", query = "SELECT a FROM App a WHERE a.appPK.companyId = :companyId"),
    @NamedQuery(name = "App.findByStatus", query = "SELECT a FROM App a WHERE a.status = :status"),
    @NamedQuery(name = "App.findByAppDomain", query = "SELECT a FROM App a WHERE a.appDomain = :appDomain")})
public class App implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AppPK appPK;
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;
    @Basic(optional = false)
    @Column(name = "app_domain")
    private String appDomain;

    public App() {
    }

    public App(AppPK appPK) {
        this.appPK = appPK;
    }

    public App(AppPK appPK, boolean status, String appDomain) {
        this.appPK = appPK;
        this.status = status;
        this.appDomain = appDomain;
    }

    public App(int themeId, int companyId) {
        this.appPK = new AppPK(themeId, companyId);
    }

    public AppPK getAppPK() {
        return appPK;
    }

    public void setAppPK(AppPK appPK) {
        this.appPK = appPK;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAppDomain() {
        return appDomain;
    }

    public void setAppDomain(String appDomain) {
        this.appDomain = appDomain;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appPK != null ? appPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof App)) {
            return false;
        }
        App other = (App) object;
        if ((this.appPK == null && other.appPK != null) || (this.appPK != null && !this.appPK.equals(other.appPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.App[ appPK=" + appPK + " ]";
    }
    
}
