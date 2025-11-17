package com.ats.assetservice.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AssetResponse {
    private Long id;
    private String assetTag;
    private String name;
    private String category;
    private String status;
    private LocalDate purchaseDate;
    private Double purchaseCost;
    private String vendor;
    private Integer warrantyMonths;
    private String description;

    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
