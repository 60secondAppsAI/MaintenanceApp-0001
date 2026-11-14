package com.MaintenanceApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MaintenanceTaskSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer maintenanceTaskId;
	
	private Integer maintenancePlanId;
	
	private String assetReference;
	
	private Date scheduledDate;
	
	private String status;
	
}
