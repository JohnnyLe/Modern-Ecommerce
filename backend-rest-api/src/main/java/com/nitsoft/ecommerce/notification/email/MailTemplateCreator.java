/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.notification.email;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 *
 * @author lxanh
 */
public class MailTemplateCreator {

    public static String createTempate(ModelMap model, String tempateName) throws Exception {
        try {
            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
            velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
            velocityEngine.init();
            String mailBody = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "templates/" + tempateName, "UTF-8", model);
            return mailBody;
        } catch (Exception e) {
            return null;
        }
    }
}
