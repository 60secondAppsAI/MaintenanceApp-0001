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

import com.MaintenanceApp.domain.MaintenanceTask;
import com.MaintenanceApp.dto.MaintenanceTaskDTO;
import com.MaintenanceApp.dto.MaintenanceTaskSearchDTO;
import com.MaintenanceApp.dto.MaintenanceTaskPageDTO;
import com.MaintenanceApp.service.MaintenanceTaskService;
import com.MaintenanceApp.dto.common.RequestDTO;
import com.MaintenanceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/maintenanceTask")
@RestController
public class MaintenanceTaskController {

	private final static Logger logger = LoggerFactory.getLogger(MaintenanceTaskController.class);

	@Autowired
	MaintenanceTaskService maintenanceTaskService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<MaintenanceTask> getAll() {

		List<MaintenanceTask> maintenanceTasks = maintenanceTaskService.findAll();
		
		return maintenanceTasks;	
	}

	@GetMapping(value = "/{maintenanceTaskId}")
	@ResponseBody
	public MaintenanceTaskDTO getMaintenanceTask(@PathVariable Integer maintenanceTaskId) {
		
		return (maintenanceTaskService.getMaintenanceTaskDTOById(maintenanceTaskId));
	}

 	@RequestMapping(value = "/addMaintenanceTask", method = RequestMethod.POST)
	public ResponseEntity<?> addMaintenanceTask(@RequestBody MaintenanceTaskDTO maintenanceTaskDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = maintenanceTaskService.addMaintenanceTask(maintenanceTaskDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/maintenanceTasks")
	public ResponseEntity<MaintenanceTaskPageDTO> getMaintenanceTasks(MaintenanceTaskSearchDTO maintenanceTaskSearchDTO) {
 
		return maintenanceTaskService.getMaintenanceTasks(maintenanceTaskSearchDTO);
	}	

	@RequestMapping(value = "/updateMaintenanceTask", method = RequestMethod.POST)
	public ResponseEntity<?> updateMaintenanceTask(@RequestBody MaintenanceTaskDTO maintenanceTaskDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = maintenanceTaskService.updateMaintenanceTask(maintenanceTaskDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
