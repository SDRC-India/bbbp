package org.sdrc.bbbp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Entity
@JsonAutoDetect
public class PeriodReference implements Serializable {

	
	/**
	 *  Debiprasad(debiprasad@sdrc.co.in) 16-03-2018 
	 */
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="periodreference_id")
	private Integer periodReferenceId;
	
	@ManyToOne
	@JoinColumn(name="periodicity_id_fk")
	private Periodicity periodicity;
	
	@Column(name="month_range")
	private String monthRange;
	
	@Column(name="short_name")
	private String shortName;
	
	@Column(name="order_by")
	private Integer orderBy;
	
	
	public PeriodReference() {
		// TODO Auto-generated constructor stub
	}
	public PeriodReference(Integer periodReferenceId){
		this.periodReferenceId = periodReferenceId;
	}
	
	

	public Integer getPeriodReferenceId() {
		return periodReferenceId;
	}

	public void setPeriodReferenceId(Integer periodReferenceId) {
		this.periodReferenceId = periodReferenceId;
	}

	public Periodicity getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(Periodicity periodicity) {
		this.periodicity = periodicity;
	}

	public String getMonthRange() {
		return monthRange;
	}

	public void setMonthRange(String monthRange) {
		this.monthRange = monthRange;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	
	
	
}
