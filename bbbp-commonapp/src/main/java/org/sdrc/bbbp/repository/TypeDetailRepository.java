package org.sdrc.bbbp.repository;

import java.util.List;

import org.sdrc.bbbp.domain.TypeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeDetailRepository extends JpaRepository<TypeDetail, Integer> {
	
	List<TypeDetail> findByTypeIdId(Integer typeId);
	
	List<TypeDetail> findByIdIn(List<Integer> typeIds);
	

}
