package org.sdrc.bbbp.service;

import java.sql.Timestamp;

import org.sdrc.bbbp.domain.User;
import org.sdrc.bbbp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByUsername(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Transactional
	public void updateLoggedOutStatus(long userLoginMetaId, Timestamp loggedOutDateTime) {

		// while server start up
	}

}
