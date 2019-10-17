package org.sdrc.bbbp.cms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.sdrc.bbbp.cms.domain.CmsData;
import org.sdrc.bbbp.cms.domain.CmsDataJson;
import org.sdrc.bbbp.cms.domain.DataModel;
import org.sdrc.bbbp.cms.domain.Language;
import org.sdrc.bbbp.cms.model.CmsDataDto;
import org.sdrc.bbbp.cms.repository.CmsDataRepository;
import org.sdrc.bbbp.cms.util.CmsLanguageComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in) Created On: 19-Jul-2018 11:15:17
 *         AM
 */
@Service
public class GalleryServiceImpl implements GalleryService {

	@Autowired
	private CmsDataRepository cmsDataRepository;
	
	private static final ObjectMapper objectMpper = new ObjectMapper();

	@Value("${photo.gallery.url}")
	private String photoURL;
	
	@Override
	@Cacheable(value="cmsdata", key = "#viewName")
	public CmsDataDto fetchPhotoGalleryData(String viewName, String cmsFilepath) throws JSONException {
		
		CmsData cmsData = cmsDataRepository.findByViewName(viewName);
		// String filePath = cmsData.get

		ModelMapper modelMapper = new ModelMapper();

		CmsDataJson cmsDataJson = new CmsDataJson();
		try {
			cmsDataJson = objectMpper.treeToValue(cmsData.getViewContent(), CmsDataJson.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// get files of image to display in
		
		Map<String, Object> getterMethodMap = CmsLanguageComponent.beanProperties(cmsDataJson);
		
		for (Map.Entry<String, Object> entry : getterMethodMap.entrySet()) {

			for (int sectionCount = 0; sectionCount < ((List<Language>) entry.getValue()).size(); sectionCount++) {
				for (DataModel dataModel : ((List<Language>) entry.getValue()).get(sectionCount).getData()) {
					dataModel.setCaption(dataModel.getTitle());
				}
				
//				DataModel firstPhotoModel = ((List<Language>) entry.getValue()).get(sectionCount).getData().get(0);
//				if(firstPhotoModel.getImageName()!=null)
//					firstPhotoModel.setUrl(ImageConverter.encodingPhoto(cmsFilepath+ firstPhotoModel.getImageName()));
			}

		}
		cmsData.setViewContent(objectMpper.convertValue(cmsDataJson, JsonNode.class));
		CmsDataDto cmsDataDto = modelMapper.map(cmsData, CmsDataDto.class);
		return cmsDataDto;
	}
	

	@Override
	public CmsDataDto fetchPhotoGalleryDataWithUrl(String viewName, String cmsFilepath) throws JSONException {
		
		CmsData cmsData = cmsDataRepository.findByViewName(viewName);
		// String filePath = cmsData.get

		ModelMapper modelMapper = new ModelMapper();



		CmsDataJson cmsDataJson = new CmsDataJson();
		try {
			cmsDataJson = objectMpper.treeToValue(cmsData.getViewContent(), CmsDataJson.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// get files of image to display in
		
		Map<String, Object> getterMethodMap = CmsLanguageComponent.beanProperties(cmsDataJson);
		
		for (Map.Entry<String, Object> entry : getterMethodMap.entrySet()) {

			for (int sectionCount = 0; sectionCount < ((List<Language>) entry.getValue()).size(); sectionCount++) {
				for (DataModel dataModel : ((List<Language>) entry.getValue()).get(sectionCount).getData()) {
					dataModel.setCaption(dataModel.getTitle());
					if(dataModel.getImageName()!=null)
						dataModel.setUrl(dataModel.getImageName());
					

				}
				
			}

		}
		cmsData.setViewContent(objectMpper.convertValue(cmsDataJson, JsonNode.class));
		CmsDataDto cmsDataDto = modelMapper.map(cmsData, CmsDataDto.class);
		return cmsDataDto;

	}
	
	
	@Override
	public List<DataModel> fetchPhotoGalleryDataWithUrl(List<DataModel> dataModels, String cmsFilepath)
			throws JSONException {

		List<DataModel> dms = new ArrayList<>();

		for (DataModel dataModel : dataModels) {
			dataModel.setCaption(dataModel.getTitle());
			if (dataModel.getImageName() != null)
				dataModel.setUrl(photoURL + dataModel.getImageName()+"&inline="+true);
//				dataModel.setUrl(ImageConverter.encodingPhoto(cmsFilepath + dataModel.getImageName()));

			dms.add(dataModel);

		}

		return dms;

	}
}
