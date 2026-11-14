package com.MaintenanceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class MaintenanceTaskDTO {

	private Integer maintenanceTaskId;

	private Integer maintenancePlanId;

	private String assetReference;

	private Date scheduledDate;

	private String status;






}
