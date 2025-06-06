package com.teleport.TrackingNumberService.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

import java.security.SecureRandom;

@Component
public class TrackingNumberGenerator {

    private static final AtomicLong COUNTER = new AtomicLong(0);
    private static final long MAX_PER_MILLISECOND = 9999;
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public synchronized String generateTrackingNumber() {
        long nowMillis = System.currentTimeMillis();
        long count = COUNTER.getAndUpdate(prev -> (prev >= MAX_PER_MILLISECOND) ? 0 : prev + 1);

        // Base string from timestamp + counter
        String baseString = String.format("%08d%04d", nowMillis % 100000000, count);
        long number = Long.parseLong(baseString);
        String tracking = Long.toString(number, 36).toUpperCase();

        // If < 16, pad with random alphanumeric characters
        if (tracking.length() < 16) {
            int remaining = 16 - tracking.length();
            StringBuilder sb = new StringBuilder(tracking);
            for (int i = 0; i < remaining; i++) {
                sb.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
            }
            tracking = sb.toString();
        } else if (tracking.length() > 16) {
            tracking = tracking.substring(0, 16);
        }

        return tracking;
    }
}
