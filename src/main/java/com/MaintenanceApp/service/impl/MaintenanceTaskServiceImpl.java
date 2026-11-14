package com.MaintenanceApp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.MaintenanceApp.dao.GenericDAO;
import com.MaintenanceApp.service.GenericService;
import com.MaintenanceApp.service.impl.GenericServiceImpl;
import com.MaintenanceApp.dao.MaintenanceTaskDAO;
import com.MaintenanceApp.domain.MaintenanceTask;
import com.MaintenanceApp.dto.MaintenanceTaskDTO;
import com.MaintenanceApp.dto.MaintenanceTaskSearchDTO;
import com.MaintenanceApp.dto.MaintenanceTaskPageDTO;
import com.MaintenanceApp.dto.MaintenanceTaskConvertCriteriaDTO;
import com.MaintenanceApp.dto.common.RequestDTO;
import com.MaintenanceApp.dto.common.ResultDTO;
import com.MaintenanceApp.service.MaintenanceTaskService;
import com.MaintenanceApp.util.ControllerUtils;


@Service
public class MaintenanceTaskServiceImpl extends GenericServiceImpl<MaintenanceTask, Integer> implements MaintenanceTaskService {

    private final static Logger logger = LoggerFactory.getLogger(MaintenanceTaskServiceImpl.class);

	@Autowired
	MaintenanceTaskDAO maintenanceTaskDao;

	

	@Override
	public GenericDAO<MaintenanceTask, Integer> getDAO() {
		return (GenericDAO<MaintenanceTask, Integer>) maintenanceTaskDao;
	}
	
	public List<MaintenanceTask> findAll () {
		List<MaintenanceTask> maintenanceTasks = maintenanceTaskDao.findAll();
		
		return maintenanceTasks;	
		
	}

	public ResultDTO addMaintenanceTask(MaintenanceTaskDTO maintenanceTaskDTO, RequestDTO requestDTO) {

		MaintenanceTask maintenanceTask = new MaintenanceTask();

		maintenanceTask.setMaintenanceTaskId(maintenanceTaskDTO.getMaintenanceTaskId());

		maintenanceTask.setMaintenancePlanId(maintenanceTaskDTO.getMaintenancePlanId());

		maintenanceTask.setAssetReference(maintenanceTaskDTO.getAssetReference());

		maintenanceTask.setScheduledDate(maintenanceTaskDTO.getScheduledDate());

		maintenanceTask.setStatus(maintenanceTaskDTO.getStatus());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		maintenanceTask = maintenanceTaskDao.save(maintenanceTask);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<MaintenanceTask> getAllMaintenanceTasks(Pageable pageable) {
		return maintenanceTaskDao.findAll(pageable);
	}

	public Page<MaintenanceTask> getAllMaintenanceTasks(Specification<MaintenanceTask> spec, Pageable pageable) {
		return maintenanceTaskDao.findAll(spec, pageable);
	}

	public ResponseEntity<MaintenanceTaskPageDTO> getMaintenanceTasks(MaintenanceTaskSearchDTO maintenanceTaskSearchDTO) {
	
			Integer maintenanceTaskId = maintenanceTaskSearchDTO.getMaintenanceTaskId(); 
 			Integer maintenancePlanId = maintenanceTaskSearchDTO.getMaintenancePlanId(); 
 			String assetReference = maintenanceTaskSearchDTO.getAssetReference(); 
 			Date scheduledDate = maintenanceTaskSearchDTO.getScheduledDate();
 			String status = maintenanceTaskSearchDTO.getStatus(); 
 			String sortBy = maintenanceTaskSearchDTO.getSortBy();
			String sortOrder = maintenanceTaskSearchDTO.getSortOrder();
			String searchQuery = maintenanceTaskSearchDTO.getSearchQuery();
			Integer page = maintenanceTaskSearchDTO.getPage();
			Integer size = maintenanceTaskSearchDTO.getSize();

	        Specification<MaintenanceTask> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, maintenanceTaskId, "maintenanceTaskId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, maintenancePlanId, "maintenancePlanId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, assetReference, "assetReference"); 
			
			spec = ControllerUtils.andIfNecessary(spec, scheduledDate, "scheduledDate");
			
			spec = ControllerUtils.andIfNecessary(spec, status, "status"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("assetReference")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("status")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<MaintenanceTask> maintenanceTasks = this.getAllMaintenanceTasks(spec, pageable);
		
		//System.out.println(String.valueOf(maintenanceTasks.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(maintenanceTasks.getTotalPages()));
		
		List<MaintenanceTask> maintenanceTasksList = maintenanceTasks.getContent();
		
		MaintenanceTaskConvertCriteriaDTO convertCriteria = new MaintenanceTaskConvertCriteriaDTO();
		List<MaintenanceTaskDTO> maintenanceTaskDTOs = this.convertMaintenanceTasksToMaintenanceTaskDTOs(maintenanceTasksList,convertCriteria);
		
		MaintenanceTaskPageDTO maintenanceTaskPageDTO = new MaintenanceTaskPageDTO();
		maintenanceTaskPageDTO.setMaintenanceTasks(maintenanceTaskDTOs);
		maintenanceTaskPageDTO.setTotalElements(maintenanceTasks.getTotalElements());
		return ResponseEntity.ok(maintenanceTaskPageDTO);
	}

	public List<MaintenanceTaskDTO> convertMaintenanceTasksToMaintenanceTaskDTOs(List<MaintenanceTask> maintenanceTasks, MaintenanceTaskConvertCriteriaDTO convertCriteria) {
		
		List<MaintenanceTaskDTO> maintenanceTaskDTOs = new ArrayList<MaintenanceTaskDTO>();
		
		for (MaintenanceTask maintenanceTask : maintenanceTasks) {
			maintenanceTaskDTOs.add(convertMaintenanceTaskToMaintenanceTaskDTO(maintenanceTask,convertCriteria));
		}
		
		return maintenanceTaskDTOs;

	}
	
	public MaintenanceTaskDTO convertMaintenanceTaskToMaintenanceTaskDTO(MaintenanceTask maintenanceTask, MaintenanceTaskConvertCriteriaDTO convertCriteria) {
		
		MaintenanceTaskDTO maintenanceTaskDTO = new MaintenanceTaskDTO();

		maintenanceTaskDTO.setMaintenanceTaskId(maintenanceTask.getMaintenanceTaskId());

		maintenanceTaskDTO.setMaintenancePlanId(maintenanceTask.getMaintenancePlanId());

		maintenanceTaskDTO.setAssetReference(maintenanceTask.getAssetReference());

		maintenanceTaskDTO.setScheduledDate(maintenanceTask.getScheduledDate());

		maintenanceTaskDTO.setStatus(maintenanceTask.getStatus());
		
		return maintenanceTaskDTO;
	}

	public ResultDTO updateMaintenanceTask(MaintenanceTaskDTO maintenanceTaskDTO, RequestDTO requestDTO) {
		
		MaintenanceTask maintenanceTask = maintenanceTaskDao.getById(maintenanceTaskDTO.getMaintenanceTaskId());
		
		maintenanceTask.setMaintenanceTaskId(ControllerUtils.setValue(maintenanceTask.getMaintenanceTaskId(), maintenanceTaskDTO.getMaintenanceTaskId()));
		
		maintenanceTask.setMaintenancePlanId(ControllerUtils.setValue(maintenanceTask.getMaintenancePlanId(), maintenanceTaskDTO.getMaintenancePlanId()));
		
		maintenanceTask.setAssetReference(ControllerUtils.setValue(maintenanceTask.getAssetReference(), maintenanceTaskDTO.getAssetReference()));
		
		maintenanceTask.setScheduledDate(ControllerUtils.setValue(maintenanceTask.getScheduledDate(), maintenanceTaskDTO.getScheduledDate()));
		
		maintenanceTask.setStatus(ControllerUtils.setValue(maintenanceTask.getStatus(), maintenanceTaskDTO.getStatus()));

        maintenanceTask = maintenanceTaskDao.save(maintenanceTask);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public MaintenanceTaskDTO getMaintenanceTaskDTOById(Integer maintenanceTaskId) {
	
		MaintenanceTask maintenanceTask = maintenanceTaskDao.getById(maintenanceTaskId);
		
		MaintenanceTaskConvertCriteriaDTO convertCriteria = new MaintenanceTaskConvertCriteriaDTO();
		return(this.convertMaintenanceTaskToMaintenanceTaskDTO(maintenanceTask,convertCriteria));
	}

}
