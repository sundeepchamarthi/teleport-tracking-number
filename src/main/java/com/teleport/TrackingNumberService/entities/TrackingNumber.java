package com.teleport.TrackingNumberService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tracking_numbers")
public class TrackingNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "tracking_number_id")
    private Long trackingNumberId;

    @Column(name= "tracking_number")
    private String trackingNumber;

    @Column(name= "origin_country")
    @NotBlank(message = "Origin country ID is required")
    private String originCountry;

    @Column(name= "destination_country")
    @NotBlank(message = "Destination country ID is required")
    private String destinationCountry;

    @Column(name= "weight")
    @Positive(message = "Weight must be greater than 0")
    private float weight;

    @Column(name= "created_at")
    @NotNull(message = "Created at timestamp is required")
    private OffsetDateTime createdAt;

    @Column(name= "customer_id")
    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    @Column(name= "customer_name")
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @Column(name= "customer_slug")
    @NotBlank(message = "Customer slug is required")
    private String customerSlug;

}
