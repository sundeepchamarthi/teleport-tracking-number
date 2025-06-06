package com.teleport.TrackingNumberService.service.impl;

import com.teleport.TrackingNumberService.entities.TrackingNumber;
import com.teleport.TrackingNumberService.exception.BadRequestException;
import com.teleport.TrackingNumberService.exception.InternalServerErrorException;
import com.teleport.TrackingNumberService.model.TrackingNumberResponse;
import com.teleport.TrackingNumberService.repository.TrackingNumberRepo;
import com.teleport.TrackingNumberService.service.TrackingNumberService;
import com.teleport.TrackingNumberService.util.TrackingNumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import java.util.regex.Pattern;

@Slf4j
@Service
public class TrackingNumberServiceImpl implements TrackingNumberService {

    private final TrackingNumberRepo trackingNumberRepo;

    private final TrackingNumberGenerator trackingNumberGenerator;

    private static final Pattern TRACKING_PATTERN = Pattern.compile("^[A-Z0-9]{1,16}$");

    public TrackingNumberServiceImpl(
            TrackingNumberRepo trackingNumberRepo,
            TrackingNumberGenerator trackingNumberGenerator
    ) {
        this.trackingNumberRepo = trackingNumberRepo;
        this.trackingNumberGenerator = trackingNumberGenerator;
    }

    @Override
    public TrackingNumberResponse generateTrackingNumber(
            String originCountryId,
            String destinationCountryId,
            BigDecimal weight,
            OffsetDateTime createdAt,
            UUID customerId,
            String customerName,
            String customerSlug
    ) {
        log.info("Generating tracking number for origin={}, destination={}, weight={}, createdAt={}, customerId={}, name={}, slug={}",
                originCountryId, destinationCountryId, weight, createdAt, customerId, customerName, customerSlug);
        try{

            // Step 1: Generate tracking number
            String trackingNumber = trackingNumberGenerator.generateTrackingNumber();
            log.debug("Generated tracking number: {}", trackingNumber);

            // Step 2: Check uniqueness
            if (trackingNumberRepo.existsByTrackingNumber(trackingNumber)) {
                log.error("Duplicate tracking number detected: {}", trackingNumber);
                throw new IllegalStateException("Duplicate tracking number generated");
            }

            // Step 3: Validate against pattern
            if (!TRACKING_PATTERN.matcher(trackingNumber).matches()) {
                log.error("Generated tracking number '{}' does not match required format", trackingNumber);
                throw new IllegalArgumentException("Generated tracking number does not meet the required format.");
            }

            // Step 4: Build and save entity
            TrackingNumber entity = TrackingNumber.builder()
                    .trackingNumber(trackingNumber)
                    .originCountryId(originCountryId)
                    .destinationCountryId(destinationCountryId)
                    .weight(weight.setScale(3, RoundingMode.HALF_UP))
                    .createdAt(OffsetDateTime.now(ZoneOffset.of("+08:00")))
                    .customerId(customerId)
                    .customerName(customerName)
                    .customerSlug(customerSlug)
                    .build();

            trackingNumberRepo.save(entity);
            log.info("Tracking number '{}' successfully saved.", trackingNumber);

            // Step 5: Build response
            return TrackingNumberResponse.builder()
                    .tracking_number(trackingNumber)
                    .created_at(entity.getCreatedAt())
                    .origin_country_id(originCountryId)
                    .customer_id(customerId)
                    .destination_country_id(destinationCountryId)
                    .customer_name(customerName)
                    .build();
        }catch (DataIntegrityViolationException | BadRequestException e) {
            log.warn("Handled error: {}", e.getMessage());
            throw e; // Will be handled by ControllerAdvice
        } catch (Exception ex) {
            log.error("Unhandled error: ", ex);
            throw new InternalServerErrorException("Internal error while generating tracking number.");
        }

    }
}
