package com.teleport.TrackingNumberService.service.impl;

import com.teleport.TrackingNumberService.entities.TrackingNumber;
import com.teleport.TrackingNumberService.exception.BadRequestException;
import com.teleport.TrackingNumberService.exception.ConflictException;
import com.teleport.TrackingNumberService.exception.InternalServerErrorException;
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
        TrackingNumberResponse response = trackingNumberService.generateTrackingNumber(
                "MY", "SG", new BigDecimal("1.234"), OffsetDateTime.now(),
                customerId, "John Doe", "john-doe"
        );

        assertNotNull(response);
        assertEquals(trackingNo, response.getTracking_number());
        verify(trackingNumberRepo, times(1)).save(any(TrackingNumber.class));
    }

    @Test
    public void testGenerateTrackingNumber_Duplicate() {
        String trackingNo = "DUPLICATE123456";
        when(trackingNumberGenerator.generateTrackingNumber()).thenReturn(trackingNo);
        when(trackingNumberRepo.existsByTrackingNumber(trackingNo)).thenReturn(true);

        assertThrows(ConflictException.class, () -> trackingNumberService.generateTrackingNumber(
                "MY", "SG", new BigDecimal("1.234"), OffsetDateTime.now(),
                UUID.randomUUID(), "Alice", "alice"
        ));

        verify(trackingNumberRepo, never()).save(any());
    }

    @Test
    public void testGenerateTrackingNumber_InvalidFormat() {
        String trackingNo = "INVALID-!@#";
        when(trackingNumberGenerator.generateTrackingNumber()).thenReturn(trackingNo);
        when(trackingNumberRepo.existsByTrackingNumber(trackingNo)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> trackingNumberService.generateTrackingNumber(
                "MY", "SG", new BigDecimal("1.234"), OffsetDateTime.now(),
                UUID.randomUUID(), "Alice", "alice"
        ));

        verify(trackingNumberRepo, never()).save(any());
    }

    @Test
    public void testGenerateTrackingNumber_SaveFailure() {
        String trackingNo = "SAVEFAIL12345678";
        when(trackingNumberGenerator.generateTrackingNumber()).thenReturn(trackingNo);
        when(trackingNumberRepo.existsByTrackingNumber(trackingNo)).thenReturn(false);
        doThrow(new RuntimeException("DB error")).when(trackingNumberRepo).save(any(TrackingNumber.class));

        assertThrows(InternalServerErrorException.class, () -> trackingNumberService.generateTrackingNumber(
                "MY", "SG", new BigDecimal("1.234"), OffsetDateTime.now(),
                UUID.randomUUID(), "Alice", "alice"
        ));
    }
}

