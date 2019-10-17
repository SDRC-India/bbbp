package org.sdrc.bbbp.cms.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataModelDto implements Serializable{

	private String imageName;
	private String content;
	private String title;
	private String url;
	private String createdDate;
	private String caption;
	private String fileName;
	private String fileTitle;
	private String size;
	private Boolean isLive;
	private Boolean isNew;
	private String publishedDate;
	private Integer order;
	private Boolean flag;
	private Boolean isPriority;
	private Boolean isHome;
	private String hashKey;
	private String link;
	private Integer areaId;
	private String areaLevel;
	private String areaName;
	private Integer parentAreaId;
	private String parentAreaName;
	private String year;
	private String quarter;

	//this will get populated while sending the image to client
	private MultipartFile file;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getFileTitle() {
		return fileTitle;
	}
	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Boolean getIsLive() {
		return isLive;
	}
	public void setIsLive(Boolean isLive) {
		this.isLive = isLive;
	}
	public Boolean getIsNew() {
		return isNew;
	}
	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}
	
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Boolean getIsPriority() {
		return isPriority;
	}
	public void setIsPriority(Boolean isPriority) {
		this.isPriority = isPriority;
	}
	public Boolean getIsHome() {
		return isHome;
	}
	public void setIsHome(Boolean isHome) {
		this.isHome = isHome;
	}
	public String getHashKey() {
		return hashKey;
	}
	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}
	

	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaLevel() {
		return this.areaLevel;
	}

	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getParentAreaId() {
		return this.parentAreaId;
	}

	public void setParentAreaId(Integer parentAreaId) {
		this.parentAreaId = parentAreaId;
	}

	public String getParentAreaName() {
		return this.parentAreaName;
	}

	public void setParentAreaName(String parentAreaName) {
		this.parentAreaName = parentAreaName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getQuarter() {
		return quarter;
	}
	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
	
}
