package com.MaintenanceApp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.Year;
import jakarta.persistence.Transient;

@Entity
@Table(name="maintenance_plans")
@Getter @Setter @NoArgsConstructor
public class MaintenancePlan {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	@Column(name="maintenance_plan_id")
	private Integer maintenancePlanId;
    
  	@Column(name="name")
	private String name;
    
  	@Column(name="asset_type")
	private String assetType;
    
  	@Column(name="frequency_days")
	private Integer frequencyDays;
    
  	@Column(name="active")
	private Boolean active;
    

}
