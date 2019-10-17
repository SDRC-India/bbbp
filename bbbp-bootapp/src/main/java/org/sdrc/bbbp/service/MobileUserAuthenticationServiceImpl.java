package org.sdrc.bbbp.service;

import org.sdrc.bbbp.domain.User;
import org.sdrc.bbbp.exceptions.MobileCredentialsExpiredException;
import org.sdrc.bbbp.models.UserModel;
import org.sdrc.bbbp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MobileUserAuthenticationServiceImpl implements MobileUserAuthenticationService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public void authenticateUserAgainstPasswordChange() {
		UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User userDomain = userRepository.findByUserName(userModel.getUsername());

		 if(!userDomain.validateAuthentication(userDomain.getPassword(), userModel.getPasswordHashInCurrentSession())) {
		    	throw new MobileCredentialsExpiredException("Password has been changed.Please login again.");
		    }
	}

}
