package org.sdrc.bbbp.cms.repository;

import org.sdrc.bbbp.cms.domain.ArchiveCmsData;

public interface ArchiveCmsDataRepository extends CmsBaseRepository<ArchiveCmsData, Long> {

	ArchiveCmsData findTop1ByViewNameOrderByUpdatedDateDesc(String viewName);
}
