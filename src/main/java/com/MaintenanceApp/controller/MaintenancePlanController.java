package com.MaintenanceApp.controller;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.ZoneId;


import com.MaintenanceApp.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;

import com.MaintenanceApp.domain.MaintenancePlan;
import com.MaintenanceApp.dto.MaintenancePlanDTO;
import com.MaintenanceApp.dto.MaintenancePlanSearchDTO;
import com.MaintenanceApp.dto.MaintenancePlanPageDTO;
import com.MaintenanceApp.service.MaintenancePlanService;
import com.MaintenanceApp.dto.common.RequestDTO;
import com.MaintenanceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/maintenancePlan")
@RestController
public class MaintenancePlanController {

	private final static Logger logger = LoggerFactory.getLogger(MaintenancePlanController.class);

	@Autowired
	MaintenancePlanService maintenancePlanService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<MaintenancePlan> getAll() {

		List<MaintenancePlan> maintenancePlans = maintenancePlanService.findAll();
		
		return maintenancePlans;	
	}

	@GetMapping(value = "/{maintenancePlanId}")
	@ResponseBody
	public MaintenancePlanDTO getMaintenancePlan(@PathVariable Integer maintenancePlanId) {
		
		return (maintenancePlanService.getMaintenancePlanDTOById(maintenancePlanId));
	}

 	@RequestMapping(value = "/addMaintenancePlan", method = RequestMethod.POST)
	public ResponseEntity<?> addMaintenancePlan(@RequestBody MaintenancePlanDTO maintenancePlanDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = maintenancePlanService.addMaintenancePlan(maintenancePlanDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/maintenancePlans")
	public ResponseEntity<MaintenancePlanPageDTO> getMaintenancePlans(MaintenancePlanSearchDTO maintenancePlanSearchDTO) {
 
		return maintenancePlanService.getMaintenancePlans(maintenancePlanSearchDTO);
	}	

	@RequestMapping(value = "/updateMaintenancePlan", method = RequestMethod.POST)
	public ResponseEntity<?> updateMaintenancePlan(@RequestBody MaintenancePlanDTO maintenancePlanDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = maintenancePlanService.updateMaintenancePlan(maintenancePlanDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
