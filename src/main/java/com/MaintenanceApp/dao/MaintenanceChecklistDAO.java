package com.MaintenanceApp.dao;

import java.util.List;
import java.util.Date;

import com.MaintenanceApp.dao.GenericDAO;
import com.MaintenanceApp.domain.MaintenanceChecklist;




import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface MaintenanceChecklistDAO extends GenericDAO<MaintenanceChecklist, Integer> {
  
	List<MaintenanceChecklist> findAll();
	


}

