package org.sdrc.bbbp.service;

import java.util.List;
import java.util.Map;

import org.sdrc.bbbp.domain.Role;
import org.sdrc.bbbp.domain.User;
import org.sdrc.bbbp.models.AreaModel;
import org.sdrc.bbbp.models.ChangePasswordModel;
import org.sdrc.bbbp.models.NewUserModel;
import org.sdrc.bbbp.models.ResetPasswordModel;
import org.springframework.http.ResponseEntity;

/**
 * @author Subham Ashish (subham@sdrc.co.in)
 *
 */
public interface UserManagementService {

	boolean addNewUser(NewUserModel newUserModel);

	List<Role> displayAllRoles();

	Map<String, List<AreaModel>> getAllAreaList();

	ResponseEntity<Boolean> resetPassword(ResetPasswordModel resetPasswordModel);

	Map<String, List<User>> getAllUser();

	ResponseEntity<String> changePassoword(ChangePasswordModel changePasswordModel);
}
