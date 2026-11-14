package com.MaintenanceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class MaintenanceChecklistDTO {

	private Integer maintenanceChecklistId;

	private Integer maintenanceTaskId;

	private String itemDescription;

	private Boolean completed;






}
