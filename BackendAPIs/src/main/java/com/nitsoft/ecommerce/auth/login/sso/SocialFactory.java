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
public class SocialFactory {
    
     public static Map<String, Object> buidSocial(SocialType sType, String sToken) {
         
         Map<String, Object> m = new HashMap<String, Object>();
         
         switch (sType) {
             
             case GOOGLE:
                 GoogleSocial g = new GoogleSocial(sToken);
                 m = g.getUserSocialInfo();
                 break;
                 
             case FACEBOOK:
                 // TODO
                 FacebookSocial f  = new FacebookSocial(sToken);
                 m = f.getUserSocialInfo();
                 break;
                 
             case LINKEDIN:
                 // TODO
                 break;
         }
         
         return m;
     }
}
