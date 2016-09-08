package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Packet;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/*
 * public interface PacketRepository extends CrudRepository<Packet, Long> {}
 */
public interface PacketRepository extends CrudRepository<Packet, Long> {
    
    @Query("from Packet where tabId=:tabId")
    public Iterable<Packet> findByTabId(@Param("tabId") long tabId);
}
