package org.sdrc.bbbp.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.sdrc.bbbp.domain.Area;
import org.sdrc.bbbp.domain.Attachment;
import org.sdrc.bbbp.domain.PeriodReference;
import org.sdrc.bbbp.domain.Question;
import org.sdrc.bbbp.domain.RolePeriodicityMapping;
import org.sdrc.bbbp.domain.SubmissionData;
import org.sdrc.bbbp.domain.SubmissionHistory;
import org.sdrc.bbbp.domain.TypeDetail;
import org.sdrc.bbbp.domain.User;
import org.sdrc.bbbp.domain.Year;
import org.sdrc.bbbp.models.DraftModel;
import org.sdrc.bbbp.models.FileModel;
import org.sdrc.bbbp.models.MobileAPISaveDataResponse;
import org.sdrc.bbbp.models.OptionModel;
import org.sdrc.bbbp.models.QuestionModel;
import org.sdrc.bbbp.models.SaveSubmission;
import org.sdrc.bbbp.models.UserModel;
import org.sdrc.bbbp.repository.AreaRepository;
import org.sdrc.bbbp.repository.AttachmentRepository;
import org.sdrc.bbbp.repository.PeriodReferenceRepository;
import org.sdrc.bbbp.repository.QuestionRepository;
import org.sdrc.bbbp.repository.RolePeriodicityMappingRepository;
import org.sdrc.bbbp.repository.RoleRepository;
import org.sdrc.bbbp.repository.SubmissionDataRepository;
import org.sdrc.bbbp.repository.SubmissionHistoryRepository;
import org.sdrc.bbbp.repository.TypeDetailRepository;
import org.sdrc.bbbp.repository.UserRepository;
import org.sdrc.bbbp.repository.YearRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DataEntryServiceImpl implements DataEntryService {
	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private TypeDetailRepository typeDetailRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private SubmissionDataRepository submissionDataRepository;

	@Autowired
	AttachmentRepository attachmentRepository;

	@Autowired
	RolePeriodicityMappingRepository rolePeriodicityMappingRepository;

	@Autowired
	PeriodReferenceRepository periodReferenceRepository;

	@Autowired
	YearRepository yearRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AreaRepository areaRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private SubmissionHistoryRepository submissionHistoryRepositoy;

	 @Value("${dataentry.user.filepath}")
	 private String dataentryFilepath;
	 
	@Override
	public Map<String, List<Map<String, List<QuestionModel>>>> getAllQuestions(Long submissionId) throws Exception {

		UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		SubmissionData submissionData = null;
		if (submissionId != null) {
			submissionData = submissionDataRepository.findBySubmissionId(submissionId);
		}

		Set<String> roles = user.getRoles();
		List<Question> listOfQuestions = null;
		for (String role : roles) {
			switch (role) {
			case "NATIONAL":
				listOfQuestions = questionRepository
						.findByRoleRoleIdOrderByQuestionOrderAsc(roleRepository.findByRoleName("NATIONAL").getRoleId());
				break;

			case "STATE":
				listOfQuestions = questionRepository
						.findByRoleRoleIdOrderByQuestionOrderAsc(roleRepository.findByRoleName("STATE").getRoleId());
				break;

			case "DISTRICT":
				listOfQuestions = questionRepository
						.findByRoleRoleIdOrderByQuestionOrderAsc(roleRepository.findByRoleName("DISTRICT").getRoleId());
				break;
			}
		}

		Map<String, Map<String, List<QuestionModel>>> sectionMap = new LinkedHashMap<String, Map<String, List<QuestionModel>>>();
		Map<String, List<QuestionModel>> subsectionMap = null;
		List<QuestionModel> listOfQuestionModel = null;
		QuestionModel questionModel = null;
		for (Question question : listOfQuestions) {
			String fieldValue = null;
			Boolean isOthersSelected = false;
			;
			String othersValue = null;
			String checkedValue = null;
			List<Attachment> attachments = null;

			if (submissionData != null) {
				Class<?> clazz = submissionData.getClass();
				if (!question.getColumnName().equals("a06") && !question.getColumnName().equals("j10") && !question.getColumnName().equals("q01")
						&& !question.getColumnName().equals("ad03") && !question.getColumnName().equals("ad02")) {
					Field field;
					field = clazz.getDeclaredField(question.getColumnName());
					field.setAccessible(true);
					if (field.getType() == TypeDetail.class) {
						TypeDetail typeDetail = (TypeDetail) field.get(submissionData);
						fieldValue = field.get(submissionData) == null ? null : typeDetail.getName().toString();
					} else {

						if (question.getColumnName().equals("b03")) {
							if ((field.get(submissionData) == null ? null
									: field.get(submissionData).equals("") ? null
											: field.get(submissionData).toString()) != null) {
								List<Integer> typeDetailIds = new ArrayList<>();

								for (String value : field.get(submissionData) == null ? null
										: field.get(submissionData).toString().split(",")) {
									typeDetailIds.add(Integer.parseInt(value));
								}

								List<TypeDetail> listOfTypeDetails = typeDetailRepository.findByIdIn(typeDetailIds);
								List<String> typeDetailName = new ArrayList<>();
								for (TypeDetail typeDetail : listOfTypeDetails) {
									typeDetailName.add(typeDetail.getName());
								}
								fieldValue = String.join(",", typeDetailName);

								checkedValue = fieldValue;
								if (fieldValue.contains("Others")) {
									isOthersSelected = true;
									othersValue = submissionData.getOthersValue();
								}
							}
						} else {
							fieldValue = field.get(submissionData) == null ? null
									: field.get(submissionData).toString();
						}
					}
				} else {

					attachments = attachmentRepository
							.findBySubmissionDataIdSubmissionIdAndColumnNameAndIsDeletedIsFalse(submissionId,
									question.getColumnName());

				}
			}

			if (sectionMap.containsKey(question.getSection())) {
				subsectionMap = sectionMap.get(question.getSection());

				if (sectionMap.get(question.getSection()).containsKey(question.getSubsection())) {
					listOfQuestionModel = subsectionMap.get(question.getSubsection());
					questionModel = setInQuestionModel(question, questionModel, fieldValue, attachments,
							isOthersSelected, othersValue, checkedValue);
					subsectionMap.get(question.getSubsection()).add(questionModel);
				} else {
					questionModel = setInQuestionModel(question, questionModel, fieldValue, attachments,
							isOthersSelected, othersValue, checkedValue);

					listOfQuestionModel = new ArrayList<>();
					listOfQuestionModel.add(questionModel);
					subsectionMap.put(question.getSubsection(), listOfQuestionModel);
				}
			} else {
				subsectionMap = new LinkedHashMap<String, List<QuestionModel>>();
				listOfQuestionModel = new ArrayList<>();
				if (subsectionMap.containsKey(question.getSubsection())) {

					questionModel = setInQuestionModel(question, questionModel, fieldValue, attachments,
							isOthersSelected, othersValue, checkedValue);

					subsectionMap.get(question.getSubsection()).add(questionModel);
				} else {

					questionModel = setInQuestionModel(question, questionModel, fieldValue, attachments,
							isOthersSelected, othersValue, checkedValue);

					listOfQuestionModel.add(questionModel);

					subsectionMap.put(question.getSubsection(), listOfQuestionModel);

				}
				sectionMap.put(question.getSection(), subsectionMap);
			}

		}
		Map<String, List<Map<String, List<QuestionModel>>>> mapOfSectionSubsectionListOfQuestionModel = new LinkedHashMap<>();

		for (Map.Entry<String, Map<String, List<QuestionModel>>> entry : sectionMap.entrySet()) {

			if (mapOfSectionSubsectionListOfQuestionModel.containsKey(entry.getKey())) {
				mapOfSectionSubsectionListOfQuestionModel.get(entry.getKey()).add(entry.getValue());
			} else {
				mapOfSectionSubsectionListOfQuestionModel.put(entry.getKey(), Arrays.asList(entry.getValue()));
			}

		}

		return mapOfSectionSubsectionListOfQuestionModel;
	}

	QuestionModel setInQuestionModel(Question question, QuestionModel questionModel, String value,
			List<Attachment> attachments, Boolean isOthersSelected, String othersValue, String checkedValue) {
		questionModel = new QuestionModel();
		questionModel.setAggRole(question.getAggRole());
		questionModel.setColumnName(question.getColumnName());
		questionModel.setDependecy(question.getDependecy());
		questionModel.setDependentColumn(question.getDependentColumn());
		questionModel.setDependentCondition(Arrays.asList(question.getDependentCondition()));
		questionModel.setType(question.getFieldType());
		questionModel.setFrequency(question.getFrequency());
		questionModel.setLabel(question.getQuestion());
		questionModel.setKey(question.getQuestionId());
		questionModel.setRoleId(question.getRole().getRoleId());
		questionModel.setType(question.getFieldType());
		questionModel.setControlType(question.getControllertype());
		questionModel.setValue(value);
		questionModel.setIsOthersSelected(isOthersSelected);
		questionModel.setOthersValue(othersValue);

		if (question.getTypeId() != null) {
			questionModel.setTypeId(question.getTypeId().getId());
			List<TypeDetail> typedetails = typeDetailRepository.findByTypeIdId(question.getTypeId().getId());
			List<OptionModel> listOfOptions = new ArrayList<>();
			for (TypeDetail typeDetail : typedetails) {
				OptionModel optionModel = new OptionModel();
				optionModel.setKey(typeDetail.getId());
				optionModel.setValue(typeDetail.getName());
				optionModel.setOrder(typeDetail.getOrderLevel());
				if (checkedValue != null) {
					
					  for (String check : checkedValue.split(",")) { if
					  (check.equals(typeDetail.getName())) {
					  optionModel.setIsSelected(true); }else{
					  optionModel.setIsSelected(false); } }
					 
					if (checkedValue.contains(typeDetail.getName())) {
						optionModel.setIsSelected(true);
					} else {
						optionModel.setIsSelected(false);
					}
				}
				listOfOptions.add(optionModel);
			}
			questionModel.setOptions(listOfOptions);
		}
		if (attachments != null) {
			List<FileModel> fileModels = new ArrayList<>();
			for (Attachment attachment : attachments) {
				FileModel fileModel = new FileModel();
				fileModel.setFileName(attachment.getOriginalName());
				fileModel.setFileSize(attachment.getFileSize());
				fileModel.setIsAttached(true);
				fileModel.setIsDeleted(attachment.getIsDeleted());
				fileModel.setAttachmentId(attachment.getAttachmentId());
				fileModel.setColumnName(attachment.getColumnName());
				fileModel.setSubmissionId(attachment.getSubmissionDataId().getSubmissionId());
				fileModel.setTypeId(attachment.getTypeDetailId() != null ? attachment.getTypeDetailId().getId() : null);
				fileModel.setTypeName(
						attachment.getTypeDetailId() != null ? attachment.getTypeDetailId().getName() : null);
				fileModels.add(fileModel);

			}
			questionModel.setFileValue(fileModels);
		}

		return questionModel;

	}

	@Override
	@Transactional
	public String saveData(SubmissionData submissionData) throws Exception {

		//in case the user has not checked agreed checkbox then discard
		if(submissionData.getIsSubmit() && !submissionData.getAgreeCorrectInformation()) {
//			System.out.println("not checked nay");
			throw new RuntimeException("Error!! DC/DM review and approval missing");
		}
		
//		System.out.println(" checked yay");
		UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SubmissionData saveSubmission = null;
		SubmissionData currentSubmission = null;

		saveSubmission = submissionDataRepository
				.findByUserUserIdAndPeriodReferencePeriodReferenceIdAndYearYearIdAndIsSubmitFalse(user.getUserId(),
						submissionData.getPeriodReference().getPeriodReferenceId(),
						submissionData.getYear().getYearId());
		if (saveSubmission != null) {
			submissionData.setSubmissionId(saveSubmission.getSubmissionId());
			submissionData.setCreatedDate(saveSubmission.getCreatedDate());
		}

		submissionData.setUser(new User(user.getUserId()));

		currentSubmission = submissionDataRepository.save(submissionData);

		SubmissionHistory submissionHistory = new SubmissionHistory();
		BeanUtils.copyProperties(submissionData, submissionHistory);
		submissionHistory.setSubmission(currentSubmission);
		submissionHistoryRepositoy.save(submissionHistory);

		return currentSubmission.getSubmissionId().toString();
	}

	@Override
	public List<QuestionModel> getYearAndPeriodFrequence(Boolean isUser) {
		List<QuestionModel> listOfYearPeriod = new ArrayList<>();

		UserModel user = null;
		
		List<PeriodReference> periodicityReferences =null;
				
		if(isUser!=null && isUser) {
			periodicityReferences = periodReferenceRepository.findAll();
		}else {
			user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<RolePeriodicityMapping> rolePeriodicityMappings = rolePeriodicityMappingRepository
					.findByRoleRoleIdIsIn(user.getRoleIds());

			List<Integer> periodicityIds = new ArrayList<>();
			for (RolePeriodicityMapping rolePeriodicityMapping : rolePeriodicityMappings) {
				periodicityIds.add(rolePeriodicityMapping.getPeriodicity().getPeriodicityId());
			}
			periodicityReferences = periodReferenceRepository
					.findByPeriodicityPeriodicityIdIsIn(periodicityIds);
		}

		List<Year> years = yearRepository.findAll();
		// Year
		QuestionModel questionModel = new QuestionModel();
		List<OptionModel> optionModels = null;
		OptionModel options = null;

		if (!years.isEmpty()) {
			optionModels = new ArrayList<>();
			questionModel.setLabel("Select Year");
			questionModel.setColumnName("Year");
			questionModel.setControlType("dropdown");
			for (Year year : years) {
				options = new OptionModel();
				options.setKey(year.getYearId());
				options.setValue(year.getYearRange());
				options.setOrder(year.getOrderBy());
				optionModels.add(options);
			}
			questionModel.setOptions(optionModels);
			listOfYearPeriod.add(questionModel);
		}

		if (!periodicityReferences.isEmpty()) {
			optionModels = new ArrayList<>();

			QuestionModel questionModel2 = new QuestionModel();
			questionModel2.setLabel("Select Quarter");
			questionModel2.setColumnName("Quarter");
			questionModel2.setControlType("dropdown");

			for (PeriodReference periodicityReference : periodicityReferences) {
				options = new OptionModel();
				options.setKey(periodicityReference.getPeriodReferenceId());
				options.setValue(periodicityReference.getMonthRange());
				options.setOrder(periodicityReference.getOrderBy());
				optionModels.add(options);
			}
			questionModel2.setOptions(optionModels);
			listOfYearPeriod.add(questionModel2);
		}

		return listOfYearPeriod;
	}

	@Override
	public String checkSubmission(Integer periodReferenceId, Integer yearId) {
		UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SubmissionData submissionData = submissionDataRepository
				.findByUserUserIdAndPeriodReferencePeriodReferenceIdAndYearYearId(user.getUserId(), periodReferenceId,
						yearId);
		if (submissionData == null) {
			return null;
		} else {
			if (submissionData.getIsSubmit()) {
				return "Data for the selected time period is already submitted ";
			} else {
				return "Data for the selected time period is saved for editing. Click Ok Go to Drafts";
			}
		}

	}

	@Override
	public List<DraftModel> getAllDrafts() {
		UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DraftModel> listOfDraftModel = new ArrayList<>();
		List<SubmissionData> submissionDatas = submissionDataRepository
				.findByUserUserIdAndIsSubmitFalse(user.getUserId());
		DraftModel draftModel = null;
		for (SubmissionData submissionData : submissionDatas) {
			draftModel = new DraftModel();

			draftModel.setSubmissionId(submissionData.getSubmissionId());
			draftModel.setTimeRange(submissionData.getPeriodReference().getMonthRange());
			draftModel.setYearRange(submissionData.getYear().getYearRange());
			draftModel.setSubmissionDate(
					new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(submissionData.getUpdatedDate()));
			draftModel.setPeriodId(submissionData.getPeriodReference().getPeriodReferenceId());
			draftModel.setYearId(submissionData.getYear().getYearId());

			listOfDraftModel.add(draftModel);
		}
		return listOfDraftModel;
	}

	@Override
	public String uploadFiles(FileModel fileModel) throws Exception {

		UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Attachment> duplicateAttachment = attachmentRepository
				.findBySubmissionDataIdSubmissionIdAndOriginalNameAndColumnNameAndIsDeletedIsFalse(
						fileModel.getSubmissionId(), fileModel.getFileName(), fileModel.getColumnName());
		Attachment attachment = null;
		SubmissionData submissionData = submissionDataRepository.findBySubmissionId(fileModel.getSubmissionId());
		if (!fileModel.getIsAttached()) {
			if (fileModel.getIsDeleted()) {
				Attachment attached = attachmentRepository
						.findByColumnNameAndOriginalNameAndSubmissionDataIdSubmissionIdAndIsDeletedIsFalse(
								fileModel.getColumnName(), fileModel.getFileName(), fileModel.getSubmissionId());
				if (attached != null) {
					attached.setIsDeleted(fileModel.getIsDeleted());
					attachmentRepository.save(attached);

				}
			} else {

				if (duplicateAttachment.isEmpty()) {
					attachment = new Attachment();
					attachment.setColumnName(fileModel.getColumnName());
					attachment.setFilePath(getFilePath(fileModel, user, submissionData));
					attachment.setIsDeleted(fileModel.getIsDeleted());
					attachment.setOriginalName(fileModel.getFileName());
					attachment.setSubmissionDataId(new SubmissionData(fileModel.getSubmissionId()));
					attachment.setTypeDetailId(
							fileModel.getTypeId() == null ? null : new TypeDetail(fileModel.getTypeId()));
					attachment.setFileSize(fileModel.getFileSize());
					attachmentRepository.save(attachment);
				}
			}
		} else {
			if (fileModel.getIsDeleted()) {
				attachment = attachmentRepository.findByAttachmentId(fileModel.getAttachmentId());
				if (attachment != null) {
					attachment.setIsDeleted(fileModel.getIsDeleted());
					attachmentRepository.save(attachment);
				}
			}
		}

		List<Attachment> listOfAttachment = attachmentRepository
				.findBySubmissionDataIdSubmissionIdAndIsDeletedIsFalse(fileModel.getSubmissionId());
		if (submissionData.getFileCount() == listOfAttachment.size()) {
			submissionData.setStatus("SUBMISSION_COMPLETED");
			submissionDataRepository.save(submissionData);
		} else {
			submissionData.setStatus("SUBMISSION_RECEIVED");
			submissionDataRepository.save(submissionData);
		}

		return "success";
	}

	private String getFilePath(FileModel fileModel, UserModel user, SubmissionData submissionData) throws IOException {

		Map<Integer, String> areaIdNameMap = new HashMap<>();
		Map<Integer, Integer> areaIdParentIdMap = new HashMap<>();
		List<Area> areaList = areaRepository.findAll();
		areaList.forEach(area1 -> areaIdNameMap.put(area1.getAreaId(), area1.getAreaName()));
		areaList.forEach(area2 -> areaIdParentIdMap.put(area2.getAreaId(), area2.getParentAreaId()));

		if (fileModel.getBase64() != null) {

			String userPath = null;

			User userDomain = userRepository.findByUserId(user.getUserId());

			for (String role : user.getRoles()) {
				switch (role) {
				case "NATIONAL":
					userPath = dataentryFilepath+ "India/files/"
							+ submissionData.getYear().getYearRange() + "/"
							+ submissionData.getPeriodReference().getMonthRange() + "/";
					break;

				case "STATE":
					userPath = dataentryFilepath+ "India/"
							+ areaIdNameMap.get(userDomain.getAreas().get(0).getArea().getAreaId()) + "/files/"
							+ submissionData.getYear().getYearRange() + "/"
							+ submissionData.getPeriodReference().getMonthRange() + "/";
					break;

				case "DISTRICT":
					userPath = dataentryFilepath + "India/"
							+ areaIdNameMap
									.get(areaIdParentIdMap.get(userDomain.getAreas().get(0).getArea().getAreaId()))
							+ "/" + areaIdNameMap.get(userDomain.getAreas().get(0).getArea().getAreaId()) + "/"
							+ submissionData.getYear().getYearRange() + "/"
							+ submissionData.getPeriodReference().getMonthRange() + "/";
					break;
				}
			}

			File file = new File(userPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			byte[] decodedBytes = Base64.decodeBase64(fileModel.getBase64());

			String finalPath = userPath + FilenameUtils.removeExtension(fileModel.getFileName()) + "_"
					+ new SimpleDateFormat("ddMMyyyyHHmmssSSS").format(new java.util.Date()) + "."
					+ (FilenameUtils.getExtension(fileModel.getFileName()));

			file = new File(finalPath);
			FileUtils.writeByteArrayToFile(file, decodedBytes);

			return finalPath;

		} else
			return null;

	}

	@Override
	public MobileAPISaveDataResponse saveMobileData(SubmissionData submissionData) throws Exception {

		UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SubmissionData saveSubmission = null;
		SubmissionData currentSubmission = null;

		saveSubmission = submissionDataRepository.findByUserUserIdAndPeriodReferencePeriodReferenceIdAndYearYearId(
				user.getUserId(), submissionData.getPeriodReference().getPeriodReferenceId(),
				submissionData.getYear().getYearId());
		if (saveSubmission == null) {
			submissionData.setUser(new User(user.getUserId()));
			currentSubmission = submissionDataRepository.save(submissionData);

			SubmissionHistory submissionHistory = new SubmissionHistory();
			BeanUtils.copyProperties(submissionData, submissionHistory);
			submissionHistory.setSubmission(currentSubmission);
			submissionHistoryRepositoy.save(submissionHistory);

			return new MobileAPISaveDataResponse(1, currentSubmission.getSubmissionId(), messageSource.getMessage("submission.entry.finalized", null, null));
		} else {
			
			if(!saveSubmission.getIsSubmit()) {
				return new MobileAPISaveDataResponse(2, saveSubmission.getSubmissionId(), messageSource.getMessage("entry.by.other.channels.ask.override", null, null));
			}
			else {
				return new MobileAPISaveDataResponse(0, saveSubmission.getSubmissionId(), messageSource.getMessage("submission.already.finalized", null, null));
			}
		}

	}

	@Override
	public List<SaveSubmission> getAllQuestionsForMobile() throws Exception {
		UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<SubmissionData> submissionDatas = submissionDataRepository.findByUserUserId(user.getUserId());
		List<SaveSubmission> listOfSaveSubmission = new ArrayList<>();
		DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		for (SubmissionData submissionData : submissionDatas) {
			SaveSubmission saveSubmission = new SaveSubmission();
			saveSubmission.setQuarter(submissionData.getPeriodReference().getMonthRange());
			saveSubmission.setYear(submissionData.getYear().getYearRange());
			saveSubmission.setQuarterId(submissionData.getPeriodReference().getPeriodReferenceId());
			saveSubmission.setYearId(submissionData.getYear().getYearId());
			saveSubmission.setLastUpdate(df.format(submissionData.getUpdatedDate()));
			saveSubmission.setIsSubmit(submissionData.getIsSubmit());

			Set<String> roles = user.getRoles();
			List<Question> listOfQuestions = null;
			for (String role : roles) {
				switch (role) {
				case "NATIONAL":
					listOfQuestions = questionRepository.findByRoleRoleIdOrderByQuestionOrderAsc(
							roleRepository.findByRoleName("NATIONAL").getRoleId());
					break;

				case "STATE":
					listOfQuestions = questionRepository
							.findByRoleRoleIdOrderByQuestionOrderAsc(roleRepository.findByRoleName("STATE").getRoleId());
					break;

				case "DISTRICT":
					listOfQuestions = questionRepository.findByRoleRoleIdOrderByQuestionOrderAsc(
							roleRepository.findByRoleName("DISTRICT").getRoleId());
					break;
				}
			}

			Map<String, Map<String, List<QuestionModel>>> sectionMap = new LinkedHashMap<String, Map<String, List<QuestionModel>>>();
			Map<String, List<QuestionModel>> subsectionMap = null;
			List<QuestionModel> listOfQuestionModel = null;
			QuestionModel questionModel = null;
			for (Question question : listOfQuestions) {
				String fieldValue = null;
				Boolean isOthersSelected = false;
				
				String othersValue = null;
				String checkedValue = null;
				List<Attachment> attachments = null;

				if (submissionData != null) {
					Class<?> clazz = submissionData.getClass();
					if (!question.getColumnName().equals("a06") && !question.getColumnName().equals("j10") && !question.getColumnName().equals("q01")
							&& !question.getColumnName().equals("ad03") && !question.getColumnName().equals("ad02")) {
						Field field;
						field = clazz.getDeclaredField(question.getColumnName());
						field.setAccessible(true);
						if (field.getType() == TypeDetail.class) {
							TypeDetail typeDetail = (TypeDetail) field.get(submissionData);
							fieldValue = field.get(submissionData) == null ? null : typeDetail.getId().toString();
						} else {

							if (question.getColumnName().equals("b03")) {
								if ((field.get(submissionData) == null ? null
										: field.get(submissionData).equals("") ? null
												: field.get(submissionData).toString()) != null) {
									List<Integer> typeDetailIds = new ArrayList<>();

									for (String value : field.get(submissionData) == null ? null
											: field.get(submissionData).toString().split(",")) {
										typeDetailIds.add(Integer.parseInt(value));
									}
									fieldValue = field.get(submissionData) == null ? null
											: field.get(submissionData).toString();

									checkedValue = fieldValue;
									if (fieldValue.contains("12")) {
										isOthersSelected = true;
										othersValue = submissionData.getOthersValue();
									}
								}
							} else {
								fieldValue = field.get(submissionData) == null ? null
										: field.get(submissionData).toString();
							}
						}
					} else {

						attachments = attachmentRepository
								.findBySubmissionDataIdSubmissionIdAndColumnNameAndIsDeletedIsFalse(
										submissionData.getSubmissionId(), question.getColumnName());

					}
				}

				if (sectionMap.containsKey(question.getSection())) {
					subsectionMap = sectionMap.get(question.getSection());

					if (sectionMap.get(question.getSection()).containsKey(question.getSubsection())) {
						listOfQuestionModel = subsectionMap.get(question.getSubsection());
						questionModel = setInQuestionModelMobile(question, questionModel, fieldValue, attachments,
								isOthersSelected, othersValue, checkedValue);
						subsectionMap.get(question.getSubsection()).add(questionModel);
					} else {
						questionModel = setInQuestionModelMobile(question, questionModel, fieldValue, attachments,
								isOthersSelected, othersValue, checkedValue);

						listOfQuestionModel = new ArrayList<>();
						listOfQuestionModel.add(questionModel);
						subsectionMap.put(question.getSubsection(), listOfQuestionModel);
					}
				} else {
					subsectionMap = new LinkedHashMap<String, List<QuestionModel>>();
					listOfQuestionModel = new ArrayList<>();
					if (subsectionMap.containsKey(question.getSubsection())) {

						questionModel = setInQuestionModelMobile(question, questionModel, fieldValue, attachments,
								isOthersSelected, othersValue, checkedValue);

						subsectionMap.get(question.getSubsection()).add(questionModel);
					} else {

						questionModel = setInQuestionModelMobile(question, questionModel, fieldValue, attachments,
								isOthersSelected, othersValue, checkedValue);

						listOfQuestionModel.add(questionModel);

						subsectionMap.put(question.getSubsection(), listOfQuestionModel);

					}
					sectionMap.put(question.getSection(), subsectionMap);
				}

			}
			Map<String, List<Map<String, List<QuestionModel>>>> mapOfSectionSubsectionListOfQuestionModel = new LinkedHashMap<>();

			for (Map.Entry<String, Map<String, List<QuestionModel>>> entry : sectionMap.entrySet()) {

				if (mapOfSectionSubsectionListOfQuestionModel.containsKey(entry.getKey())) {
					mapOfSectionSubsectionListOfQuestionModel.get(entry.getKey()).add(entry.getValue());
				} else {
					mapOfSectionSubsectionListOfQuestionModel.put(entry.getKey(), Arrays.asList(entry.getValue()));
				}

			}
			saveSubmission.setAllQuestions(mapOfSectionSubsectionListOfQuestionModel);

			listOfSaveSubmission.add(saveSubmission);
		}
		return listOfSaveSubmission;
	}

	private QuestionModel setInQuestionModelMobile(Question question, QuestionModel questionModel, String fieldValue,
			List<Attachment> attachments, Boolean isOthersSelected, String othersValue, String checkedValue) {

		questionModel = new QuestionModel();
		questionModel.setAggRole(question.getAggRole());
		questionModel.setColumnName(question.getColumnName());
		questionModel.setDependecy(question.getDependecy());
		questionModel.setDependentColumn(question.getDependentColumn());
		questionModel.setDependentCondition(Arrays.asList(question.getDependentCondition()));
		questionModel.setType(question.getFieldType());
		questionModel.setFrequency(question.getFrequency());
		questionModel.setLabel(question.getQuestion());
		questionModel.setKey(question.getQuestionId());
		questionModel.setRoleId(question.getRole().getRoleId());
		questionModel.setType(question.getFieldType());
		questionModel.setControlType(question.getControllertype());
		questionModel.setValue(fieldValue);
		questionModel.setIsOthersSelected(isOthersSelected);
		questionModel.setOthersValue(othersValue);

		if (question.getTypeId() != null) {
			questionModel.setTypeId(question.getTypeId().getId());
			List<TypeDetail> typedetails = typeDetailRepository.findByTypeIdId(question.getTypeId().getId());
			List<OptionModel> listOfOptions = new ArrayList<>();
			for (TypeDetail typeDetail : typedetails) {
				OptionModel optionModel = new OptionModel();
				optionModel.setKey(typeDetail.getId());
				optionModel.setValue(typeDetail.getName());
				optionModel.setOrder(typeDetail.getOrderLevel());
				if (checkedValue != null) {
					
					  for (String check : checkedValue.split(",")) { if
					  (check.equals(typeDetail.getName())) {
					  optionModel.setIsSelected(true); }else{
					  optionModel.setIsSelected(false); } }
					 
					if (checkedValue.contains(typeDetail.getName())) {
						optionModel.setIsSelected(true);
					} else {
						optionModel.setIsSelected(false);
					}
				}
				listOfOptions.add(optionModel);
			}
			questionModel.setOptions(listOfOptions);
		}
		if (attachments != null) {
			List<FileModel> fileModels = new ArrayList<>();
			Map<Integer,List<FileModel>> filemap = new HashMap<>();
			for (Attachment attachment : attachments) {
				FileModel fileModel = null;
				if (attachment.getTypeDetailId() == null) {
					fileModel = new FileModel();
					fileModel.setFileName(attachment.getOriginalName());
					fileModel.setFileSize(attachment.getFileSize());
					fileModel.setIsAttached(true);
					fileModel.setIsDeleted(attachment.getIsDeleted());
					fileModel.setAttachmentId(attachment.getAttachmentId());
					fileModel.setColumnName(attachment.getColumnName());
					fileModel.setSubmissionId(attachment.getSubmissionDataId().getSubmissionId());
					fileModel.setTypeId(
							attachment.getTypeDetailId() != null ? attachment.getTypeDetailId().getId() : null);
					fileModel.setTypeName(
							attachment.getTypeDetailId() != null ? attachment.getTypeDetailId().getName() : null);
					fileModels.add(fileModel);
					questionModel.setFileValue(fileModels);
				}
				else {

					if (filemap.containsKey(attachment.getTypeDetailId().getId())) {
						fileModel = new FileModel();
						fileModels =new ArrayList<>();
						fileModel.setFileName(attachment.getOriginalName());
						fileModel.setFileSize(attachment.getFileSize());
						fileModel.setIsAttached(true);
						fileModel.setIsDeleted(attachment.getIsDeleted());
						fileModel.setAttachmentId(attachment.getAttachmentId());
						fileModel.setColumnName(attachment.getColumnName());
						fileModel.setSubmissionId(attachment.getSubmissionDataId().getSubmissionId());
						fileModel.setTypeId(
								attachment.getTypeDetailId() != null ? attachment.getTypeDetailId().getId() : null);
						fileModel.setTypeName(
								attachment.getTypeDetailId() != null ? attachment.getTypeDetailId().getName() : null);
						fileModels.add(fileModel);
						filemap.get(attachment.getTypeDetailId().getId()).add(fileModel);
					} else {
						fileModel = new FileModel();
						fileModels =new ArrayList<>();
						fileModel.setFileName(attachment.getOriginalName());
						fileModel.setFileSize(attachment.getFileSize());
						fileModel.setIsAttached(true);
						fileModel.setIsDeleted(attachment.getIsDeleted());
						fileModel.setAttachmentId(attachment.getAttachmentId());
						fileModel.setColumnName(attachment.getColumnName());
						fileModel.setSubmissionId(attachment.getSubmissionDataId().getSubmissionId());
						fileModel.setTypeId(
								attachment.getTypeDetailId() != null ? attachment.getTypeDetailId().getId() : null);
						fileModel.setTypeName(
								attachment.getTypeDetailId() != null ? attachment.getTypeDetailId().getName() : null);
						fileModels.add(fileModel);
						filemap.put(attachment.getTypeDetailId().getId(), fileModels);
					}
				}

			}
			
			questionModel.setFilesByType(filemap);
		}

		return questionModel;

	
	}

}
