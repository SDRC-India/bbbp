package org.sdrc.bbbp.dashboard.models;

import java.util.List;

public class SubSectorModel {
	
	private Integer subSectorId;
	private String subSectorName;
	private List<IndicatorModel> indicators;
	private List<ValueObject> kpiList;
	private List<IndicatorGroupModel> groupedIndList;
	
	
	public Integer getSubSectorId() {
		return subSectorId;
	}
	public void setSubSectorId(Integer subSectorId) {
		this.subSectorId = subSectorId;
	}
	
	public String getSubSectorName() {
		return subSectorName;
	}
	public void setSubSectorName(String subSectorName) {
		this.subSectorName = subSectorName;
	}
	public List<IndicatorModel> getIndicators() {
		return indicators;
	}
	public void setIndicators(List<IndicatorModel> indicators) {
		this.indicators = indicators;
	}
	public List<IndicatorGroupModel> getGroupedIndList() {
		return groupedIndList;
	}
	public void setGroupedIndList(List<IndicatorGroupModel> groupedIndList) {
		this.groupedIndList = groupedIndList;
	}
	public List<ValueObject> getKpiList() {
		return kpiList;
	}
	public void setKpiList(List<ValueObject> kpiList) {
		this.kpiList = kpiList;
	}

}
