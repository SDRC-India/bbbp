package org.sdrc.bbbp.models;

public class OptionModel {
	
	private Integer key;
	private String value;
	private Integer order;
	private Boolean isSelected;
	private Integer parentId;
	
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	

}
