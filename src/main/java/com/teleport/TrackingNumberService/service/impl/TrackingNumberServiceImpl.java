package com.teleport.TrackingNumberService.service.impl;

import com.teleport.TrackingNumberService.service.TrackingNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

@Slf4j
@Service
public class TrackingNumberServiceImpl implements TrackingNumberService {


    private final ConcurrentHashMap<String, AtomicLong> counterMap = new ConcurrentHashMap<>();
    private static final Pattern VALID_PATTERN = Pattern.compile("^[A-Z0-9]{1,16}$");


    @Override
    public String generateTrackingNumber(String originCountryId, String destinationCountryId, double weight,
                                         OffsetDateTime createdAt, UUID customerId, String customerName, String customerSlug) {

            log.info("Generating tracking number for originCountryId: {}, destinationCountryId: {}, weight: {}, createdAt: {}," +
                    " customerId: {}, customerName: {}, customerSlug: {}", originCountryId, destinationCountryId, weight, createdAt,
                    customerId, customerName, customerSlug);
        String key = originCountryId + "-" + destinationCountryId + "-" + customerSlug;
        counterMap.putIfAbsent(key, new AtomicLong(0));
        long sequence = counterMap.get(key).incrementAndGet();

        String base = String.format("%s%s%s%06d",
                originCountryId.toUpperCase(),
                destinationCountryId.toUpperCase(),
                customerSlug.replace("-", "").toUpperCase(),
                sequence);

        String trackingNumber = base.length() > 16 ? base.substring(0, 16) : base;

        if (!VALID_PATTERN.matcher(trackingNumber).matches()) {
            throw new IllegalArgumentException("Generated tracking number does not meet the required format.");
        }

        log.info("Generated tracking number: {}", sequence);

        return trackingNumber;
    }
}
