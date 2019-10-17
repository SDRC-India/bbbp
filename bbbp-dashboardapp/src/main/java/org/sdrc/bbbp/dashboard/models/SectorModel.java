package org.sdrc.bbbp.dashboard.models;

import java.util.List;

public class SectorModel {
	
	
	private Integer sectorId;
	private String sectorName;
	private List<SubSectorModel> subSectors;
	
	public Integer getSectorId() {
		return sectorId;
	}
	public void setSectorId(Integer sectorId) {
		this.sectorId = sectorId;
	}
	public String getSectorName() {
		return sectorName;
	}
	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}
	public List<SubSectorModel> getSubSectors() {
		return subSectors;
	}
	public void setSubSectors(List<SubSectorModel> subSectors) {
		this.subSectors = subSectors;
	}
	
	
	

}
