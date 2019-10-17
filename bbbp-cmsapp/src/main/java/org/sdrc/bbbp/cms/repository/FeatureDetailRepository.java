package org.sdrc.bbbp.cms.repository;

import java.util.List;

import org.sdrc.bbbp.cms.domain.FeatureDetail;

public interface FeatureDetailRepository extends CmsBaseRepository<FeatureDetail, Long>{

	List<FeatureDetail> findByFeatureFeatureIdOrderByFeatureDetailOrderAsc(Integer featureId);
	
	List<FeatureDetail> findAllByOrderByFeatureDetailOrderAsc();
}
