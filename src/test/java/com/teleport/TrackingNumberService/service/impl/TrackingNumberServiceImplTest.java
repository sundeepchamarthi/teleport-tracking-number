package com.teleport.TrackingNumberService.service.impl;

import com.teleport.TrackingNumberService.entities.TrackingNumber;
import com.teleport.TrackingNumberService.model.TrackingNumberResponse;
import com.teleport.TrackingNumberService.repository.TrackingNumberRepo;
import com.teleport.TrackingNumberService.util.TrackingNumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackingNumberServiceImplTest {

    @Mock
    private TrackingNumberRepo trackingNumberRepo;

    @Mock
    private TrackingNumberGenerator trackingNumberGenerator;

    @InjectMocks
    private TrackingNumberServiceImpl trackingNumberService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateTrackingNumber_Success() {
        String trackingNo = "ABC123456789XYZ0";
        when(trackingNumberGenerator.generateTrackingNumber()).thenReturn(trackingNo);
        when(trackingNumberRepo.existsByTrackingNumber(trackingNo)).thenReturn(false);

        UUID customerId = UUID.randomUUID();
        BigDecimal weight = new BigDecimal("1.234");
        OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

        TrackingNumberResponse response = trackingNumberService.generateTrackingNumber(
                "MY", "SG", weight, createdAt, customerId, "John Doe", "john-doe"
        );

        assertNotNull(response);
        assertEquals(trackingNo, response.getTracking_number());
        assertEquals("MY", response.getOrigin_country_id());
        assertEquals("SG", response.getDestination_country_id());
        verify(trackingNumberRepo, times(1)).save(any(TrackingNumber.class));
    }

}
