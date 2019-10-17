package org.sdrc.bbbp.dashboard.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.sdrc.bbbp.dashboard.core.UtUnitType;

/**
 * @author debiprasad (debiprasad@sdrc.co.in) The persistent class for the
 *         ut_unit_en database table.
 * 
 */
@Entity
@Table(name = "ut_unit_en")
public class UtUnitEn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int unit_NId;

	private String unit_Name;

	@Enumerated(EnumType.STRING)
	@Column(name="utunittype")
	private UtUnitType utUnitType;
	
	
	public UtUnitEn() {
	}

	public int getUnit_NId() {
		return this.unit_NId;
	}

	public void setUnit_NId(int unit_NId) {
		this.unit_NId = unit_NId;
	}

	public String getUnit_Name() {
		return this.unit_Name;
	}

	public void setUnit_Name(String unit_Name) {
		this.unit_Name = unit_Name;
	}

	public UtUnitType getUtUnitType() {
		return utUnitType;
	}

	public void setUtUnitType(UtUnitType utUnitType) {
		this.utUnitType = utUnitType;
	}

}