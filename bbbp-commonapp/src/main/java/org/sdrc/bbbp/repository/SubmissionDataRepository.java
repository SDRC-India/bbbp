package org.sdrc.bbbp.repository;

import java.util.List;

import org.sdrc.bbbp.domain.SubmissionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SubmissionDataRepository extends JpaRepository<SubmissionData, Long> {
	
	
	@Transactional
	//SubmissionData save(SubmissionData submissionData);
	
	SubmissionData findBySubmissionId(Long sId);
	
	SubmissionData findByUserUserIdAndPeriodReferencePeriodReferenceIdAndYearYearIdAndIsSubmitFalse(Integer userId,Integer periodRefId,Integer yearId);
	
	SubmissionData findByUserUserIdAndPeriodReferencePeriodReferenceIdAndYearYearId(Integer userId,Integer periodRefId,Integer yearId);
	
	List<SubmissionData> findByUserUserIdAndIsSubmitFalse(Integer userId);
	
	List<SubmissionData> findByUserUserId(Integer userId);
	List<SubmissionData> findAllByOrderBySubmissionIdAsc();

//	List<SubmissionData> findByYearYearIdAndUserUserIdAndStatus(Integer yearId, Integer userId, String string);

	/*List<SubmissionData> findByYearYearIdAndIsSubmitTrue(Integer yearId);

	@Query(value="select sd.* from submission_data sd, user_area_mapping uam, area ar " + 
			"where sd.user_id_fk= uam.user_fk and uam.area_id_fk = ar.area_id " + 
			"and sd.year_id_fk = :yearId and ar.parent_area_id = :districtId and sd.is_submit is true", nativeQuery= true)
	List<SubmissionData> getStateAllData(@Param("districtId")Integer districtId, @Param("yearId")Integer yearId);

	@Query(value="select sd.* from submission_data sd, user_area_mapping uam, area ar " + 
			"where sd.user_id_fk= uam.user_fk and uam.area_id_fk = ar.area_id " + 
			"and sd.year_id_fk = :yearId and ar.area_id = :districtId and ar.parent_area_id = :stateId and sd.is_submit is true", nativeQuery=true)
	List<SubmissionData> getSelectedAreaData(@Param("stateId")Integer stateId, @Param("districtId")Integer districtId, @Param("yearId")Integer yearId);

	@Query(value="select sd.* from submission_data sd, user_area_mapping uam, area ar " + 
			"where sd.user_id_fk= uam.user_fk and uam.area_id_fk = ar.area_id " + 
			"and sd.year_id_fk = :yearId and ar.area_id = :districtId " + 
			"and sd.is_submit is true", nativeQuery=true)
	List<SubmissionData> getDistrictAllData(@Param("districtId")Integer districtId, @Param("yearId")Integer yearId);
*/
	List<SubmissionData> findAllByIsSubmitTrue();
	
	@Query(value="select sd.* from submission_data sd, user_area_mapping uam, area ar " + 
			"where sd.user_id_fk= uam.user_fk and uam.area_id_fk = ar.area_id " + 
			"and ar.parent_area_id = :districtId and sd.is_submit is true", nativeQuery= true)
	List<SubmissionData> getStateAllData(@Param("districtId")Integer districtId);
	
	@Query(value="select sd.* from submission_data sd, user_area_mapping uam, area ar " + 
			"where sd.user_id_fk= uam.user_fk and uam.area_id_fk = ar.area_id " + 
			"and ar.area_id = :districtId and sd.is_submit is true", nativeQuery=true)
	List<SubmissionData> getDistrictAllData(@Param("districtId")Integer districtId);
	

	@Query(value="select sd.* from submission_data sd, user_area_mapping uam, area ar " + 
			"where sd.user_id_fk= uam.user_fk and uam.area_id_fk = ar.area_id " + 
			"and ar.area_id = :districtId and ar.parent_area_id = :stateId and sd.is_submit is true", nativeQuery=true)
	List<SubmissionData> getSelectedAreaData(@Param("stateId")Integer stateId, @Param("districtId")Integer districtId);
	
	@Query(value="select ar.parent_area_id,count(*), utt.time_period_nid, utt.time_period " + 
			"from ut_data ud, area ar, ut_timeperiod utt " + 
			"where indicator_nid = :indicatorId " + 
			"and ar.area_id = ud.area_nid " + 
			"and utt.time_period_nid = ud.time_period_nid " + 
			"group by ar.parent_area_id, utt.time_period_nid, utt.time_period", nativeQuery=true)
	List<Object[]> getDataByGroup(@Param("indicatorId")Integer indicatorId);


}
