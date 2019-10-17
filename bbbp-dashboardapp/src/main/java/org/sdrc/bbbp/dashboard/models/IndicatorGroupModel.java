package org.sdrc.bbbp.dashboard.models;

import java.util.List;

import lombok.Data;

@Data
public class IndicatorGroupModel {
	
	private String groupedIndName;
	private String indicatorName;
	private List<String> chartsAvailable;
	private List<List<ChartDataModel>> chartData;
	private List<LegendModel> legends;
	private String indicatorGroup;
}
