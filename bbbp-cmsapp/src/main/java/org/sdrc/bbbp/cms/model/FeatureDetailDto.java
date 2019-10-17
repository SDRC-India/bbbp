package org.sdrc.bbbp.cms.model;

public class FeatureDetailDto {

	private Integer id;

	private Integer featureDetailOrder;

	private String section;

	private String label;
	
	private String hindiLabel;

	private String fieldType;

	private String columnName;

	private Boolean dependecy;

	private String dependentColumn;

	private String dependentCondition;

	private String controllerType;
	
	private String displayLabel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFeatureDetailOrder() {
		return featureDetailOrder;
	}

	public void setFeatureDetailOrder(Integer featureDetailOrder) {
		this.featureDetailOrder = featureDetailOrder;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getHindiLabel() {
		return hindiLabel;
	}

	public void setHindiLabel(String hindiLabel) {
		this.hindiLabel = hindiLabel;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Boolean getDependecy() {
		return dependecy;
	}

	public void setDependecy(Boolean dependecy) {
		this.dependecy = dependecy;
	}

	public String getDependentColumn() {
		return dependentColumn;
	}

	public void setDependentColumn(String dependentColumn) {
		this.dependentColumn = dependentColumn;
	}

	public String getDependentCondition() {
		return dependentCondition;
	}

	public void setDependentCondition(String dependentCondition) {
		this.dependentCondition = dependentCondition;
	}

	public String getControllerType() {
		return controllerType;
	}

	public void setControllerType(String controllerType) {
		this.controllerType = controllerType;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}
	
	
}
