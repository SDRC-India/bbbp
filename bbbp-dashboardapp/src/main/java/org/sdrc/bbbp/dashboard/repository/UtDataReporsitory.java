package org.sdrc.bbbp.dashboard.repository;

import java.util.List;

import org.sdrc.bbbp.dashboard.domain.UtData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UtDataReporsitory extends JpaRepository<UtData, Long> {

	// @Cacheable("utdatabyperiodandparentid")
	@Query(value = "select ar.area_code,ar.area_id,ar.area_name,dt.data_value from "
			+ "ut_data dt inner join area ar on dt.area_nid=ar.area_id "
			+ "inner join ut_timeperiod tim on tim.time_period_nid = dt.time_period_nid "
			+ "inner join periodicity pir on pir.periodicity_id= tim.periodicity "
			+ "and pir.periodicity_id=:periodicityId and ar.parent_area_id=:parentarreaId "
			+ "and tim.time_period_nid = (select time_period_nid from ut_timeperiod "
			+ "order by time_period_nid desc LIMIT 1)", nativeQuery = true)
	List<Object[]> findByPeriodicityAndParentAreaId(@Param("periodicityId") int periodicityId,
			@Param("parentarreaId") int parentarreaId);

	// @Cacheable("utdatabyperiodandareaid")
	@Query(value = "select ar.area_code,ar.area_id,ar.area_name,dt.data_value from "
			+ "ut_data dt inner join area ar on dt.area_nid=ar.area_id "
			+ "inner join ut_timeperiod tim on tim.time_period_nid = dt.time_period_nid "
			+ "inner join periodicity pir on pir.periodicity_id= tim.periodicity "
			+ "and pir.periodicity_id=:periodicityId and ar.area_id=:areaId "
			+ "and tim.time_period_nid = (select time_period_nid from ut_timeperiod "
			+ "order by time_period_nid desc LIMIT 1)", nativeQuery = true)
	List<Object[]> findByPeriodicityAndAreaId(@Param("periodicityId") int periodicityId, @Param("areaId") int areaId);

	// @Cacheable("utdatabyareaandind")
	@Query(value = "select tim.time_period_nid,tim.time_period,dt.data_value,ar.area_id,ar.area_name,ind.indicator_name from ut_data dt "
			+ "Inner join "
			+ "(select tmp.time_period_nid,tmp.time_period from ut_timeperiod tmp where periodicity =:periodicityId order by time_period_nid desc  Limit 10) "
			+ "tim on dt.time_period_nid=tim.time_period_nid and dt.area_nid=:areaId and dt.indicator_nid=:indicatorNid left join area ar on ar.area_id=dt.area_nid "
			+ "left join ut_indicator_unit_subgroup ius on ius.iusnid=dt.iusnid "
			+ "left join indicator_question_mapping ind on ind.indicator_nid=ius.indicator_nid", nativeQuery = true)
	List<Object[]> findByAreaIdAndIndicatorNid(@Param("areaId") int areaId, @Param("indicatorNid") int indicatorNid, @Param("periodicityId") int periodicityId);

	// @Cacheable("allutdatabyperiodandparentid")
	@Query("SELECT ar.areaCode,ar.areaId,ar.areaName,dt.data_Value,tim.timePeriod_NId,tim.timePeriod FROM "
			+ "UtData dt INNER JOIN Area ar ON dt.area_NId.areaId=ar.areaId "
			+ "INNER JOIN UtTimeperiod tim ON tim.timePeriod_NId = dt.timePeriod_NId.timePeriod_NId "
			+ "INNER JOIN Periodicity pir ON pir.periodicityId= tim.periodicity.periodicityId "
			+ "AND pir.periodicityId=:periodicityId AND ar.parentAreaId < :areaId ORDER BY tim.timePeriod_NId desc ")
	List<Object[]> findAllByPeriodicityAndParentAreaId(@Param("periodicityId") int periodicityId,
			@Param("areaId") int areaId);

	// @Cacheable("allinds")
	@Query("SELECT ice.IC_NId,ice.IC_Name,icen.IC_NId,icen.IC_Name, ind.indicator_NId, ind.indicator_Name ,unit.unit_Name "
			+ "FROM UtIndicatorClassificationsEn ice "
			+ "INNER JOIN UtIndicatorClassificationsEn icen ON ice.IC_NId=icen.IC_Parent_NId.IC_NId "
			+ "INNER JOIN UtIcIus icius ON icius.IC_NId.IC_NId = icen.IC_NId "
			+ "INNER JOIN UtIndicatorUnitSubgroup ius ON ius.IUSNId=icius.IUSNId.IUSNId "
			+ "INNER JOIN IndicatorQuestionMapping ind ON ind.indicator_NId = ius.indicator_NId.indicator_NId "
			+ "INNER JOIN UtUnitEn unit ON ius.unit_NId.unit_NId = unit.unit_NId "
			+ "WHERE unit.unit_NId = :unitNId ORDER BY ice.IC_NId,icen.IC_NId,ind.indicator_NId")
	List<Object[]> findAllIndicators(@Param("unitNId") Integer unitNId);

	// @Cacheable("utdatbyindid")
/*	@Query("SELECT ar.areaCode,ar.areaId,ar.areaName,data.data_Value,tim.timePeriod_NId,tim.timePeriod,ius.unit_NId.unit_NId FROM IndicatorQuestionMapping ind "
			+ "INNER JOIN UtIndicatorUnitSubgroup ius ON ind.indicator_NId = ius.indicator_NId.indicator_NId "
			+ "INNER JOIN UtData data ON data.IUSNId.IUSNId = ius.IUSNId "
			+ "INNER JOIN Area ar ON data.area_NId.areaId = ar.areaId "
			+ "INNER JOIN UtTimeperiod tim ON tim.timePeriod_NId = data.timePeriod_NId.timePeriod_NId "
			+ "WHERE ius.indicator_NId.indicator_NId =:indicatorId")*/
	@Query("SELECT ar.areaCode,ar.areaId,ar.areaName,data.data_Value,tim.timePeriod_NId,tim.timePeriod,ius.unit_NId.unit_NId FROM IndicatorQuestionMapping ind "
			+ "INNER JOIN UtIndicatorUnitSubgroup ius ON ind.indicator_NId = ius.indicator_NId.indicator_NId "
			+ "INNER JOIN UtData data ON data.IUSNId.IUSNId = ius.IUSNId "
			+ "INNER JOIN Area ar ON data.area_NId.areaId = ar.areaId "
			+ "INNER JOIN UtTimeperiod tim ON tim.timePeriod_NId = data.timePeriod_NId.timePeriod_NId "
			+ "WHERE ius.indicator_NId.indicator_NId =:indicatorId and ar.level.areaLevelId in :areaLevelIds")
	List<Object[]> findAllByIndicatorNid(@Param("indicatorId") int indicatorId, @Param("areaLevelIds")List<Integer> areaLevelIds);

	@Query(value = "select tim.time_period_nid,tim.time_period,dt.data_value,ar.area_id,ar.area_name,ind.indicator_name from ut_data dt "
			+ "right join "
			+ "(select tmp.time_period_nid,tmp.time_period from ut_timeperiod tmp where periodicity =:periodicityId ORDER BY tmp.year DESC, tmp.period_reference DESC  Limit 4) "
			+ "tim on dt.time_period_nid=tim.time_period_nid and dt.area_nid=:areaId and dt.indicator_nid=:indicatorNid left join area ar on ar.area_id=dt.area_nid "
			+ "left join ut_indicator_unit_subgroup ius on ius.iusnid=dt.iusnid "
			+ "left join indicator_question_mapping ind on ind.indicator_nid=ius.indicator_nid", nativeQuery = true)
	List<Object[]> findByAreaIdAndIndicatorNidAndPeriodicity(@Param("areaId") int areaId,
			@Param("indicatorNid") int indicatorNid, @Param("periodicityId") int periodicityId);

	@Query(value="select data.* from ut_data data where data.time_period_nid in (:timePeriod) and data.area_nid = :areaId", nativeQuery = true)
	List<UtData> getDataValue(@Param("timePeriod") List<Integer> timePeriod, @Param("areaId") int areaId);

	@Query(value="select * from ut_data utd where utd.time_period_nid = :timePeriodId " + 
			"and utd.indicator_nid = :indicatorId and utd.area_nid in(:childAreaList)",nativeQuery=true)
	List<UtData> getThematicViewData(@Param("indicatorId")Integer indicatorId, @Param("timePeriodId")Integer timePeriodId, @Param("childAreaList")List<Integer> childAreaList);

	@Query(value="select ar.area_name, ud.data_value,ar.area_id from ut_data ud, area ar where ud.indicator_nid = :indicatorId " + 
				"and ud.time_period_nid = :timePeriodId and ar.area_id = ud.area_nid and ar.parent_area_id = :parentAreaId", nativeQuery=true)
	List<Object[]> getUtDatasForAllState(@Param("indicatorId")Integer indicatorId, @Param("timePeriodId")Integer timePeriodId,
													@Param("parentAreaId")Integer parentAreaId);
	
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM public.ut_data WHERE indicator_nid = :indicatorId",nativeQuery=true)
	void deleteByIndicator_NId(@Param("indicatorId")Integer indicatorId);

}
