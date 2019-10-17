package org.sdrc.bbbp.dashboard.models;

import lombok.Data;

@Data
public class ThematicViewImageModel {
	private String sectorName;
	private String subSectorName;
	private String indicatorName;
	private Integer indicatorId;
	private String timePeriod;
	private Integer timePeriodId;
	private Integer areaId;
	private String areaName;
	private String indiaMapImage; // base64
	private String indiaMapImageLegend; // base64
	private String districtImage; // base64
}
