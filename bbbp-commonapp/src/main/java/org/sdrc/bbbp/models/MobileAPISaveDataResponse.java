package org.sdrc.bbbp.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MobileAPISaveDataResponse {
	
	private Integer status;
	private Long submissionId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;

	public MobileAPISaveDataResponse(Integer status, Long submissionId, String message) {
		super();
		this.status = status;
		this.submissionId = submissionId;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
