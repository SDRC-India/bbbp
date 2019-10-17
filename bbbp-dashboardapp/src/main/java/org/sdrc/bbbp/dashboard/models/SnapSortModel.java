package org.sdrc.bbbp.dashboard.models;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SnapSortModel {

	private String areaLevelName;
	private Integer areaLevelId;
	private Integer stateId;
	private String stateName;
	private Integer districtId;
	private String districtName;
	private String timeperiod;
	private Map<String, Map<String, List<String>>> svgs;

}
