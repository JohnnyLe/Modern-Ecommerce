/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.scheduler;

import com.nitsoft.ecommerce.notification.NotificationQueueManager;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Class Name: PushNotificationWorker.java
 * @author: lxanh
 */
@Component
public class PushNotificationWorker extends JobWorker{

    @Autowired
    private SimpMessagingTemplate template;
    
    private static final String WEBSOCKET_TOPIC = "/topic/notification";
    private static final String WEBSOCKET_SYSTEM_NOTIFICATION = "/topic/systemnotify";
   
    @Async
    @Override
    public void doWork(){
        if (!NotificationQueueManager.getInstance().IsQueueEmpty()) {
            Object message=NotificationQueueManager.getInstance().getMessage();
            System.out.println("PushNotificationWorker run ...");
            template.convertAndSend(WEBSOCKET_TOPIC, message);  
        }
        if(!NotificationQueueManager.getInstance().IsQueueSystemNotifyEmpty()){
            Object messObject = NotificationQueueManager.getInstance().getMessageSystemNotify();
            template.convertAndSend(WEBSOCKET_SYSTEM_NOTIFICATION, messObject);
        }        
    }    
   private String jobName = "PushNotificationWorker";
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
        return false;
    }
        
    @Override
    public JobType getJobType() {
       return JobType.MULTIPLE;
    }
    
}
