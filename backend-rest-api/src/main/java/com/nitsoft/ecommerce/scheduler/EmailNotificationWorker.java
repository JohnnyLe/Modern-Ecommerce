/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.scheduler;

import com.nitsoft.ecommerce.core.LocalQueueManager;
import com.nitsoft.ecommerce.notification.email.EmailSender;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Class Name: EmailNotificationWorker.java
 * @author: lxanh
 * @created: Dec 09, 2014
 * @version: 2.0
 * @brief: Implement EmailNotificationWorker
 * @RTM: HG_project_file_encode
 */
@Component
public class EmailNotificationWorker extends JobWorker{

    //static Logger logger = Logger.getLogger("service");
    @Autowired private EmailSender emailSender;
  
    @Async
    @Override
    public void doWork(){
        if (!LocalQueueManager.getInstance().IsMailQueueEmpty()) {
           // EventLogManager.getInstance().info("doWork send notification email");
            Map<String, Object> request= LocalQueueManager.getInstance().getMailQueue();
            String emailAddress=(String)request.get("mail_address");
            String subject=(String)request.get("subject");
            String body=(String)request.get("body");
            EventLogManager.getInstance().info("EmailNotificationWorker Send email to=" +emailAddress);
            emailSender.SendEmail(emailAddress, subject, body);
        }
    }    
   private String jobName = "EmailNotificationWorker";
    @Override
    public String getJobName() {
        return this.jobName;
    }

    @Override
    public void setJobName(String name) {
        this.jobName=name;
    }
    
    @Override
    public synchronized Boolean isQueueEmpty() {
        return LocalQueueManager.getInstance().IsMailQueueEmpty();
    }

    @Override
    public JobType getJobType() {
       return JobType.MULTIPLE;
    }
    
}
