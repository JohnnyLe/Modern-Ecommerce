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
@Table(name = "applications")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a"),
    @NamedQuery(name = "Application.findByThemeId", query = "SELECT a FROM Application a WHERE a.themeId = :themeId"),
    @NamedQuery(name = "Application.findByCompanyId", query = "SELECT a FROM Application a WHERE a.companyId = :companyId"),
    @NamedQuery(name = "Application.findByStatus", query = "SELECT a FROM Application a WHERE a.status = :status"),
    @NamedQuery(name = "Application.findByApplicationDomain", query = "SELECT a FROM Application a WHERE a.appDomain = :appDomain")})
public class Application implements Serializable {

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

    public Application() {
    }

    public Application(boolean status, String appDomain) {
        this.status = status;
        this.appDomain = appDomain;
    }

    public Application(int themeId, int companyId) {
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
