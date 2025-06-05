package com.teleport.TrackingNumberService.service;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface TrackingNumberService {

    String generateTrackingNumber(String originCountryId, String destinationCountryId, double weight,
                                  OffsetDateTime createdAt, UUID customerId, String customerName, String customerSlug);
}
