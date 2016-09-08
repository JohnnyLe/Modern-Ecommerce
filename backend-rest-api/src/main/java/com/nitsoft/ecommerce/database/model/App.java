/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.database.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    @NamedQuery(name = "App.findByThemeId", query = "SELECT a FROM App a WHERE a.themeId = :themeId"),
    @NamedQuery(name = "App.findByCompanyId", query = "SELECT a FROM App a WHERE a.companyId = :companyId"),
    @NamedQuery(name = "App.findByStatus", query = "SELECT a FROM App a WHERE a.status = :status"),
    @NamedQuery(name = "App.findByAppDomain", query = "SELECT a FROM App a WHERE a.appDomain = :appDomain")})
public class App implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "theme_id")
    private int themeId;

    @Id
    @Basic(optional = false)
    @Column(name = "company_id")
    private int companyId;

    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;
    @Basic(optional = false)
    @Column(name = "app_domain")
    private String appDomain;

    public App() {
    }

    public App(boolean status, String appDomain) {
        this.status = status;
        this.appDomain = appDomain;
    }

    public App(int themeId, int companyId) {
        this.themeId
                = themeId;
        this.companyId = companyId;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
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

}
