package org.sdrc.bbbp.cms.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.sdrc.bbbp.cms.domain.KeyContactModel;
import org.sdrc.bbbp.cms.domain.KeyContactRoleModel;
import org.sdrc.bbbp.cms.model.DataModelDto;
import org.sdrc.bbbp.cms.model.FeatureDetailDto;
import org.sdrc.bbbp.cms.model.QuestionFeatureDetailDtoModel;
import org.sdrc.bbbp.cms.service.CmsDataService;
import org.sdrc.bbbp.models.ErrorClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/api/cms")
public class CmsHomeController {

	@Autowired
	private CmsDataService cmsDataService;
	
	@Value("${cms.filepath}")
	private String cmsFilepath;
	
	private Logger log = LoggerFactory.getLogger(CmsHomeController.class);
	
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:26:58 PM
	 * @param featureId
	 * @return
	 */
	@GetMapping("/fetchPageDetails")
	@ResponseBody
	public List<FeatureDetailDto>  getPageDetails(@RequestParam("featureId") Integer featureId) {
		return cmsDataService.fetchPageDetails(featureId);
	}
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:26:55 PM
	 * @param name
	 * @param response
	 * @param inline
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadCmsDoc", method = RequestMethod.GET)
	public void downLoad(@RequestParam("fileName") String name,
			HttpServletResponse response, @RequestParam(value="inline", required=false) Boolean inline) throws IOException {

		InputStream inputStream;
		String fileName = "";

		try {
			log.info(fileName);
			fileName = cmsFilepath + name.trim().replaceAll("%3A", ":").replaceAll("%2F", "/")
							.replaceAll("%5C", "/").replaceAll("%2C", ",").replaceAll("\\\\", "/")
							.replaceAll("%20", " ").replaceAll("%26", "&")
							.replaceAll("\\+", " ").replaceAll("%22", "").replaceAll("%3F", "?").replaceAll("%3D", "=");
			
			inputStream = new FileInputStream(fileName);
			String headerKey = "Content-Disposition";
			String headerValue = "";
			
			if(inline!=null && inline) {
				headerValue= String.format("inline; filename=\"%s\"",
						new java.io.File(fileName).getName());
				response.setContentType("application/pdf"); // for pdf file
				// type
			}else {
				headerValue= String.format("attachment; filename=\"%s\"",
						new java.io.File(fileName).getName());
				response.setContentType("application/octet-stream"); // for all file
				// type
			}
				
			response.setHeader(headerKey, headerValue);
			
			ServletOutputStream outputStream = response.getOutputStream();
			FileCopyUtils.copy(inputStream, outputStream);
			inputStream.close();
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException",  e);
		} catch (IOException e) {
			log.error("IOException",  e);
		}
		
	}

	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:26:51 PM
	 * @param dataModel
	 * @param viewName
	 * @param languageIndex
	 * @param dataIndex
	 * @param pageLanguage
	 * @param isUpdate
	 * @param tableSize
	 * @return
	 */
	@PostMapping("/updateAddCmsContent")
	@ResponseBody
	public ErrorClass addCmsContent(@RequestBody DataModelDto dataModel, @RequestParam("viewName") String viewName,
			@RequestParam("languageIndex") Integer languageIndex, @RequestParam("dataIndex") Integer dataIndex,
			@RequestParam("pageLanguage") String pageLanguage, @RequestParam("isUpdate") Boolean isUpdate,
			@RequestParam(value="tableSize", required = false) Integer tableSize) {
		
		return cmsDataService.updateAddRecordInNestedAndTabularData(dataModel, viewName, languageIndex, dataIndex, pageLanguage, isUpdate, tableSize);
	}
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:04 PM
	 * @param viewName
	 * @return
	 */
	@GetMapping("/fetchFeatureDetails")
	@ResponseBody
	public Map<String, List<QuestionFeatureDetailDtoModel>>  getFeatureDetails(@RequestParam(value = "viewName", required = false) String viewName) {
		return cmsDataService.getFeatureDetails(cmsFilepath, viewName);
	}

	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:07 PM
	 * @param fileUpload
	 * @param viewName
	 * @param section
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/uploadFile")
	@ResponseBody
	public Map<String, String> handleFileUpload(
			@RequestParam MultipartFile[] fileUpload,@RequestParam("viewName") String viewName,@RequestParam("section") String section)throws Exception {
		List<MultipartFile> file = Arrays.asList(fileUpload);
		return cmsDataService.uploadFile(file, viewName, section);
	}
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:10 PM
	 * @param fileUpload
	 * @param viewName
	 * @param columnName
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/uploadAllFiles")
	@ResponseBody
	public Map<String, String> handleAllFileUpload(
			@RequestParam("fileUpload") MultipartFile fileUpload,
			@RequestParam("viewName") String viewName,
			@RequestParam("columnName") String columnName)throws Exception {
		System.out.println("called");
		return cmsDataService.uploadAllFile(fileUpload, viewName, columnName);
	}
	
	
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:13 PM
	 * @param viewName
	 * @param languageIndex
	 * @param dataIndex
	 * @param pageLanguage
	 * @param oldHashkey
	 * @param isHome
	 * @param tableSize
	 * @return
	 */
	@GetMapping("/deleteCmsContent")
	@ResponseBody
	public ErrorClass deleteCmsContent(@RequestParam("viewName") String viewName,
			@RequestParam("languageIndex") Integer languageIndex, @RequestParam("dataIndex") Integer dataIndex,
			@RequestParam("pageLanguage") String pageLanguage,
			@RequestParam(value = "oldHashkey", required = false) String oldHashkey,
			@RequestParam(value = "isHome", required = false) Boolean isHome,
			@RequestParam("tableSize") Integer tableSize) {
		
		return cmsDataService.deleteRecordInNestedAndTabularData(viewName, languageIndex, dataIndex, pageLanguage,oldHashkey, isHome, tableSize);
	}
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:15 PM
	 * @param section
	 * @param viewName
	 * @param languageIndex
	 * @param pageLanguage
	 * @param isUpdate
	 * @param stateIndex
	 * @return
	 */
	@GetMapping("/sectionAddCmsContent")
	@ResponseBody
	public ErrorClass sectionaddCmsContent( @RequestParam("section") String section,@RequestParam("viewName") String viewName,
			@RequestParam("languageIndex") Integer languageIndex,@RequestParam("pageLanguage") String pageLanguage,
			@RequestParam("isUpdate") Boolean isUpdate,@RequestParam(value="stateIndex", required=false) Integer stateIndex) {
		
		return cmsDataService.updateAddSectionRecordInNestedAndTabularData(section, viewName, languageIndex, pageLanguage, isUpdate,stateIndex);
	}
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:18 PM
	 * @param viewName
	 * @param languageIndex
	 * @param pageLanguage
	 * @param tableSize
	 * @return
	 */
	@GetMapping("/deleteCmsSectionContent")
	@ResponseBody
	public ErrorClass deleteCmsSectionContent(@RequestParam("viewName") String viewName,
			@RequestParam("languageIndex") Integer languageIndex,@RequestParam("pageLanguage") String pageLanguage,@RequestParam("tableSize")int tableSize) {
		
		return cmsDataService.deleteSectionRecordInTabularData(viewName, languageIndex, pageLanguage,tableSize);
	}
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:21 PM
	 * @param description
	 * @param viewName
	 * @param pageLanguage
	 * @return
	 */
	@GetMapping("/editCmsDescription")
	@ResponseBody
	public ErrorClass editCmsDescription(@RequestParam("description") String description,@RequestParam("viewName") String viewName,
			@RequestParam("pageLanguage") String pageLanguage) {
		return cmsDataService.editPageDescription(description, viewName, pageLanguage);
	}
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:24 PM
	 * @return
	 */
	@GetMapping("/fetchServerDate")
	@ResponseBody
	public String  fetchServerDate() {
		return cmsDataService.fetchServerDate();
	}

	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:27 PM
	 * @param keyContactModel
	 * @param viewName
	 * @param languageIndex
	 * @param dataIndex
	 * @param pageLanguage
	 * @param isUpdate
	 * @param stateIndex
	 * @param stateName
	 * @return
	 */
	@PostMapping("/updateAddCmsContentKeyContacts")
	@ResponseBody
	public ErrorClass addCmsContentKeyContacts(@RequestBody KeyContactModel  keyContactModel,
			@RequestParam("viewName") String viewName,
			@RequestParam("languageIndex") Integer languageIndex, @RequestParam("dataIndex") Integer dataIndex,
			@RequestParam("pageLanguage") String pageLanguage, @RequestParam("isUpdate") Boolean isUpdate,
			@RequestParam(value="stateIndex", required=false) Integer stateIndex,
			@RequestParam(value="stateName", required=false) String stateName) {
		
		KeyContactRoleModel keyContactRoleModel = null ;
		return cmsDataService.updateAddRecordInNestedAndTabularDataOfKeyContact(keyContactModel, keyContactRoleModel, viewName, languageIndex, dataIndex, pageLanguage, isUpdate, stateIndex, stateName);
	}
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:30 PM
	 * @param keyContactRoleModel
	 * @param viewName
	 * @param languageIndex
	 * @param dataIndex
	 * @param pageLanguage
	 * @param isUpdate
	 * @param stateIndex
	 * @param stateName
	 * @return
	 */
	@PostMapping("/updateAddCmsContentKeyContactsDistrict")
	@ResponseBody
	public ErrorClass addCmsContentKeyContactsDistrict(@RequestBody KeyContactRoleModel  keyContactRoleModel,
			@RequestParam("viewName") String viewName,
			@RequestParam("languageIndex") Integer languageIndex, @RequestParam("dataIndex") Integer dataIndex,
			@RequestParam("pageLanguage") String pageLanguage, @RequestParam("isUpdate") Boolean isUpdate,
			@RequestParam(value="stateIndex", required=false) Integer stateIndex,
			@RequestParam(value="stateName", required=false) String stateName) {
		KeyContactModel keyContactModel = null;
		return cmsDataService.updateAddRecordInNestedAndTabularDataOfKeyContact(keyContactModel, keyContactRoleModel, viewName, languageIndex, dataIndex, pageLanguage, isUpdate, stateIndex, stateName);
	}
	
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:33 PM
	 * @param viewName
	 * @param languageIndex
	 * @param dataIndex
	 * @param pageLanguage
	 * @param stateIndex
	 * @param tableSize
	 * @return
	 */
	@GetMapping("/deleteCmsContentKeyContacts")
	@ResponseBody
	public ErrorClass deleteCmsContentKeyContacts(@RequestParam("viewName") String viewName,
			@RequestParam("languageIndex") Integer languageIndex, @RequestParam("dataIndex") Integer dataIndex,
			@RequestParam("pageLanguage") String pageLanguage, 
			@RequestParam(value="stateIndex", required=false) Integer stateIndex,@RequestParam("tableSize") int tableSize) {
		
		return cmsDataService.deleteRecordInNestedAndTabularDataKeyContact(viewName,languageIndex,dataIndex, pageLanguage, stateIndex,tableSize);
	}
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:36 PM
	 * @param viewName
	 * @param languageIndex
	 * @param pageLanguage
	 * @param tableSize
	 * @param stateIndex
	 * @return
	 */
	@GetMapping("/deleteCmsSectionContentKeyContacts")
	@ResponseBody
	public ErrorClass deleteCmsSectionContentKeyContact(@RequestParam("viewName") String viewName,
			@RequestParam("languageIndex") Integer languageIndex,@RequestParam("pageLanguage") String pageLanguage,
			@RequestParam("tableSize") int tableSize,@RequestParam(value="stateIndex",required=false) Integer stateIndex) {
		
		return cmsDataService.deleteSectionRecordInTabularDataKeyContact(viewName,languageIndex, pageLanguage,tableSize,stateIndex);
	}
	
	/**
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 06-Aug-2019 5:27:41 PM
	 * @param receiveDataModel
	 * @param viewName
	 * @param languageIndex
	 * @param dataIndex
	 * @param pageLanguage
	 * @param isUpdate
	 * @return
	 */
	@PostMapping("/updateRTICmsContent")
	@ResponseBody
	public ErrorClass addCmsContent(@RequestBody Map<String, Object> receiveDataModel, @RequestParam("viewName") String viewName,
			@RequestParam("languageIndex") Integer languageIndex, @RequestParam("dataIndex") Integer dataIndex,
			@RequestParam("pageLanguage") String pageLanguage, @RequestParam("isUpdate") Boolean isUpdate) {
		
		return cmsDataService.updateRTI(receiveDataModel, viewName, languageIndex, dataIndex, pageLanguage, isUpdate);
	}
	
	@GetMapping("/updateArchiveCmsData")
	@ResponseBody
	public String updateArchiveCmsData(@RequestParam("viewName") String viewName) {
		
		return cmsDataService.updateArchiveData(viewName);
	}


}
