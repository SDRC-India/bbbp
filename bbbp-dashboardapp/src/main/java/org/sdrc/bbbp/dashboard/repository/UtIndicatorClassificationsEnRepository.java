package org.sdrc.bbbp.dashboard.repository;

import java.util.List;

import org.sdrc.bbbp.dashboard.domain.UtIndicatorClassificationsEn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UtIndicatorClassificationsEnRepository extends JpaRepository<UtIndicatorClassificationsEn, Integer> {

	@Query(value="select ic1.ic_nid as sector_id,ic1.ic_name as sector_name,ic2.ic_nid as sub_sector_id,ic2.ic_name as sub_sector_name " + 
			"from ut_indicator_classifications_en ic1, ut_indicator_classifications_en ic2 " + 
			"where ic1.ic_nid = ic2.ic_parent_nid", nativeQuery=true)
	List<Object[]> getIndicatorClassfication();

}
