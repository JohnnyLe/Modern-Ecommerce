package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app")
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

    public App(int themeId, int companyId, boolean status, String appDomain) {
        this.themeId = themeId;
        this.companyId = companyId;
        this.status = status;
        this.appDomain = appDomain;
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
