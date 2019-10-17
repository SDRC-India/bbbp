package org.sdrc.bbbp.cms.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.sdrc.bbbp.domain.Feature;
import org.sdrc.bbbp.domain.Type;

/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in)
 * Created On: 30-Jul-2018 12:16:36 AM
 */
@Entity
public class FeatureDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "feature_detail_id")
	private Integer id;

	private Integer featureDetailOrder;

	@Column(length = 700)
	private String section;

	@Column(length = 700)
	private String label;
	
	@Column(length = 700)
	private String hindiLabel;

	private String fieldType;

	private String columnName;

	private Boolean dependecy;

	private String dependentColumn;

	private String dependentCondition;

	@Column(length = 100)
	private String displayLabel;
	
	@ManyToOne
	@JoinColumn(name = "feature_id_fk")
	private Feature feature;
	
	@ManyToOne
	@JoinColumn(name = "type_id_fk")
	private Type typeId;

	private String controllerType;
	

	@Column(nullable = true, columnDefinition = "text", length = 65556)
	private String validation;
	
	@Column(length = 50)
	private String metaData;

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

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public String getControllerType() {
		return controllerType;
	}

	public void setControllerType(String controllerType) {
		this.controllerType = controllerType;
	}

	public String getHindiLabel() {
		return hindiLabel;
	}

	public void setHindiLabel(String hindiLabel) {
		this.hindiLabel = hindiLabel;
	}

	public String getValidation() {
		return validation;
	}

	public void setValidation(String validation) {
		this.validation = validation;
	}

	public Type getTypeId() {
		return typeId;
	}

	public void setTypeId(Type typeId) {
		this.typeId = typeId;
	}

	public String getDisplayLabel() {
		return displayLabel;
	}

	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	public String getMetaData() {
		return metaData;
	}

	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}
	
	

}
