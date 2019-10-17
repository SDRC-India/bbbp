package org.sdrc.bbbp.dashboard.models;

import java.util.List;

import lombok.Data;

@Data
public class ThematicViewModel {
	private String chartType; 
	List<CSRTrendChartModel> thematicView;
}
