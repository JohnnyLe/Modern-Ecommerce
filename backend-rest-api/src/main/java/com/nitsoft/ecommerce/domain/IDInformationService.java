/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.domain;

import com.nitsoft.ecommerce.repository.PacketRepository;
import com.nitsoft.ecommerce.database.model.ByteInfo;
import com.nitsoft.ecommerce.database.model.IdInformation;
import com.nitsoft.ecommerce.database.model.Packet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 *
 */
@Service
public class IDInformationService {
    
    @Autowired
    PacketRepository packetRepository;
    
    public List<Packet> getPackets(long tabInfoId){
         Iterable<Packet> packets=packetRepository.findByTabId(tabInfoId);
         return toList(packets);
    }
    
    
    public <T> List<T> toList(final Iterable<T> iterable) {
        return (List<T>) StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
    
}
