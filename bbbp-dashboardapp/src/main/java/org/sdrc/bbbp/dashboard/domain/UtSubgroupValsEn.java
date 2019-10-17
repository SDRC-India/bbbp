package org.sdrc.bbbp.dashboard.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author debiprasad (debiprasad@sdrc.co.in) The persistent class for the
 *         ut_subgroup_vals_en database table.
 * 
 */
@Entity
@Table(name = "ut_subgroup_vals_en")
public class UtSubgroupValsEn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int subgroup_Val_NId;

	private String subgroup_Val;

	private int subgroup_Val_Order;
	
	private String subgroup_dimension;

	public int getSubgroup_Val_NId() {
		return this.subgroup_Val_NId;
	}

	public void setSubgroup_Val_NId(int subgroup_Val_NId) {
		this.subgroup_Val_NId = subgroup_Val_NId;
	}

	public String getSubgroup_Val() {
		return this.subgroup_Val;
	}

	public void setSubgroup_Val(String subgroup_Val) {
		this.subgroup_Val = subgroup_Val;
	}

	public int getSubgroup_Val_Order() {
		return this.subgroup_Val_Order;
	}

	public void setSubgroup_Val_Order(int subgroup_Val_Order) {
		this.subgroup_Val_Order = subgroup_Val_Order;
	}

	public String getSubgroup_dimension() {
		return subgroup_dimension;
	}

	public void setSubgroup_dimension(String subgroup_dimension) {
		this.subgroup_dimension = subgroup_dimension;
	}

}