package org.sdrc.bbbp.repository;

import java.util.List;
import java.util.Set;

import org.sdrc.bbbp.domain.RolePeriodicityMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePeriodicityMappingRepository extends JpaRepository<RolePeriodicityMapping, Integer> {
	
	List<RolePeriodicityMapping> findByRoleRoleIdIsIn(Set<Integer> roleIds);

}
