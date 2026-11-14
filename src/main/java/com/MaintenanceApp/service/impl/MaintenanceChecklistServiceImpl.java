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
import com.MaintenanceApp.dao.MaintenanceChecklistDAO;
import com.MaintenanceApp.domain.MaintenanceChecklist;
import com.MaintenanceApp.dto.MaintenanceChecklistDTO;
import com.MaintenanceApp.dto.MaintenanceChecklistSearchDTO;
import com.MaintenanceApp.dto.MaintenanceChecklistPageDTO;
import com.MaintenanceApp.dto.MaintenanceChecklistConvertCriteriaDTO;
import com.MaintenanceApp.dto.common.RequestDTO;
import com.MaintenanceApp.dto.common.ResultDTO;
import com.MaintenanceApp.service.MaintenanceChecklistService;
import com.MaintenanceApp.util.ControllerUtils;


@Service
public class MaintenanceChecklistServiceImpl extends GenericServiceImpl<MaintenanceChecklist, Integer> implements MaintenanceChecklistService {

    private final static Logger logger = LoggerFactory.getLogger(MaintenanceChecklistServiceImpl.class);

	@Autowired
	MaintenanceChecklistDAO maintenanceChecklistDao;

	

	@Override
	public GenericDAO<MaintenanceChecklist, Integer> getDAO() {
		return (GenericDAO<MaintenanceChecklist, Integer>) maintenanceChecklistDao;
	}
	
	public List<MaintenanceChecklist> findAll () {
		List<MaintenanceChecklist> maintenanceChecklists = maintenanceChecklistDao.findAll();
		
		return maintenanceChecklists;	
		
	}

	public ResultDTO addMaintenanceChecklist(MaintenanceChecklistDTO maintenanceChecklistDTO, RequestDTO requestDTO) {

		MaintenanceChecklist maintenanceChecklist = new MaintenanceChecklist();

		maintenanceChecklist.setMaintenanceChecklistId(maintenanceChecklistDTO.getMaintenanceChecklistId());

		maintenanceChecklist.setMaintenanceTaskId(maintenanceChecklistDTO.getMaintenanceTaskId());

		maintenanceChecklist.setItemDescription(maintenanceChecklistDTO.getItemDescription());

		maintenanceChecklist.setCompleted(maintenanceChecklistDTO.getCompleted());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		maintenanceChecklist = maintenanceChecklistDao.save(maintenanceChecklist);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<MaintenanceChecklist> getAllMaintenanceChecklists(Pageable pageable) {
		return maintenanceChecklistDao.findAll(pageable);
	}

	public Page<MaintenanceChecklist> getAllMaintenanceChecklists(Specification<MaintenanceChecklist> spec, Pageable pageable) {
		return maintenanceChecklistDao.findAll(spec, pageable);
	}

	public ResponseEntity<MaintenanceChecklistPageDTO> getMaintenanceChecklists(MaintenanceChecklistSearchDTO maintenanceChecklistSearchDTO) {
	
			Integer maintenanceChecklistId = maintenanceChecklistSearchDTO.getMaintenanceChecklistId(); 
 			Integer maintenanceTaskId = maintenanceChecklistSearchDTO.getMaintenanceTaskId(); 
 			String itemDescription = maintenanceChecklistSearchDTO.getItemDescription(); 
  			String sortBy = maintenanceChecklistSearchDTO.getSortBy();
			String sortOrder = maintenanceChecklistSearchDTO.getSortOrder();
			String searchQuery = maintenanceChecklistSearchDTO.getSearchQuery();
			Integer page = maintenanceChecklistSearchDTO.getPage();
			Integer size = maintenanceChecklistSearchDTO.getSize();

	        Specification<MaintenanceChecklist> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, maintenanceChecklistId, "maintenanceChecklistId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, maintenanceTaskId, "maintenanceTaskId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, itemDescription, "itemDescription"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("itemDescription")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<MaintenanceChecklist> maintenanceChecklists = this.getAllMaintenanceChecklists(spec, pageable);
		
		//System.out.println(String.valueOf(maintenanceChecklists.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(maintenanceChecklists.getTotalPages()));
		
		List<MaintenanceChecklist> maintenanceChecklistsList = maintenanceChecklists.getContent();
		
		MaintenanceChecklistConvertCriteriaDTO convertCriteria = new MaintenanceChecklistConvertCriteriaDTO();
		List<MaintenanceChecklistDTO> maintenanceChecklistDTOs = this.convertMaintenanceChecklistsToMaintenanceChecklistDTOs(maintenanceChecklistsList,convertCriteria);
		
		MaintenanceChecklistPageDTO maintenanceChecklistPageDTO = new MaintenanceChecklistPageDTO();
		maintenanceChecklistPageDTO.setMaintenanceChecklists(maintenanceChecklistDTOs);
		maintenanceChecklistPageDTO.setTotalElements(maintenanceChecklists.getTotalElements());
		return ResponseEntity.ok(maintenanceChecklistPageDTO);
	}

	public List<MaintenanceChecklistDTO> convertMaintenanceChecklistsToMaintenanceChecklistDTOs(List<MaintenanceChecklist> maintenanceChecklists, MaintenanceChecklistConvertCriteriaDTO convertCriteria) {
		
		List<MaintenanceChecklistDTO> maintenanceChecklistDTOs = new ArrayList<MaintenanceChecklistDTO>();
		
		for (MaintenanceChecklist maintenanceChecklist : maintenanceChecklists) {
			maintenanceChecklistDTOs.add(convertMaintenanceChecklistToMaintenanceChecklistDTO(maintenanceChecklist,convertCriteria));
		}
		
		return maintenanceChecklistDTOs;

	}
	
	public MaintenanceChecklistDTO convertMaintenanceChecklistToMaintenanceChecklistDTO(MaintenanceChecklist maintenanceChecklist, MaintenanceChecklistConvertCriteriaDTO convertCriteria) {
		
		MaintenanceChecklistDTO maintenanceChecklistDTO = new MaintenanceChecklistDTO();

		maintenanceChecklistDTO.setMaintenanceChecklistId(maintenanceChecklist.getMaintenanceChecklistId());

		maintenanceChecklistDTO.setMaintenanceTaskId(maintenanceChecklist.getMaintenanceTaskId());

		maintenanceChecklistDTO.setItemDescription(maintenanceChecklist.getItemDescription());

		maintenanceChecklistDTO.setCompleted(maintenanceChecklist.getCompleted());
		
		return maintenanceChecklistDTO;
	}

	public ResultDTO updateMaintenanceChecklist(MaintenanceChecklistDTO maintenanceChecklistDTO, RequestDTO requestDTO) {
		
		MaintenanceChecklist maintenanceChecklist = maintenanceChecklistDao.getById(maintenanceChecklistDTO.getMaintenanceChecklistId());
		
		maintenanceChecklist.setMaintenanceChecklistId(ControllerUtils.setValue(maintenanceChecklist.getMaintenanceChecklistId(), maintenanceChecklistDTO.getMaintenanceChecklistId()));
		
		maintenanceChecklist.setMaintenanceTaskId(ControllerUtils.setValue(maintenanceChecklist.getMaintenanceTaskId(), maintenanceChecklistDTO.getMaintenanceTaskId()));
		
		maintenanceChecklist.setItemDescription(ControllerUtils.setValue(maintenanceChecklist.getItemDescription(), maintenanceChecklistDTO.getItemDescription()));
		
		maintenanceChecklist.setCompleted(ControllerUtils.setValue(maintenanceChecklist.getCompleted(), maintenanceChecklistDTO.getCompleted()));

        maintenanceChecklist = maintenanceChecklistDao.save(maintenanceChecklist);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public MaintenanceChecklistDTO getMaintenanceChecklistDTOById(Integer maintenanceChecklistId) {
	
		MaintenanceChecklist maintenanceChecklist = maintenanceChecklistDao.getById(maintenanceChecklistId);
		
		MaintenanceChecklistConvertCriteriaDTO convertCriteria = new MaintenanceChecklistConvertCriteriaDTO();
		return(this.convertMaintenanceChecklistToMaintenanceChecklistDTO(maintenanceChecklist,convertCriteria));
	}

}
