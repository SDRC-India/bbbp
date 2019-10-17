package org.sdrc.bbbp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@Entity
@JsonAutoDetect
public class Year implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  Debiprasad(debiprasad@sdrc.co.in) 16-03-2018 
	 */
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="year_id")
	private Integer yearId;
	
	@Column(name="year_range")
	private String yearRange;
	
	@Column(name="order_by")
	private Integer orderBy;
	
	public Year() {
		// TODO Auto-generated constructor stub
	}
	public Year(Integer yearId) {
		this.yearId = yearId;
	}

	public Integer getYearId() {
		return yearId;
	}

	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}

	public String getYearRange() {
		return yearRange;
	}

	public void setYearRange(String yearRange) {
		this.yearRange = yearRange;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	
	
	
	
	

}
