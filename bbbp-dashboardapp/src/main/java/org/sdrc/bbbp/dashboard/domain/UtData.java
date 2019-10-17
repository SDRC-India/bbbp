package org.sdrc.bbbp.dashboard.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.sdrc.bbbp.domain.Area;
import org.sdrc.bbbp.domain.TypeDetail;

/**
 * @author debiprasad (debiprasad@sdrc.co.in)
 * 
 */
@Entity
@Table(name = "ut_data",

		indexes = @Index(columnList = "IUSNId,timePeriod_NId,area_NId", name = "ius_tp_area_index"), uniqueConstraints = @UniqueConstraint(columnNames = { "IUSNId",

				"timePeriod_NId", "area_NId","type_detail_id_fk" }, name = "uq_ius_tp_area"))
public class UtData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int data_NId;

	@JoinColumn(name = "area_NId")
	@ManyToOne
	private Area area_NId;

//	private Double data_Value;
	
	private String data_Value;

	private Integer IC_IUS_Order;

	private int indicator_NId;

	@JoinColumn(name = "type_detail_id_fk")
	@ManyToOne
	private TypeDetail typeDetail;

	@JoinColumn(name = "IUSNId")
	@ManyToOne
	private UtIndicatorUnitSubgroup IUSNId;

	@JoinColumn(name = "source_NId")
	@ManyToOne
	private UtIndicatorClassificationsEn source_NId;

	@JoinColumn(name = "timePeriod_NId")
	@ManyToOne
	private UtTimeperiod timePeriod_NId;
	
	private Boolean highIsGood;

	public UtData() {
	}

	public int getData_NId() {
		return this.data_NId;
	}

	public void setData_NId(int data_NId) {
		this.data_NId = data_NId;
	}

//	public Double getData_Value() {
//		return this.data_Value;
//	}
//
//	public void setData_Value(Double data_Value) {
//		this.data_Value = data_Value;
//	}
	
	public String getData_Value() {
		return data_Value;
	}

	public void setData_Value(String data_Value) {
		this.data_Value = data_Value;
	}

	public Integer getIC_IUS_Order() {
		return this.IC_IUS_Order;
	}

	public void setIC_IUS_Order(Integer IC_IUS_Order) {
		this.IC_IUS_Order = IC_IUS_Order;
	}

	public int getIndicator_NId() {
		return this.indicator_NId;
	}

	public void setIndicator_NId(int indicator_NId) {
		this.indicator_NId = indicator_NId;
	}

	public UtIndicatorUnitSubgroup getIUSNId() {
		return this.IUSNId;
	}

	public void setIUSNId(UtIndicatorUnitSubgroup IUSNId) {
		this.IUSNId = IUSNId;
	}

	public UtIndicatorClassificationsEn getSource_NId() {
		return this.source_NId;
	}

	public void setSource_NId(UtIndicatorClassificationsEn source_NId) {
		this.source_NId = source_NId;
	}

	public UtTimeperiod getTimePeriod_NId() {
		return this.timePeriod_NId;
	}

	public void setTimePeriod_NId(UtTimeperiod timePeriod_NId) {
		this.timePeriod_NId = timePeriod_NId;
	}

	public Area getArea_NId() {
		return area_NId;
	}

	public void setArea_NId(Area area_NId) {
		this.area_NId = area_NId;
	}

	public TypeDetail getTypeDetail() {
		return typeDetail;
	}

	public void setTypeDetail(TypeDetail typeDetail) {
		this.typeDetail = typeDetail;
	}

	public Boolean getHighIsGood() {
		return highIsGood;
	}

	public void setHighIsGood(Boolean highIsGood) {
		this.highIsGood = highIsGood;
	}
	
	

}