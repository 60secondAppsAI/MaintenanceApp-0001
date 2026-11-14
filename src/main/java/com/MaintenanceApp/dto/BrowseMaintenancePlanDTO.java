package com.MaintenanceApp.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class BrowseMaintenancePlanDTO {

	private Integer ownerId;

	private Integer maintenancePlanId;

	private Integer maintenancePlanStatus;
	
	private Integer nextOrPrevious;
}

