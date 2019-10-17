package org.sdrc.bbbp.models;

public class FileModel {
	private String base64;
	private String fileName;
	private Integer typeId;
	private Boolean isDeleted;
	private Long submissionId;
	private Boolean isAttached;
	private String columnName;
	private String monthRange;
	private String yearRange;
	private Long fileSize;
	private String typeName;
	private Long attachmentId;
	
	
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Long getSubmissionId() {
		return submissionId;
	}
	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}
	public Boolean getIsAttached() {
		return isAttached;
	}
	public void setIsAttached(Boolean isAttached) {
		this.isAttached = isAttached;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getMonthRange() {
		return monthRange;
	}
	public void setMonthRange(String monthRange) {
		this.monthRange = monthRange;
	}
	public String getYearRange() {
		return yearRange;
	}
	public void setYearRange(String yearRange) {
		this.yearRange = yearRange;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Long getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	
	

}
