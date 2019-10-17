package org.sdrc.bbbp.repository;

import java.util.List;

import org.sdrc.bbbp.domain.Area;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

	
//@RepositoryDefinition(idClass=Integer.class,domainClass=Area.class)
public interface AreaRepository  extends Repository<Area, Integer>{

	List<Area> findAll();
	
	Area findByAreaCode(String areaCode);
	
	Area save(Area area);

	List<Area> findByParentAreaId(Integer parentAreaId);
	
	Area findByAreaId(Integer areaId);
	
	List<Area> findByParentAreaIdOrderByAreaName(Integer parentAreaId);

	List<Area> findByLevelAreaLevelIdOrderByAreaName(Integer areaLevelId);

	 List<Area> findByOrderByAreaName();

	List<Area> findByLevelAreaLevelIdInOrderByAreaIdAsc(List<Integer> asList);

	@Query(value="select ar.* " + 
				"from user_tbl ut, area ar, user_area_mapping uam " + 
				"where ut.user_id = uam.user_fk " + 
				"and uam.area_id_fk = ar.area_id " + 
				"and (uam.area_id_fk = :areaId or ar.parent_area_id = :areaId)", nativeQuery=true)
	List<Area> getAreaListState(@Param("areaId") Integer areaId);

	@Query(value="select ar.* from user_tbl ut, area ar, user_area_mapping uam " + 
				"where ut.user_id = uam.user_fk and uam.area_id_fk = ar.area_id " + 
				"and uam.area_id_fk = :areaId", nativeQuery=true)
	List<Area> getAreaListDistrict(@Param("areaId") Integer areaId);
}
