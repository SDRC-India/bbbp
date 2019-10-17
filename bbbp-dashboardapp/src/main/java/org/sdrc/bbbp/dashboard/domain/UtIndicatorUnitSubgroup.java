package org.sdrc.bbbp.dashboard.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The persistent class for the ut_indicator_unit_subgroup database table.
 * 
 * @author debiprasad (debiprasad@sdrc.co.in)
 */
@Entity
@Table(name = "ut_indicator_unit_subgroup", indexes = @Index(columnList = "indicator_NId,subgroup_Val_NId,unit_NId", name = "ius_index"), uniqueConstraints = @UniqueConstraint(columnNames = { "indicator_NId", "subgroup_Val_NId", "unit_NId" }, name = "uk_ius"))
public class UtIndicatorUnitSubgroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int IUSNId;

	@JoinColumn(name = "indicator_NId")
	@ManyToOne
	private IndicatorQuestionMapping indicator_NId;

	private double max_Value;

	private double min_Value;

	@JoinColumn(name = "subgroup_Val_NId")
	@ManyToOne
	private UtSubgroupValsEn subgroup_Val_NId;

	@JoinColumn(name = "unit_NId")
	@ManyToOne
	private UtUnitEn unit_NId;
	
//	@OneToMany(mappedBy="IUSNId")
//	List<UtIcIus> icIus;
	
	public UtIndicatorUnitSubgroup() {
		super();
	}

	public UtIndicatorUnitSubgroup(int IUSNId) {
		this.IUSNId = IUSNId;
	}


/*	public List<UtIcIus> getIcIus() {
		return icIus;
	}

	public void setIcIus(List<UtIcIus> icIus) {
		this.icIus = icIus;
	}*/

	public int getIUSNId() {
		return this.IUSNId;
	}

	public void setIUSNId(int IUSNId) {
		this.IUSNId = IUSNId;
	}

	public double getMax_Value() {
		return this.max_Value;
	}

	public void setMax_Value(double max_Value) {
		this.max_Value = max_Value;
	}

	public double getMin_Value() {
		return this.min_Value;
	}

	public void setMin_Value(double min_Value) {
		this.min_Value = min_Value;
	}

	public IndicatorQuestionMapping getIndicator_NId() {
		return indicator_NId;
	}

	public void setIndicator_NId(IndicatorQuestionMapping indicator_NId) {
		this.indicator_NId = indicator_NId;
	}

	public UtSubgroupValsEn getSubgroup_Val_NId() {
		return this.subgroup_Val_NId;
	}

	public void setSubgroup_Val_NId(UtSubgroupValsEn subgroup_Val_NId) {
		this.subgroup_Val_NId = subgroup_Val_NId;
	}

	public UtUnitEn getUnit_NId() {
		return this.unit_NId;
	}

	public void setUnit_NId(UtUnitEn unit_NId) {
		this.unit_NId = unit_NId;
	}
	
	
	
}