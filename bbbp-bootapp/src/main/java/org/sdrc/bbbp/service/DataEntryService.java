package org.sdrc.bbbp.service;

import java.util.List;
import java.util.Map;

import org.sdrc.bbbp.domain.SubmissionData;
import org.sdrc.bbbp.models.DraftModel;
import org.sdrc.bbbp.models.FileModel;
import org.sdrc.bbbp.models.MobileAPISaveDataResponse;
import org.sdrc.bbbp.models.QuestionModel;
import org.sdrc.bbbp.models.SaveSubmission;

public interface DataEntryService {
	
	Map<String,List<Map<String,List<QuestionModel>>>> getAllQuestions(Long submissionId)throws Exception;
	
	String saveData(SubmissionData submissionData) throws Exception;
	
	List<QuestionModel> getYearAndPeriodFrequence(Boolean isUser);
	
	String checkSubmission(Integer periodReferenceId,Integer yearId);
	
	List<DraftModel> getAllDrafts();
	
	String uploadFiles(FileModel fileModel) throws Exception;
	
	MobileAPISaveDataResponse saveMobileData(SubmissionData submissionData) throws Exception;
	
	List<SaveSubmission> getAllQuestionsForMobile()throws Exception;
	
	
	

}
