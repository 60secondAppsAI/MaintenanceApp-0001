package com.MaintenanceApp.dto;

import java.sql.Timestamp;
import java.time.Year;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MaintenancePlanSearchDTO {

	private Integer page = 0;
	private Integer size;
	private String sortBy;
	private String sortOrder;
	private String searchQuery;

	private Integer maintenancePlanId;
	
	private String name;
	
	private String assetType;
	
	private Integer frequencyDays;
	
	private Boolean active;
	
}
