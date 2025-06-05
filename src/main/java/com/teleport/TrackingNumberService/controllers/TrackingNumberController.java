package com.teleport.TrackingNumberService.controllers;

import com.teleport.TrackingNumberService.service.TrackingNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping
public class TrackingNumberController {

  @Autowired
  TrackingNumberService trackingNumberService;

  @GetMapping("/next-tracking-number")
  public String getNextTrackingNumber(
          @RequestParam("origin_country_id") String originCountryId,
          @RequestParam("destination_country_id") String destinationCountryId,
          @RequestParam("weight") double weight,
          @RequestParam("created_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime createdAt,
          @RequestParam("customer_id") UUID customerId,
          @RequestParam("customer_name") String customerName,
          @RequestParam("customer_slug") String customerSlug
  ) {

      String trackingNumber = trackingNumberService.generateTrackingNumber(originCountryId, destinationCountryId, weight, createdAt, customerId, customerName, customerSlug);
      log.info("Generated tracking number: {}", trackingNumber);

      return trackingNumber;
  }


}
