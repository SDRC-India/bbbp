package org.sdrc.bbbp.service;

public interface JobService {

	void quarterlyJob();

	void yearlyCSRTimeperiodJob();
	
	String dailyMailReportJob(String stateId, String districtId/*, Integer yearId*/);
	
}
