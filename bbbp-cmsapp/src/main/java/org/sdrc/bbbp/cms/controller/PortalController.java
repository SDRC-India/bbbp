package org.sdrc.bbbp.cms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sdrc.bbbp.cms.domain.DataModel;
import org.sdrc.bbbp.cms.model.CMSLandingPage;
import org.sdrc.bbbp.cms.model.CmsDataDto;
import org.sdrc.bbbp.cms.service.CmsDataService;
import org.sdrc.bbbp.cms.service.CmsLandingPage;
import org.sdrc.bbbp.cms.service.GalleryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/portal")
public class PortalController {
	@Autowired
	private CmsDataService cmsDataService;

	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private	CmsLandingPage cmsLandingPage;
	
	@Value("${cms.filepath}")
	private String cmsFilepath;
	
	private Logger log = LoggerFactory.getLogger(CmsHomeController.class);
	
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:28:04 PM
	 * @return
	 */
	@GetMapping("/fetchLandingPageData")
	public CMSLandingPage  fetchLandingPageData() {
		return cmsLandingPage.fetchCMSLandingPage();
	}
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:28:07 PM
	 * @param viewName
	 * @param viewType
	 * @param request
	 * @return
	 */
	@GetMapping(value= {"/getPageContent"})
	public CmsDataDto getHome(@RequestParam(value = "viewName", required = true) String viewName,
			@RequestParam(value = "viewType", required = false) String viewType, HttpServletRequest request, HttpSession se) {
		
		try {
			return cmsDataService.fetchCmsData(viewName,viewType, se);
		} catch (Exception e) {
			log.error(request.getRequestURI()+"ERR-getPageContent Failed to fetch data from cmsData : {} with request viewType : {} ",viewName,viewType,e,request.getRequestURI());
			
			throw e;
		}
		
	}
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:28:10 PM
	 * @param viewName
	 * @return
	 */
	@GetMapping("/fetchPhotoGallery")
	public CmsDataDto getGalleryData(@RequestParam("viewName") String viewName) {
		return galleryService.fetchPhotoGalleryData(viewName, cmsFilepath);
	}


	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:28:12 PM
	 * @param subPhoto
	 * @return
	 */
	@PostMapping("/fetchAlbumPhotos")
	public List<DataModel> fetchAlbumPhotos(@RequestBody List<DataModel> subPhoto) {
		return galleryService.fetchPhotoGalleryDataWithUrl(subPhoto, cmsFilepath);
	}
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 07-Aug-2019 7:58:13 PM
	 * @return
	 */
	@GetMapping("/fetchVistorCount")
	public long  fetchVistorCount() {
		return cmsDataService.getVisitorCount();
	}
}
