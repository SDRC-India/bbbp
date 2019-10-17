package org.sdrc.bbbp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
/**
 * 
 * @author Debiprasad (debiprasad@sdrc.co.in) created on 01/03/2018 02:18
 *
 */
@Entity
public class Question implements Serializable {


	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private Integer questionId;
	
	
	@Column(name = "question_order")
	private Long questionOrder;

	@Column(name = "section",length=700)
	private String section;
	
	@Column(name = "Subsection",length=700)
	private String subsection;
		
	@Column(name = "question",length=700)
	private String question;
	
	@Column(name = "agg_role")
	private String aggRole;
		
	@Column(name = "field_type")
	private String fieldType;
	
	@Column(name = "column_name")
	private String columnName;
	
	@Column(name = "dependecy")
	private Boolean dependecy;

	@Column(name = "dependent_column")
	private String dependentColumn;
	
	@Column(name = "dependent_condition")
	private String dependentCondition;

	/*@Column(name = "condition_type")
	private String conditionType;
*/
	@Column(name = "frequency")
	private String frequency;

	@ManyToOne
	@JoinColumn(name = "role_id_fk")
	private Role role;
	
	
	/*@ManyToOne
	@JoinColumn(name= "typeDetail_id_fk")
	private TypeDetail typeDetail;*/
	
	@ManyToOne
	@JoinColumn(name = "type_id_fk")
	private Type typeId;
	
	@Column(name = "controller_type")
	private String controllertype;
	
	

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSubsection() {
		return subsection;
	}

	public void setSubsection(String subsection) {
		this.subsection = subsection;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAggRole() {
		return aggRole;
	}

	public void setAggRole(String aggRole) {
		this.aggRole = aggRole;
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

	/*public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}
*/
	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	

	public String getControllertype() {
		return controllertype;
	}

	public void setControllertype(String controllertype) {
		this.controllertype = controllertype;
	}

	public Type getTypeId() {
		return typeId;
	}

	public void setTypeId(Type typeId) {
		this.typeId = typeId;
	}

	public Long getQuestionOrder() {
		return questionOrder;
	}

	public void setQuestionOrder(Long questionOrder) {
		this.questionOrder = questionOrder;
	}

	/*public TypeDetail getTypeDetail() {
		return typeDetail;
	}

	public void setTypeDetail(TypeDetail typeDetail) {
		this.typeDetail = typeDetail;
	}*/
	
	
	
	

}
