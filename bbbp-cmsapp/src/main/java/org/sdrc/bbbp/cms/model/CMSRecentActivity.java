package org.sdrc.bbbp.cms.model;

import java.util.List;

public class CMSRecentActivity {
	
	private String recentActivities;
	private List<CmsObject> cmsObject;
	public String getRecentActivities() {
		return recentActivities;
	}
	public void setRecentActivities(String recentActivities) {
		this.recentActivities = recentActivities;
	}
	public List<CmsObject> getCmsObject() {
		return cmsObject;
	}
	public void setCmsObject(List<CmsObject> cmsObject) {
		this.cmsObject = cmsObject;
	}
	
	
}
