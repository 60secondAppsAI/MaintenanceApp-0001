package com.MaintenanceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseMaintenanceTaskDTO {

	private Integer ownerId;

	private Integer maintenanceTaskId;

	private Integer maintenanceTaskStatus;
	
	private Integer nextOrPrevious;
}

