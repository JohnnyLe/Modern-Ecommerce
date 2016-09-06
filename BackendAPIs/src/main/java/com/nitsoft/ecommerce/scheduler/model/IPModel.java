/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.scheduler.model;

/**
 *
 * @author xuananh
 */
public class IPModel {
    
    private String longitude;
    private String latitude;
    private String city;
    
    public IPModel(String longitude, String latitude, String city) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getCity() {
        return city;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setCity(String city) {
        this.city = city;
    } 
}
