package org.sdrc.bbbp.cms.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.json.JSONException;
import org.modelmapper.ModelMapper;
import org.sdrc.bbbp.cms.domain.ArchiveCmsData;
import org.sdrc.bbbp.cms.domain.CmsData;
import org.sdrc.bbbp.cms.domain.CmsDataJson;
import org.sdrc.bbbp.cms.domain.DataModel;
import org.sdrc.bbbp.cms.domain.FeatureDetail;
import org.sdrc.bbbp.cms.domain.KeyContactModel;
import org.sdrc.bbbp.cms.domain.KeyContactRoleModel;
import org.sdrc.bbbp.cms.domain.Language;
import org.sdrc.bbbp.cms.model.CmsDataDto;
import org.sdrc.bbbp.cms.model.DataModelDto;
import org.sdrc.bbbp.cms.model.FeatureDetailDto;
import org.sdrc.bbbp.cms.model.QuestionFeatureDetailDtoModel;
import org.sdrc.bbbp.cms.repository.ArchiveCmsDataRepository;
import org.sdrc.bbbp.cms.repository.CmsDataRepository;
import org.sdrc.bbbp.cms.repository.FeatureDetailRepository;
import org.sdrc.bbbp.cms.util.CmsLanguageComponent;
import org.sdrc.bbbp.domain.Area;
import org.sdrc.bbbp.domain.PeriodReference;
import org.sdrc.bbbp.domain.TypeDetail;
import org.sdrc.bbbp.domain.Year;
import org.sdrc.bbbp.models.ErrorClass;
import org.sdrc.bbbp.models.OptionModel;
import org.sdrc.bbbp.models.QuestionModel;
import org.sdrc.bbbp.models.Validation;
import org.sdrc.bbbp.repository.AreaRepository;
import org.sdrc.bbbp.repository.PeriodReferenceRepository;
import org.sdrc.bbbp.repository.TypeDetailRepository;
import org.sdrc.bbbp.repository.VisitorCountRepository;
import org.sdrc.bbbp.repository.YearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in) Created On: 13-Jul-2018 6:20:38
 *         PM
 */
@Service
@Slf4j
public class CmsDataServiceImpl implements CmsDataService {

	@Autowired
	private CmsDataRepository cmsDataRepository;

	@Autowired
	private ArchiveCmsDataRepository archiveCmsDataRepository;

	@Autowired
	private FeatureDetailRepository featureDetailRepository;

	@Autowired
	private AreaRepository areaRepository;
	
	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	ServletContext context;
	
	@Autowired
	private YearRepository yearRepository;

	@Autowired
	private PeriodReferenceRepository periodReferenceRepository;
	
	@Autowired
	private VisitorCountRepository visitorCountRepository;
	
	@Autowired
	private TypeDetailRepository typeDetailRepository;

	private static final ModelMapper modelMapper = new ModelMapper();
	private static final ObjectMapper objectMpper = new ObjectMapper();
	
	@Value("${cms.filepath}")
	private String cmsFilepath;
	
	@Value("${news.view.name}")
	private String newsViewName;
	
	@Value("${innovative.view.name}")
	private String innovativeViewName;
	
	@Value("${activity.view.name}")
	private String activityViewName;
	
	@Value("${achievement.view.name}")
	private String achievementViewName;
	
	@Value("${guideline.view.name}")
	private String guidelineViewName;
	
	@Value("${photo.view.name}")
	private String photoViewName;
	
	@Value("${video.view.name}")
	private String videoViewName;
	
	@Value("${banner.section.name}")
	private String bannerSectionName;
	
	@Value("${banner.feature.name}")
	private String bannerFeatureName;
	
	@Value("${aboutus.feature.name}")
	private String aboutUsFeatureName;
	
	@Value("${photo.feature.name}")
	private String photoFeatureName;
	
	@Value("${banner.image.path}")
	private String bannerPath;
	
	@Value("classpath:static/assets/images/banners")
	Resource resourceFileBanner;
	
//	@Value("classpath:static/assets/footer")
//	Resource resourceFileFooter;
	
	@Value("${keyContact.nationallevel.view.name}")
	private String nationalLevel;
	
	@Value("${keyContact.statelevel.view.name}")
	private String stateLevel;
	
	@Value("${keyContact.districtlevel.view.name}")
	private String districtLevel;
	
	WebApplicationContext ctx;
	
	@Autowired
	DataSource datasource;
//	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
//	static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private  Path cmsFilePathLocation ;
	
	@PostConstruct
	public void init() {
		cmsFilePathLocation = Paths.get(cmsFilepath);
	}
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	@Cacheable(value="cmsdata", key = "#viewName", condition = "!#viewName.equals('Menu')")
	public CmsDataDto fetchCmsData(String viewName, String viewType, HttpSession session) throws JSONException {
		
		modelMapper.validate();
		
		if((viewType != null && viewType.equalsIgnoreCase("portalMenu"))) {
			return modelMapper.map(fetchCmsNonCachedData(viewName, viewType), CmsDataDto.class);
		}
		else {
			CmsData cmsData = cmsDataRepository.findByViewName(viewName);
			return modelMapper.map(cmsData, CmsDataDto.class) ;
		}
	}

	public  CmsData fetchCmsNonCachedData(String viewName, String viewType) throws JSONException {
		CmsData cmsData = cmsDataRepository.findByViewName(viewName);

		CmsDataJson cmsDataJson = new CmsDataJson();
		try {
			cmsDataJson = objectMpper.treeToValue(cmsData.getViewContent(), CmsDataJson.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// filter home menu
		if (viewType != null && viewType.equalsIgnoreCase("portalMenu")) {
			
			List<DataModel> dataModels;
			Map<String, Object> getterMethodMap = CmsLanguageComponent.beanProperties(cmsDataJson);
			
			for (Map.Entry<String, Object> entry : getterMethodMap.entrySet()) {
				for (int sectionCount = 0; sectionCount < ((List<Language>) entry.getValue()).size(); sectionCount++) {
					
					dataModels = filterDataModels(((List<Language>) entry.getValue()).get(sectionCount).getData(), isPoratlMenu(null));
					((List<Language>) entry.getValue()).get(sectionCount).getData().clear();
					((List<Language>) entry.getValue()).get(sectionCount).getData().addAll(dataModels);
				}
			}

		}
		
		/*if(viewName.equals("About Us") || viewName.equals("Organisational Structure") || viewName.equals("Home") || viewName.equals(newsViewName)) {
			
			Map<String, Object> getterMethodMap = CmsLanguageComponent.beanProperties(cmsDataJson);
			
			for (Map.Entry<String, Object> entry : getterMethodMap.entrySet()) {
				for (int sectionCount = 0; sectionCount < ((List<Language>) entry.getValue()).size(); sectionCount++) {
					for (DataModel dataModel : ((List<Language>) entry.getValue()).get(sectionCount).getData()) {
						if (dataModel.getImageName() != "") {
							dataModel.setUrl(ImageConverter.encodingPhoto(cmsFilepath + dataModel.getImageName()));
						}
					}
					
				}
			}
			
		}*/
		cmsData.setViewContent(objectMpper.convertValue(cmsDataJson, JsonNode.class));
		return cmsData;
	}
	
	@Override
	public List<FeatureDetailDto> fetchPageDetails(Integer featureId){
		
		List<FeatureDetail> featureDetails = featureDetailRepository.findByFeatureFeatureIdOrderByFeatureDetailOrderAsc(featureId);
		
		ModelMapper modelMapper = new ModelMapper();
		
		List<FeatureDetailDto> featureDetailDtos = new ArrayList<>();
		featureDetails.forEach(
				featureDetail -> featureDetailDtos.add(modelMapper.map(featureDetail, FeatureDetailDto.class)));
		
		return featureDetailDtos;
		
	}
	
	@Transactional
	@Override
	@Caching(evict = {
		    @CacheEvict(value = "cmsdata", key = "'Home'"),
		    @CacheEvict(value = "cmsdata", key = "#viewName")
		})
	public ErrorClass updateAddRecordInNestedAndTabularData(DataModelDto dataModel, String viewName, int languageIndex,
			int dataIndex, String pageLanguage, boolean isUpdate, Integer tableSize) {
		dataModel.setCreatedDate(DateTimeFormatter.ofPattern("dd-MM-YYYY").format(LocalDateTime.now().toLocalDate()));

		// set new=true when new data is publishing
		if (!isUpdate)
			dataModel.setIsNew(true);

		CmsData cmsData = cmsDataRepository.findByViewName(viewName);
		Boolean isPriority = dataModel.getIsPriority();
		// set blank values in case of null

		// first persist the old record in a separate table then add new record
		archiveCmsData(cmsData, viewName);

		// if (dataModel.getIsHome() == null) {
		if (viewName.equalsIgnoreCase(newsViewName) && !isUpdate)
			dataModel.setHashKey(DigestUtils.md5Hex(dataModel.getTitle() + dataModel.getCreatedDate()));

		if (viewName.equalsIgnoreCase(newsViewName))
			dataModel.setIsPriority(false);

		updateCMSData(dataModel, null, viewName, languageIndex, dataIndex, pageLanguage, isUpdate);
		// }
		if (viewName.equalsIgnoreCase(newsViewName)) {
			dataModel.setIsPriority(isPriority);
			CmsData cmsDataHome = cmsDataRepository.findByViewName("Home");// Fetch
																			// CmsData
																			// of
																			// Home
			archiveCmsData(cmsDataHome, "Home"); // Archive Home CMSData
			List<DataModel> listOfDataModels = null;
			
			CmsDataJson homeCmsDataJson = new CmsDataJson();
			try {
				homeCmsDataJson = objectMpper.treeToValue(cmsDataHome.getViewContent(), CmsDataJson.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			if (pageLanguage.equals("english")) {
				listOfDataModels = homeCmsDataJson.getEnglish().get(4).getData();
			} else {
				listOfDataModels = homeCmsDataJson.getHindi().get(4).getData();
			}
			int index = 0;
			Boolean isTrueAvailable = false;
			if (!isUpdate) {
				if (listOfDataModels.get(0).getIsPriority() == null) {
					updateCMSData(dataModel, null, "Home", 4, listOfDataModels.size() - 1, pageLanguage, isUpdate);
				} else {
					if (dataModel.getIsPriority()) {
						index = 0;
						for (DataModel data : listOfDataModels) {
							if (data.getIsPriority()) {
								data.setIsPriority(false);
								updateCMSData(null, data, "Home", 4, index, pageLanguage, isUpdate);
								if (listOfDataModels.size() == 10) {
									deleteCmsData("Home", 4, 0, pageLanguage, listOfDataModels.size());

									updateCMSData(dataModel, null, "Home", 4, listOfDataModels.size() - 1, pageLanguage,
											isUpdate);

								} else {
									updateCMSData(dataModel, null, "Home", 4, listOfDataModels.size(), pageLanguage,
											isUpdate);
								}
								isTrueAvailable = true;
								break;
							}
							++index;

						}
						if (!isTrueAvailable) {
							if (listOfDataModels.size() == 10) {
								deleteCmsData("Home", 4, 0, pageLanguage, listOfDataModels.size());

								updateCMSData(dataModel, null, "Home", 4, listOfDataModels.size() - 1, pageLanguage,
										isUpdate);

							} else {
								updateCMSData(dataModel, null, "Home", 4, listOfDataModels.size(), pageLanguage,
										isUpdate);
							}
						}

					} else {

						if (listOfDataModels.size() == 10) {
							if (listOfDataModels.get(0).getIsPriority()) {
								deleteCmsData("Home", 4, 1, pageLanguage, listOfDataModels.size());
							} else {
								deleteCmsData("Home", 4, 0, pageLanguage, listOfDataModels.size());
							}

							updateCMSData(dataModel, null, "Home", 4, listOfDataModels.size() - 1, pageLanguage,
									isUpdate);

						} else {
							updateCMSData(dataModel, null, "Home", 4, listOfDataModels.size(), pageLanguage, isUpdate);
						}

					}
				}
			} else {
				isTrueAvailable = false;
				if (dataModel.getIsPriority()) {
					index = 0;
					for (DataModel data : listOfDataModels) {

						if (data.getHashKey().equals(dataModel.getHashKey()) && data.getIsPriority()
								&& dataModel.getIsPriority()) {
							updateCMSData(dataModel, null, "Home", 4, index, pageLanguage, isUpdate);
							isTrueAvailable = true;
							break;
						}

						if (data.getIsPriority()) {
							data.setIsPriority(false);
							updateCMSData(null, data, "Home", 4, index, pageLanguage, isUpdate);

						}
						if (data.getHashKey().equals(dataModel.getHashKey())) {
							updateCMSData(dataModel, null, "Home", 4, index, pageLanguage, isUpdate);
							isTrueAvailable = true;
						}

						++index;
					}
					if (!isTrueAvailable) {

						if (listOfDataModels.size() == 10) {
							if (listOfDataModels.get(0).getIsPriority()) {
								deleteCmsData("Home", 4, 1, pageLanguage, listOfDataModels.size());
							} else {
								deleteCmsData("Home", 4, 0, pageLanguage, listOfDataModels.size());
							}

							updateCMSData(dataModel, null, "Home", 4, listOfDataModels.size() - 1, pageLanguage, false);

						} else {
							updateCMSData(dataModel, null, "Home", 4, listOfDataModels.size(), pageLanguage, false);
						}

					}

				} else {
					index = 0;
					isTrueAvailable = false;
					for (DataModel data : listOfDataModels) {
						if (data.getHashKey().equals(dataModel.getHashKey())) {
							updateCMSData(dataModel, null, "Home", 4, index, pageLanguage, isUpdate);
							isTrueAvailable = true;
						}
						++index;
					}
				}

			}

		}

		// persist latest data in home json--------------------
		// in case of editing the last json only, update it in home json
		if ((viewName.equals(innovativeViewName) || viewName.equals(activityViewName))
				&& (!isUpdate || tableSize - 1 == dataIndex)) {
			Integer homeKeyIndex = cmsDataRepository.getNewsSectionIndex(pageLanguage, viewName, "Home");
//			dataModel.setImageName(viewName.equals("Innovative initiatives") ? "./assets/images/gallery/best-practices.jpg"
//					: "./assets/images/gallery/activities.jpg");
			if (homeKeyIndex != null) {
				updateCMSData(dataModel, null, "Home", homeKeyIndex, 0, pageLanguage, true);
			}
		} else if (viewName.equals(achievementViewName) && !isUpdate) {
			Integer homeKeyIndex = cmsDataRepository.getNewsSectionIndex(pageLanguage, viewName, "Home");
			CmsDataJson cmsDataJson = new CmsDataJson();
			try {
				cmsDataJson = objectMpper.treeToValue(cmsData.getViewContent(), CmsDataJson.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			if (homeKeyIndex != null) {
				List<DataModel> dataModels = ((List<Language>) CmsLanguageComponent
						.beanProperties(cmsDataJson).get(pageLanguage)).get(languageIndex).getData();
				if (dataModels.size() >= 1) {
					DataModel lastDataModel = dataModels.get(dataIndex - 1);
					// in this case we have to keep 2 dataset in home json. move
					// the latest one to 1 index and persist new in 0th index
//					lastDataModel.setImageName("./assets/images/gallery/betibachao1.jpg");
					updateCMSData(null, lastDataModel, "Home", homeKeyIndex, 1, pageLanguage, true);
					
//					dataModel.setImageName( "./assets/images/gallery/s20180308123059.jpg");
					updateCMSData(dataModel, null, "Home", homeKeyIndex, 0, pageLanguage, true);
				}
			}

		} else if (viewName.equals(achievementViewName) && isUpdate) {
			Integer homeKeyIndex = cmsDataRepository.getNewsSectionIndex(pageLanguage, viewName, "Home");
			
			if (homeKeyIndex != null) {
				if (tableSize - 2 == dataIndex) {// in case of editing second
													// last index -- change the
													// 1th index of homejson
//					dataModel.setImageName("./assets/images/gallery/betibachao1.jpg");
					updateCMSData(dataModel, null, "Home", homeKeyIndex, 1, pageLanguage, true);
				} else if (tableSize - 1 == dataIndex) {
//					dataModel.setImageName( "./assets/images/gallery/s20180308123059.jpg");
					updateCMSData(dataModel, null, "Home", homeKeyIndex, 0, pageLanguage, true);
				}
			}

		}

		ErrorClass errorClass = new ErrorClass();
		errorClass.setErrorMessage("success");

		return errorClass;}

	private void archiveCmsData(CmsData cmsData, String viewName) {
		ArchiveCmsData archiveCmsData = new ArchiveCmsData();
		archiveCmsData.setViewContent(cmsData.getViewContent());
		archiveCmsData.setViewName(viewName);
		archiveCmsData.setViewType(cmsData.getViewType());
		archiveCmsData.setUpdatedDate(new Date());
		archiveCmsDataRepository.save(archiveCmsData);
		
	}

	public void updateCMSData(DataModelDto dataModelDto, DataModel dataModel, String viewName, int languageIndex,
			int dataIndex, String pageLanguage, boolean isUpdate) {
		try {
			String dataModelJsonInString = dataModelDto!=null ? objectMpper.writeValueAsString(dataModelDto) : objectMpper.writeValueAsString(dataModel);
			String path = "{" + pageLanguage + ", " + languageIndex + ", data, " + dataIndex + "}";
			if (isUpdate) {
				cmsDataRepository.updateAddViewContentDataByIndex(pageLanguage, languageIndex, dataIndex,
						dataModelJsonInString, viewName, "data", path, isUpdate);
			} else {
				cmsDataRepository.updateAddViewContentDataByIndex(pageLanguage, languageIndex, dataIndex - 1,
						dataModelJsonInString, viewName, "data", path, true);
			}

		} catch (Exception e) {
			log.error("Exception in updateCMSData",  e);
		}
	}

	@Transactional
	@Override
	@Caching(evict = {
		    @CacheEvict(value = "cmsdata", key = "'Home'"),
		    @CacheEvict(value = "cmsdata", key = "#viewName")
		})
	public ErrorClass deleteRecordInNestedAndTabularData(String viewName, int languageIndex, int dataIndex,
			String pageLanguage, String oldHashkey, Boolean isHome, Integer tableSize) {
		CmsData cmsData = cmsDataRepository.findByViewName(viewName);
		ErrorClass errorClass = new ErrorClass();
		
		if (viewName.equals(newsViewName)) {
			CmsData homeCmsData = cmsDataRepository.findByViewName("Home");
			archiveCmsData(homeCmsData, "Home");
			List<DataModel> whatsNewDataModels = null;
			
//			CmsDataJson cmsDataJson = new CmsDataJson();
			CmsDataJson homeCmsDataJson = new CmsDataJson();
			try {
//				cmsDataJson = objectMpper.treeToValue(cmsData.getViewContent(), CmsDataJson.class);
				homeCmsDataJson = objectMpper.treeToValue(homeCmsData.getViewContent(), CmsDataJson.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			if(pageLanguage.equals("english")){
				whatsNewDataModels = homeCmsDataJson.getEnglish().get(4).getData();
			}else{
				whatsNewDataModels = homeCmsDataJson.getHindi().get(4).getData();
			}
			int index = 0;
			for (DataModel whatsNewDataModel : whatsNewDataModels) {
				if (whatsNewDataModel.getHashKey()
						.equals(oldHashkey)) {
					deleteCmsData("Home", 4, index, pageLanguage, tableSize);
				}
				++index;
			}
		}

		// first persist the old record in a separate table then add new record
		archiveCmsData(cmsData, viewName);
		
		//in case ishome is getting unchecked we will not delete it from latest news json
		deleteCmsData(viewName, languageIndex, dataIndex, pageLanguage, tableSize);
		
		//when the max index data of table is getting deleted which is on home json as well then replace it with second last json
		if((viewName.equals(innovativeViewName) || viewName.equals(activityViewName) || viewName.equals(achievementViewName))
				&& (tableSize-1 == dataIndex)){
			
			CmsDataJson cmsDataJson = new CmsDataJson();
			try {
				cmsDataJson = objectMpper.treeToValue(cmsData.getViewContent(), CmsDataJson.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			Integer homeKeyIndex = cmsDataRepository.getNewsSectionIndex(pageLanguage, viewName, "Home");
			List<DataModel> existingDataModels = ((List<Language>) CmsLanguageComponent.beanProperties(cmsDataJson)
					.get(pageLanguage)).get(languageIndex).getData();
			if(homeKeyIndex!=null) {
//				if(viewName.equals(achievementViewName))
//					existingDataModels.get(dataIndex-1).setImageName( "./assets/images/gallery/s20180308123059.jpg");
				updateCMSData(null, existingDataModels.get(dataIndex-1), "Home", homeKeyIndex, 0, pageLanguage, true);
				if(viewName.equals(achievementViewName)) {
//					existingDataModels.get(dataIndex-2).setImageName("./assets/images/gallery/betibachao1.jpg");
					updateCMSData(null, existingDataModels.get(dataIndex-2), "Home", homeKeyIndex, 1, pageLanguage, true);
				}
			}
		}else if(viewName.equals(achievementViewName) && (tableSize-2 == dataIndex)) { //while deleting second last
			
			CmsDataJson cmsDataJson = new CmsDataJson();
			try {
				cmsDataJson = objectMpper.treeToValue(cmsData.getViewContent(), CmsDataJson.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			
			List<DataModel> existingDataModels =  ((List<Language>) CmsLanguageComponent.beanProperties(cmsDataJson).get(pageLanguage)).get(languageIndex).getData();
			Integer homeKeyIndex = cmsDataRepository.getNewsSectionIndex(pageLanguage, viewName, "Home");
			if(homeKeyIndex!=null) {
//				existingDataModels.get(dataIndex-1).setImageName("./assets/images/gallery/betibachao1.jpg");
				updateCMSData(null, existingDataModels.get(dataIndex-1), "Home", homeKeyIndex, 1, pageLanguage, true);
			}
		}
		

		errorClass.setErrorMessage("success");
		
		//check if any exception occurs , revert changes
	/*	ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
		
		emailExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					
				ArchiveCmsData archiveCmsData =	archiveCmsDataRepository.findOne((long) 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
//		emailExecutor.shutdown();
		
		return errorClass;
	}
	private void deleteCmsData(String viewName, int languageIndex, int dataIndex, String pageLanguage, Integer tableSize) {
		try {
			String path = "{" + pageLanguage + ", " + languageIndex + ", data, " + dataIndex + "}";
			cmsDataRepository.deleteViewContentDataByIndex(viewName, path);
			
//			add a blank object
			if(tableSize == 1) {
				path = "{" + pageLanguage + ", " + languageIndex + ", data}";
				cmsDataRepository.addBlankObjectInContentDataByIndex(pageLanguage, languageIndex, "{}", viewName, "data", path, false);
			}

		} catch (Exception e) {
			log.error("Exception in deleteCmsData",  e);
		}
		
	}

	@Transactional
    @Override
    @Caching(evict = {
		    @CacheEvict(value = "cmsdata", key = "'Home'"),
		    @CacheEvict(value = "cmsdata", key = "#viewName")
		})
	public ErrorClass updateAddSectionRecordInNestedAndTabularData(String section, String viewName, int languageIndex,
			String pageLanguage, boolean isUpdate,Integer stateIndex) {

		CmsData cmsData = cmsDataRepository.findByViewName(viewName);
         String districtSection=section;
		// first persist the old record in a separate table then add new record
		archiveCmsData(cmsData,viewName);
		
		String path = null;

		try {
			if (!isUpdate) {
				if (viewName.equalsIgnoreCase(stateLevel)) {
					path = "{" + pageLanguage + ", " + languageIndex + "}";
					section = "{\"keyContacts\":[{}],\"section\":\"" + section + "\"}";
					cmsDataRepository.updateAddViewContentSectionByIndex(pageLanguage, languageIndex - 1, section,
							viewName, path, true);
					section=districtSection;
					section = "{\"keyContactRoles\":[{}],\"section\":\"" + section + "\"}";

					cmsDataRepository.updateAddViewContentSectionByIndex(pageLanguage, languageIndex - 1, section,
							districtLevel, path, true);
				} else if (viewName.equalsIgnoreCase(districtLevel)) {

					path = "{" + pageLanguage + ", " + stateIndex + ",keyContactRoles," + languageIndex + "}";
					section = "{\"keyContacts\":[{}],\"section\":\"" + section + "\"}";
					cmsDataRepository.updateAddViewContentNestedSectionByIndex(pageLanguage, stateIndex, section,
							viewName, path, true, "keyContactRoles", languageIndex - 1);

				} else {
					nestedFolderCreat(viewName, section);
					path = "{" + pageLanguage + ", " + languageIndex + "}";
					section = "{\"keyContacts\":[{}],\"section\":\"" + section + "\"}";
					cmsDataRepository.updateAddViewContentSectionByIndex(pageLanguage, languageIndex - 1, section,
							viewName, path, true);
				}
			} else {// while editing an existing sanction order tp/validation
					// order type
				if (viewName.equalsIgnoreCase(stateLevel)) {
					path = "{" + pageLanguage + ", " + languageIndex + ", section}";
					cmsDataRepository.updateSectionByIndex("\"" + section + "\"", viewName, path, false);
					cmsDataRepository.updateSectionByIndex("\"" + section + "\"", districtLevel, path, false);

				} else if (viewName.equalsIgnoreCase(districtLevel)) {

					path = "{" + pageLanguage + ", " + stateIndex + ",keyContactRoles,"+ languageIndex
							+ ", section}";
					cmsDataRepository.updateSectionByIndex("\"" + section + "\"", viewName, path, false);

				} else {
					nestedFolderCreat(viewName, section);
					path = "{" + pageLanguage + ", " + languageIndex + ", section}";
					cmsDataRepository.updateSectionByIndex("\"" + section + "\"", viewName, path, false);
				}
			}
		} catch (Exception e) {
			log.error("Exception in updateAddSectionRecordInNestedAndTabularData",  e);
		}
		ErrorClass errorClass = new ErrorClass();
		errorClass.setErrorMessage("success");
		
		return errorClass;
	}

	private void nestedFolderCreat(String viewName, String section) {
		File file = new File(cmsFilepath+"/"+viewName+"/"+section.trim().replaceAll("[^\\dA-Za-z ]", "").replaceAll("\\s_", "+"));
		if(!file.exists()){
		file.mkdirs();
		}
		
	}

	// predicate to be applied on uuid which is unique
	public static List<DataModel> filterDataModels(List<DataModel> dataModels, Predicate<DataModel> predicate) {
		return dataModels.stream().filter(predicate).collect(Collectors.<DataModel>toList());
	}

	public static Predicate<DataModel> isPoratlMenu(Boolean flag) {
		return p -> p.getFlag() == flag;
	}

	@Override
//	@Cacheable(value="cmsdata", key = "#viewName", unless="#result != null")
	public Map<String, List<QuestionFeatureDetailDtoModel>> getFeatureDetails(String cmsFilepath, String viewName) {
		Map<String, List<QuestionFeatureDetailDtoModel>> featureDetailsMap = new LinkedHashMap<>();

		List<CmsData> listOfCMSData = null;
		List<FeatureDetail> listOfFeatureDetails = null;
		if(viewName==null) {
			listOfCMSData = cmsDataRepository.findAll();
			listOfFeatureDetails = featureDetailRepository.findAllByOrderByFeatureDetailOrderAsc();
		}else {
			listOfCMSData = Arrays.asList(cmsDataRepository.findByViewName(viewName));
			listOfFeatureDetails = featureDetailRepository.findByFeatureFeatureIdOrderByFeatureDetailOrderAsc(Integer.parseInt(listOfCMSData.get(0).getFeature()));
		}

		List<QuestionFeatureDetailDtoModel> listOfQuestionFeatureDetailDtoModel = null;
		List<CmsDataDto> listOfCmsDataDto = null;
		List<QuestionModel> listQuestionModel = null;
		QuestionModel questionModel = null;
		CmsDataDto cmsDataDto = null;
		QuestionFeatureDetailDtoModel questionFeatureDetailDtoModel = null;

		Map<String, List<CmsData>> cMSDataMap = new HashMap<>();

		List<CmsData> listOfdata = null;
		for (CmsData cmsData : listOfCMSData) {
			if (cmsData.getFeature() != null) {
				if (cMSDataMap.containsKey(String.valueOf(cmsData.getFeature()))) {
					cMSDataMap.get(String.valueOf(cmsData.getFeature())).add(cmsData);

				} else {
					listOfdata = new ArrayList<>();
					listOfdata.add(cmsData);
					cMSDataMap.put(String.valueOf(cmsData.getFeature()), listOfdata);
				}
			}

		}

		for (FeatureDetail featureDetail : listOfFeatureDetails) {

			if (featureDetailsMap.containsKey(featureDetail.getFeature().getFeatureName())) {
				questionModel = setInQuestionModel(featureDetail);
				featureDetailsMap.get(featureDetail.getFeature().getFeatureName()).get(0).getListOfQuestionModel()
						.add(questionModel);
			} else {

				listOfQuestionFeatureDetailDtoModel = new ArrayList<>();
				questionFeatureDetailDtoModel = new QuestionFeatureDetailDtoModel();
				listOfCmsDataDto = new ArrayList<>();
				listQuestionModel = new ArrayList<>();
				questionModel = setInQuestionModel(featureDetail);
				listQuestionModel.add(questionModel);
				questionFeatureDetailDtoModel.setListOfQuestionModel(listQuestionModel);
				for (Entry<String, List<CmsData>> entry : cMSDataMap.entrySet()) {
					if (isContain(entry.getKey(), String.valueOf(featureDetail.getFeature().getFeatureId()))) {
					
						listOfCMSData = entry.getValue();

						if (listOfCMSData != null) {
							for (CmsData cmsData : listOfCMSData) {
								
								CmsDataJson cmsDataJson = new CmsDataJson();
								try {
									cmsDataJson = objectMpper.treeToValue(cmsData.getViewContent(), CmsDataJson.class);
								} catch (JsonProcessingException e) {
									e.printStackTrace();
								}
								if (featureDetail.getFeature().getFeatureName().equals(aboutUsFeatureName)
										|| featureDetail.getFeature().getFeatureName().equals(bannerFeatureName)
										|| featureDetail.getFeature().getFeatureName().equals("Exposure Visits")) {

									Map<String, Object> getterMethodMap = CmsLanguageComponent
											.beanProperties(cmsDataJson);

									for (Map.Entry<String, Object> getterEntry : getterMethodMap.entrySet()) {
										for (int sectionCount = 0; sectionCount < ((List<Language>) getterEntry
												.getValue()).size(); sectionCount++) {

											for (DataModel dataModel : ((List<Language>) getterEntry.getValue()).get(sectionCount).getData()) {
												if (dataModel.getImageName() != "") {
													//in case of about us preview option is available which require base64
//													if(!featureDetail.getFeature().getFeatureName().equals("Exposure Visits")) {
//														dataModel.setUrl(ImageConverter
//																.encodingPhoto(cmsFilepath + dataModel.getImageName()));
//													}else {
														dataModel.setUrl(dataModel.getImageName());
//													}
													
												}
											}
										}
									}
									cmsData.setViewContent(objectMpper.convertValue(cmsDataJson, JsonNode.class));
								}
								
								cmsDataDto = !(featureDetail.getFeature().getFeatureName().equals(photoFeatureName))
										? setInCmsDataDto(cmsData)
										: galleryService.fetchPhotoGalleryDataWithUrl(photoViewName, cmsFilepath);
								listOfCmsDataDto.add(cmsDataDto);
							}

						}
					}

				}
				questionFeatureDetailDtoModel.setListOfCmsDataDto(listOfCmsDataDto);
				listOfQuestionFeatureDetailDtoModel.add(questionFeatureDetailDtoModel);
				featureDetailsMap.put(featureDetail.getFeature().getFeatureName(), listOfQuestionFeatureDetailDtoModel);
			}
		}
		return featureDetailsMap;
	}

	private CmsDataDto setInCmsDataDto(CmsData cmsData) {
		modelMapper.validate();
		return modelMapper.map(cmsData, CmsDataDto.class);
	}

	private static boolean isContain(String source, String subItem) {
		String pattern = "\\b" + subItem + "\\b";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.find();
	}

	private QuestionModel setInQuestionModel(FeatureDetail featureDetail) {
		QuestionModel questionModel = new QuestionModel();
		questionModel.setDependentCondition(Arrays.asList(featureDetail.getDependentCondition()));
		questionModel.setColumnName(featureDetail.getColumnName());
		questionModel.setDependentColumn(featureDetail.getDependentColumn());
		questionModel.setLabel(featureDetail.getLabel());
		questionModel.setControlType(featureDetail.getControllerType());
		questionModel.setKey(featureDetail.getFeatureDetailOrder());
		questionModel.setType(featureDetail.getFieldType());
		questionModel.setDependecy(featureDetail.getDependecy());
		questionModel.setRoleId(featureDetail.getId());
		questionModel.setSection(featureDetail.getSection().equals("") ? null : featureDetail.getSection());
		questionModel.setDisplayLabel(featureDetail.getDisplayLabel());
		if(featureDetail.getValidation()!=null){
			Gson gson = new Gson();
			Validation validation = gson.fromJson(featureDetail.getValidation(), Validation.class);
			questionModel.setValidation(validation);
		}
		
		if (featureDetail.getTypeId() != null) {
			questionModel.setTypeId(featureDetail.getTypeId().getId());
			List<TypeDetail> typedetails = typeDetailRepository.findByTypeIdId(featureDetail.getTypeId().getId());
			List<OptionModel> listOfOptions = new ArrayList<>();
			for (TypeDetail typeDetail : typedetails) {
				OptionModel optionModel = new OptionModel();
				optionModel.setKey(typeDetail.getId());
				optionModel.setValue(typeDetail.getName());
				optionModel.setOrder(typeDetail.getOrderLevel());
				
				listOfOptions.add(optionModel);
			}
			questionModel.setOptions(listOfOptions);
		}
		
		if(featureDetail.getMetaData()!=null) {
			List<OptionModel> listOfOptions = new ArrayList<>();
			switch (featureDetail.getMetaData()) {

			case "state":
				List<Area> allState = areaRepository.findByLevelAreaLevelIdOrderByAreaName(2);
				for (Area area : allState) {
					OptionModel optionModel = new OptionModel();
					optionModel.setKey(area.getAreaId());
					optionModel.setValue(area.getAreaName());
					optionModel.setParentId(area.getParentAreaId());
					listOfOptions.add(optionModel);
				}
				break;
			case "district":
				List<Area> allDistrict = areaRepository.findByLevelAreaLevelIdOrderByAreaName(3);
				for (Area area : allDistrict) {
					OptionModel optionModel = new OptionModel();
					optionModel.setKey(area.getAreaId());
					optionModel.setValue(area.getAreaName());
					optionModel.setParentId(area.getParentAreaId());
					listOfOptions.add(optionModel);
				}
				break;

			case "year":
				List<Year> yearList = yearRepository.findAll();
				for (Year year : yearList) {
					OptionModel optionModel = new OptionModel();
					optionModel.setKey(year.getYearId());
					optionModel.setValue(year.getYearRange());
					listOfOptions.add(optionModel);
				}
				break;

			case "quarter":
				List<PeriodReference> periodReferences = periodReferenceRepository.findAll();
				for (PeriodReference periodReference : periodReferences) {
					OptionModel optionModel = new OptionModel();
					optionModel.setKey(periodReference.getPeriodReferenceId());
					optionModel.setValue(periodReference.getMonthRange());
					listOfOptions.add(optionModel);
				}
				break;
			}
			
			questionModel.setOptions(listOfOptions);
			questionModel.setMetaData(featureDetail.getMetaData());
		}
		 
		return questionModel;
	}
	@Override
	public Map<String, String> uploadFile(List<MultipartFile> multipartfiles, String viewName, String section) throws Exception {
		String filePath = null;
		Map<String, String> fileTypeNameMap = new HashMap<>();
		
		if (!multipartfiles.isEmpty()) {
			File newFile = null;
//			if (!viewName.equalsIgnoreCase("Home")) {
				if (viewName.equalsIgnoreCase(section)) {
					newFile = new File(cmsFilepath + "/" + viewName);
				} else {
					newFile = new File(cmsFilepath + "/" + viewName + "/" + section.trim().replaceAll("[^\\dA-Za-z ]", ""));
				}
				if (!newFile.exists()) {
					newFile.mkdirs();
				}
//			}

			if (section.equalsIgnoreCase(bannerSectionName)) {
				filePath = bannerPath;
//				filePath = "./assets/images/banners/";
				/*} else
				if (section.equalsIgnoreCase("Footer")) {
				filePath = resourceFileFooter.getURL().getPath().replaceFirst("/", ""); */
			} else
				if (viewName.equalsIgnoreCase(section)) {
				filePath = viewName + "/";
			} else {
				filePath = viewName + "/" + section.trim().replaceAll("[^\\dA-Za-z ]", "")+ "/";
			}
			
			try {
				for (MultipartFile file : multipartfiles) {
					String extentionName = FilenameUtils.getExtension(file.getOriginalFilename());
					String fileNameWithDateTime = FilenameUtils.getBaseName(file.getOriginalFilename()) + "_"
							+ new Date().getTime() + "." + extentionName;

					if (extentionName.equalsIgnoreCase("jpg") || extentionName.equalsIgnoreCase("jpeg")
							|| extentionName.equalsIgnoreCase("png")) {
						
						if (!viewName.equalsIgnoreCase("Home")) {
							makeThumbnail(new File(cmsFilepath + filePath + fileNameWithDateTime), file.getBytes(),
									FilenameUtils.getExtension(file.getOriginalFilename()));
						} else {
							String url = null;
							if (section.equalsIgnoreCase(bannerSectionName)) {
								url = resourceFileBanner.getFile().toString().replaceFirst("/", "");
								newFile = new File(url);
								if (!newFile.exists()) {
									newFile.mkdirs();
								}
								//in case of banner do not compress the image
								BannerThumbnail(Paths.get(url).resolve(fileNameWithDateTime).toFile(), file.getBytes(),
										FilenameUtils.getExtension(file.getOriginalFilename()));
							}
						}
					} else {
						Files.copy(file.getInputStream(), this.cmsFilePathLocation.resolve(filePath + fileNameWithDateTime),
								StandardCopyOption.REPLACE_EXISTING);
					}

					switch (FilenameUtils.getExtension(file.getOriginalFilename())) {
					case "pdf":
						fileTypeNameMap.put("pdf", filePath + fileNameWithDateTime);
						break;
					case "jpg":
						fileTypeNameMap.put("image", filePath + fileNameWithDateTime);
						break;
					case "png":
						fileTypeNameMap.put("image", filePath + fileNameWithDateTime);
						break;
					case "jpeg":
						fileTypeNameMap.put("image", filePath + fileNameWithDateTime);
						break;
					case "mp3":
						fileTypeNameMap.put("audio", filePath + fileNameWithDateTime);
						break;
					default:
						fileTypeNameMap.put("pdf", filePath + fileNameWithDateTime);
						break;

					}

				}
			}catch (Exception e) {
				log.error("Exception occcured in uploadFile ",e);
			}
			
		}

		return fileTypeNameMap;
		}

	private void makeThumbnail(File writeFilePath, byte[] decodedBytes, String extension) throws IOException {

		BufferedImage img = ImageIO.read(new ByteArrayInputStream(decodedBytes));// load image
		BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY,Mode.AUTOMATIC,img.getWidth(),img.getHeight(), Scalr.OP_ANTIALIAS);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(thumbImg, extension, os);

		ImageIO.write(thumbImg, extension, writeFilePath);
		
	}
	
	private void BannerThumbnail(File writeFilePath, byte[] decodedBytes, String extension) throws IOException {

		BufferedImage img = ImageIO.read(new ByteArrayInputStream(decodedBytes));// load image
//		BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY,Mode.AUTOMATIC,img.getWidth(),img.getHeight(), Scalr.OP_ANTIALIAS);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(img, extension, os);

		ImageIO.write(img, extension, writeFilePath);
		
	}

	@Override
	public Map<String, String> uploadAllFile(MultipartFile file, String viewName, String columnName) throws Exception {
		String filePath = null;
		Map<String, String> fileTypeNameMap = new HashMap<>();
		
		if (file !=null) {
			File newFile = null;
			newFile = new File(cmsFilepath + "/" + viewName);
			if (!newFile.exists()) {
				newFile.mkdirs();
			}
			filePath = viewName + "/";

			try {
				String extentionName = FilenameUtils.getExtension(file.getOriginalFilename());
				String fileNameWithDateTime = FilenameUtils.getBaseName(file.getOriginalFilename()) + "_"
						+ new Date().getTime() + "." + extentionName;

				Files.copy(file.getInputStream(), this.cmsFilePathLocation.resolve(filePath + fileNameWithDateTime),
						StandardCopyOption.REPLACE_EXISTING);

				fileTypeNameMap.put(columnName, filePath + fileNameWithDateTime);
			} catch (Exception e) {
				log.error("Exception occcured in uploadFile ", e);
			}

		}

		return fileTypeNameMap;
		}
	
	@Transactional
	@Override
	@Caching(evict = { @CacheEvict(value = "cmsdata", key = "#viewName")})
	public ErrorClass deleteSectionRecordInTabularData(String viewName, int languageIndex, String pageLanguage,int tableSize) {

		CmsData cmsData = cmsDataRepository.findByViewName(viewName);
		ErrorClass errorClass = new ErrorClass();
		// first persist the old record in a separate table then add new record
		archiveCmsData(cmsData,viewName);

		try {
			String path = "{" + pageLanguage + ", " + languageIndex + "}";
			cmsDataRepository.deleteViewContentDataByIndex(viewName, path);
			if(tableSize == 1) {
				  path = "{" + pageLanguage + "}";
					cmsDataRepository.addBlankObjectInSectionDataByIndex(pageLanguage, "{}", viewName, path, false);
			  }

		} catch (Exception e) {
			errorClass.setErrorMessage("fail");
			log.error("Exception occcured in deleteSectionRecordInTabularData ",e);
		}
		
		
		errorClass.setErrorMessage("success");
		
		return errorClass;
	
	}
	
	//this will handle the editing of page desc and also updating home json in respective box 
	@Transactional
	@Override
	@Caching(evict = {
		    @CacheEvict(value = "cmsdata", key = "'Home'"),
		    @CacheEvict(value = "cmsdata", key = "#viewName")
		})
	public ErrorClass editPageDescription(String description, String viewName, String pageLanguage) {
		ErrorClass errorClass = new ErrorClass();
		try {
			CmsData cmsData = cmsDataRepository.findByViewName(viewName);
			archiveCmsData(cmsData,viewName);
			
			
			//update in homejson
			if(viewName.equals(guidelineViewName) || viewName.equals(photoViewName) || viewName.equals(videoViewName)) {
				Integer homeKeyIndex = cmsDataRepository.getNewsSectionIndex(pageLanguage, viewName, "Home");
				
				if(homeKeyIndex!=null) {
					DataModel dataModel = new DataModel();
					dataModel.setContent(description);
					updateCMSData(null, dataModel, "Home", homeKeyIndex, 0, pageLanguage, false);
				}
			}
			cmsDataRepository.updateViewType(description, viewName);
//			cmsData.setViewType(description);
			errorClass.setErrorMessage("success");
			
		} catch (Exception e) {
			errorClass.setErrorMessage("fail");
			log.error("Exception occcured in editPageDescription ",e);
		}
	
		
		return errorClass;
	}

	@Override
	public String fetchServerDate() {
		return DateTimeFormatter.ofPattern("dd/MM/YYYY").format(LocalDateTime.now().toLocalDate());
	}
	@Transactional
	@Override
	@Caching(evict = {@CacheEvict(value = "cmsdata", key = "#viewName")	})
	public ErrorClass updateAddRecordInNestedAndTabularDataOfKeyContact(KeyContactModel keyContactModel,
			KeyContactRoleModel keyContactRoleModel, String viewName, int languageIndex, int dataIndex,
			String pageLanguage, boolean isUpdate, Integer stateIndex, String stateName) {
		
		CmsData cmsData = cmsDataRepository.findByViewName(viewName);
		archiveCmsData(cmsData, viewName);
		String dataModelJsonInString = null;
		String path=null;
		ErrorClass errorClass = new ErrorClass();
		try {
			
			if (isUpdate) {
				if (!viewName.equalsIgnoreCase(districtLevel)) {
					dataModelJsonInString = objectMpper.writeValueAsString(keyContactModel);
					path = "{" + pageLanguage + ", " + languageIndex + ", keyContacts, " + dataIndex + "}";
					cmsDataRepository.updateAddViewContentDataByIndex(pageLanguage, languageIndex, dataIndex,
							dataModelJsonInString, viewName, "keyContacts", path, isUpdate);
				} else {
					dataModelJsonInString = objectMpper.writeValueAsString(keyContactRoleModel.getKeyContacts()[0]);
					path = "{" + pageLanguage + ", " + stateIndex + "," + "keyContactRoles" + "," + languageIndex
							+ ", keyContacts, " + dataIndex + "}";
					cmsDataRepository.updateAddViewNestedContentDataByIndex(
							pageLanguage, languageIndex, dataIndex,
							dataModelJsonInString, viewName, "keyContactRoles", path, isUpdate,stateIndex,"keyContacts");
				} 
			} else {
				if (!viewName.equalsIgnoreCase(districtLevel)) {
					dataModelJsonInString = objectMpper.writeValueAsString(keyContactModel);
					path = "{" + pageLanguage + ", " + languageIndex + ", keyContacts, " + dataIndex + "}";
					cmsDataRepository.updateAddViewContentDataByIndex(pageLanguage, languageIndex, dataIndex - 1,
							dataModelJsonInString, viewName, "keyContacts", path, true);
				} else{
					dataModelJsonInString = objectMpper.writeValueAsString(keyContactRoleModel.getKeyContacts()[0]);
					path = "{" + pageLanguage + ", " + stateIndex + "," + "keyContactRoles" + "," + languageIndex
							+ ", keyContacts, " + dataIndex + "}";
					cmsDataRepository.updateAddViewNestedContentDataByIndex(
							pageLanguage, languageIndex, dataIndex-1,
							dataModelJsonInString, viewName, "keyContactRoles", path, true,stateIndex,"keyContacts");

				} 
			}
			
		}catch (Exception e) {
			errorClass.setErrorMessage("fail");
			log.error("Exception occcured in updateAddRecordInNestedAndTabularDataOfKeyContact ",e);
		}
		
		
		errorClass.setErrorMessage("success");
		
		return errorClass;
	}

	
	//DataDElete
	@Transactional
	@Override
	@Caching(evict = {@CacheEvict(value = "cmsdata", key = "#viewName")	})
	public ErrorClass deleteRecordInNestedAndTabularDataKeyContact(String viewName, int languageIndex, int dataIndex,
			String pageLanguage, Integer stateIndex,int tableSize) {
		
		CmsData cmsData = cmsDataRepository.findByViewName(viewName);
		archiveCmsData(cmsData, viewName);
		String path=null;
		ErrorClass errorClass = new ErrorClass();
		try {
			if(!viewName.equalsIgnoreCase(districtLevel)){
				  path = "{" + pageLanguage + ", " + languageIndex + ",keyContacts, " + dataIndex + "}";
				  cmsDataRepository.deleteViewContentDataByIndex(viewName, path);
				  if(tableSize == 1) {
					  path = "{" + pageLanguage + ", " + languageIndex + ", keyContacts}";
						cmsDataRepository.addBlankObjectInContentDataByIndex(pageLanguage, languageIndex, "{}", viewName, "keyContacts", path, false);
				  }
			}else{
				  path = "{" + pageLanguage + ", " + stateIndex + ",keyContactRoles,"+languageIndex+",keyContacts, " + dataIndex + "}";
				  cmsDataRepository.deleteViewContentDataByIndex(viewName, path);
				  if(tableSize == 1) {
					  path = "{" + pageLanguage + ", " + stateIndex + ", keyContactRoles,"+languageIndex+",keyContacts}";
						cmsDataRepository.addBlankObjectInNestedContentDataByIndex(pageLanguage, stateIndex, "{}", viewName,  "keyContactRoles" , path, false,languageIndex,"keyContacts");
				  }
			}

		} catch (Exception e) {
			errorClass.setErrorMessage("fail");
			log.error("Exception occcured in deleteRecordInNestedAndTabularDataKeyContact ",e);
		}

			
			errorClass.setErrorMessage("success");

			return errorClass;
		
		
	}

	//SectionDelete
	@Transactional
	@Override
	@Caching(evict = {@CacheEvict(value = "cmsdata", key = "#viewName")	})
	public ErrorClass deleteSectionRecordInTabularDataKeyContact(String viewName, int languageIndex,
			String pageLanguage, Integer tableSize,Integer stateIndex) {

		CmsData cmsData = cmsDataRepository.findByViewName(viewName);
		archiveCmsData(cmsData,viewName);
		String path = null;
		ErrorClass errorClass = new ErrorClass();
		try {
			if(viewName.equalsIgnoreCase(stateLevel)){
				path ="{" + pageLanguage + ", " + languageIndex + "}";
				cmsDataRepository.deleteViewContentDataByIndex(viewName, path);
				CmsData cmsDatadistrictLevel = cmsDataRepository.findByViewName(districtLevel);
				archiveCmsData(cmsDatadistrictLevel,districtLevel);
				cmsDataRepository.deleteViewContentDataByIndex(districtLevel, path);
				if(tableSize == 1) {
					  path = "{" + pageLanguage + "}";
						cmsDataRepository.addBlankObjectInSectionDataByIndex(pageLanguage, "{}", viewName, path, false);
						cmsDataRepository.addBlankObjectInSectionDataByIndex(pageLanguage, "{}", districtLevel, path, false);
				  }
			}else{
				path ="{" + pageLanguage + ", " + stateIndex + ",keyContactRoles,"+languageIndex+"}";
				cmsDataRepository.deleteViewContentDataByIndex(viewName, path);
				if(tableSize == 1) {
					  path = "{" + pageLanguage + ", " + stateIndex + ", keyContactRoles}";
						cmsDataRepository.addBlankObjectSectionInContentDataByIndex(pageLanguage, "{}", viewName, "keyContactRoles", path, false,stateIndex);
				  }
				
			}

		} catch (Exception e) {
			errorClass.setErrorMessage("fail");
			log.error("Exception occcured in deleteSectionRecordInTabularDataKeyContact ",e);
		}
		
		
		errorClass.setErrorMessage("success");
		
		return errorClass;
	
	
	}

	@Override
	@Caching(evict = {
		    @CacheEvict(value = "cmsdata", key = "#viewName")
		})
	public ErrorClass updateRTI(Map<String, Object> receiveDataModel, String viewName, Integer languageIndex,
			Integer dataIndex, String pageLanguage, Boolean isUpdate) {
		
		CmsData cmsData = cmsDataRepository.findByViewName(viewName);

		// first persist the old record in a separate table then add new record
		archiveCmsData(cmsData, viewName);
	
		ErrorClass errorClass = new ErrorClass();
		try {
			receiveDataModel.put("createdDate", DateTimeFormatter.ofPattern("dd-MM-YYYY").format(LocalDateTime.now().toLocalDate()));
			updateRTICMSData(receiveDataModel, viewName, languageIndex, dataIndex, pageLanguage, isUpdate);
		} catch (Exception e) {
			errorClass.setErrorMessage("fail");
			log.error("Exception occcured in updateRTI ",e);
		}
		
		errorClass.setErrorMessage("success");
		
		return errorClass;
	}

	public void updateRTICMSData(Map<String, Object> receiveDataModel, String viewName, int languageIndex,
			int dataIndex, String pageLanguage, boolean isUpdate) {
		try {
			String dataModelJsonInString = objectMpper.writeValueAsString(receiveDataModel);
			String path = "{" + pageLanguage + ", " + languageIndex + ", data, " + dataIndex + "}";
			
			if (isUpdate) {
				cmsDataRepository.updateAddViewContentDataByIndex(pageLanguage, languageIndex, dataIndex,
						dataModelJsonInString, viewName, "data", path, isUpdate);
			} else {
				cmsDataRepository.updateAddViewContentDataByIndex(pageLanguage, languageIndex, dataIndex - 1,
						dataModelJsonInString, viewName, "data", path, true);
			}

		} catch (Exception e) {
			log.error("Exception in updateCMSData",  e);
		}
	}

	@Override
	public long getVisitorCount() {
		return visitorCountRepository.countVistor();
	}
	
	/* (non-Javadoc)
	 * @see org.sdrc.bbbp.cms.service.CmsDataService#updateArchiveData(java.lang.String)
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 30-Aug-2019 11:26:36 AM
	 * this method simply inserts archived view content to cms data 
	 * if any exception occurs then call this api
	 */
	@Caching(evict = {
		    @CacheEvict(value = "cmsdata", key = "'Home'"),
		    @CacheEvict(value = "cmsdata", key = "#viewName")
		})
	@Override @Transactional public String updateArchiveData(String viewName) {
		
		ArchiveCmsData archiveCmsData =  archiveCmsDataRepository.findTop1ByViewNameOrderByUpdatedDateDesc(viewName);
		
		CmsData cmsData = cmsDataRepository.findByViewName(viewName);
		cmsData.setViewContent(archiveCmsData.getViewContent());
		cmsData.setViewType(archiveCmsData.getViewType());
		cmsDataRepository.save(cmsData);
		
		return "old data updated";
		
	}
}
