/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author VS9 X64Bit
 */
@Embeddable
public class AppPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "theme_id")
    private int themeId;
    @Basic(optional = false)
    @Column(name = "company_id")
    private int companyId;

    public AppPK() {
    }

    public AppPK(int themeId, int companyId) {
        this.themeId = themeId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) themeId;
        hash += (int) companyId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AppPK)) {
            return false;
        }
        AppPK other = (AppPK) object;
        if (this.themeId != other.themeId) {
            return false;
        }
        if (this.companyId != other.companyId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.AppPK[ themeId=" + themeId + ", companyId=" + companyId + " ]";
    }
    
}
