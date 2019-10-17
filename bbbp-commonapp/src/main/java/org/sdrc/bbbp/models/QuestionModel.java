package org.sdrc.bbbp.models;

import java.util.List;
import java.util.Map;

public class QuestionModel {
	
	
	private Integer key;
	private String label;
	private String aggRole;
	private String type;
	private String columnName;
	private Boolean dependecy;
	private String dependentColumn;
	private List<String> dependentCondition;
	private String frequency;
	private Integer roleId;
	private List<OptionModel> options;
	private Integer typeId;
	private String controlType;
	private String value;
	private List<FileModel> fileValue;
	private Boolean isOthersSelected;
	private String othersValue;
	private Map<Integer,List<FileModel>> filesByType;
	private String section;
	private Validation validation;
	private String displayLabel;
	private String metaData;

	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getAggRole() {
		return aggRole;
	}
	public void setAggRole(String aggRole) {
		this.aggRole = aggRole;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public List<String> getDependentCondition() {
		return dependentCondition;
	}
	public void setDependentCondition(List<String> dependentCondition) {
		this.dependentCondition = dependentCondition;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public List<OptionModel> getOptions() {
		return options;
	}
	public void setOptions(List<OptionModel> options) {
		this.options = options;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getControlType() {
		return controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<FileModel> getFileValue() {
		return fileValue;
	}
	public void setFileValue(List<FileModel> fileValue) {
		this.fileValue = fileValue;
	}
	public Boolean getIsOthersSelected() {
		return isOthersSelected;
	}
	public void setIsOthersSelected(Boolean isOthersSelected) {
		this.isOthersSelected = isOthersSelected;
	}
	public String getOthersValue() {
		return othersValue;
	}
	public void setOthersValue(String othersValue) {
		this.othersValue = othersValue;
	}
	public Map<Integer, List<FileModel>> getFilesByType() {
		return filesByType;
	}
	public void setFilesByType(Map<Integer, List<FileModel>> filesByType) {
		this.filesByType = filesByType;
	}
	public Validation getValidation() {
		return validation;
	}
	public void setValidation(Validation validation) {
		this.validation = validation;
	}
	public String getDisplayLabel() {
		return displayLabel;
	}
	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}
	public String getMetaData() {
		return this.metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

}
