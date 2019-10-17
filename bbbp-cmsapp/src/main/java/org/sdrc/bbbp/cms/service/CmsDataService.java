package org.sdrc.bbbp.cms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.sdrc.bbbp.cms.domain.KeyContactModel;
import org.sdrc.bbbp.cms.domain.KeyContactRoleModel;
import org.sdrc.bbbp.cms.model.CmsDataDto;
import org.sdrc.bbbp.cms.model.DataModelDto;
import org.sdrc.bbbp.cms.model.FeatureDetailDto;
import org.sdrc.bbbp.cms.model.QuestionFeatureDetailDtoModel;
import org.sdrc.bbbp.models.ErrorClass;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Sarita Panigrahi (sarita@sdrc.co.in)
 * Created On: 13-Jul-2018 6:20:43 PM
 */
public interface CmsDataService {

	CmsDataDto fetchCmsData(String viewName,String viewType, HttpSession se) throws JSONException;

	ErrorClass updateAddRecordInNestedAndTabularData(DataModelDto newdataModel, String viewName, int languageIndex, int dataIndex,
			String pageLanguage, boolean isUpdate, Integer tableSize);

//	void addRecordInNestedAndTabularData(DataModel dataModel, String viewName, int languageIndex, int dataIndex,
//			String pageLanguage);
	
	Map<String,List<QuestionFeatureDetailDtoModel>> getFeatureDetails(String cmsFilepath, String viewName);


	Map<String, String> uploadFile(List<MultipartFile> file,String viewName,String section)throws Exception ;


	ErrorClass deleteRecordInNestedAndTabularData(String viewName, int languageIndex, int dataIndex, String pageLanguage,String oldHashkey, Boolean isHome, Integer tableSize);

	ErrorClass updateAddSectionRecordInNestedAndTabularData(String section, String viewName, int languageIndex,
			String pageLanguage, boolean isUpdate,Integer dataIndex);
	
	ErrorClass deleteSectionRecordInTabularData(String viewName, int languageIndex,String pageLanguage,int tableSize);
	
	ErrorClass editPageDescription(String description, String viewName, String pageLanguage);
	
	String fetchServerDate();
	
	ErrorClass updateAddRecordInNestedAndTabularDataOfKeyContact(KeyContactModel keyContactModel,KeyContactRoleModel keyContactRoleModel, String viewName, int languageIndex, int dataIndex,
			String pageLanguage, boolean isUpdate,Integer stateIndex,String stateName);
	
	ErrorClass deleteRecordInNestedAndTabularDataKeyContact(String viewName, int languageIndex, int dataIndex, String pageLanguage,Integer stateIndex,int tableSize);
	
	ErrorClass deleteSectionRecordInTabularDataKeyContact(String viewName, int languageIndex,String pageLanguage,Integer tableSize,Integer stateIndex);

	Map<String, String> uploadAllFile(MultipartFile multipartfile, String viewName, String columnName)
			throws Exception;

	ErrorClass updateRTI(Map<String, Object> receiveDataModel, String viewName, Integer languageIndex,
			Integer dataIndex, String pageLanguage, Boolean isUpdate);

/*	void uploadFile(List<MultipartFile> files);*/
	List<FeatureDetailDto> fetchPageDetails(Integer featureId);
	
	long getVisitorCount();

	String updateArchiveData(String viewName);

}
