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

import com.MaintenanceApp.domain.MaintenanceChecklist;
import com.MaintenanceApp.dto.MaintenanceChecklistDTO;
import com.MaintenanceApp.dto.MaintenanceChecklistSearchDTO;
import com.MaintenanceApp.dto.MaintenanceChecklistPageDTO;
import com.MaintenanceApp.service.MaintenanceChecklistService;
import com.MaintenanceApp.dto.common.RequestDTO;
import com.MaintenanceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/maintenanceChecklist")
@RestController
public class MaintenanceChecklistController {

	private final static Logger logger = LoggerFactory.getLogger(MaintenanceChecklistController.class);

	@Autowired
	MaintenanceChecklistService maintenanceChecklistService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<MaintenanceChecklist> getAll() {

		List<MaintenanceChecklist> maintenanceChecklists = maintenanceChecklistService.findAll();
		
		return maintenanceChecklists;	
	}

	@GetMapping(value = "/{maintenanceChecklistId}")
	@ResponseBody
	public MaintenanceChecklistDTO getMaintenanceChecklist(@PathVariable Integer maintenanceChecklistId) {
		
		return (maintenanceChecklistService.getMaintenanceChecklistDTOById(maintenanceChecklistId));
	}

 	@RequestMapping(value = "/addMaintenanceChecklist", method = RequestMethod.POST)
	public ResponseEntity<?> addMaintenanceChecklist(@RequestBody MaintenanceChecklistDTO maintenanceChecklistDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = maintenanceChecklistService.addMaintenanceChecklist(maintenanceChecklistDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/maintenanceChecklists")
	public ResponseEntity<MaintenanceChecklistPageDTO> getMaintenanceChecklists(MaintenanceChecklistSearchDTO maintenanceChecklistSearchDTO) {
 
		return maintenanceChecklistService.getMaintenanceChecklists(maintenanceChecklistSearchDTO);
	}	

	@RequestMapping(value = "/updateMaintenanceChecklist", method = RequestMethod.POST)
	public ResponseEntity<?> updateMaintenanceChecklist(@RequestBody MaintenanceChecklistDTO maintenanceChecklistDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = maintenanceChecklistService.updateMaintenanceChecklist(maintenanceChecklistDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
