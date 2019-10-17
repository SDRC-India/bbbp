package org.sdrc.bbbp.models;

import java.util.List;
import java.util.Map;

public class SaveSubmission {
	
	String quarter;
	String year;
	Map<String,List<Map<String,List<QuestionModel>>>> allQuestions;
	Integer quarterId;
	Integer yearId;
	String lastUpdate;
	Boolean isSubmit;
	
	public String getQuarter() {
		return quarter;
	}
	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Map<String, List<Map<String, List<QuestionModel>>>> getAllQuestions() {
		return allQuestions;
	}
	public void setAllQuestions(Map<String, List<Map<String, List<QuestionModel>>>> allQuestions) {
		this.allQuestions = allQuestions;
	}
	public Integer getQuarterId() {
		return quarterId;
	}
	public void setQuarterId(Integer quarterId) {
		this.quarterId = quarterId;
	}
	public Integer getYearId() {
		return yearId;
	}
	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Boolean getIsSubmit() {
		return isSubmit;
	}
	public void setIsSubmit(Boolean isSubmit) {
		this.isSubmit = isSubmit;
	}

}
