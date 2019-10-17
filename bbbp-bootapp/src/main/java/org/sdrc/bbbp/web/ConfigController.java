package org.sdrc.bbbp.web;

import org.sdrc.bbbp.service.ConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

	private Logger _log = LoggerFactory.getLogger(ConfigController.class);

	@Autowired
	ConfigurationService configurationService;
	
	
//	@RequestMapping("/createRoleFeaturePermission")
//	public boolean createRoleFeaturePermission() {
//		return configurationService.createRoleFeaturePermissionScheme();
//	}
//	@RequestMapping("/createAreaAndUsers")
//	public boolean createAreaAndUsers() {
//		return configurationService.createUsers();
//	}
//	
//	@RequestMapping("/createUsersFromAreaTable")
//	public boolean createUsersFromAreaTable() {
//		return configurationService.createUsersFromAreaTable();
//	}
	
//	@RequestMapping("/resetPassword")
//	public boolean createUsersFromAreaTable() {
//		return configurationService.resetPassword();
//	}
	
//	@RequestMapping("/checkPassword")
//	public boolean createUsersFromAreaTable() {
//		return configurationService.checkPassword();
//	}
	
}
