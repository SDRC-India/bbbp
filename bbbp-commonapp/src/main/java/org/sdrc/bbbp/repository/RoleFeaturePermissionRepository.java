package org.sdrc.bbbp.repository;

import java.util.List;

import org.sdrc.bbbp.domain.Role;
import org.sdrc.bbbp.domain.RoleFeaturePermissionScheme;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(idClass=Integer.class,domainClass=RoleFeaturePermissionScheme.class)
public interface RoleFeaturePermissionRepository {
	
	
	public List<RoleFeaturePermissionScheme> findAll();

	public List<RoleFeaturePermissionScheme> findByRole(Role role);

	public RoleFeaturePermissionScheme save(RoleFeaturePermissionScheme aDMIN_FEATURE_USER_MGMT_EDIT_PERMISSION);

}
