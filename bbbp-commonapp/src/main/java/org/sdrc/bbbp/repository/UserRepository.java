package org.sdrc.bbbp.repository;

import java.util.List;

import org.sdrc.bbbp.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(idClass=Integer.class,domainClass=User.class)
public interface UserRepository  {

	@Cacheable("users")
	User findByUserName(String username);

	List<User> findAll();

	User save(User user);

	User findByUserId(Integer userId);
	
	User findByUuid(String uuid);

	

}
