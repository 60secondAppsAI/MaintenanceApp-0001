package com.MaintenanceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.MaintenanceApp.domain.MaintenanceRecord;
import com.MaintenanceApp.dto.MaintenanceRecordDTO;
import com.MaintenanceApp.dto.MaintenanceRecordSearchDTO;
import com.MaintenanceApp.dto.MaintenanceRecordPageDTO;
import com.MaintenanceApp.dto.MaintenanceRecordConvertCriteriaDTO;
import com.MaintenanceApp.service.GenericService;
import com.MaintenanceApp.dto.common.RequestDTO;
import com.MaintenanceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface MaintenanceRecordService extends GenericService<MaintenanceRecord, Integer> {

	List<MaintenanceRecord> findAll();

	ResultDTO addMaintenanceRecord(MaintenanceRecordDTO maintenanceRecordDTO, RequestDTO requestDTO);

	ResultDTO updateMaintenanceRecord(MaintenanceRecordDTO maintenanceRecordDTO, RequestDTO requestDTO);

    Page<MaintenanceRecord> getAllMaintenanceRecords(Pageable pageable);

    Page<MaintenanceRecord> getAllMaintenanceRecords(Specification<MaintenanceRecord> spec, Pageable pageable);

	ResponseEntity<MaintenanceRecordPageDTO> getMaintenanceRecords(MaintenanceRecordSearchDTO maintenanceRecordSearchDTO);
	
	List<MaintenanceRecordDTO> convertMaintenanceRecordsToMaintenanceRecordDTOs(List<MaintenanceRecord> maintenanceRecords, MaintenanceRecordConvertCriteriaDTO convertCriteria);

	MaintenanceRecordDTO getMaintenanceRecordDTOById(Integer maintenanceRecordId);



	
}
