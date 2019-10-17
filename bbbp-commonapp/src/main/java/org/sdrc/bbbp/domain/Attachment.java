package org.sdrc.bbbp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Attachment	implements Serializable {

	/** 
	 * Debiprasad (debiprasad@sdrc.co.in)
	 * */
		
		private static final long serialVersionUID = 1L;
		
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "attachment_id")
		private Long attachmentId;
		
		@Column(name = "column_name")
		private String columnName;
		
		@Column(name = "file_path")
		private String filePath;
		
		@ManyToOne
		@JoinColumn(name = "submissionData_id_fk")
		private SubmissionData submissionDataId;
		
		@ManyToOne
		@JoinColumn(name = "typedetail_id_fk")
		private TypeDetail typeDetailId; //typedetails
		
		@Column(name="is_deleted")
		private Boolean isDeleted;
	
		
		@Column(name="original_Name")
		private String originalName;
		
		@Column (name="file_Size")
		private Long fileSize;
		
		

		public Long getAttachmentId() {
			return attachmentId;
		}

		public void setAttachmentId(Long attachmentId) {
			this.attachmentId = attachmentId;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}

		public SubmissionData getSubmissionDataId() {
			return submissionDataId;
		}

		public void setSubmissionDataId(SubmissionData submissionDataId) {
			this.submissionDataId = submissionDataId;
		}

		public TypeDetail getTypeDetailId() {
			return typeDetailId;
		}

		public void setTypeDetailId(TypeDetail typeDetailId) {
			this.typeDetailId = typeDetailId;
		}

		public Boolean getIsDeleted() {
			return isDeleted;
		}

		public void setIsDeleted(Boolean isDeleted) {
			this.isDeleted = isDeleted;
		}



		public String getOriginalName() {
			return originalName;
		}

		public void setOriginalName(String originalName) {
			this.originalName = originalName;
		}

		public Long getFileSize() {
			return fileSize;
		}

		public void setFileSize(Long fileSize) {
			this.fileSize = fileSize;
		}

}
