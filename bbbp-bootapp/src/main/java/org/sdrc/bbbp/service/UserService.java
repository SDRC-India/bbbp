package org.sdrc.bbbp.service;

import org.sdrc.bbbp.domain.User;

public interface UserService {

	User findByUsername(String userName);
	
}
