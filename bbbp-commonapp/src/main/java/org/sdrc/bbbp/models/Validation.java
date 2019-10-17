package org.sdrc.bbbp.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Validation implements Serializable{

	private Integer maxLength;
	private Integer minLength;
	private Boolean mandatory;
	private String fileType;
	private String fileSize;
	private String imageType;
	
	
	public Integer getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}
	public Integer getMinLength() {
		return minLength;
	}
	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}
	public Boolean getMandatory() {
		return mandatory;
	}
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	

}
