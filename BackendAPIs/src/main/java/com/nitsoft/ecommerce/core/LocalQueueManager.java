/*
 */
package com.nitsoft.ecommerce.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Class Name: LocalQueueManager.java
 * @author: lxanh
 * @created: Oct 20, 2014
 * @version: 2.0
 * @brief: This class is implementation for Queue store Encode Queue message
 * @RTM: HG_project_file_encode
 */
@Service
public class LocalQueueManager {

    // Instance
    private static LocalQueueManager instance;
    
    public static synchronized LocalQueueManager getInstance() {
        if (instance == null) {
            instance = new LocalQueueManager();
        }
        return instance;
    }
    // Queue email notification
    private static Queue<Map<String, Object>> emailQueue;

    private LocalQueueManager() {
       
        emailQueue = new LinkedList<Map<String, Object>>();
        //Load all queue in DB
        loadData();
    }


    public synchronized Boolean IsMailQueueEmpty() {
        if (emailQueue.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    // For Email notification queue
    public synchronized Map<String, Object> getMailQueue() {
        return emailQueue.remove();
    }

    public synchronized void addMailQueue(Map<String, Object> obj) {
        emailQueue.add(obj);
    }

    private void loadData() {
       //
    }
}
