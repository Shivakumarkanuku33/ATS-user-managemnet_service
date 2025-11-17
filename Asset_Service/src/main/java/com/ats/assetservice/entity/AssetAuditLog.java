package com.ats.assetservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "asset_audit_logs")
@Data
public class AssetAuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long assetId;

    private String action;   
    // CREATE, UPDATE, DELETE
    
    private String fieldName;
    
    @Column(length = 1000)
    private String oldValue;
    
    @Column(length = 1000)
    private String newValue;

    private String changedBy;       // username (from JWT)
    private LocalDateTime changedAt;
}
