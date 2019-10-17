package org.sdrc.bbbp.dashboard.service;

import org.sdrc.bbbp.dashboard.domain.UtTimeperiod;

public interface AggregationService {
	
	public UtTimeperiod createQuaterlyTimePeriod();
	
	public boolean callAggregation(UtTimeperiod timeperiod);

	public void aggregateData();

	void aggregateDataQuarterly(Integer tpId, Integer yearId, Integer period);

	void aggregateAllData();
}
