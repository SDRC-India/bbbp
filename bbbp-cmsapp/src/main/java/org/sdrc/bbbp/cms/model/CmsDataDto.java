package org.sdrc.bbbp.cms.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.sdrc.bbbp.cms.domain.CmsDataJson;

import com.fasterxml.jackson.databind.JsonNode;

public class CmsDataDto implements Serializable{

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private Long id;

	private String viewName;

	private String viewType;

	private String cmsType;
	
	private JsonNode viewContent;

	private String updatedDate;

	public Date getSubmissionDateConverted(String timezone) throws ParseException {
		dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
		return dateFormat.parse(this.updatedDate);
	}

	public void setSubmissionDate(Date updatedDate, String timezone) {
		dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
		this.updatedDate = dateFormat.format(updatedDate);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}

	public JsonNode getViewContent() {
		return viewContent;
	}

	public void setViewContent(JsonNode viewContent) {
		this.viewContent = viewContent;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public static SimpleDateFormat getDateformat() {
		return dateFormat;
	}

	public String getCmsType() {
		return cmsType;
	}

	public void setCmsType(String cmsType) {
		this.cmsType = cmsType;
	}

}
