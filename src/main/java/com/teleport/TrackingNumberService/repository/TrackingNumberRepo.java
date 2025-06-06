package com.teleport.TrackingNumberService.repository;

import com.teleport.TrackingNumberService.entities.TrackingNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingNumberRepo extends JpaRepository<TrackingNumber,Long> {

    boolean existsByTrackingNumber(String trackingNumber);
}
