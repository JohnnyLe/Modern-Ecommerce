/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.auth.login.sso;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author vkloan
 */
public class GoogleSocial extends Social {
    
    GoogleSocial(String sToken) {
        super(SocialType.GOOGLE, sToken);
    }
    
    @Override
    protected Map<String, Object> getUserSocialInfo() {
        Map <String, Object> userInfo = new HashMap<String, Object> ();
        try {
            String sToken = GoogleSocial.super.getToken();
            // TODO 
            // Process to get user info
//            LegacyGoogleProfile lg = new GoogleTemplate(sToken).getRestTemplate().getForObject("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + sToken, LegacyGoogleProfile.class);
//            userInfo.put("id", lg.getId()); 
//            userInfo.put("email", lg.getEmail()); 
//            userInfo.put("firstname", lg.getFirstName()); 
//            userInfo.put("lastname", lg.getLastName()); 
//            userInfo.put("name", lg.getName());
//            userInfo.put("locale", lg.getLocale());
//            userInfo.put("gender", lg.getGender());
            
            // Call API
            URL u1 = new URL("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + sToken);
            HttpsURLConnection req = (HttpsURLConnection) u1.openConnection();
            req.setDoInput(true);
            req.setRequestMethod("GET");
            req.setRequestProperty("Content-Type", "application/json");
            JsonObject je = (JsonObject) new JsonParser().parse(IOUtils.toString(req.getInputStream(), "UTF-8"));
            userInfo.put("id", je.get("id").getAsString()); 
            userInfo.put("email", je.get("email").getAsString()); 
            userInfo.put("firstname", je.get("family_name").getAsString()); 
            userInfo.put("lastname", je.get("given_name").getAsString()); 
            userInfo.put("name", je.get("name").getAsString());
            
        } 
        catch (Exception ex) {
            Logger.getLogger(GoogleSocial.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userInfo;
    }
    
}
