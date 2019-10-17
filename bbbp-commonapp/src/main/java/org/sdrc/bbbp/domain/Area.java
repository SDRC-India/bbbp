package org.sdrc.bbbp.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.NamedQuery;


@Entity
@Table
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "area")
@NamedQuery(name = "Area.findAll", query="select a from Area a order by a.areaName")
public class Area implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1558538811474305739L;

	@Id
	@Column(name = "area_id")
	private Integer areaId;

	@Column(name = "area_name")
	private String areaName;

	@Column(name = "parent_area_id")
	private Integer parentAreaId;

	/*@Column(name = "level")
	private int level;*/

	@ManyToOne
	@JoinColumn(name="level", nullable = false)
	private AreaLevel level;
	
	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "updated_date")
	private Timestamp updatedDate;

	@Column(name = "is_live")
	private boolean isLive;

	@Column(name = "area_code")
	private String areaCode;

	@Column(name = "short_name")
	private String shortName;

	// ******** bi-directional one-to-many association to UserAreaMapping
	// *******
	@JsonIgnore
	@OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
	private List<UserAreaMapping> userAreaMappings;

	public Area() {
		super();
	}

	public Area(int areaId) {
		this.areaId = areaId;
	}

	// getter setters
	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer getParentAreaId() {
		return parentAreaId;
	}

	public void setParentAreaId(Integer parentAreaId) {
		this.parentAreaId = parentAreaId;
	}

	/*public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}*/

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<UserAreaMapping> getUserAreaMappings() {
		return userAreaMappings;
	}

	public void setUserAreaMappings(List<UserAreaMapping> userAreaMappings) {
		this.userAreaMappings = userAreaMappings;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public AreaLevel getLevel() {
		return level;
	}

	public void setLevel(AreaLevel level) {
		this.level = level;
	}
	

}
