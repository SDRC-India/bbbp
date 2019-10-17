
package org.sdrc.bbbp.repository;

import java.util.List;

import org.sdrc.bbbp.domain.UserRoleFeaturePermissionMapping;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(idClass=Integer.class,domainClass=UserRoleFeaturePermissionMapping.class)
public interface UserRoleFeaturePermissionMappingRepository {

	<S extends UserRoleFeaturePermissionMapping> List<S> save(Iterable<S> userRoleFeaturePermissionMappings);
	
	public void save(UserRoleFeaturePermissionMapping userRoleFeaturePermissionMapping);

	List<UserRoleFeaturePermissionMapping> findAll();
	
}
