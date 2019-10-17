package org.sdrc.bbbp.cms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.sdrc.bbbp.cms.domain.CmsData;
import org.sdrc.bbbp.cms.model.CMSLandingPage;
import org.sdrc.bbbp.cms.model.CMSRecentActivity;
import org.sdrc.bbbp.cms.model.CMSRecentContent;
import org.sdrc.bbbp.cms.model.CmsObject;
import org.sdrc.bbbp.cms.repository.CmsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmsLandingPageServiseImpl implements CmsLandingPage {
	
	@Autowired
	private CmsDataRepository cmsDataRepository;

	
	SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");

	@Override
	public CMSLandingPage fetchCMSLandingPage() {
		CMSLandingPage cMSLandingPage = new CMSLandingPage();
		
		CMSRecentContent cMSRecentContent= new CMSRecentContent();
		List<CmsData> listOfCMSData = cmsDataRepository.findCmsData();
		cMSRecentContent.setContent("This page enables the user to manage, edit and delete the content to be displayed in the portal under various options.");
		cMSRecentContent.setLastUpdateViweName("Last Updated On");
		cMSRecentContent.setLastUpdateOn(format.format(listOfCMSData.get(0).getUpdatedDate()));
		cMSRecentContent.setFileName("User Manual");
		cMSRecentContent.setFilePath("UserManual/BBBP_CMS_UserManual.pdf");
		
		CMSRecentActivity cMSRecentActivity = new CMSRecentActivity();
		cMSRecentActivity.setRecentActivities("Recent Activities");
		List<CmsObject> listOFCmsObject =new ArrayList<>();
		for (CmsData cmsData : listOfCMSData) {
			CmsObject cmsObject =new CmsObject();
			cmsObject.setViewName(cmsData.getViewName());
			cmsObject.setLastUpdateOn(format.format(cmsData.getUpdatedDate()));
			listOFCmsObject.add(cmsObject);
		}
		cMSRecentActivity.setCmsObject(listOFCmsObject);
		
		/*Map<String,CMSRecentContent> cMSRecentContentMap = new HashMap<String,CMSRecentContent>();
	    Map<String,CMSRecentActivity> cMSRecentActivityMap = new HashMap<String,CMSRecentActivity>();
	    cMSRecentContentMap.put("CMSRecentContent", cMSRecentContent);
	    cMSRecentActivityMap.put("CMSRecentActivity", cMSRecentActivity);*/
	    
		cMSLandingPage.setcMSRecentContent(cMSRecentContent);
		cMSLandingPage.setcMSRecentActivity(cMSRecentActivity);
		return cMSLandingPage;
	}

}
