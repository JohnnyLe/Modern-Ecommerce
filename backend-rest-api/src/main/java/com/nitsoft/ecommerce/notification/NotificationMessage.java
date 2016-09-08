/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.notification;

import java.util.List;

/**
 *
 * @author xuananh
 */
public class NotificationMessage {
    private int code;
    private Object body;
    private String clientId;
    private String projectId=null;
    private List<String> notifyUserIds=null;
   
    public NotificationMessage(int code, Object body, String clientId, String projectId)
    {
        this.code=code;
        this.body=body;
        this.clientId=clientId;
        this.projectId=projectId;
    }
    
    public NotificationMessage(int code, Object body, String clientId, String projectId, List<String> userIds)
    {
        this.code=code;
        this.body=body;
        this.clientId=clientId;
        this.projectId=projectId;
        this.notifyUserIds=userIds;
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<String> getNotifyUserIds() {
        return notifyUserIds;
    }

    public void setNotifyUserIds(List<String> notifyUserIds) {
        this.notifyUserIds = notifyUserIds;
    }

    
    
    public static enum NotificationType {

        ADD_COMMENT(100, "ADD_COMMENT"), EDIT_COMMENT(101, "EDIT_COMMENT"), DELETE_COMMMENT(102, "DELETE_COMMMENT"),
        ADD_PROJECT(200,"ADD_PROJECT"), EDIT_PROJECT(201, "EDIT_PROJECT"), DELETE_PROJECT(202, "DELETE_PROJECT"),
        MOVE_FOLDER(300, "MOVE_FOLDER"),ADD_FOLDER(301, "ADD_FOLDER"),DELETE_FOLDER(302, "DELETE_FOLDER"),
        MOVE_FILE(400, "MOVE_FILE"),ADD_FILE(401, "ADD_FILE"),ADD_FILE_CLOUD(402, "ADD_FILE_CLOUD"),DELETE_FILE(403, "DELETE_FILE"),
        ADD_MEMBER(500, "ADD_MEMBER"), ADD_MEMBER_FROM_CSV(501, "ADD_MEMBER_FROM_CSV"), UPDATE_MEMBER(502, "UPDATE_MEMBER"), DELETE_MEMBER(503, "DELETE_MEMBER"),
        RENAME(600,"RENAME"),
        INVITE_USER(700,"INVITE_USER_TO_PROJECT");
        private final int value;
        private final String type;
        private NotificationType(int value, String type) {
            this.value = value;
            this.type = type;
        }
        public int getValue() {
            return value;
        }
        public String getType() {
            return type;
        }
    }
}
