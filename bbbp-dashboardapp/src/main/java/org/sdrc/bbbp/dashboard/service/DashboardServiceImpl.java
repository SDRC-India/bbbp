package org.sdrc.bbbp.dashboard.service;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sdrc.bbbp.dashboard.domain.IndicatorQuestionMapping;
import org.sdrc.bbbp.dashboard.domain.UtData;
import org.sdrc.bbbp.dashboard.domain.UtIndicatorClassificationsEn;
import org.sdrc.bbbp.dashboard.domain.UtIndicatorUnitSubgroup;
import org.sdrc.bbbp.dashboard.domain.UtTimeperiod;
import org.sdrc.bbbp.dashboard.models.CSRTrendChartModel;
import org.sdrc.bbbp.dashboard.models.ChartDataModel;
import org.sdrc.bbbp.dashboard.models.CsrAllAreaMapModel;
import org.sdrc.bbbp.dashboard.models.CsrAreaModel;
import org.sdrc.bbbp.dashboard.models.GroupIndicatorModel;
import org.sdrc.bbbp.dashboard.models.IndicatorGroupModel;
import org.sdrc.bbbp.dashboard.models.IndicatorModel;
import org.sdrc.bbbp.dashboard.models.LegendModel;
import org.sdrc.bbbp.dashboard.models.SectorModel;
import org.sdrc.bbbp.dashboard.models.SnapShortDataModel;
import org.sdrc.bbbp.dashboard.models.SubSectorModel;
import org.sdrc.bbbp.dashboard.models.ThematicViewModel;
import org.sdrc.bbbp.dashboard.models.ValueObject;
import org.sdrc.bbbp.dashboard.repository.IndicatorGroupRepository;
import org.sdrc.bbbp.dashboard.repository.IndicatorQuestionMappingRepository;
import org.sdrc.bbbp.dashboard.repository.UtDataReporsitory;
import org.sdrc.bbbp.dashboard.repository.UtIndicatorClassificationsEnRepository;
import org.sdrc.bbbp.dashboard.repository.UtTimeperiodRepository;
import org.sdrc.bbbp.dashboard.util.Constants;
import org.sdrc.bbbp.domain.Area;
import org.sdrc.bbbp.domain.AreaLevel;
import org.sdrc.bbbp.domain.Periodicity;
import org.sdrc.bbbp.domain.Year;
import org.sdrc.bbbp.models.AreaModel;
import org.sdrc.bbbp.models.UserModel;
import org.sdrc.bbbp.repository.AreaLevelepository;
import org.sdrc.bbbp.repository.AreaRepository;
import org.sdrc.bbbp.repository.SubmissionDataRepository;
import org.sdrc.bbbp.repository.UserAreaMappingRepository;
import org.sdrc.bbbp.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DashboardServiceImpl implements DashboardService {

	@Autowired
	private UtDataReporsitory utDataReporsitory;

//	@Autowired
//	private UserRepository userRepository;

	@Autowired
	private UtTimeperiodRepository utTimeperiodRepository;

//	@Autowired
//	private ServletContext context;

	@Autowired
	private AreaRepository areaRepository;
	
	@Autowired
	private IndicatorQuestionMappingRepository indicatorQuestionMappingRepository;

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private AreaLevelepository areaLevelepository;
	
	@Autowired
	private UtIndicatorClassificationsEnRepository utIndicatorClassificationsEnRepository;
	
	@Autowired
	private IndicatorGroupRepository indicatorGroupRepository;
	
	@Autowired
	private UserAreaMappingRepository userAreaMappingRepository;

	@Autowired
	private YearRepository yearRepository;
	
	@Autowired
	private SubmissionDataRepository submissionDataRepository;
	
	@Override
	public List<CsrAllAreaMapModel> csrMapView() {

		List<Object[]> listOfCountryData = utDataReporsitory
				.findAllByPeriodicityAndParentAreaId(Constants.Web.BIENNIAL_PERIODICITY_ID, 2);
		List<Area> allState = areaRepository.findByParentAreaId(1);
		List<UtTimeperiod> listOfTimePeriod = utTimeperiodRepository
				.findByPeriodicityPeriodicityId(Constants.Web.BIENNIAL_PERIODICITY_ID);
		
		List<String> colors = Arrays.asList("#F00", "#FFC200", "#3cb541", "#929da8");
		Map<String, List<CsrAreaModel>> csrAreaModelMap = new LinkedHashMap<>();
		Map<String, List<Integer>> sliceValueMap = new LinkedHashMap<>();
		List<CsrAreaModel> listOfCsrAreaModel = null;
		List<Integer> listOfValue = null;
		ValueObject valueObject = null;

		listOfTimePeriod.forEach(timeperiod -> csrAreaModelMap
				.put(timeperiod.getTimePeriod_NId() + "_" + timeperiod.getTimePeriod(), new ArrayList<CsrAreaModel>()));
		listOfTimePeriod.forEach(timeperiod -> sliceValueMap
				.put(timeperiod.getTimePeriod_NId() + "_" + timeperiod.getTimePeriod(), new ArrayList<Integer>()));

		for (Object[] data : listOfCountryData) {
			CsrAreaModel csrAreaModel = null;
			if (csrAreaModelMap.containsKey(String.valueOf((Integer) data[4]) + "_" + (String) data[5])) {

				csrAreaModel = new CsrAreaModel();
				listOfValue = new ArrayList<>();
				csrAreaModel.setAreaId((String) data[0]);
				csrAreaModel.setAreaNid((Integer) data[1]);
				csrAreaModel.setAreaName((String) data[2]);
//				csrAreaModel.setValue((Double) data[3]);
				csrAreaModel.setValue(data[3]==null?null:Double.valueOf(data[3].toString()));
				if (Double.valueOf(data[3].toString()) < 952 
						
						&& (Integer) data[1] != 1) {
//					listOfValue.add(((Double) data[3]).intValue());
					listOfValue.add((data[3]==null?null:Double.valueOf(data[3].toString())).intValue());
				}
				csrAreaModelMap.get(String.valueOf((Integer) data[4]) + "_" + (String) data[5]).add(csrAreaModel);
				sliceValueMap.get(String.valueOf((Integer) data[4]) + "_" + (String) data[5]).addAll(listOfValue);

			} else {
				csrAreaModel = new CsrAreaModel();
				listOfValue = new ArrayList<>();
				listOfCsrAreaModel = new ArrayList<>();
				csrAreaModel.setAreaId((String) data[0]);
				csrAreaModel.setAreaNid((Integer) data[1]);
				csrAreaModel.setAreaName((String) data[2]);
//				csrAreaModel.setValue((Double) data[3]);
				csrAreaModel.setValue(Double.valueOf(data[3].toString()));
				listOfCsrAreaModel.add(csrAreaModel);
				if (Double.valueOf(data[3].toString()) < 952 && (Integer) data[1] != 1) {
//					listOfValue.add(((Double) data[3]).intValue());
					listOfValue.add((data[3]==null?null:Double.valueOf(data[3].toString())).intValue());
				}
				csrAreaModelMap.put(String.valueOf((Integer) data[4]) + "_" + (String) data[5], listOfCsrAreaModel);
				sliceValueMap.put(String.valueOf((Integer) data[4]) + "_" + (String) data[5], listOfValue);
			}

		}

		List<CsrAllAreaMapModel> listOfCsrAllAreaMapModel = new ArrayList<>();
		List<ValueObject> listOfValueObject = null;
		for (Entry<String, List<CsrAreaModel>> entry : csrAreaModelMap.entrySet()) {
			CsrAllAreaMapModel csrAllAreaMapModel = new CsrAllAreaMapModel();
			csrAllAreaMapModel.setTimeperiodId(Integer.parseInt(entry.getKey().split("_")[0]));
			csrAllAreaMapModel.setTimeperiod(entry.getKey().split("_")[1]);
			List<CsrAreaModel> csrStateModel = new ArrayList<>();
			int firstslice = 0, secondslice = 0, thirdslice = 0; // fourthslice = 0;

			List<Integer> valueList = sliceValueMap.get(entry.getKey());

			if (!valueList.isEmpty()) {
				Integer mean = Math.round((Collections.min(valueList) + Collections.max(valueList)) / 2);

				for (CsrAreaModel csrAllArea : entry.getValue()) {
					if (csrAllArea.getAreaNid() == 1) {
						csrAllAreaMapModel.setIndiaData(csrAllArea);
					} else {
						if (csrAllArea.getValue() != null) {
							if (csrAllArea.getValue() >= 952 && csrAllArea.getValue() <= 1000) {
								csrAllArea.setCssClass(Constants.Web.FIRST_SLICE);
								csrAllArea.setColorCode(colors.get(2));
								firstslice++;
							} else if (csrAllArea.getValue() >= mean && csrAllArea.getValue() < 952) {
								csrAllArea.setCssClass(Constants.Web.SECOND_SLICE);
								csrAllArea.setColorCode(colors.get(1));
								secondslice++;
							} else if (csrAllArea.getValue() >= Collections.min(valueList)
									&& csrAllArea.getValue() < mean) {
								csrAllArea.setCssClass(Constants.Web.THIRD_SLICE);
								csrAllArea.setColorCode(colors.get(0));
								thirdslice++;
							}
						}
						csrStateModel.add(csrAllArea);
					}
				}			//Arrays.asList("#F00", "#FFC200", "#3cb541", "#929da8");
				listOfValueObject = new ArrayList<>();
//				valueObject = new ValueObject(Constants.Web.FIRST_SLICE, "952-1000", Constants.Web.FIRST_SLICE_COLOR,
//						firstslice);
				valueObject = new ValueObject(Constants.Web.THIRD_SLICE, "952-1000", Constants.Web.THIRD_SLICE_COLOR,
						firstslice);
				listOfValueObject.add(valueObject);
				
				valueObject = new ValueObject(Constants.Web.SECOND_SLICE, String.valueOf(mean) + "-" + "951",
						Constants.Web.SECOND_SLICE_COLOR, secondslice);
				listOfValueObject.add(valueObject);
				
//				valueObject = new ValueObject(Constants.Web.THIRD_SLICE,
//						String.valueOf(Collections.min(valueList)) + "-"
//								+ String.valueOf(mean),
//						Constants.Web.THIRD_SLICE_COLOR, thirdslice);
				valueObject = new ValueObject(Constants.Web.FIRST_SLICE,
						String.valueOf(Collections.min(valueList)) + "-"
								+ String.valueOf(mean),
						Constants.Web.FIRST_SLICE_COLOR, thirdslice);
				
				listOfValueObject.add(valueObject);
				valueObject = new ValueObject(Constants.Web.FOURTH_SLICE, Constants.Web.FOURTH_SLICE_RANGE,
						Constants.Web.FOURTH_SLICE_COLOR, allState.size() - (firstslice + secondslice + thirdslice));
				
				listOfValueObject.add(valueObject);
				csrAllAreaMapModel.setStateData(csrStateModel);
				csrAllAreaMapModel.setLigends(listOfValueObject);
			}
			listOfCsrAllAreaMapModel.add(csrAllAreaMapModel);
		}

		return listOfCsrAllAreaMapModel;
	}

	@Override
	public List<CSRTrendChartModel> csrTrendChartModel(int areaId, int indicatorId) {

		List<CSRTrendChartModel> listOfCSRTrendChartModel = new ArrayList<>();
		Area area=areaRepository.findByAreaId(areaId);
		IndicatorQuestionMapping indicator = indicatorQuestionMappingRepository.findByIndicatorNId(indicatorId);
		List<Object[]> listOfData =null;
		if (indicatorId == 45) {
			listOfData = utDataReporsitory.findByAreaIdAndIndicatorNid(areaId, indicatorId,4); //periodicity is 4
			Collections.reverse(listOfData);
			for (Object[] objects : listOfData) {
				CSRTrendChartModel cSRTrendChartModel = setcSRTrendChartModel((int) objects[0],(String) objects[1],objects[2]==null?null:Double.valueOf(objects[2].toString()),area.getAreaName(),indicator.getIndicator_Name());
				listOfCSRTrendChartModel.add(cSRTrendChartModel);
			}
		}else{
			listOfData = utDataReporsitory.findByAreaIdAndIndicatorNidAndPeriodicity(areaId, indicatorId, 1);
			Collections.reverse(listOfData);
			for (Object[] objects : listOfData) {
				CSRTrendChartModel cSRTrendChartModel = setcSRTrendChartModel((int) objects[0],(String) objects[1],objects[2]==null?null:Double.valueOf(objects[2].toString()),area.getAreaName(),indicator.getIndicator_Name());
			
				listOfCSRTrendChartModel.add(cSRTrendChartModel);
			}
		}

		return listOfCSRTrendChartModel;
	}

	private CSRTrendChartModel setcSRTrendChartModel(int timePeriodId, String timePeriod, Double dataValue, String areaName, String indicatorName) {
		CSRTrendChartModel cSRTrendChartModel = new CSRTrendChartModel();
		cSRTrendChartModel.setTimeNid(timePeriodId);
		cSRTrendChartModel.setDate(timePeriod);
		cSRTrendChartModel.setValue(dataValue==null?null:(Double) dataValue);
		cSRTrendChartModel.setName(areaName);
		cSRTrendChartModel.setKey(indicatorName);
		return cSRTrendChartModel;
	}


//	public String uploadUtdata() throws Exception {
//
//		FileInputStream inputStream = new FileInputStream(
//				new File(context.getRealPath("/templates/SCR_TEMPLATE.xlsx")));

	/* (non-Javadoc)
	 * @see org.sdrc.bbbp.dashboard.service.DashboardService#uploadUtdata(org.springframework.web.multipart.MultipartFile)
	 * @author Sarita Panigrahi (sarita@sdrc.co.in)
	 * Created On: 30-Aug-2019 2:59:01 PM
	 * this method will update all csr data. if the time period does not exist it will create one
	 */
	@Override
	@Transactional
	public String uploadUtdata(MultipartFile uploadCSRFile) throws Exception {
		
		Workbook workbook = new XSSFWorkbook(uploadCSRFile.getInputStream());
		
		try {
			Sheet firstSheet = workbook.getSheetAt(0);
			List<UtData> listOfUtData = new ArrayList<>();
			for (int i = 3; i <= firstSheet.getLastRowNum(); i++) { // row
				for (int j = 2; j < firstSheet.getRow(i).getLastCellNum(); j++) {// column
					if (firstSheet.getRow(i).getCell(j).getCellType() != Cell.CELL_TYPE_BLANK) {
						UtData utData = new UtData();
						utData.setArea_NId(new Area((int) firstSheet.getRow(i).getCell(1).getNumericCellValue()));
						utData.setData_Value(String.valueOf(firstSheet.getRow(i).getCell(j).getNumericCellValue()));
						utData.setIC_IUS_Order(0);
						utData.setIndicator_NId(45);
						utData.setIUSNId(new UtIndicatorUnitSubgroup(1));
						utData.setSource_NId(new UtIndicatorClassificationsEn(1));

						UtTimeperiod utTimeperiod = utTimeperiodRepository.findByTimePeriodAndPeriodicityPeriodicityId(
								firstSheet.getRow(2).getCell(j).getStringCellValue(), 4); //4 periodicity
						if(utTimeperiod == null) {
							
							UtTimeperiod newTimeperiod = new UtTimeperiod();

							Calendar cal = Calendar.getInstance();
							cal.set(Calendar.YEAR,Integer.parseInt( firstSheet.getRow(2).getCell(j).getStringCellValue().split("-")[0]));
							cal.set(Calendar.MONTH, 0); // jan
							cal.set(Calendar.DAY_OF_MONTH, 1); // new year
							
							newTimeperiod.setStartDate(cal.getTime());

							cal.set(Calendar.YEAR, Integer.parseInt( firstSheet.getRow(2).getCell(j).getStringCellValue().split("-")[1]));
							cal.set(Calendar.MONTH, 11); // 11 = December
							cal.set(Calendar.DAY_OF_MONTH, 31); // new years eve
							newTimeperiod.setEndDate(cal.getTime());
							newTimeperiod.setPeriodicity(new Periodicity(4));
							newTimeperiod.setTimePeriod( firstSheet.getRow(2).getCell(j).getStringCellValue());
							
							UtTimeperiod savedTimeperiod =  utTimeperiodRepository.save(newTimeperiod);
							utData.setTimePeriod_NId(savedTimeperiod);
						}else {
							utData.setTimePeriod_NId(utTimeperiodRepository.findByTimePeriodAndPeriodicityPeriodicityId(
									firstSheet.getRow(2).getCell(j).getStringCellValue(), 4));
						}
						
						
						listOfUtData.add(utData);
					}
				}
			}
			utDataReporsitory.deleteByIndicator_NId(45);
			utDataReporsitory.save(listOfUtData);
		}catch(Exception e) {
			throw new RuntimeException("Invalid format");
		}finally {
			workbook.close();
		}
		
		return "CSR data updated successfully";
	}

	DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	@Override
	public List<SectorModel> getAllSectorsSubsctorsIndicators() {
		Map<String, Map<String, List<String>>> sectorSubsectorIndectorMap = new LinkedHashMap<>();
		Map<String, List<String>> subSectorIndicatorMap = null;
		List<String> listOfIndicators = null;
		List<Object[]> listOfData = utDataReporsitory.findAllIndicators(
				Integer.parseInt(messageSource.getMessage(Constants.Web.PERCENT_UNIT_NID, null, null))); //get only percent units
		for (Object[] data : listOfData) {
			if (sectorSubsectorIndectorMap.containsKey(data[0] + "#" + data[1])) {
				subSectorIndicatorMap = sectorSubsectorIndectorMap.get(data[0] + "#" + data[1]);
				if (sectorSubsectorIndectorMap.get(data[0] + "#" + data[1]).containsKey(data[2] + "#" + data[3])) {
//					listOfIndicators = subSectorIndicatorMap.get(data[4] + "#" + data[5] + "#" + data[6]);
					subSectorIndicatorMap.get(data[2] + "#" + data[3]).add(data[4] + "#" + data[5] + "#" + data[6]);
				} else {
					listOfIndicators = new ArrayList<>();
					listOfIndicators.add(data[4] + "#" + data[5] + "#" + data[6]);
					subSectorIndicatorMap.put(data[2] + "#" + data[3], listOfIndicators);
				}
			} else {
				subSectorIndicatorMap = new LinkedHashMap<String, List<String>>();
				listOfIndicators = new ArrayList<>();
				if (subSectorIndicatorMap.containsKey(data[2] + "#" + data[3])) {
					subSectorIndicatorMap.get(data[2] + "#" + data[3]).add(data[4] + "#" + data[5] + "#" + data[6]);
				} else {
					listOfIndicators.add(data[4] + "#" + data[5] + "#" + data[6]);
					subSectorIndicatorMap.put(data[2] + "#" + data[3], listOfIndicators);
				}
				sectorSubsectorIndectorMap.put(data[0] + "#" + data[1], subSectorIndicatorMap);
			}
		}
		List<SectorModel> listOfSectors = new ArrayList<>();
		SectorModel sectorModelCSR = new SectorModel();
		sectorModelCSR.setSectorName("Child Sex Ratio");
		listOfSectors.add(sectorModelCSR);

		for (Entry<String, Map<String, List<String>>> entry : sectorSubsectorIndectorMap.entrySet()) {
			SectorModel sectorModel = new SectorModel();
			sectorModel.setSectorId(Integer.parseInt(entry.getKey().split("#")[0]));
			sectorModel.setSectorName(entry.getKey().split("#")[1]);
			List<SubSectorModel> listOfSubsector = new ArrayList<>();
			for (Entry<String, List<String>> entry2 : entry.getValue().entrySet()) {
				SubSectorModel subSectorModel = new SubSectorModel();
				subSectorModel.setSubSectorId(Integer.parseInt(entry2.getKey().split("#")[0]));
				subSectorModel.setSubSectorName(entry2.getKey().split("#")[1]);
				List<IndicatorModel> listOfIndicator = new ArrayList<>();
				for (String indicators : entry2.getValue()) {
					IndicatorModel indicatorModel = new IndicatorModel();
					indicatorModel.setIndicatorId(Integer.parseInt(indicators.split("#")[0]));
					indicatorModel.setIndicatorName(indicators.split("#")[1]);
					indicatorModel.setUnitName(indicators.split("#")[2]);
					listOfIndicator.add(indicatorModel);
				}
				listOfSubsector.add(subSectorModel);
				subSectorModel.setIndicators(listOfIndicator);
			}
			sectorModel.setSubSectors(listOfSubsector);
			listOfSectors.add(sectorModel);
		}
		return listOfSectors;
	}

	@Override
	public List<CsrAllAreaMapModel> thimaticView(Integer indicatorNid) {
		
		List<Object[]> listOfSubmissionData = submissionDataRepository.getDataByGroup(indicatorNid);
		Map<String, Map<Integer, Integer>> mapOfSubmissionData = new HashMap<>();
		Map<Integer, Integer> sdData = null;
		for (Object[] object : listOfSubmissionData) {
			if(!mapOfSubmissionData.containsKey((Integer)object[2]+"_"+(String)object[3])) {
				sdData = new HashMap<>();
				sdData.put((Integer) object[0], (Integer)((BigInteger)(object[1])).intValue());
				mapOfSubmissionData.put((Integer)object[2]+"_"+(String)object[3], sdData);
			}else {
				sdData = mapOfSubmissionData.get((Integer)object[2]+"_"+(String)object[3]);
				sdData.put((Integer) object[0], (Integer)((BigInteger)(object[1])).intValue());
			}
		}
		/**
		 * Only National and State Level Data is required. So we sending areaLevelId 1 and 2
		 */
		List<Integer> areaLevels =  Arrays.asList(1,2);
		List<Object[]> listOfData = utDataReporsitory.findAllByIndicatorNid(indicatorNid, areaLevels);
		List<Area> allState = areaRepository.findByParentAreaId(1);
		Map<String, List<CsrAreaModel>> csrAreaModelMap = new LinkedHashMap<>();
		Map<String, List<Double>> sliceValueMap = new LinkedHashMap<>();
		List<CsrAreaModel> listOfCsrAreaModel = null;
		List<Double> listOfValue = null;
		ValueObject valueObject = null;

		List<UtTimeperiod> listOfTimePeriod = utTimeperiodRepository.findByPeriodicityPeriodicityIdYearDESC(1);
		listOfTimePeriod.forEach(timeperiod -> csrAreaModelMap
				.put(timeperiod.getTimePeriod_NId() + "_" + timeperiod.getTimePeriod(), new ArrayList<CsrAreaModel>()));
		listOfTimePeriod.forEach(timeperiod -> sliceValueMap
				.put(timeperiod.getTimePeriod_NId() + "_" + timeperiod.getTimePeriod(), new ArrayList<Double>()));

		Integer checkCount = 0;
		boolean flag = true;
		for (Object[] data : listOfData) {
			if (flag) {
				checkCount = (Integer) data[6];
				flag = false;
			}
			CsrAreaModel csrAreaModel = null;
			if (csrAreaModelMap.containsKey(String.valueOf((Integer) data[4]) + "_" + (String) data[5])) {

				csrAreaModel = new CsrAreaModel();
				listOfValue = new ArrayList<>();
				csrAreaModel.setAreaId((String) data[0]);
				csrAreaModel.setAreaNid((Integer) data[1]);
				csrAreaModel.setAreaName((String) data[2]);
//				csrAreaModel.setValue((Double) data[3]);
				csrAreaModel.setValue(data[3]==null?null:Double.valueOf(data[3].toString()));
				if ((Integer) data[1] != 1) {
					listOfValue.add(data[3]==null?null:Double.valueOf(data[3].toString()));
				}
				csrAreaModel.setCount(mapOfSubmissionData.containsKey(String.valueOf((Integer) data[4]) + "_" + (String) data[5]) ?
						mapOfSubmissionData.get(String.valueOf((Integer) data[4]) + "_" + (String) data[5]).containsKey((Integer) data[1]) ?
						mapOfSubmissionData.get(String.valueOf((Integer) data[4]) + "_" + (String) data[5]).get((Integer) data[1]): null : null);
				csrAreaModelMap.get(String.valueOf((Integer) data[4]) + "_" + (String) data[5]).add(csrAreaModel);
				sliceValueMap.get(String.valueOf((Integer) data[4]) + "_" + (String) data[5]).addAll(listOfValue);

			} else {
				csrAreaModel = new CsrAreaModel();
				listOfValue = new ArrayList<>();
				listOfCsrAreaModel = new ArrayList<>();
				csrAreaModel.setAreaId((String) data[0]);
				csrAreaModel.setAreaNid((Integer) data[1]);
				csrAreaModel.setAreaName((String) data[2]);
//				csrAreaModel.setValue((Double) data[3]);
				csrAreaModel.setValue(data[3]==null?null:Double.valueOf(data[3].toString()));
				listOfCsrAreaModel.add(csrAreaModel);
				if ((Integer) data[1] != 1) {
//					listOfValue.add((Double) data[3]);
					listOfValue.add(data[3]==null?null:Double.valueOf(data[3].toString()));
				}
				csrAreaModel.setCount(mapOfSubmissionData.containsKey(String.valueOf((Integer) data[4]) + "_" + (String) data[5]) ?
						mapOfSubmissionData.get(String.valueOf((Integer) data[4]) + "_" + (String) data[5]).containsKey((Integer) data[1]) ?
						mapOfSubmissionData.get(String.valueOf((Integer) data[4]) + "_" + (String) data[5]).get((Integer) data[1]): null : null);
				csrAreaModelMap.put(String.valueOf((Integer) data[4]) + "_" + (String) data[5], listOfCsrAreaModel);
				sliceValueMap.put(String.valueOf((Integer) data[4]) + "_" + (String) data[5], listOfValue);
			}

		}
		List<String> colors = Arrays.asList("#F00", "#FFC200", "#3cb541", "#929da8");
		List<CsrAllAreaMapModel> listOfCsrAllAreaMapModel = new ArrayList<>();
		List<ValueObject> listOfValueObject = null;
		for (Entry<String, List<CsrAreaModel>> entry : csrAreaModelMap.entrySet()) {
			if (checkCount != 2) {
				CsrAllAreaMapModel csrAllAreaMapModel = new CsrAllAreaMapModel();
				csrAllAreaMapModel.setTimeperiodId(Integer.parseInt(entry.getKey().split("_")[0]));
				csrAllAreaMapModel.setTimeperiod(entry.getKey().split("_")[1]);
				List<CsrAreaModel> csrStateModel = new ArrayList<>();
				int firstslice = 0, secondslice = 0, thirdslice = 0, fourthslice = 0;
				
				List<Double> valueList = sliceValueMap.get(entry.getKey());
				valueList.removeAll(Collections.singleton(null));
				
				if (!valueList.isEmpty()) {
//					Double minValue = Collections.min(valueList);
//					Double maxValue = Collections.max(valueList);
//					Double differnceValue = (double) (Math
//							.round((double) ((Collections.max(valueList) - Collections.min(valueList)) / 3)));

					for (CsrAreaModel csrAllArea : entry.getValue()) {
						if (csrAllArea.getAreaNid() == 1) {
							csrAllAreaMapModel.setIndiaData(csrAllArea);
						} else {
							if (csrAllArea.getValue() != null) {

								if ((csrAllArea.getValue() / 100) * 100 >= 0.0
										&& (csrAllArea.getValue() / 100) * 100 < 60.0) {
									csrAllArea
											.setCssClass(Constants.Web.FIRST_SLICE);
									csrAllArea.setColorCode(colors.get(0));
									firstslice++;
								} else if ((csrAllArea.getValue() / 100) * 100 >= 60.0
										&& (csrAllArea.getValue() / 100) * 100 < 80.0) {
									csrAllArea
											.setCssClass(Constants.Web.SECOND_SLICE);
									csrAllArea.setColorCode(colors.get(1));
									secondslice++;
								} else if ((csrAllArea.getValue() / 100) * 100 >= 80.0) {
//										&& (csrAllArea.getValue() / 100) * 100 <= 100.0) {
									csrAllArea
											.setCssClass(Constants.Web.THIRD_SLICE);
									csrAllArea.setColorCode(colors.get(2));
									thirdslice++;
								}
							} else {
								csrAllArea.setCssClass(Constants.Web.FOURTH_SLICE);
								csrAllArea.setColorCode(colors.get(3));
//								fourthslice++;
							}
							csrStateModel.add(csrAllArea);
						}
					}
					fourthslice = allState.size()-(firstslice+secondslice+thirdslice);
					listOfValueObject = new ArrayList<>();
					valueObject = new ValueObject(Constants.Web.FIRST_SLICE, Constants.Web.FIRST_SLICE_RANGE,Constants.Web.FIRST_SLICE_COLOR,firstslice);
					listOfValueObject.add(valueObject);	
					valueObject = new ValueObject(Constants.Web.SECOND_SLICE, Constants.Web.SECOND_SLICE_RANGE,Constants.Web.SECOND_SLICE_COLOR,secondslice);
					listOfValueObject.add(valueObject);
					valueObject = new ValueObject(Constants.Web.THIRD_SLICE, Constants.Web.THIRD_SLICE_RANGE,Constants.Web.THIRD_SLICE_COLOR,thirdslice);
					listOfValueObject.add(valueObject);
					valueObject = new ValueObject(Constants.Web.FOURTH_SLICE, Constants.Web.FOURTH_SLICE_RANGE,Constants.Web.FOURTH_SLICE_COLOR,fourthslice);
					listOfValueObject.add(valueObject);
					csrAllAreaMapModel.setStateData(csrStateModel);
					csrAllAreaMapModel.setLigends(listOfValueObject);
				}
				listOfCsrAllAreaMapModel.add(csrAllAreaMapModel);
			} else {
				CsrAllAreaMapModel csrAllAreaMapModel = new CsrAllAreaMapModel();
				csrAllAreaMapModel.setTimeperiodId(Integer.parseInt(entry.getKey().split("_")[0]));
				csrAllAreaMapModel.setTimeperiod(entry.getKey().split("_")[1]);
				List<CsrAreaModel> csrStateModel = new ArrayList<>();
				for (CsrAreaModel csrAllArea : entry.getValue()) {
					if (csrAllArea.getAreaNid() == 1) {
						csrAllAreaMapModel.setIndiaData(csrAllArea);
					} else {
						csrStateModel.add(csrAllArea);
					}
				}
				csrAllAreaMapModel.setStateData(csrStateModel);
				csrAllAreaMapModel.setLigends(listOfValueObject);
				listOfCsrAllAreaMapModel.add(csrAllAreaMapModel);
			}
		}

		return listOfCsrAllAreaMapModel;
	}
	
	@Override
	public List<CsrAreaModel> getAllStateData() {
		List<Area> areas = areaRepository.findByParentAreaIdOrderByAreaName(1);
		List<CsrAreaModel> csrAreaModels=new ArrayList<>();
		for (Area area : areas) {
			CsrAreaModel csrAreaModel = new CsrAreaModel();
			
			csrAreaModel.setAreaId(area.getAreaCode());
			csrAreaModel.setAreaNid(area.getAreaId());
			csrAreaModel.setAreaName(area.getAreaName());
			csrAreaModels.add(csrAreaModel);
		}
		return csrAreaModels;
	}
	
	/**
	 * This method will give all the area levels.
	 * 
	 * @author subrata
	 */
	@Override
	public List<ValueObject> getAllAreaLevels() {
		List<AreaLevel> areaLevels = areaLevelepository.findAll();
		
		List<ValueObject> listOfAreaLevels = new ArrayList<ValueObject>();
		areaLevels.forEach(v->{
			ValueObject valueObject = new ValueObject();
			valueObject.setId(v.getAreaLevelId());
			valueObject.setKeyValue(StringUtils.capitalize(v.getAreaLevelName().toLowerCase()));
			listOfAreaLevels.add(valueObject);
		});
		return listOfAreaLevels;
	}
	
	@Override
	public Map<String, List<AreaModel>> getAllArea() {
		List<Area> areas = areaRepository.findByLevelAreaLevelIdInOrderByAreaIdAsc(Arrays.asList(2, 3));
		
		List<AreaModel> areaModelList = new ArrayList<>();
		Map<String, List<AreaModel>> areaMap = new LinkedHashMap<>();

		// setting areas is area-model list
		for (Area area : areas) {

			AreaModel areaModel = new AreaModel();

			areaModel.setAreaCode(area.getAreaCode());
			areaModel.setAreaId(area.getAreaId());
			areaModel.setAreaLevel(area.getLevel().getAreaLevelName());
			areaModel.setAreaLevelId(area.getLevel().getAreaLevelId());
			areaModel.setAreaName(area.getAreaName());
			areaModel.setLive(area.isLive());
			areaModel.setParentAreaId(area.getParentAreaId());
			areaModelList.add(areaModel);
		}

		// making levelName as a key
		for (AreaModel areaModel : areaModelList) {

			if (areaMap.containsKey(areaModel.getAreaLevel())) {
				areaMap.get(areaModel.getAreaLevel()).add(areaModel);
			} else {
				areaModelList = new ArrayList<>();
				areaModelList.add(areaModel);
				areaMap.put(areaModel.getAreaLevel(), areaModelList);
			}
		}

		return areaMap;
	}

	@Override
	public SnapShortDataModel getSnapChatData(Integer areaLevelId, Integer areaId) {
		
		List<UtTimeperiod> utTimeperiodList = utTimeperiodRepository.findByPeriodicityPeriodicityIdYearDESCLimit(1);
		
		Integer latestTimePeriod = utTimeperiodList.get(0).getTimePeriod_NId();
		String latestTP = utTimeperiodList.get(0).getTimePeriod();
		
		Collections.reverse(utTimeperiodList);
		Map<Integer, String> utTimeperiods = new LinkedHashMap<>();
		for (UtTimeperiod utTimeperiod : utTimeperiodList) {
			utTimeperiods.put(utTimeperiod.getTimePeriod_NId(), utTimeperiod.getShortName());
		}
		List<String> listOfColorCode = Arrays.asList("#234a7c", "#006ece", "#6ab7f9", "#428ead");
		List<GroupIndicatorModel> groupIndicatorModels = indicatorGroupRepository.findAll().get(0).getGroupIndicatorsJson();
		
//		Map<Integer, String> map = indicatorQuestionMappingRepository.findAll()
//				.stream().collect(Collectors.toMap(IndicatorQuestionMapping::getIndicator_NId, IndicatorQuestionMapping::getIndicator_Name));
		
		Map<Integer, IndicatorQuestionMapping> map = indicatorQuestionMappingRepository.findAll()
				.stream().collect(Collectors.toMap(IndicatorQuestionMapping::getIndicator_NId, v->v));

		Map<Integer, String> utDataMap = new HashMap<>();
		
		List<UtData> utDataTrend = utDataReporsitory.getDataValue(new ArrayList<Integer>(utTimeperiods.keySet()), areaId);
		
		Map<Integer, Map<Integer, String>> trendData = new HashMap<>();
		Map<Integer, String> utdata  = null;
		for (UtData data : utDataTrend) {
			if(!trendData.containsKey(data.getIndicator_NId())) {
				utdata  = new HashMap<>();
				if(!utdata.containsKey(data.getTimePeriod_NId().getTimePeriod_NId())) {
					utdata.put(data.getTimePeriod_NId().getTimePeriod_NId(), data.getData_Value());
					trendData.put(data.getIndicator_NId(), utdata);
				}
			} else {
				utdata = trendData.get(data.getIndicator_NId());
				if(!utdata.containsKey(data.getTimePeriod_NId().getTimePeriod_NId())) {
					utdata.put(data.getTimePeriod_NId().getTimePeriod_NId(), data.getData_Value());
				}
			}
			
			if(data.getTimePeriod_NId().getTimePeriod_NId()==latestTimePeriod.intValue()) {
				utDataMap.put(data.getIndicator_NId(), data.getData_Value());
			}
		}	
		
/*		Map<String, Map<String, List<String>>> sectorSubsectorIndectorMap = new LinkedHashMap<>();
		Map<String, List<String>> subSectorIndicatorMap = null;
		List<String> listOfIndicators = null;
		List<Object[]> listOfData = utDataReporsitory.findAllIndicators(
				Integer.parseInt(messageSource.getMessage(Constants.Web.PERCENT_UNIT_NID, null, null))); //get only percent units
		for (Object[] data : listOfData) {
			if (sectorSubsectorIndectorMap.containsKey(data[0] + "#" + data[1])) {
				subSectorIndicatorMap = sectorSubsectorIndectorMap.get(data[0] + "#" + data[1]);
				if (sectorSubsectorIndectorMap.get(data[0] + "#" + data[1]).containsKey(data[2] + "#" + data[3])) {
//					listOfIndicators = subSectorIndicatorMap.get(data[4] + "#" + data[5] + "#" + data[6]);
					subSectorIndicatorMap.get(data[2] + "#" + data[3]).add(data[4] + "#" + data[5] + "#" + data[6]);
				} else {
					listOfIndicators = new ArrayList<>();
					listOfIndicators.add(data[4] + "#" + data[5] + "#" + data[6]);
					subSectorIndicatorMap.put(data[2] + "#" + data[3], listOfIndicators);
				}
			} else {
				subSectorIndicatorMap = new LinkedHashMap<String, List<String>>();
				listOfIndicators = new ArrayList<>();
				if (subSectorIndicatorMap.containsKey(data[2] + "#" + data[3])) {
					subSectorIndicatorMap.get(data[2] + "#" + data[3]).add(data[4] + "#" + data[5] + "#" + data[6]);
				} else {
					listOfIndicators.add(data[4] + "#" + data[5] + "#" + data[6]);
					subSectorIndicatorMap.put(data[2] + "#" + data[3], listOfIndicators);
				}
				sectorSubsectorIndectorMap.put(data[0] + "#" + data[1], subSectorIndicatorMap);
			}
		}
		List<SectorModel> listOfSectors = new ArrayList<>();

		for (Entry<String, Map<String, List<String>>> entry : sectorSubsectorIndectorMap.entrySet()) {
			SectorModel sectorModel = new SectorModel();
			sectorModel.setSectorId(Integer.parseInt(entry.getKey().split("#")[0]));
			sectorModel.setSectorName(entry.getKey().split("#")[1]);
			List<SubSectorModel> listOfSubsector = new ArrayList<>();
			for (Entry<String, List<String>> entry2 : entry.getValue().entrySet()) {
				SubSectorModel subSectorModel = new SubSectorModel();
				subSectorModel.setSubSectorId(Integer.parseInt(entry2.getKey().split("#")[0]));
				subSectorModel.setSubSectorName(entry2.getKey().split("#")[1]);
				
				List<ValueObject> listOfKPIIndicator = new ArrayList<>();
				List<IndicatorGroupModel> indicatorGroupModels = new ArrayList<IndicatorGroupModel>();
		
				groupIndicatorModels.stream()
					.filter(v->v.getSubSector()==(Integer.parseInt(entry2.getKey().split("#")[0])))*/
		
		Map<String, Map<Integer, String>> map1 = new HashMap<>();
		List<Object[]> vClassificationsEns = utIndicatorClassificationsEnRepository.getIndicatorClassfication();
		vClassificationsEns.stream().forEach(obj->{
			if(!map1.containsKey(obj[0]+"@"+obj[1])) {
				Map<Integer, String> map2 = new HashMap<>();
				map2.put((Integer)obj[2], obj[3].toString());
				map1.put(obj[0]+"@"+obj[1], map2);
			} else {
				Map<Integer, String> map2 = map1.get(obj[0]+"@"+obj[1]);
				map2.put((Integer)obj[2], obj[3].toString());
			}
		});
		List<SectorModel> listOfSectors = new ArrayList<>();
		
		for(Entry<String, Map<Integer, String>> entry : map1.entrySet())	{
			SectorModel sectorModel = new SectorModel();
			sectorModel.setSectorId(Integer.parseInt(entry.getKey().split("@")[0]));
			sectorModel.setSectorName(entry.getKey().split("@")[1]);
			List<SubSectorModel> listOfSubsector = new ArrayList<>();
			
			for (Entry<Integer, String> entry2 : map1.get(entry.getKey()).entrySet()) {
				SubSectorModel subSectorModel = new SubSectorModel();
				subSectorModel.setSubSectorId(entry2.getKey());
				subSectorModel.setSubSectorName(entry2.getValue());
				
				List<ValueObject> listOfKPIIndicator = new ArrayList<>();
				List<IndicatorGroupModel> indicatorGroupModels = new ArrayList<IndicatorGroupModel>();

				groupIndicatorModels.stream()
						.filter(v->v.getSubSector()==entry2.getKey())
						.filter(v->v.getAreaLevels().contains(areaLevelId))
						.forEach(v->{
							List<List<ChartDataModel>> chartData = new ArrayList<>();
							if(v.getChartType().contains("box")) {
								v.getIndicatorNids().stream().forEach(value->{
									ValueObject valueObject = new ValueObject();
									valueObject.setId(value);
									if(areaLevelId==3) {
										valueObject.setKeyValue(utDataMap.get(value)==null ? null : utDataMap.get(value));
									}else {
										valueObject.setCount(utDataMap.get(value)==null ? null : Integer.valueOf(utDataMap.get(value)).intValue());
									}
									valueObject.setKey(map.get(value).getIndicator_Name());
									valueObject.setShortNmae(map.get(value).getShort_Name());
									valueObject.setDescription(v.getIndicatorGroup()+"@"+value);
									
									listOfKPIIndicator.add(valueObject);
								});
							} else if(v.getChartType().contains("bar") || v.getChartType().contains("pie")) {
								IndicatorGroupModel indicatorGroupModel = new IndicatorGroupModel();
								indicatorGroupModel.setGroupedIndName(v.getIndicatorGroup());
								indicatorGroupModel.setChartsAvailable(v.getChartType());
								List<ChartDataModel> listOfChartType = new ArrayList<>();
								
								List<LegendModel> listOfLegends = new ArrayList<>();
								
								v.getIndicatorNids().stream().forEach(value->{
									ChartDataModel chartDataModel = new ChartDataModel();
									LegendModel legendModel = new LegendModel();
//										chartDataModel.setId(value);
									chartDataModel.setAxis(map.get(value).getShort_Name());
									chartDataModel.setValue(utDataMap.get(value)==null ? null : Double.valueOf(utDataMap.get(value)));
									chartDataModel.setKey(v.getIndicatorGroup());
									chartDataModel.setColorCode(listOfColorCode.get(listOfChartType.size()));
									chartDataModel.setIndicatorName(map.get(value).getIndicator_Name());
									legendModel.setColorCode(listOfColorCode.get(listOfChartType.size()));
									legendModel.setValue(map.get(value).getShort_Name());
									
									listOfChartType.add(chartDataModel);
									
									listOfLegends.add(legendModel);
								});
								chartData.add(listOfChartType);
								
								indicatorGroupModel.setChartData(chartData);
								indicatorGroupModel.setLegends(listOfLegends);
								indicatorGroupModel.setIndicatorGroup(v.getIndicatorGroup());
								
								indicatorGroupModels.add(indicatorGroupModel);
							} else if(v.getChartType().contains("trend")) {
								
								v.getIndicatorNids().stream().forEach(value->{
									IndicatorGroupModel indicatorGroupModel = new IndicatorGroupModel();
									indicatorGroupModel.setChartsAvailable(v.getChartType());
									List<List<ChartDataModel>>  chartDatas = new ArrayList<>();
									List<ChartDataModel> listOfChartType = new ArrayList<>();
									Map<Integer, String> mapTrendData = trendData.get(value);
									
									for(Integer timeIdValue : utTimeperiods.keySet()){
										
										ChartDataModel chartDataModel = new ChartDataModel();
//											chartDataModel.setId(value);
										chartDataModel.setAxis(utTimeperiods.get(timeIdValue));
										chartDataModel.setValue(mapTrendData==null?null:(mapTrendData.containsKey(timeIdValue)? mapTrendData.get(timeIdValue)==null?null:Double.parseDouble(mapTrendData.get(timeIdValue)) : null));
										chartDataModel.setKey(map.get(value).getShort_Name());
										listOfChartType.add(chartDataModel);
									}
									chartDatas.add(listOfChartType);
									indicatorGroupModel.setGroupedIndName(map.get(value).getShort_Name());
									indicatorGroupModel.setChartData(chartDatas);
									indicatorGroupModel.setIndicatorName(map.get(value).getIndicator_Name());
									indicatorGroupModel.setIndicatorGroup(v.getIndicatorGroup()+"@"+value);
									indicatorGroupModels.add(indicatorGroupModel);
								});
							}
						});
					
				subSectorModel.setKpiList(listOfKPIIndicator);
				subSectorModel.setGroupedIndList(indicatorGroupModels);
				listOfSubsector.add(subSectorModel);
				
			}
			sectorModel.setSubSectors(listOfSubsector);
			listOfSectors.add(sectorModel);
		}
		SnapShortDataModel snapshortData = new SnapShortDataModel();
		snapshortData.setSnapshotData(listOfSectors);
		snapshortData.setTimePeriod(latestTP);
		
		return snapshortData;
	}
	
	@Override
	public Map<String, List<AreaModel>> getAreaList() {
		Map<Integer, Integer> mapOfUserArea = userAreaMappingRepository.findAll().stream().collect(Collectors.toMap(k->k.getUser().getUserId(), v->v.getArea().getAreaId()));
		
		UserModel user = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> roleIds = new ArrayList<String>();
		roleIds.addAll(user.getRoles());
		
		List<Area> areaList = null;
		if(roleIds.contains("NATIONAL")) {
			areaList = areaRepository.findByLevelAreaLevelIdInOrderByAreaIdAsc(Arrays.asList(1, 2, 3));
		} else if(roleIds.contains("STATE")) {
			areaList = areaRepository.getAreaListState(mapOfUserArea.get(user.getUserId()));
		}else if(roleIds.contains("DISTRICT")) {
			areaList = areaRepository.getAreaListDistrict(mapOfUserArea.get(user.getUserId()));
		}
		List<AreaModel> areaModelList = new ArrayList<>();
		Map<String, List<AreaModel>> areaMap = new LinkedHashMap<>();

		// setting areas is area-model list
		for (Area area : areaList) {

			AreaModel areaModel = new AreaModel();

			areaModel.setAreaCode(area.getAreaCode());
			areaModel.setAreaId(area.getAreaId());
			areaModel.setAreaLevel(area.getLevel().getAreaLevelName());
			areaModel.setAreaLevelId(area.getLevel().getAreaLevelId());
			areaModel.setAreaName(area.getAreaName());
			areaModel.setLive(area.isLive());
			areaModel.setParentAreaId(area.getParentAreaId());
			areaModelList.add(areaModel);
		}

		// making levelName as a key
		for (AreaModel areaModel : areaModelList) {

			if (areaMap.containsKey(areaModel.getAreaLevel())) {
				areaMap.get(areaModel.getAreaLevel()).add(areaModel);
			} else {
				areaModelList = new ArrayList<>();
				areaModelList.add(areaModel);
				areaMap.put(areaModel.getAreaLevel(), areaModelList);
			}
		}

		return areaMap;
	}
	
	@Override
	public ThematicViewModel getThematicView(Integer indicatorId, Integer timePeriodId,	Integer areaId) {
		//integer-bar-chart, yesNo-table
		
		IndicatorQuestionMapping indicatorQuestionMappings = indicatorQuestionMappingRepository.findByIndicatorNId(indicatorId);
				
		List<CSRTrendChartModel> listOfCSRTrendChartModel = new ArrayList<>();
		List<Area> areaList = areaRepository.findByParentAreaIdOrderByAreaName(areaId);
		List<Integer> childAreaList = new ArrayList<>();
		for (Area area : areaList) {
			childAreaList.add(area.getAreaId());
		}
		List<UtData> utDataList = utDataReporsitory.getThematicViewData(indicatorId, timePeriodId, childAreaList);
		Map<Integer, String> utDatas = new LinkedHashMap<>();
		for (UtData utData : utDataList) {
			utDatas.put(utData.getArea_NId().getAreaId(), utData.getData_Value());
		}
				
		for (Area area : areaList) {
			CSRTrendChartModel chartModel = new CSRTrendChartModel();
			chartModel.setKey(!utDatas.containsKey(area.getAreaId()) ? null : utDatas.get(area.getAreaId()));
			chartModel.setName(area.getAreaName());
			
			listOfCSRTrendChartModel.add(chartModel);
		}
		ThematicViewModel viewModel = new ThematicViewModel();
		viewModel.setThematicView(listOfCSRTrendChartModel);
		if(indicatorQuestionMappings.getIndicatorType().equals("select_one option") 
				|| indicatorQuestionMappings.getIndicatorType().equals("numerator_yesno_percent")) {
			viewModel.setChartType("table");
		} else {
			viewModel.setChartType("barChart");
		}
		return viewModel;
	}
	
	@Override
	public List<CsrAllAreaMapModel> getYearList() {
		List<CsrAllAreaMapModel> models = new ArrayList<>();
		List<Year> years = yearRepository.findAll();
		
		for (Year year : years) {
			CsrAllAreaMapModel csrAllAreaMapModel = new CsrAllAreaMapModel();
			csrAllAreaMapModel.setTimeperiodId(year.getYearId());
			csrAllAreaMapModel.setTimeperiod(year.getYearRange());
			
			models.add(csrAllAreaMapModel);
		}
		
		return models;
	}
	
}
