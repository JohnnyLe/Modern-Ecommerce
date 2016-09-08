/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.rest.information;


import com.nitsoft.ecommerce.database.model.ByteInfo;
import com.nitsoft.ecommerce.database.model.IdInformation;
import com.nitsoft.ecommerce.database.model.Packet;
import java.util.List;

/**
 *
 */
public class IDInformationResponse {
    
    private double arbID;
    private List<Packet> packets;

    public double getArbID() {
        return arbID;
    }

    public void setArbID(double arbID) {
        this.arbID = arbID;
    }

    public List<Packet> getPackets() {
        return packets;
    }

    public void setPackets(List<Packet> packets) {
        this.packets = packets;
    }
    
    
}
