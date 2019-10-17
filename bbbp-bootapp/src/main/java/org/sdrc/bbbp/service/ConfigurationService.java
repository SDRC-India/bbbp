package org.sdrc.bbbp.service;

public interface ConfigurationService {

	
	public boolean createUsers();

	boolean generateUserRoleFeaturePermissionMapping();

	boolean createRoleFeaturePermissionScheme();

	boolean createUsersFromAreaTable();

	boolean resetPassword();

	boolean checkPassword(); 
	 
}
