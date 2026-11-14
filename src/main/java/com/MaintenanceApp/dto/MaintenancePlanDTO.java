package com.MaintenanceApp.dto;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import java.time.Year;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class MaintenancePlanDTO {

	private Integer maintenancePlanId;

	private String name;

	private String assetType;

	private Integer frequencyDays;

	private Boolean active;






}
