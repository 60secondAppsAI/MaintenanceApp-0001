package com.MaintenanceApp.service;

import java.util.List;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.MaintenanceApp.domain.MaintenanceTask;
import com.MaintenanceApp.dto.MaintenanceTaskDTO;
import com.MaintenanceApp.dto.MaintenanceTaskSearchDTO;
import com.MaintenanceApp.dto.MaintenanceTaskPageDTO;
import com.MaintenanceApp.dto.MaintenanceTaskConvertCriteriaDTO;
import com.MaintenanceApp.service.GenericService;
import com.MaintenanceApp.dto.common.RequestDTO;
import com.MaintenanceApp.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface MaintenanceTaskService extends GenericService<MaintenanceTask, Integer> {

	List<MaintenanceTask> findAll();

	ResultDTO addMaintenanceTask(MaintenanceTaskDTO maintenanceTaskDTO, RequestDTO requestDTO);

	ResultDTO updateMaintenanceTask(MaintenanceTaskDTO maintenanceTaskDTO, RequestDTO requestDTO);

    Page<MaintenanceTask> getAllMaintenanceTasks(Pageable pageable);

    Page<MaintenanceTask> getAllMaintenanceTasks(Specification<MaintenanceTask> spec, Pageable pageable);

	ResponseEntity<MaintenanceTaskPageDTO> getMaintenanceTasks(MaintenanceTaskSearchDTO maintenanceTaskSearchDTO);
	
	List<MaintenanceTaskDTO> convertMaintenanceTasksToMaintenanceTaskDTOs(List<MaintenanceTask> maintenanceTasks, MaintenanceTaskConvertCriteriaDTO convertCriteria);

	MaintenanceTaskDTO getMaintenanceTaskDTOById(Integer maintenanceTaskId);



	
}
