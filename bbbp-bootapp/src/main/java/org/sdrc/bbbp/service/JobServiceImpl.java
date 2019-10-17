package org.sdrc.bbbp.service;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.sdrc.bbbp.dashboard.domain.UtTimeperiod;
import org.sdrc.bbbp.dashboard.repository.UtTimeperiodRepository;
import org.sdrc.bbbp.dashboard.util.Constants;
import org.sdrc.bbbp.domain.Area;
import org.sdrc.bbbp.domain.Periodicity;
import org.sdrc.bbbp.domain.Question;
import org.sdrc.bbbp.domain.SubmissionData;
import org.sdrc.bbbp.domain.TypeDetail;
import org.sdrc.bbbp.domain.Year;
import org.sdrc.bbbp.repository.AreaRepository;
import org.sdrc.bbbp.repository.LoginAuditRepository;
import org.sdrc.bbbp.repository.QuestionRepository;
import org.sdrc.bbbp.repository.SubmissionDataRepository;
import org.sdrc.bbbp.repository.TypeDetailRepository;
import org.sdrc.bbbp.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private YearRepository yearRepository;

	@Autowired
	private UtTimeperiodRepository utTimeperiodRepository;
	
	
	@Autowired 
	private QuestionRepository questionRepository;
	
	@Autowired
	private SubmissionDataRepository submissionDataRepository;
	
	@Autowired
	private ConfigurableEnvironment configurableEnvironment;
	
	@Autowired
	private TypeDetailRepository typeDetailRepository;
	
	@Autowired
	private AreaRepository  areaRepository;
	
	@Autowired
	private LoginAuditRepository loginAuditRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MessageSource notificationSource;
	
	@Autowired
	private EmbeddedWebApplicationContext appContext;
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
	private SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Value("${rawdata.report.header.name}")
	private String headerValue;

	@Override
	public void quarterlyJob() {

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		Year lastYear = yearRepository.findTop1ByOrderByYearIdDesc();

		if (yearRepository.findByYearRange(year + "-" + (year + 1)) == null) {
			Year yearLatest = new Year();
			yearLatest.setYearRange(year + "-" + (year + 1));
			yearLatest.setOrderBy(lastYear.getOrderBy() + 1);
			yearRepository.save(yearLatest);
		}
	}

	@Override
	public void yearlyCSRTimeperiodJob() {
		UtTimeperiod utTimeperiod = new UtTimeperiod();

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year - 2);

		utTimeperiod.setStartDate(cal.getTime());

		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, 11); // 11 = december
		cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
		utTimeperiod.setEndDate(cal.getTime());
		utTimeperiod.setPeriodicity(new Periodicity(4));
		utTimeperiod.setTimePeriod(year -2 + "-" + (year));
		if (utTimeperiodRepository.findByTimePeriodAndPeriodicityPeriodicityId((year - 2 + "-" + (year)),
				Constants.Web.BIENNIAL_PERIODICITY_ID) == null) {
			utTimeperiodRepository.save(utTimeperiod);
		}
	}

	@Override
	public String dailyMailReportJob(String stateId, String districtId/*, Integer yearId*/) {
		List<SubmissionData> submissionList = null;
		String path = null;
		List<Question> questionList = questionRepository.findAllByOrderByQuestionIdAsc();
		
	/*	if("All".equals(stateId) && "All".equals(districtId)) {
			submissionList = submissionDataRepository.findByYearYearIdAndIsSubmitTrue(yearId);
		} else if( !"All".equals(stateId) && "All".equals(districtId)) {
			submissionList = submissionDataRepository.getStat	eAllData(Integer.parseInt(stateId), yearId);
		}else if("Null".equals(stateId) && !"All".equals(districtId)) {
			submissionList = submissionDataRepository.getDistrictAllData(Integer.parseInt(districtId), yearId);
		}else {
			submissionList = submissionDataRepository.getSelectedAreaData(Integer.parseInt(stateId), Integer.parseInt(districtId), yearId);
		}*/
		
		if("All".equals(stateId) && "All".equals(districtId)) {
			submissionList = submissionDataRepository.findAllByIsSubmitTrue();
		} else if( !"All".equals(stateId) && "All".equals(districtId)) {
			submissionList = submissionDataRepository.getStateAllData(Integer.parseInt(stateId));
		}else if("Null".equals(stateId) && !"All".equals(districtId)) {
			submissionList = submissionDataRepository.getDistrictAllData(Integer.parseInt(districtId));
		}else {
			submissionList = submissionDataRepository.getSelectedAreaData(Integer.parseInt(stateId), Integer.parseInt(districtId));
		}

		List<String> qstn = new ArrayList<>();

		Map<String, Integer> sectionMap = new LinkedHashMap<>();

		Integer questionValue = 0;

		/**
		 * as some of the quest has to be removed ::
		 */
		List<Question> newQuestionList = new LinkedList<>();

		for (Question question : questionList) {

			if (!question.getColumnName().equals("a06") && !question.getColumnName().equals("q01")
					&& !question.getColumnName().equals("ad03") && !question.getColumnName().equals("ad02")) {

				newQuestionList.add(question);
			}

		}

		// section
		for (Question ques : newQuestionList) {

			if (sectionMap.containsKey(ques.getSection())) {

				sectionMap.put(ques.getSection(), ++questionValue);

			} else {

				questionValue = 0;
				qstn = new ArrayList<>();
				qstn.add(ques.getQuestion());
				sectionMap.put(ques.getSection(), ++questionValue);
			}

		}

		// subsection
		Map<String, Integer> subSectionMap = new LinkedHashMap<>();
		Set<String> subSectionList = new HashSet<>();

		for (Question ques : newQuestionList) {

			if (subSectionMap.get(ques.getSubsection()) == null)

				subSectionMap.put(ques.getSubsection(), 1);

			else {
				subSectionMap.put(ques.getSubsection(), subSectionMap.get(ques.getSubsection()) + 1);
			}

		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Raw_Data");

		// header font
		HSSFFont font = workbook.createFont();
		font.setBold(true);

		// header
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerStyle.setBorderTop(CellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		headerStyle.setWrapText(true);
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setFont(font);

		// odd cell
		CellStyle colStyleOdd = workbook.createCellStyle();
		colStyleOdd.setBorderBottom(CellStyle.BORDER_THIN);
		colStyleOdd.setBorderTop(CellStyle.BORDER_THIN);
		colStyleOdd.setBorderLeft(CellStyle.BORDER_THIN);
		colStyleOdd.setBorderRight(CellStyle.BORDER_THIN);
		colStyleOdd.setWrapText(true);
		colStyleOdd.setAlignment(XSSFCellStyle.ALIGN_CENTER);

		// even cell
		CellStyle colStyleEven = workbook.createCellStyle();
		colStyleEven.setBorderBottom(CellStyle.BORDER_THIN);
		colStyleEven.setBorderTop(CellStyle.BORDER_THIN);
		colStyleEven.setBorderLeft(CellStyle.BORDER_THIN);
		colStyleEven.setBorderRight(CellStyle.BORDER_THIN);
		colStyleEven.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		colStyleEven.setWrapText(true);
		colStyleEven.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		colStyleEven.setFillPattern(CellStyle.SOLID_FOREGROUND);

		try {

			Row row;
			Cell cell;

			row = sheet.createRow(0);
			cell = row.createCell(0);
			
			sheet = doMerge(0, 0, 0, 4, sheet);
			cell.setCellValue("BBBP:Raw Data Report");
			row = sheet.createRow(1);
			cell = row.createCell(0);
			
			sheet = doMerge(1, 1, 0, 4, sheet);
			cell.setCellValue("Report generate on: "+dateTimeFormat.format(new Date()));
   
			row = sheet.createRow(2);
			cell = row.createCell(0);
			cell.setCellStyle(headerStyle);
			sheet = doMerge(2, 2, 0, 8, sheet);
			cell.setCellValue("Section");
			int firstCol = 9;

			/**
			 * Sections
			 */
			// iterating to merge column and set section value
			for (Map.Entry<String, Integer> entry : sectionMap.entrySet()) {
				cell = row.createCell(firstCol);
				cell.setCellStyle(headerStyle);
				sheet = doMerge(2, 2, firstCol, entry.getValue() + firstCol - 1, sheet);
				cell.setCellValue(entry.getKey());
				firstCol = entry.getValue() + firstCol;
			}

			/**
			 * sub-sections
			 */

			// sub section
			row = null;
			row = sheet.createRow(3);
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 8));
			cell = row.createCell(0);
			cell.setCellStyle(headerStyle);
			cell.setCellValue("Sub Section");
			firstCol = 9;

			for (Entry<String, Integer> entry : subSectionMap.entrySet()) {

				if (entry.getValue() > 1) {

					cell = row.createCell(firstCol);
					int temp = firstCol;
					cell = row.createCell(temp++);
					cell.setCellStyle(headerStyle);
					sheet = doMerge(3, 3, firstCol, firstCol + entry.getValue() - 1, sheet);
					cell.setCellValue(entry.getKey());
					firstCol = entry.getValue() + firstCol;
				} else {

					cell = row.createCell(firstCol);
					int temp = firstCol;
					cell = row.createCell(temp++);
					cell.setCellStyle(headerStyle);
					cell.setCellValue(entry.getKey());
					firstCol = entry.getValue() + firstCol;
				}

			}

			String[] fixedHederValue = headerValue.split(",");

			int index;
			row = sheet.createRow(4);

			for (index = 0; index < fixedHederValue.length; index++) {

				cell = row.createCell(index);

				if (fixedHederValue[index].length() == 6)
					sheet.setColumnWidth(cell.getColumnIndex(), 1700);

				else if (fixedHederValue[index].length() == 5)
					sheet.setColumnWidth(cell.getColumnIndex(), 2500);

				else if (fixedHederValue[index].length() > 6 && fixedHederValue[index].length() < 10)
					sheet.setColumnWidth(cell.getColumnIndex(), 4000);
				else
					sheet.setColumnWidth(cell.getColumnIndex(), 5500);

				sheet.setHorizontallyCenter(true);
				cell.setCellStyle(headerStyle);
				cell.setCellValue(fixedHederValue[index]);
			}

			/**
			 * iterating questions
			 */
			for (Question question : questionList) {

				if (!question.getColumnName().equals("a06") && !question.getColumnName().equals("q01")
						&& !question.getColumnName().equals("ad03") && !question.getColumnName().equals("ad02")) {

					cell = row.createCell(index);

					if (question.getQuestion().length() >= 10 && question.getQuestion().length() < 20)
						sheet.setColumnWidth(cell.getColumnIndex(), 1000);
					else if (question.getQuestion().length() >= 20 && question.getQuestion().length() < 50)
						sheet.setColumnWidth(cell.getColumnIndex(), 6000);
					else if (question.getQuestion().length() >= 50 && question.getQuestion().length() < 100)
						sheet.setColumnWidth(cell.getColumnIndex(), 9000);
					else if (question.getQuestion().length() >= 100 && question.getQuestion().length() < 150)
						sheet.setColumnWidth(cell.getColumnIndex(), 11000);
					else
						sheet.setColumnWidth(cell.getColumnIndex(), 13000);

					sheet.setHorizontallyCenter(true);
					cell.setCellStyle(headerStyle);
					cell.setCellValue(question.getQuestion());
					index++;
				}

			}

			int rowIndex = 5;
			int colIndex = 1;

			for (int i = 0; i < submissionList.size(); i++) {

				row = sheet.createRow(rowIndex);

				cell = row.createCell(0);
				cell.setCellStyle(rowIndex % 2 == 0 ? colStyleEven : colStyleOdd);
				cell.setCellValue(rowIndex - 4);

				colIndex = 1;

				SubmissionData submissionData = submissionList.get(i);

				Class<?> clazz = submissionData.getClass();

				Area parent = areaRepository
						.findByAreaId(submissionData.getUser().getAreas().get(0).getArea().getParentAreaId());

				// user name
				cell = row.createCell(colIndex++);
				cell.setCellStyle(rowIndex % 2 == 0 ? colStyleEven : colStyleOdd);
				cell.setCellValue(submissionData.getUser().getUserName());

				// date
				cell = row.createCell(colIndex++);
				cell.setCellStyle(rowIndex % 2 == 0 ? colStyleEven : colStyleOdd);
				cell.setCellValue(dateTimeFormat.format(submissionData.getUpdatedDate()));

				// state
				cell = row.createCell(colIndex++);
				cell.setCellStyle(rowIndex % 2 == 0 ? colStyleEven : colStyleOdd);
				cell.setCellValue(parent.getAreaName());

				// district
				cell = row.createCell(colIndex++);
				cell.setCellStyle(rowIndex % 2 == 0 ? colStyleEven : colStyleOdd);
				cell.setCellValue(submissionData.getUser().getAreas().get(0).getArea().getAreaName());

				// month
				cell = row.createCell(colIndex++);
				cell.setCellStyle(rowIndex % 2 == 0 ? colStyleEven : colStyleOdd);
				cell.setCellValue(submissionData.getPeriodReference().getMonthRange());

				// year
				cell = row.createCell(colIndex++);
				cell.setCellStyle(rowIndex % 2 == 0 ? colStyleEven : colStyleOdd);
				cell.setCellValue(submissionData.getYear().getYearRange());

				// submission status
				cell = row.createCell(colIndex++);
				cell.setCellStyle(rowIndex % 2 == 0 ? colStyleEven : colStyleOdd);
				cell.setCellValue(submissionData.getIsSubmit() == true ? "Submitted" : "Saved");

				// file count
				cell = row.createCell(colIndex++);
				cell.setCellStyle(rowIndex % 2 == 0 ? colStyleEven : colStyleOdd);
				cell.setCellValue(submissionData.getFileCount());

				for (int j = 0; j < questionList.size(); j++) {

					try {

						String fieldValue = null;
						String checkedValue = null;
						Boolean isOthersSelected = false;
						String othersValue = null;

						Question question = questionList.get(j);

						if (!question.getColumnName().equals("a06") && !question.getColumnName().equals("q01")
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

										List<TypeDetail> listOfTypeDetails = typeDetailRepository
												.findByIdIn(typeDetailIds);

										List<String> typeDetailName = new ArrayList<>();

										for (TypeDetail typeDetail : listOfTypeDetails) {
											typeDetailName.add(typeDetail.getName());
										}

										fieldValue = String.join(",", typeDetailName);

										checkedValue = fieldValue;

										if (fieldValue.contains("Others")) {

											isOthersSelected = true;
											othersValue = submissionData.getOthersValue();

											if (othersValue != null)
												fieldValue = fieldValue.concat("(" + othersValue + ")");
										}

									}
								} else {
									fieldValue = field.get(submissionData) == null ? null
											: field.get(submissionData).toString();
								}
							}

							cell = row.createCell(colIndex);

							cell.setCellStyle(rowIndex % 2 == 0 ? colStyleEven : colStyleOdd);
							cell.setCellValue(fieldValue != null ? fieldValue : "");

							colIndex++;

						}

					} catch (Exception e) {
						e.printStackTrace();
						throw new RuntimeException();
					}

				}

				rowIndex++;

			}

			path = configurableEnvironment.getProperty("cms.filepath") + "BBBP-RawData"+".xls";

			if(new File(path).exists()){
				new File(path).delete();
				}
			
			FileOutputStream fos = new FileOutputStream(new File(path));
			workbook.write(fos);
			fos.close();
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();

		}
		return path;
		}
private HSSFSheet doMerge(int rowIndex, int columnIndex, int rowSpan, int columnSpan, HSSFSheet sheet) {
		
		Cell cell = sheet.getRow(rowIndex).getCell(rowSpan);
	    CellRangeAddress range = new CellRangeAddress(rowIndex, columnIndex,rowSpan,columnSpan);

	    sheet.addMergedRegion(range);

	    RegionUtil.setBorderBottom(cell.getCellStyle().getBorderBottom(), range, sheet, sheet.getWorkbook());
	    RegionUtil.setBorderTop(cell.getCellStyle().getBorderTop(), range, sheet, sheet.getWorkbook());
	    RegionUtil.setBorderLeft(cell.getCellStyle().getBorderLeft(), range, sheet, sheet.getWorkbook());
	    RegionUtil.setBorderRight(cell.getCellStyle().getBorderRight(), range, sheet, sheet.getWorkbook());

	    RegionUtil.setBottomBorderColor(cell.getCellStyle().getBottomBorderColor(), range, sheet, sheet.getWorkbook());
	    RegionUtil.setTopBorderColor(cell.getCellStyle().getTopBorderColor(), range, sheet, sheet.getWorkbook());
	    RegionUtil.setLeftBorderColor(cell.getCellStyle().getLeftBorderColor(), range, sheet, sheet.getWorkbook());
	    RegionUtil.setRightBorderColor(cell.getCellStyle().getRightBorderColor(), range, sheet, sheet.getWorkbook());
	    
	    return sheet;
		}
}
