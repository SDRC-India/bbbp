package org.sdrc.bbbp.web;

import java.util.List;
import java.util.Map;

import org.sdrc.bbbp.domain.Role;
import org.sdrc.bbbp.domain.User;
import org.sdrc.bbbp.models.AreaModel;
import org.sdrc.bbbp.models.ChangePasswordModel;
import org.sdrc.bbbp.models.NewUserModel;
import org.sdrc.bbbp.models.ResetPasswordModel;
import org.sdrc.bbbp.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Subham Ashish (subham@sdrc.co.in)
 *
 */
@Controller
@RequestMapping("/api")
public class UserManagement {

	@Autowired
	private UserManagementService userManagementService;
	
	/**
	 * @param newUserModel
	 * @return
	 */
	@PreAuthorize("hasAuthority('user_mgmt,edit')")
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	@ResponseBody
	public boolean addUser(@RequestBody NewUserModel newUserModel) {

		return userManagementService.addNewUser(newUserModel);
	}

	/**
	 * @return
	 */
	@PreAuthorize("hasAuthority('user_mgmt,edit')")
	@RequestMapping(value = "/allRole")
	@ResponseBody
	public List<Role> getAllRoles() {

		return userManagementService.displayAllRoles();

	}

	/**
	 * @return
	 */
	// @PreAuthorize("hasAuthority('user_mgmt,edit')")
	@ResponseBody
	@RequestMapping(value = "/getAllArea")
	Map<String, List<AreaModel>> displayArea() {

		return userManagementService.getAllAreaList();

	}

	
	/**
	 * area wise user display
	 * @return
	 */
	@PreAuthorize("hasAuthority('user_mgmt,edit')")
	@RequestMapping(value="/allUser",method=RequestMethod.GET)
	@ResponseBody
	Map<String, List<User>> displayAllUser(){
		
			return userManagementService.getAllUser();
			
	}
	

	/**
	 * @param resetPasswordModel
	 * @return
	 */
	@PreAuthorize("hasAuthority('user_mgmt,edit')")
	@RequestMapping(value = "/resetPassword")
	@ResponseBody
	public ResponseEntity<Boolean> resetPassword(@RequestBody ResetPasswordModel resetPasswordModel) {

		return userManagementService.resetPassword(resetPasswordModel);

	}
	
	
	/**
	 * @param changePasswordModel
	 * @return
	 */
//	@PreAuthorize("hasAuthority('user_mgmt,edit')")
	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordModel changePasswordModel){
		
		return userManagementService.changePassoword(changePasswordModel);
	}
	

}
