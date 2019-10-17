package org.sdrc.bbbp.repository;

import java.util.List;

import org.sdrc.bbbp.domain.Role;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(idClass = Integer.class, domainClass = Role.class)
public interface RoleRepository {

	public List<Role> findAll();

	public Role findByRoleName(String rollName);

	public List<Role> findAllByRoleNameIn(List<String> rols);

	public Role findByRoleId(int roleId);

	public Role save(Role role);

	public List<Role> findByOrderByRoleIdAsc();

}
