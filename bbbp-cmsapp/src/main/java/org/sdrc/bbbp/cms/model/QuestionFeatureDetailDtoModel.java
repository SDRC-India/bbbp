package org.sdrc.bbbp.cms.model;

import java.util.List;

import org.sdrc.bbbp.models.QuestionModel;

public class QuestionFeatureDetailDtoModel {
	private List<QuestionModel> listOfQuestionModel;
	private List<CmsDataDto> listOfCmsDataDto;
	
	public List<QuestionModel> getListOfQuestionModel() {
		return listOfQuestionModel;
	}
	public void setListOfQuestionModel(List<QuestionModel> listOfQuestionModel) {
		this.listOfQuestionModel = listOfQuestionModel;
	}
	public List<CmsDataDto> getListOfCmsDataDto() {
		return listOfCmsDataDto;
	}
	public void setListOfCmsDataDto(List<CmsDataDto> listOfCmsDataDto) {
		this.listOfCmsDataDto = listOfCmsDataDto;
	}
	
	
	
	
	

}
