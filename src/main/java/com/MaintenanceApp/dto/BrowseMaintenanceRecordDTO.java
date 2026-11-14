package com.MaintenanceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseMaintenanceRecordDTO {

	private Integer ownerId;

	private Integer maintenanceRecordId;

	private Integer maintenanceRecordStatus;
	
	private Integer nextOrPrevious;
}

