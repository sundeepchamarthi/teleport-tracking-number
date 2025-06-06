package com.teleport.TrackingNumberService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tracking_numbers")
public class TrackingNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "tracking_number_id")
    private Long trackingNumberId;

    @Column(name = "tracking_number", unique = true, length = 16, nullable = false)
    @Size(min = 16, max = 16, message = "Tracking number must be exactly 16 characters")
    @Pattern(regexp = "^[A-Z0-9]{16}$", message = "Tracking number must be alphanumeric uppercase")
    private String trackingNumber;

    @Column(name = "origin_country")
    @NotBlank(message = "Origin country ID is required")
    @Size(min = 2, message = "Origin country ID must be at least 2 characters")
    private String originCountryId;

    @Column(name = "destination_country")
    @NotBlank(message = "Destination country ID is required")
    @Size(min = 2, message = "Destination country ID must be at least 2 characters")
    private String destinationCountryId;

    @Column(name = "weight")
    @Positive(message = "Weight must be greater than 0")
    private BigDecimal weight;

    @Column(name= "created_at")
    @NotNull(message = "Created at timestamp is required")
    private OffsetDateTime createdAt;

    @Column(name= "customer_id", unique = true, nullable = false)
    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    @Column(name= "customer_name")
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Column(name= "customer_slug")
    @NotBlank(message = "Customer slug is required")
    private String customerSlug;

}
