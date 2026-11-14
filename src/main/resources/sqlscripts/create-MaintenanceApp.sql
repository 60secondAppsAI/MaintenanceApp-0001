CREATE DATABASE IF NOT EXISTS MaintenanceApp;

CREATE TABLE MaintenanceApp.maintenance_plans (
    `maintenance_plan_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `asset_type` VARCHAR(255),
    `frequency_days` INT,
    `active` BOOLEAN,
PRIMARY KEY (maintenance_plan_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE MaintenanceApp.maintenance_tasks (
    `maintenance_task_id` INT AUTO_INCREMENT,
    `maintenance_plan_id` INT,
    `asset_reference` VARCHAR(255),
    `scheduled_date` DATETIME,
    `status` VARCHAR(255),
PRIMARY KEY (maintenance_task_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE MaintenanceApp.maintenance_records (
    `maintenance_record_id` INT AUTO_INCREMENT,
    `maintenance_task_id` INT,
    `completed_date` DATETIME,
    `technician` VARCHAR(255),
    `notes` VARCHAR(255),
PRIMARY KEY (maintenance_record_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE MaintenanceApp.maintenance_checklists (
    `maintenance_checklist_id` INT AUTO_INCREMENT,
    `maintenance_task_id` INT,
    `item_description` VARCHAR(255),
    `completed` BOOLEAN,
PRIMARY KEY (maintenance_checklist_id)) ENGINE=MyISAM DEFAULT CHARSET=latin1;

USE drcvelocity;
