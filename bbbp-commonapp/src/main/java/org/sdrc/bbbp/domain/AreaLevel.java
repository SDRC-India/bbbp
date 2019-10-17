package org.sdrc.bbbp.domain;


import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="area_level")
public class AreaLevel implements Serializable{
	
	private static final long serialVersionUID = 1519381375815795764L;

	@Id
	@Column(name = "area_level_id_pk")
	private Integer areaLevelId;
	
	@Column(name = "area_level_name", nullable = false)
	private String areaLevelName;
	
	@OneToMany(mappedBy="level")
	private List<Area> areas;

	public Integer getAreaLevelId() {
		return areaLevelId;
	}

	public void setAreaLevelId(Integer areaLevelId) {
		this.areaLevelId = areaLevelId;
	}

	public String getAreaLevelName() {
		return areaLevelName;
	}

	public void setAreaLevelName(String areaLevelName) {
		this.areaLevelName = areaLevelName;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public AreaLevel() {
		super();
		
	}
	
	public AreaLevel(int level ) {
		
		this.areaLevelId=level;
		
	}

}
