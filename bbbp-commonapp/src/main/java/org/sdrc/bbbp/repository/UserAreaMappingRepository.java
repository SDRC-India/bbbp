
package org.sdrc.bbbp.repository;

import java.util.List;

import org.sdrc.bbbp.domain.UserAreaMapping;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(idClass=Integer.class,domainClass=UserAreaMapping.class)
public interface UserAreaMappingRepository  {

	
	List<UserAreaMapping> findAll();
	
	UserAreaMapping save(UserAreaMapping userAreaMapping);
	
}
