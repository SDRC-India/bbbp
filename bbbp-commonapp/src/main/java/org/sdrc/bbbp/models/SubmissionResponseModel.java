package org.sdrc.bbbp.models;

public class SubmissionResponseModel {

	private String yearRange;
	private String periodReference;
	private String message;

	public String getYearRange() {
		return yearRange;
	}

	public void setYearRange(String yearRange) {
		this.yearRange = yearRange;
	}

	public String getPeriodReference() {
		return periodReference;
	}

	public void setPeriodReference(String periodReference) {
		this.periodReference = periodReference;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
