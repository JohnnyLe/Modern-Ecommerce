/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.auth.login.sso;

import java.util.Map;

/**
 *
 * @author vkloan
 */
public abstract class Social {
    
    private SocialType sType = null;
    private String sToken  = null;

    public String getToken() {
        return sToken;
    }

    public void setToken(String sToken) {
        this.sToken = sToken;
    }

    // Getter & Setter
    
    public void setType(SocialType sType) {
        this.sType = sType;
    }

    public SocialType getType() {
        return sType;
    }
    
    public Social() {
    }

    public Social(SocialType sType, String sToken) {
        this.sType = sType;
        this.sToken = sToken;
    }

    /**
     * Get user info from Social page
     * @return Map object
     */
    protected abstract Map<String, Object> getUserSocialInfo();
                
}

