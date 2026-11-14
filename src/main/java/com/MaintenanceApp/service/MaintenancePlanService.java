package com.MaintenanceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.MaintenanceApp.domain.MaintenancePlan;
import com.MaintenanceApp.dto.MaintenancePlanDTO;
import com.MaintenanceApp.dto.MaintenancePlanSearchDTO;
import com.MaintenanceApp.dto.MaintenancePlanPageDTO;
import com.MaintenanceApp.dto.MaintenancePlanConvertCriteriaDTO;
import com.MaintenanceApp.service.GenericService;
import com.MaintenanceApp.dto.common.RequestDTO;
import com.MaintenanceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface MaintenancePlanService extends GenericService<MaintenancePlan, Integer> {

	List<MaintenancePlan> findAll();

	ResultDTO addMaintenancePlan(MaintenancePlanDTO maintenancePlanDTO, RequestDTO requestDTO);

	ResultDTO updateMaintenancePlan(MaintenancePlanDTO maintenancePlanDTO, RequestDTO requestDTO);

    Page<MaintenancePlan> getAllMaintenancePlans(Pageable pageable);

    Page<MaintenancePlan> getAllMaintenancePlans(Specification<MaintenancePlan> spec, Pageable pageable);

	ResponseEntity<MaintenancePlanPageDTO> getMaintenancePlans(MaintenancePlanSearchDTO maintenancePlanSearchDTO);
	
	List<MaintenancePlanDTO> convertMaintenancePlansToMaintenancePlanDTOs(List<MaintenancePlan> maintenancePlans, MaintenancePlanConvertCriteriaDTO convertCriteria);

	MaintenancePlanDTO getMaintenancePlanDTOById(Integer maintenancePlanId);



	
}
