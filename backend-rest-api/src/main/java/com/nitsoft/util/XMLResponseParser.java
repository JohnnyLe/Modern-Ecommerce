/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.util;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 */
public class XMLResponseParser{
    
      
      public static String parseXML(InputStream input, String tagname) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = (Document) dBuilder.parse(input);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(tagname);
            return nList.item(0).getTextContent();
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return null;
        }

     }
      /**
       * Parse XML returned from Google after call its API getting contact list
       * @param input InputStream
       * @param tagname XML's node name. Our context is 'entry'
       * @return list object {email, given_name, additional_name, family_name, organization}
       */
      public static List<Map<String, Object>> parseGoogleContactXML(InputStream input, String tagname) {
        try { 
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = (org.w3c.dom.Document) dBuilder.parse(input);
            doc.getDocumentElement().normalize();
            
            NodeList nList = doc.getElementsByTagName(tagname);
            
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            
            for (int i=0; i < nList.getLength(); i ++) {
                
                Map<String, Object> info = new HashMap<String, Object>();
                
                NodeList entry = nList.item(i).getChildNodes();
                //valid data
                boolean validEmail = false;
                boolean validName = false;
                //var data
                String email = null;
                String givenName = null;
                String additionalName = null;
                String familyName = null;
                String organization = null;
                
                for (int j=0; j < entry.getLength(); j ++) {
                    //Get email
                    if (entry.item(j).getNodeName().equals("gd:email")) {
                        email = (entry.item(j).hasAttributes()) ? entry.item(j).getAttributes().getNamedItem("address").getNodeValue() : "";
                        if (email == null) email = "";
                        validEmail = true;
                    }
                    //Get name
                    if (entry.item(j).getNodeName().equals("gd:name")) {

                        NodeList name = entry.item(j).getChildNodes();
                        
                        for (int k=0; k < name.getLength(); k++) {
                            
                            //Just like switch
                            if (name.item(k).getNodeName().equals("gd:givenName")) 
                                givenName = name.item(k).getFirstChild().getNodeValue();
                            
                            if (name.item(k).getNodeName().equals("gd:additionalName")) 
                                additionalName = name.item(k).getFirstChild().getNodeValue();
                                
                            if (name.item(k).getNodeName().equals("gd:familyName")) 
                                familyName = name.item(k).getFirstChild().getNodeValue();
                        }
                        //Put name into modal
                        if (givenName == null) givenName = "";
                        if (additionalName == null) additionalName = "";
                        if (familyName == null) familyName = "";
                        validName = true;
                    }
                    
                    //Get organization 
                    if (entry.item(j).getNodeName().equals("gd:organization")) {
                        if (entry.item(j).getFirstChild().hasChildNodes()) {
                            organization = entry.item(j).getFirstChild().getFirstChild().getNodeValue();
                        }
                    } 
                    
                            
                } //End each entry
                //Add to list
                if (validName && validEmail) {
                    if (organization == null) organization = "";
                    if (!StringUtil.containSpecialCharacter(givenName) && !StringUtil.containSpecialCharacter(additionalName) && !StringUtil.containSpecialCharacter(familyName)) {
                        info.put("email", email);
                        info.put("given_name", givenName);
                        info.put("additional_name", additionalName);
                        info.put("family_name", familyName);
                        info.put("organization", organization);
                    }
                }
                //Test after adding
                if (info.containsKey("email") && info.containsKey("given_name") && !info.get("email").toString().equals("") && !info.get("given_name").toString().equals("")) {
                    list.add(info);
                }
            }
            return list;
           
        } catch (Exception e) {
             EventLogManager.getInstance().log(e.getMessage());
             return null;
        }
    }
}
