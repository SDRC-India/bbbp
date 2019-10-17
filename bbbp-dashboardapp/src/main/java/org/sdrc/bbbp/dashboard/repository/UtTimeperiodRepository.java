package org.sdrc.bbbp.dashboard.repository;

import java.sql.Date;
import java.util.List;

import org.sdrc.bbbp.dashboard.domain.UtTimeperiod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
	
public interface UtTimeperiodRepository extends JpaRepository<UtTimeperiod, Integer> {
	
	UtTimeperiod findByTimePeriodAndPeriodicityPeriodicityId(String timePeriod,int periodicity);
	
	
	@Query("SELECT tim FROM UtTimeperiod tim WHERE tim.periodicity.periodicityId =:periodicity ORDER BY tim.timePeriod_NId DESC")
	List<UtTimeperiod> findByPeriodicityPeriodicityId(@Param("periodicity")int periodicity);
	
	@Query(value="SELECT tim.* FROM ut_timeperiod tim WHERE tim.periodicity =:periodicity ORDER BY year DESC, period_reference DESC",nativeQuery = true)
	List<UtTimeperiod> findByPeriodicityPeriodicityIdYearDESC(@Param("periodicity")int periodicity);
	
	@Query(value="select ut.* from ut_timeperiod ut where ut.periodicity =:periodicity order by ut.time_period_nid desc LIMIT 4",nativeQuery = true)
	List<UtTimeperiod> findByPeriodicityPeriodicityIdDESC(@Param("periodicity")int periodicity);
	
	@Query(value="SELECT tim.* FROM ut_timeperiod tim WHERE tim.periodicity =:periodicity ORDER BY tim.year DESC, tim.period_reference desc LIMIT 4",nativeQuery = true)
	List<UtTimeperiod> findByPeriodicityPeriodicityIdYearDESCLimit(@Param("periodicity")int periodicity);

	public UtTimeperiod findByStartDateAndEndDate(Date startDate, Date endDate);

	@Transactional
	@Procedure(name = "aggregatedata")
	Integer aggregateData(Integer timePeriod_NId);

	@Transactional
	@Procedure(procedureName = "aggregateDataUsingYearAndQuarter")
	void aggregateDataUsingYearAndQuarter(Integer timePeriodId, Integer year, Integer quarter);
	
}
