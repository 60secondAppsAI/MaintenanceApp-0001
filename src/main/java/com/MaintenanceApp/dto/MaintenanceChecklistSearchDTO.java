package com.MaintenanceApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MaintenanceChecklistSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer maintenanceChecklistId;
	
	private Integer maintenanceTaskId;
	
	private String itemDescription;
	
	private Boolean completed;
	
}
