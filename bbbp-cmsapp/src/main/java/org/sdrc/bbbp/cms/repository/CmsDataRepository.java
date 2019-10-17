package org.sdrc.bbbp.cms.repository;

import java.util.List;

import org.sdrc.bbbp.cms.domain.CmsData;
import org.sdrc.bbbp.cms.domain.CmsDataJson;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CmsDataRepository extends CmsBaseRepository<CmsData, Long> {

//	@Cacheable("cmsdata")
	CmsData findByViewName(String viewName);

//	@Caching(evict= {@CacheEvict(value="cmsdata"), key = "#viewName"} )
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_content =  jsonb_set(view_content, "
			+ " CAST(:path AS text[]), view_content-> :language -> :languageIndex -> :dataArrayKey -> :dataIndex||CAST(:dataKeyValue AS jsonb), :createMissing) "
			+ " WHERE view_name= :viewName", nativeQuery = true)
	void updateAddViewContentDataByIndex(@Param("language") String language, @Param("languageIndex") int languageIndex,
			@Param("dataIndex") int dataIndex, @Param("dataKeyValue") String dataKeyValue,
			@Param("viewName") String viewName, @Param("dataArrayKey") String dataArrayKey,
			@Param("path") String path, @Param("createMissing") boolean createMissing);
	
//	@Caching(evict= {@CacheEvict("cmsdata")}, put= {@CachePut("cmsdata")})
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_content =  view_content #- CAST(:path AS text[])"
			+ " WHERE view_name= :viewName", nativeQuery = true)
	void deleteViewContentDataByIndex(@Param("viewName") String viewName, @Param("path") String path);
	
//	@Caching(evict= {@CacheEvict("cmsdata")}, put= {@CachePut("cmsdata")})
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_content =  jsonb_set(view_content, "
			+ " CAST(:path AS text[]), view_content-> :language -> :languageIndex ||CAST(:dataKeyValue AS jsonb), :createMissing) "
			+ " WHERE view_name= :viewName", nativeQuery = true)
	void updateAddViewContentSectionByIndex(@Param("language") String language,
			@Param("languageIndex") int languageIndex, @Param("dataKeyValue") String dataKeyValue,
			@Param("viewName") String viewName, @Param("path") String path,
			@Param("createMissing") boolean createMissing);
	
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_type = :description where view_name= :viewName", nativeQuery = true)
	int updateViewType(@Param("description") String description, @Param("viewName") String viewName);
	
	
	@Query(value = "select * from cms_data where updated_date notnull  order by updated_date desc limit 4 ", nativeQuery = true)
	List<CmsData> findCmsData();
	
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_content =  jsonb_set(view_content, "
			+ " CAST(:path AS text[]),((( view_content-> :language -> :languageIndex -> 'data' ) - (SELECT i "
			+ "FROM generate_series(0, jsonb_array_length(view_content -> :language -> :languageIndex -> 'data' ) - 1) AS i "
			+ " WHERE (view_content ->  :language -> :languageIndex -> 'data' -> i ->> :hashKey = :hashValue ))) || CAST(:dataKeyValue AS jsonb)), :createMissing) "
			+ " WHERE (view_content ->  :language -> :languageIndex ->> 'section') = :newsSection AND view_name= :viewName", nativeQuery = true)
	void updateWhatsNewContentSectionByIndex(@Param("language") String language,
			@Param("languageIndex") int languageIndex, @Param("dataKeyValue") String dataKeyValue,
			@Param("viewName") String viewName, @Param("path") String path,
			@Param("createMissing") boolean createMissing, @Param("newsSection") String newsSection,
			@Param("hashKey") String hashKey, @Param("hashValue") String hashValue);
	
	@Query(value = "Select( SELECT i FROM generate_series(0, jsonb_array_length(view_content -> :language) - 1) AS i "
			+ "WHERE (view_content -> :language -> i ->>'key' = :newsSection ))from cms_data where view_name= :viewName", nativeQuery = true)
	Integer getNewsSectionIndex(@Param("language") String language,  @Param("newsSection") String newsSection, @Param("viewName") String viewName);
	
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_content =  jsonb_set(view_content, "
			+ " CAST(:path AS text[]), CAST(:section AS jsonb), :createMissing) "
			+ " WHERE view_name= :viewName", nativeQuery = true)
	void updateSectionByIndex(@Param("section") String section,
			@Param("viewName") String viewName, @Param("path") String path,
			@Param("createMissing") boolean createMissing);
	
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_content =  jsonb_set(view_content, "
			+ " CAST(:path AS text[]), view_content-> :language -> :languageIndex -> :dataArrayKey || CAST(:dataKeyValue AS jsonb), :createMissing) "
			+ " WHERE view_name= :viewName", nativeQuery = true)
	void addBlankObjectInContentDataByIndex(@Param("language") String language, @Param("languageIndex") int languageIndex,
			@Param("dataKeyValue") String dataKeyValue,	@Param("viewName") String viewName, @Param("dataArrayKey") String dataArrayKey,
			@Param("path") String path, @Param("createMissing") boolean createMissing);

	
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_content =  jsonb_set(view_content, "
			+ " CAST(:path AS text[]), view_content-> :language -> :languageIndex -> :section -> :dataIndex ||CAST(:dataKeyValue AS jsonb), :createMissing) "
			+ " WHERE view_name= :viewName", nativeQuery = true)
	void updateAddViewContentNestedSectionByIndex(@Param("language") String language,
			@Param("languageIndex") int languageIndex, @Param("dataKeyValue") String dataKeyValue,
			@Param("viewName") String viewName, @Param("path") String path,
			@Param("createMissing") boolean createMissing,@Param("section") String section,@Param("dataIndex") int dataIndex);
	
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_content =  jsonb_set(view_content, "
			+ " CAST(:path AS text[]), view_content-> :language  || CAST(:dataKeyValue AS jsonb), :createMissing) "
			+ " WHERE view_name= :viewName", nativeQuery = true)
	void addBlankObjectInSectionDataByIndex(@Param("language") String language,
			@Param("dataKeyValue") String dataKeyValue,	@Param("viewName") String viewName,
			@Param("path") String path, @Param("createMissing") boolean createMissing);
	
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_content =  jsonb_set(view_content, "
			+ " CAST(:path AS text[]), view_content-> :language -> :stateIndex -> :dataArrayKey -> :languageIndex -> :nestedDataKey -> :dataIndex || CAST(:dataKeyValue AS jsonb), :createMissing) "
			+ " WHERE view_name= :viewName", nativeQuery = true)
	void updateAddViewNestedContentDataByIndex(@Param("language") String language, @Param("languageIndex") int languageIndex,
			@Param("dataIndex") int dataIndex, @Param("dataKeyValue") String dataKeyValue,
			@Param("viewName") String viewName, @Param("dataArrayKey") String dataArrayKey,
			@Param("path") String path, @Param("createMissing") boolean createMissing,
			@Param("stateIndex") int stateIndex,@Param("nestedDataKey") String nestedDataKey);
	
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_content =  jsonb_set(view_content, "
			+ " CAST(:path AS text[]), view_content-> :language -> :languageIndex -> :dataArrayKey -> :stateIndex -> :nestedDataKey  || CAST(:dataKeyValue AS jsonb), :createMissing) "
			+ " WHERE view_name= :viewName", nativeQuery = true)
	void addBlankObjectInNestedContentDataByIndex(@Param("language") String language, @Param("languageIndex") int languageIndex,
			@Param("dataKeyValue") String dataKeyValue,	@Param("viewName") String viewName, @Param("dataArrayKey") String dataArrayKey,
			@Param("path") String path, @Param("createMissing") boolean createMissing,
			@Param("stateIndex") int stateIndex,@Param("nestedDataKey") String nestedDataKey );
	
//	@CacheEvict(value = "cmsdata", key = "#viewName")
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE cms_data SET view_content =  jsonb_set(view_content, "
			+ " CAST(:path AS text[]), view_content-> :language -> :stateIndex -> :dataArrayKey   || CAST(:dataKeyValue AS jsonb), :createMissing) "
			+ " WHERE view_name= :viewName", nativeQuery = true)
	void addBlankObjectSectionInContentDataByIndex(@Param("language") String language,
			@Param("dataKeyValue") String dataKeyValue,	@Param("viewName") String viewName, @Param("dataArrayKey") String dataArrayKey,
			@Param("path") String path, @Param("createMissing") boolean createMissing,
			@Param("stateIndex") int stateIndex);


	@Query(value = "SELECT view_content-> :language -> :languageIndex -> :dataArrayKey FROM cms_data "
			+ " WHERE view_name= :viewName", nativeQuery = true)
	CmsDataJson getPhotoGalleryData(@Param("viewName") String viewName, @Param("language") String language, @Param("languageIndex") int languageIndex,
			@Param("dataArrayKey") String dataArrayKey);


}
