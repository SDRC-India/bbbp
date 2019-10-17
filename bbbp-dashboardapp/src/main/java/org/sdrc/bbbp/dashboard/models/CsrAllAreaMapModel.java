package org.sdrc.bbbp.dashboard.models;

import java.util.List;

public class CsrAllAreaMapModel {
	
	private Integer timeperiodId;
	private String timeperiod;
	private CsrAreaModel indiaData;
	private List<CsrAreaModel> stateData;
	private List<ValueObject>  ligends;	
	
	
	public Integer getTimeperiodId() {
		return timeperiodId;
	}
	public void setTimeperiodId(Integer timeperiodId) {
		this.timeperiodId = timeperiodId;
	}
	public String getTimeperiod() {
		return timeperiod;
	}
	public void setTimeperiod(String timeperiod) {
		this.timeperiod = timeperiod;
	}
	public CsrAreaModel getIndiaData() {
		return indiaData;
	}
	public void setIndiaData(CsrAreaModel indiaData) {
		this.indiaData = indiaData;
	}
	public List<CsrAreaModel> getStateData() {
		return stateData;
	}
	public void setStateData(List<CsrAreaModel> stateData) {
		this.stateData = stateData;
	}
	public List<ValueObject> getLigends() {
		return ligends;
	}
	public void setLigends(List<ValueObject> ligends) {
		this.ligends = ligends;
	}
	
	
	

}
