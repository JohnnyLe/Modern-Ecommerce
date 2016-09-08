/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.scheduler;

import com.nitsoft.ecommerce.configs.AppConfig;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * @Class Name: SchedulerService.java
 * @author: lxanh
 * @created: Oct 20, 2014
 * @version: 2.0
 *
 * @brief: This class is implementation for execute Scheduler background
 * process: Encode file Data cleaner Email notification Async process to Bookend
 * server
 */
@Service
public class SchedulerService {

    @Autowired
    private ApplicationContext appContext;
    private static ThreadPoolTaskExecutor taskExecutor;

  
    @Autowired
    private EmailNotificationWorker emailNotificationWorker;

    @Autowired
    private PushNotificationWorker notificationWorker;

    

    @Autowired
    AppConfig appConfig;

//    @Scheduled(fixedDelay = 1000)// 1 second
//    public void doJobSendEmailNitification() {
//        //System.out.println("********* Start doJobRegisterLicense *****");
//        doJob(emailNotificationWorker);
//    }
//
//
//    @Scheduled(fixedDelay = 1000)// 1 second
//    public void pushNotification() {
//        //System.out.println("********* Start doJobRegisterLicense *****");
//        doJob(notificationWorker);
//    }

   
    /**
     * doJob
     *
     * @param jobWorker
     */
    private void doJob(JobWorker jobWorker) {
        try {
            //System.out.println("Job worker " + jobWorker.getJobName() + " Runing ...");
            if (taskExecutor == null) {
                taskExecutor = (ThreadPoolTaskExecutor) appContext.getBean("executorWithPoolSizeRange");
            }
            // Get thread pool size available
            int corePoolSize = taskExecutor.getCorePoolSize();
            // Check message queue is available for encode
            if (!jobWorker.isQueueEmpty()) {
                // Check thread can create in Thread pool
                if (taskExecutor.getActiveCount() < corePoolSize) {
                    if (jobWorker.getJobType().equals(JobWorker.JobType.SINGLE)) {
                        jobWorker.doWork();
                    } else {
                        // There're free Thread
                        for (int i = 0; i < Math.min(corePoolSize - taskExecutor.getActiveCount(), corePoolSize); i++) {
                            jobWorker.doWork();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            EventLogManager.getInstance().error(ex);
        }
    }
}
