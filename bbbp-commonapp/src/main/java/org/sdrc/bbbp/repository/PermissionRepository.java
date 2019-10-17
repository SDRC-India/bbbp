package org.sdrc.bbbp.repository;

import org.sdrc.bbbp.domain.Permission;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(idClass = Integer.class, domainClass = Permission.class)
public interface PermissionRepository {

	public Permission save(Permission permission);
}
