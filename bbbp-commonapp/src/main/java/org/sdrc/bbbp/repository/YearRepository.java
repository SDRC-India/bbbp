package org.sdrc.bbbp.repository;

import org.sdrc.bbbp.domain.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface YearRepository extends JpaRepository<Year, Integer> {
	@Transactional
	Year findByYearId(Integer yearId);
	Year findByYearRange(String yearRange);
    Year findTop1ByOrderByYearIdDesc();
    
    
   
}
