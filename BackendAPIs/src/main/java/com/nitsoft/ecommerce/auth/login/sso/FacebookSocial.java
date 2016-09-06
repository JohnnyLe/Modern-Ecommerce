/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.auth.login.sso;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class FacebookSocial extends Social {

    public FacebookSocial(String sToken) {
        super(SocialType.FACEBOOK, sToken);
    }
    
    @Override
    protected Map<String, Object> getUserSocialInfo() {
        //TODO
        return new HashMap<String, Object>();
    }
}
