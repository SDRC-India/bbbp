package org.sdrc.bbbp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Periodicity implements Serializable {

	/**
	 * Debiprasad(debiprasad@sdrc.co.in) 16-03-2018 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="periodicity_id")
	private Integer periodicityId;
	
	@Column(name="periodicity_name")
	private String periodicityName;
	
	@Column(name="order_by")
	private Integer orderBy;
	
	public Periodicity(){
		super();
	}

	public Periodicity(Integer periodicityId) {
		super();
		this.periodicityId = periodicityId;
	}


	public Integer getPeriodicityId() {
		return periodicityId;
	}

	public void setPeriodicityId(Integer periodicityId) {
		this.periodicityId = periodicityId;
	}

	public String getPeriodicityName() {
		return periodicityName;
	}

	public void setPeriodicityName(String periodicityName) {
		this.periodicityName = periodicityName;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	
	
	
	
	
	

}
