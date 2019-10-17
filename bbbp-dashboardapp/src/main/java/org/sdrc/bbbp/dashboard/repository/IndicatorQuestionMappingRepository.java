package org.sdrc.bbbp.dashboard.repository;

import org.sdrc.bbbp.dashboard.domain.IndicatorQuestionMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IndicatorQuestionMappingRepository extends JpaRepository<IndicatorQuestionMapping, Integer> {
	@Query("Select ind from IndicatorQuestionMapping ind where ind.indicator_NId=:indicatorNId ")
	IndicatorQuestionMapping findByIndicatorNId(@Param("indicatorNId")Integer indicatorNId);

}
