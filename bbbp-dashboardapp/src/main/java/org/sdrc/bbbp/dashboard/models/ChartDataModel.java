package org.sdrc.bbbp.dashboard.models;

import lombok.Data;

@Data
public class ChartDataModel {
	
	private String axis;
	
	private Double value;
	
	private Integer id;
	
	private String IndicatorName;
	
	private String key;
	
	private String colorCode;
	
}
