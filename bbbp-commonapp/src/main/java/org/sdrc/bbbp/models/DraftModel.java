package org.sdrc.bbbp.models;

public class DraftModel {
	
	private Long submissionId;
	private String timeRange;
	private String yearRange;
	private String submissionDate;
	private Integer periodId;
	private Integer yearId;
	
	public Long getSubmissionId() {
		return submissionId;
	}
	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}
	public String getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}
	public String getYearRange() {
		return yearRange;
	}
	public void setYearRange(String yearRange) {
		this.yearRange = yearRange;
	}
	public String getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(String submissionDate) {
		this.submissionDate = submissionDate;
	}
	public Integer getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Integer periodId) {
		this.periodId = periodId;
	}
	public Integer getYearId() {
		return yearId;
	}
	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}
	
	

}
