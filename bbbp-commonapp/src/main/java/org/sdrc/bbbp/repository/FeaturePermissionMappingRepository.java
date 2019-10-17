package org.sdrc.bbbp.repository;

import org.sdrc.bbbp.domain.FeaturePermissionMapping;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(idClass=Integer.class,domainClass=FeaturePermissionMapping.class)
public interface FeaturePermissionMappingRepository {

	public FeaturePermissionMapping save(FeaturePermissionMapping featurePermissionMapping);
	
}
