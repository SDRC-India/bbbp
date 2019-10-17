package org.sdrc.bbbp.repository;

import org.sdrc.bbbp.domain.Feature;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(idClass = Integer.class, domainClass = Feature.class)
public interface FeatureRepository {

	public Feature save(Feature feature);
	
}
