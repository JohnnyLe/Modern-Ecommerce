/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.scheduler;

import org.springframework.stereotype.Component;
/**
 * @Class Name: JobWorker.java
 * @author: lxanh
 * @created: Oct 20, 2014
 * @version: 2.0
 * @RTM: HG_project_file_encode
 *
 * @brief: Abstract Job Worker, the base job of scheduler
 */
@Component
public abstract class JobWorker {
    
    public abstract JobType getJobType();
    public abstract String getJobName();
    public abstract void setJobName(String name);
    public abstract Boolean isQueueEmpty();
    public abstract void doWork();
    
    public static enum JobType {
        // Allow run single thread for each schedule
        SINGLE, 
        // Allow run multi-thread for each schedule & manage by thread pool size
        MULTIPLE 
    }
}
