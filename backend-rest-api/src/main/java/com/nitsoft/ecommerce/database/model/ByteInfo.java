/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 */
@Entity
@Table(name = "byte_info")
public class ByteInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;
    
    @Column(name="name")
    private String name;
    @Column(name="is_change")
    private boolean isChange;
    @Column(name="continuous")
    private boolean continuous;
    @Column(name="correlations")
    private String correlations;
    @Column(name="comments")
    private String comments;
    @Column(name="tab_id")
    private long tabId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        this.isChange = change;
    }

    public boolean isContinuous() {
        return continuous;
    }

    public void setContinuous(boolean continuous) {
        this.continuous = continuous;
    }

    public String getCorrelations() {
        return correlations;
    }

    public void setCorrelations(String correlations) {
        this.correlations = correlations;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public long getTabId() {
        return tabId;
    }

    public void setTabId(long tabId) {
        this.tabId = tabId;
    }
   
    
    
}
