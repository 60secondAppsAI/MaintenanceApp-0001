package com.MaintenanceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseMaintenanceChecklistDTO {

	private Integer ownerId;

	private Integer maintenanceChecklistId;

	private Integer maintenanceChecklistStatus;
	
	private Integer nextOrPrevious;
}

