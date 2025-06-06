package com.teleport.TrackingNumberService.service;

import com.teleport.TrackingNumberService.model.TrackingNumberResponse;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public interface TrackingNumberService {

    TrackingNumberResponse generateTrackingNumber(String originCountryId, String destinationCountryId, BigDecimal weight,
                                                  OffsetDateTime createdAt, UUID customerId, String customerName, String customerSlug);
}
