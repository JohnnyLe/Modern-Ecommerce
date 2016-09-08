/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "idinformation")
public class IdInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String freequency;
    private String canBus;
    private String correlations;
    private String commentTags;
    private String generalComment;
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    public String getFreequency() {
        return freequency;
    }

    public void setFreequency(String freequency) {
        this.freequency = freequency;
    }

    public String getCanBus() {
        return canBus;
    }

    public void setCanBus(String canBus) {
        this.canBus = canBus;
    }

    public String getCorrelations() {
        return correlations;
    }

    public void setCorrelations(String correlations) {
        this.correlations = correlations;
    }

    public String getCommentTags() {
        return commentTags;
    }

    public void setCommentTags(String commentTags) {
        this.commentTags = commentTags;
    }

    public String getGeneralComment() {
        return generalComment;
    }

    public void setGeneralComment(String generalComment) {
        this.generalComment = generalComment;
    }
    
    
}
