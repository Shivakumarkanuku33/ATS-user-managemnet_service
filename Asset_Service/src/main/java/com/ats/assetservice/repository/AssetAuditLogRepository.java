package com.ats.assetservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.assetservice.entity.AssetAuditLog;

public interface AssetAuditLogRepository extends JpaRepository<AssetAuditLog, Long>{

}
