/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.notification.email;

import com.nitsoft.ecommerce.notification.email.transport.EmailTransportConfiguration;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    //private String SmtpHost="localhost";
    private String smtpHost = "";
    private int smtpPort = 25;
    private Boolean isSmtps = false;

    public boolean SendEmail(String mailto, String Subject, String Body) {
        boolean status = true;
        try {
            EmailTransportConfiguration.configure(smtpHost, smtpPort, isSmtps);
            // Send email
            new EmailMessage()
                    .from("no-reply@mydomain.com")
                    .to(mailto)
                    .withSubject(Subject)
                    .withBody(Body)
                    .send();
        } catch (Exception e) {
            EventLogManager.getInstance().error("Send mail error: " + e.getMessage());
            status = false;
        }
        return status;
    }
}
