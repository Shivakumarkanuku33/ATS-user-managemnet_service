package com.ats.assetservice.service.impl;

import com.ats.assetservice.dto.AssetRequest;
import com.ats.assetservice.entity.Asset;
import com.ats.assetservice.entity.AssetAuditLog;
import com.ats.assetservice.repository.AssetAuditLogRepository;
import com.ats.assetservice.service.AuditLogService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

	
	private final AssetAuditLogRepository repository;

	@Override
	public void logCreate(Asset asset) {
		// For create, everything is new → field = "ALL"
		saveLog(asset.getId(), "CREATE", "ALL", null, asset.toString(), "system");
	}

	@Override
	public void logUpdate(Asset oldAsset, AssetRequest newData) {

		if (!oldAsset.getName().equals(newData.getName())) {
			saveLog(oldAsset.getId(), "UPDATE", "name", oldAsset.getName(), newData.getName(), "system");
		}

		if (!oldAsset.getCategory().equals(newData.getCategory())) {
			saveLog(oldAsset.getId(), "UPDATE", "category", oldAsset.getCategory(), newData.getCategory(), "system");
		}
	}

	@Override
	public void logDelete(Asset asset) {
		// For delete, old asset data is saved under oldValue
		saveLog(asset.getId(), "DELETE", "ALL", asset.toString(), null, "system");
	}

	private void saveLog(Long assetId, String action, String field, String oldValue, String newValue, String user) {

		try {
			
			AssetAuditLog log = new AssetAuditLog();
			log.setAssetId(assetId);
			log.setAction(action);
			log.setFieldName(field);
			log.setOldValue(oldValue);
			log.setNewValue(newValue);
			log.setChangedBy(user);
			log.setChangedAt(LocalDateTime.now());

			repository.save(log);
			
		} catch (Exception e) {
			System.err.println("Audit log failed: " + e.getMessage());
		}
		
	}
}
