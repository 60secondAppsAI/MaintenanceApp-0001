package com.MaintenanceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class MaintenanceRecordDTO {

	private Integer maintenanceRecordId;

	private Integer maintenanceTaskId;

	private Date completedDate;

	private String technician;

	private String notes;






}
