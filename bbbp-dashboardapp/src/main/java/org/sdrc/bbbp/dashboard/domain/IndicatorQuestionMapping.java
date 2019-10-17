package org.sdrc.bbbp.dashboard.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class IndicatorQuestionMapping {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int indicator_NId;

	private byte highIsGood;

	@Column(length=65536)
	private String indicator_Info;

	private String indicator_Name;

	private Integer indicator_Order;

	private String short_Name;
	
	@Column(name="indicator_type")
	private String indicatorType;
	
	
	@Column(name="deno_or_max_score")
	private String denominatorOrMaxScore;
	
	@Column(name="numerator")
	private String numerator;
	
	private String tableName;
	
//	private String questionName;

	public int getIndicator_NId() {
		return indicator_NId;
	}

	public void setIndicator_NId(int indicator_NId) {
		this.indicator_NId = indicator_NId;
	}

	public byte getHighIsGood() {
		return highIsGood;
	}

	public void setHighIsGood(byte highIsGood) {
		this.highIsGood = highIsGood;
	}

	public String getIndicator_Info() {
		return indicator_Info;
	}

	public void setIndicator_Info(String indicator_Info) {
		this.indicator_Info = indicator_Info;
	}

	public String getIndicator_Name() {
		return indicator_Name;
	}

	public void setIndicator_Name(String indicator_Name) {
		this.indicator_Name = indicator_Name;
	}

	public Integer getIndicator_Order() {
		return indicator_Order;
	}

	public void setIndicator_Order(Integer indicator_Order) {
		this.indicator_Order = indicator_Order;
	}

	public String getShort_Name() {
		return short_Name;
	}

	public void setShort_Name(String short_Name) {
		this.short_Name = short_Name;
	}

	public String getIndicatorType() {
		return indicatorType;
	}

	public void setIndicatorType(String indicatorType) {
		this.indicatorType = indicatorType;
	}

	public String getDenominatorOrMaxScore() {
		return denominatorOrMaxScore;
	}

	public void setDenominatorOrMaxScore(String denominatorOrMaxScore) {
		this.denominatorOrMaxScore = denominatorOrMaxScore;
	}

	public String getNumerator() {
		return numerator;
	}

	public void setNumerator(String numerator) {
		this.numerator = numerator;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/*public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}*/
	
}
