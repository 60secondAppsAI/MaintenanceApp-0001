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
import com.MaintenanceApp.dao.MaintenancePlanDAO;
import com.MaintenanceApp.domain.MaintenancePlan;
import com.MaintenanceApp.dto.MaintenancePlanDTO;
import com.MaintenanceApp.dto.MaintenancePlanSearchDTO;
import com.MaintenanceApp.dto.MaintenancePlanPageDTO;
import com.MaintenanceApp.dto.MaintenancePlanConvertCriteriaDTO;
import com.MaintenanceApp.dto.common.RequestDTO;
import com.MaintenanceApp.dto.common.ResultDTO;
import com.MaintenanceApp.service.MaintenancePlanService;
import com.MaintenanceApp.util.ControllerUtils;


@Service
public class MaintenancePlanServiceImpl extends GenericServiceImpl<MaintenancePlan, Integer> implements MaintenancePlanService {

    private final static Logger logger = LoggerFactory.getLogger(MaintenancePlanServiceImpl.class);

	@Autowired
	MaintenancePlanDAO maintenancePlanDao;

	

	@Override
	public GenericDAO<MaintenancePlan, Integer> getDAO() {
		return (GenericDAO<MaintenancePlan, Integer>) maintenancePlanDao;
	}
	
	public List<MaintenancePlan> findAll () {
		List<MaintenancePlan> maintenancePlans = maintenancePlanDao.findAll();
		
		return maintenancePlans;	
		
	}

	public ResultDTO addMaintenancePlan(MaintenancePlanDTO maintenancePlanDTO, RequestDTO requestDTO) {

		MaintenancePlan maintenancePlan = new MaintenancePlan();

		maintenancePlan.setMaintenancePlanId(maintenancePlanDTO.getMaintenancePlanId());

		maintenancePlan.setName(maintenancePlanDTO.getName());

		maintenancePlan.setAssetType(maintenancePlanDTO.getAssetType());

		maintenancePlan.setFrequencyDays(maintenancePlanDTO.getFrequencyDays());

		maintenancePlan.setActive(maintenancePlanDTO.getActive());

		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
		
		maintenancePlan = maintenancePlanDao.save(maintenancePlan);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<MaintenancePlan> getAllMaintenancePlans(Pageable pageable) {
		return maintenancePlanDao.findAll(pageable);
	}

	public Page<MaintenancePlan> getAllMaintenancePlans(Specification<MaintenancePlan> spec, Pageable pageable) {
		return maintenancePlanDao.findAll(spec, pageable);
	}

	public ResponseEntity<MaintenancePlanPageDTO> getMaintenancePlans(MaintenancePlanSearchDTO maintenancePlanSearchDTO) {
	
			Integer maintenancePlanId = maintenancePlanSearchDTO.getMaintenancePlanId(); 
 			String name = maintenancePlanSearchDTO.getName(); 
 			String assetType = maintenancePlanSearchDTO.getAssetType(); 
 			Integer frequencyDays = maintenancePlanSearchDTO.getFrequencyDays(); 
  			String sortBy = maintenancePlanSearchDTO.getSortBy();
			String sortOrder = maintenancePlanSearchDTO.getSortOrder();
			String searchQuery = maintenancePlanSearchDTO.getSearchQuery();
			Integer page = maintenancePlanSearchDTO.getPage();
			Integer size = maintenancePlanSearchDTO.getSize();

	        Specification<MaintenancePlan> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, maintenancePlanId, "maintenancePlanId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, name, "name"); 
			
			spec = ControllerUtils.andIfNecessary(spec, assetType, "assetType"); 
			
			spec = ControllerUtils.andIfNecessary(spec, frequencyDays, "frequencyDays"); 
			
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("name")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("assetType")), "%" + searchQuery.toLowerCase() + "%") 
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

		Page<MaintenancePlan> maintenancePlans = this.getAllMaintenancePlans(spec, pageable);
		
		//System.out.println(String.valueOf(maintenancePlans.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(maintenancePlans.getTotalPages()));
		
		List<MaintenancePlan> maintenancePlansList = maintenancePlans.getContent();
		
		MaintenancePlanConvertCriteriaDTO convertCriteria = new MaintenancePlanConvertCriteriaDTO();
		List<MaintenancePlanDTO> maintenancePlanDTOs = this.convertMaintenancePlansToMaintenancePlanDTOs(maintenancePlansList,convertCriteria);
		
		MaintenancePlanPageDTO maintenancePlanPageDTO = new MaintenancePlanPageDTO();
		maintenancePlanPageDTO.setMaintenancePlans(maintenancePlanDTOs);
		maintenancePlanPageDTO.setTotalElements(maintenancePlans.getTotalElements());
		return ResponseEntity.ok(maintenancePlanPageDTO);
	}

	public List<MaintenancePlanDTO> convertMaintenancePlansToMaintenancePlanDTOs(List<MaintenancePlan> maintenancePlans, MaintenancePlanConvertCriteriaDTO convertCriteria) {
		
		List<MaintenancePlanDTO> maintenancePlanDTOs = new ArrayList<MaintenancePlanDTO>();
		
		for (MaintenancePlan maintenancePlan : maintenancePlans) {
			maintenancePlanDTOs.add(convertMaintenancePlanToMaintenancePlanDTO(maintenancePlan,convertCriteria));
		}
		
		return maintenancePlanDTOs;

	}
	
	public MaintenancePlanDTO convertMaintenancePlanToMaintenancePlanDTO(MaintenancePlan maintenancePlan, MaintenancePlanConvertCriteriaDTO convertCriteria) {
		
		MaintenancePlanDTO maintenancePlanDTO = new MaintenancePlanDTO();

		maintenancePlanDTO.setMaintenancePlanId(maintenancePlan.getMaintenancePlanId());

		maintenancePlanDTO.setName(maintenancePlan.getName());

		maintenancePlanDTO.setAssetType(maintenancePlan.getAssetType());

		maintenancePlanDTO.setFrequencyDays(maintenancePlan.getFrequencyDays());

		maintenancePlanDTO.setActive(maintenancePlan.getActive());
		
		return maintenancePlanDTO;
	}

	public ResultDTO updateMaintenancePlan(MaintenancePlanDTO maintenancePlanDTO, RequestDTO requestDTO) {
		
		MaintenancePlan maintenancePlan = maintenancePlanDao.getById(maintenancePlanDTO.getMaintenancePlanId());
		
		maintenancePlan.setMaintenancePlanId(ControllerUtils.setValue(maintenancePlan.getMaintenancePlanId(), maintenancePlanDTO.getMaintenancePlanId()));
		
		maintenancePlan.setName(ControllerUtils.setValue(maintenancePlan.getName(), maintenancePlanDTO.getName()));
		
		maintenancePlan.setAssetType(ControllerUtils.setValue(maintenancePlan.getAssetType(), maintenancePlanDTO.getAssetType()));
		
		maintenancePlan.setFrequencyDays(ControllerUtils.setValue(maintenancePlan.getFrequencyDays(), maintenancePlanDTO.getFrequencyDays()));
		
		maintenancePlan.setActive(ControllerUtils.setValue(maintenancePlan.getActive(), maintenancePlanDTO.getActive()));

        maintenancePlan = maintenancePlanDao.save(maintenancePlan);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public MaintenancePlanDTO getMaintenancePlanDTOById(Integer maintenancePlanId) {
	
		MaintenancePlan maintenancePlan = maintenancePlanDao.getById(maintenancePlanId);
		
		MaintenancePlanConvertCriteriaDTO convertCriteria = new MaintenancePlanConvertCriteriaDTO();
		return(this.convertMaintenancePlanToMaintenancePlanDTO(maintenancePlan,convertCriteria));
	}

}
