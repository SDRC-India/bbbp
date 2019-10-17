package org.sdrc.bbbp.repository;

import java.util.List;

import org.sdrc.bbbp.domain.PeriodReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodReferenceRepository extends JpaRepository<PeriodReference, Integer> {
	
	List<PeriodReference> findByPeriodicityPeriodicityIdIsIn(List<Integer> periodiityIds);
	
	PeriodReference findByPeriodReferenceId(Integer periodReferenceId);

	PeriodReference findByMonthRange(String monthRange);

}
