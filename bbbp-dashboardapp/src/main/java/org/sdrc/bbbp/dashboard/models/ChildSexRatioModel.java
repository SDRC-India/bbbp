package org.sdrc.bbbp.dashboard.models;

import java.util.List;

public class ChildSexRatioModel {
	
	private List<CsrAreaModel> dataCollection;
	private List<ValueObject>  ligends;
    private List<CsrAllAreaMapModel> csrAllAreaMapModel;
    
	
	public List<CsrAreaModel> getDataCollection() {
		return dataCollection;
	}
	public void setDataCollection(List<CsrAreaModel> dataCollection) {
		this.dataCollection = dataCollection;
	}
	public List<ValueObject> getLigends() {
		return ligends;
	}
	public void setLigends(List<ValueObject> ligends) {
		this.ligends = ligends;
	}
	public List<CsrAllAreaMapModel> getCsrAllAreaMapModel() {
		return csrAllAreaMapModel;
	}
	public void setCsrAllAreaMapModel(List<CsrAllAreaMapModel> csrAllAreaMapModel) {
		this.csrAllAreaMapModel = csrAllAreaMapModel;
	}
	
	
	
	
	
	
	

}
