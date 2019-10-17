package org.sdrc.bbbp.models;

import java.sql.Timestamp;

public class AreaModel {

	private Integer areaId;

	private String areaName;

	private int parentAreaId;

	private String areaLevel;
	
	private int areaLevelId;

	private Timestamp createdDate;

	private Timestamp updatedDate;

	private boolean isLive;

	private String areaCode;

	private String shortName;

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getParentAreaId() {
		return parentAreaId;
	}

	public void setParentAreaId(int parentAreaId) {
		this.parentAreaId = parentAreaId;
	}

	public String getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int getAreaLevelId() {
		return areaLevelId;
	}

	public void setAreaLevelId(int areaLevelId) {
		this.areaLevelId = areaLevelId;
	}

}
