package com.MaintenanceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.MaintenanceApp.domain.MaintenanceChecklist;
import com.MaintenanceApp.dto.MaintenanceChecklistDTO;
import com.MaintenanceApp.dto.MaintenanceChecklistSearchDTO;
import com.MaintenanceApp.dto.MaintenanceChecklistPageDTO;
import com.MaintenanceApp.dto.MaintenanceChecklistConvertCriteriaDTO;
import com.MaintenanceApp.service.GenericService;
import com.MaintenanceApp.dto.common.RequestDTO;
import com.MaintenanceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface MaintenanceChecklistService extends GenericService<MaintenanceChecklist, Integer> {

	List<MaintenanceChecklist> findAll();

	ResultDTO addMaintenanceChecklist(MaintenanceChecklistDTO maintenanceChecklistDTO, RequestDTO requestDTO);

	ResultDTO updateMaintenanceChecklist(MaintenanceChecklistDTO maintenanceChecklistDTO, RequestDTO requestDTO);

    Page<MaintenanceChecklist> getAllMaintenanceChecklists(Pageable pageable);

    Page<MaintenanceChecklist> getAllMaintenanceChecklists(Specification<MaintenanceChecklist> spec, Pageable pageable);

	ResponseEntity<MaintenanceChecklistPageDTO> getMaintenanceChecklists(MaintenanceChecklistSearchDTO maintenanceChecklistSearchDTO);
	
	List<MaintenanceChecklistDTO> convertMaintenanceChecklistsToMaintenanceChecklistDTOs(List<MaintenanceChecklist> maintenanceChecklists, MaintenanceChecklistConvertCriteriaDTO convertCriteria);

	MaintenanceChecklistDTO getMaintenanceChecklistDTOById(Integer maintenanceChecklistId);



	
}
