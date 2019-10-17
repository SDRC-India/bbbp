package org.sdrc.bbbp.repository;

import java.util.List;

import org.sdrc.bbbp.domain.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository  extends JpaRepository<Attachment, Long> {

	Attachment findByColumnNameAndOriginalNameAndSubmissionDataIdSubmissionIdAndIsDeletedIsFalse(String columnName,
			String fileName, Long submissionId);

	List<Attachment> findBySubmissionDataIdSubmissionIdAndIsDeletedIsFalse(Long submissionId);
	
	List<Attachment> findBySubmissionDataIdSubmissionIdAndColumnNameAndIsDeletedIsFalse( Long submissionId,String columnName);
	
    Attachment findByAttachmentId(Long attachmentId);
    
    List<Attachment> findBySubmissionDataIdSubmissionIdAndOriginalNameAndColumnNameAndIsDeletedIsFalse(Long submissionId, String originalFileName,String columnName);
	
	//@Transactional
	//Iterable<Attachment> save(Iterable<Attachment> attachment);
	
}
