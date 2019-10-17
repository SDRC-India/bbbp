package org.sdrc.bbbp.cms.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in) Created On: 13-Jul-2018 6:14:27
 *         PM
 * @param <T>
 * @param <ID>
 *            Intermediate repository interface with @NoRepositoryBean.
 */
@NoRepositoryBean
public interface CmsBaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	Optional<T> findById(ID id);

	@Transactional
	<S extends T> S save(S entity);

	List<T> findAll();

}
