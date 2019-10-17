package org.sdrc.bbbp.dashboard.models;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class GroupIndicatorModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String indicatorGroup;
	
	private List<Integer> indicatorNids;
	
	private List<String> chartType;
	
	private List<Integer> areaLevels;
	
	private Integer subSector;	

}
