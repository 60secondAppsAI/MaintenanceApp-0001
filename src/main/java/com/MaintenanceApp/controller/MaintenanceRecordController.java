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

import com.MaintenanceApp.domain.MaintenanceRecord;
import com.MaintenanceApp.dto.MaintenanceRecordDTO;
import com.MaintenanceApp.dto.MaintenanceRecordSearchDTO;
import com.MaintenanceApp.dto.MaintenanceRecordPageDTO;
import com.MaintenanceApp.service.MaintenanceRecordService;
import com.MaintenanceApp.dto.common.RequestDTO;
import com.MaintenanceApp.dto.common.ResultDTO;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;




@CrossOrigin(origins = "*")
@RequestMapping("/maintenanceRecord")
@RestController
public class MaintenanceRecordController {

	private final static Logger logger = LoggerFactory.getLogger(MaintenanceRecordController.class);

	@Autowired
	MaintenanceRecordService maintenanceRecordService;



	@RequestMapping(value="/", method = RequestMethod.GET)
	public List<MaintenanceRecord> getAll() {

		List<MaintenanceRecord> maintenanceRecords = maintenanceRecordService.findAll();
		
		return maintenanceRecords;	
	}

	@GetMapping(value = "/{maintenanceRecordId}")
	@ResponseBody
	public MaintenanceRecordDTO getMaintenanceRecord(@PathVariable Integer maintenanceRecordId) {
		
		return (maintenanceRecordService.getMaintenanceRecordDTOById(maintenanceRecordId));
	}

 	@RequestMapping(value = "/addMaintenanceRecord", method = RequestMethod.POST)
	public ResponseEntity<?> addMaintenanceRecord(@RequestBody MaintenanceRecordDTO maintenanceRecordDTO, HttpServletRequest request) {

		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = maintenanceRecordService.addMaintenanceRecord(maintenanceRecordDTO, requestDTO);
		
		return result.asResponseEntity();
	}

	@GetMapping("/maintenanceRecords")
	public ResponseEntity<MaintenanceRecordPageDTO> getMaintenanceRecords(MaintenanceRecordSearchDTO maintenanceRecordSearchDTO) {
 
		return maintenanceRecordService.getMaintenanceRecords(maintenanceRecordSearchDTO);
	}	

	@RequestMapping(value = "/updateMaintenanceRecord", method = RequestMethod.POST)
	public ResponseEntity<?> updateMaintenanceRecord(@RequestBody MaintenanceRecordDTO maintenanceRecordDTO, HttpServletRequest request) {
		RequestDTO requestDTO = new RequestDTO(request);
		ResultDTO result = maintenanceRecordService.updateMaintenanceRecord(maintenanceRecordDTO, requestDTO);
		
//		if (result.isSuccessful()) {
//		}

		return result.asResponseEntity();
	}





}
