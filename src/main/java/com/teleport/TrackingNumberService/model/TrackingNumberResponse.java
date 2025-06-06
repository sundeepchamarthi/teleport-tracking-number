package com.teleport.TrackingNumberService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingNumberResponse {

    private String tracking_number;

    private OffsetDateTime created_at;

    private UUID customer_id;

    private String customer_name;

    private String origin_country_id;

    private String destination_country_id;
}
