package org.sdrc.bbbp.dashboard.models;

import java.util.List;

import lombok.Data;

@Data
public class SnapShortDataModel {
	
	private String timePeriod;
	private List<SectorModel> snapshotData;

}
