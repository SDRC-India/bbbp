package org.sdrc.bbbp.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.sdrc.bbbp.domain.SubmissionData;
import org.sdrc.bbbp.models.DraftModel;
import org.sdrc.bbbp.models.FileModel;
import org.sdrc.bbbp.models.MobileAPISaveDataResponse;
import org.sdrc.bbbp.models.QuestionModel;
import org.sdrc.bbbp.models.SaveSubmission;
import org.sdrc.bbbp.models.UserModel;
import org.sdrc.bbbp.repository.AttachmentRepository;
import org.sdrc.bbbp.service.DataEntryService;
import org.sdrc.bbbp.service.MobileUserAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class DataEntryController {
	
	private Logger _log = LoggerFactory.getLogger(DataEntryController.class);
	
	@Autowired
	DataEntryService dataEntryService;
	
	@Autowired
	AttachmentRepository attachmentRepository;
	
	@Autowired
	MobileUserAuthenticationService mobileUserAuthenticationService;
	
	@RequestMapping("/getAllDataEntryQuestions")
	@ResponseBody
	public Map<String,List<Map<String,List<QuestionModel>>>>  getLandingPageData(@RequestParam(value = "submissionId", required = false) Long submissionId)throws Exception {
		try{
			return dataEntryService.getAllQuestions(submissionId);
		}catch(Exception e){
			UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			_log.error("ERR-getAllDataEntryQuestions Failed to fetch questions/draft data for user : {} with submission id : {} ",user.getUsername(),submissionId);
			throw e;
		}
		
	}
	
	@RequestMapping("/saveData")
	@ResponseBody
	public String  saveData(@RequestBody SubmissionData submissionData) throws Exception {
		try{
			return dataEntryService.saveData(submissionData);
		}catch(Exception e){
			UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			_log.error("ERR-saveData Failed to save data from user : {} with request payload : {} ",user.getUsername(),submissionData.toString(),e);
			throw e;
		}
		
	}
	
	@RequestMapping("/getAllPeriodReference")
	@ResponseBody
	public List<QuestionModel>  getAllPeriodReferenceByLoinUser(@RequestParam(value="isUser",required=false) Boolean isUser){
		return dataEntryService.getYearAndPeriodFrequence(isUser);
	}
	
	@RequestMapping("/getCheckSubmission")
	@ResponseBody
	public String  getLandingPageData(@RequestParam Integer periodReferenceId,@RequestParam Integer yearId) {
		return dataEntryService.checkSubmission(periodReferenceId,yearId);
	}
	
	@RequestMapping("/getAllDrafts")
	@ResponseBody
	public List<DraftModel>  getSavedData() {
	return dataEntryService.getAllDrafts();
	}
	
	
	@RequestMapping("/uploadFiles")
	@ResponseBody
	public String  uploadFiles(@RequestBody FileModel fileModel) throws Exception {
		return dataEntryService.uploadFiles(fileModel);
	}
	
	
	@RequestMapping(value = "/downloadAttached")
	@ResponseBody
	public void downLoad(@RequestParam("attachmentId") Long attachmentId,
			HttpServletResponse response) throws IOException {
		
		InputStream inputStream;
		String fileName = attachmentRepository.findByAttachmentId(attachmentId).getFilePath();
		UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		try {

			inputStream = new FileInputStream(fileName);
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",
					new java.io.File(fileName).getName());
			response.setHeader(headerKey, headerValue);
			response.setContentType("application/octet-stream"); // for all file
																	// type
			ServletOutputStream outputStream = response.getOutputStream();
			FileCopyUtils.copy(inputStream, outputStream);
			outputStream.close();
			//new File(fileName).delete(); //delete the file once down
		} catch (FileNotFoundException e) {
			_log.error("ERR-downloadAttached Failed to download file for user : {} with attachement id : {}",user.getUsername(),attachmentId,e);
			e.printStackTrace();
		} catch (IOException e) {
			_log.error("ERR-downloadAttached Failed to download file for user : {} with attachement id : {}",user.getUsername(),attachmentId,e);
			e.printStackTrace();
		}
		
		
	}
	@RequestMapping("/saveMobileData")
	@ResponseBody
	public MobileAPISaveDataResponse  saveMobileData(@RequestBody SubmissionData submissionData) throws Exception {
		try{
			mobileUserAuthenticationService.authenticateUserAgainstPasswordChange();
			return dataEntryService.saveMobileData(submissionData);
		}catch(Exception e){
			UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			_log.error("ERR-saveData Failed to save data from user : {} with request payload : {} ",user.getUsername(),submissionData.toString(),e);
			throw e;
		}
		
	}
	
	
	@RequestMapping("/getAllSaveAndSubmittedData")
	@ResponseBody
	public List<SaveSubmission> getAllSaveDataEntryQuestions()throws Exception {
		try{
			return dataEntryService.getAllQuestionsForMobile();
		}catch(Exception e){
			UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			_log.error("ERR-getAllDataEntryQuestions Failed to fetch questions/draft data for  mobile user : {} with submission id : {} ",user.getUsername());
			throw e;
		}
		
	}
	
	

}
