/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.auth.login;

import com.nitsoft.ecommerce.model.User;


public class AuthResponse {

    private String authToken;
    private String userId;
    private String teamId;
    private int roleId;
    private String mailAddress;
    private int status;
    private String firstName;
    private String middleName;
    private String lastName;
    private int sessionTimeOut;
    
    // Customer pros
    // TODO

    public AuthResponse() {
    }
    
    public AuthResponse(User user, String token) {
        this.authToken=token;
        this.userId=user.getUserId();
     
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getTeamId() {
        return teamId;
    }

    public int getRoleId() {
        return roleId;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public int getStatus() {
        return status;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSessionTimeOut(int sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }
}
