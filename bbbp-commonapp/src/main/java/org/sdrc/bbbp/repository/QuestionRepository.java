package org.sdrc.bbbp.repository;

import java.util.List;

import org.sdrc.bbbp.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	List<Question> findByRoleRoleIdOrderByQuestionOrderAsc(Integer roleId);
	
	List<Question> findAllByOrderByQuestionIdAsc();

	List<Question> findByOrderByQuestionIdAsc();
	
	

}
