package org.sdrc.bbbp.cms.service;

import java.util.List;

import org.json.JSONException;
import org.sdrc.bbbp.cms.domain.DataModel;
import org.sdrc.bbbp.cms.model.CmsDataDto;

public interface GalleryService {

	CmsDataDto fetchPhotoGalleryData(String viewName, String cmsFilepath) throws JSONException;

	List<DataModel> fetchPhotoGalleryDataWithUrl( List<DataModel> dataModels, String cmsFilepath)
			throws JSONException;

	CmsDataDto fetchPhotoGalleryDataWithUrl(String viewName, String cmsFilepath) throws JSONException;

	
}

