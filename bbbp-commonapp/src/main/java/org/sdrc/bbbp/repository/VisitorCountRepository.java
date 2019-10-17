package org.sdrc.bbbp.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import org.sdrc.bbbp.domain.VisitorCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in)
 * Created On: 09-Aug-2019 12:25:48 PM
 */
public interface VisitorCountRepository extends JpaRepository<VisitorCount, Long> {

//	@Override
//	@Transactional
//	<S extends VisitorCount> List<S> save(Iterable<S> entities);
	
	List<VisitorCount> findByIpAddress(String ipAddress);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE VisitorCount vs SET vs.count = :count, vs.lastVisitedDate= :lastvisiteddate, vs.lastSessionid= :lastsessionid where vs.id= :id")
	void updateVisitorCountById(@Param("count") Long count, @Param("lastvisiteddate") Date lastvisiteddate,
			@Param("lastsessionid") String lastsessionid, @Param("id") Long id);

	@Query(value = "select SUM(count) from visitorcount vc", nativeQuery=true)
	Long countVistor();

}
