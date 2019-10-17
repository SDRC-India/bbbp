package org.sdrc.bbbp.dashboard.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.sdrc.bbbp.dashboard.domain.UtTimeperiod;
import org.sdrc.bbbp.dashboard.repository.UtTimeperiodRepository;
import org.sdrc.bbbp.domain.PeriodReference;
import org.sdrc.bbbp.domain.Periodicity;
import org.sdrc.bbbp.domain.Year;
import org.sdrc.bbbp.repository.PeriodReferenceRepository;
import org.sdrc.bbbp.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@EnableScheduling
@Slf4j
public class AggregationServiceImpl implements AggregationService {
	
	@Autowired
	private UtTimeperiodRepository utTimeperiodRepository;
	
	@Autowired
	private YearRepository yearRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	private SimpleDateFormat simpleDateformater = new SimpleDateFormat("yyyy-MM-dd");
	
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private SimpleDateFormat simpleDateformat = new SimpleDateFormat("MMM yyyy");
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM");
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yy");
	
	@Autowired
	private PeriodReferenceRepository periodReferenceRepository;

	/**
	 * @author Subrata
	 * 
	 *  @Description On first day of every three month this method will execute, 
	 *  creating time period in TimePeriod Table. (in every three month for a periodicity 1).
	 *  and will aggregate the data.
	 *   
	 */
	@Scheduled(cron = "0 3 0 1 1/3 ?")
	@Override
	public void aggregateData() {
		try {
//			System.out.println("Starting quarterly indicator aggreation");
			
			log.info("Starting quarterly indicator aggreation");
			//************************AGGREGATE ONE TIMEPERIOD*********************
//			creating quarter in Timeperiod
			UtTimeperiod timeperiod = createQuaterlyTimePeriod();
			
			// calling the aggregation function
			callAggregationUsingYearAndQuarter(timeperiod.getTimePeriod_NId(),
					timeperiod.getYear().getYearId(), timeperiod.getPeriodReference().getPeriodReferenceId());
			
//			************************END**************************
			log.info("Finished quarterly indicator aggreation");
//			System.out.println("Finished quarterly indicator aggreation");
		} catch (Exception e) {
		} finally {
		}
	}
	@Override
	public void aggregateAllData() {
		try {
			System.out.println("Starting quarterly indicator aggreation");
			
//			****************AGGREGATE ALL TIMEPERIODS********************
			List<UtTimeperiod> timeperiods = utTimeperiodRepository.findByPeriodicityPeriodicityId(1);
			
			for (UtTimeperiod timeperiod : timeperiods) {
				
				System.out.println(timeperiod.getTimePeriod_NId());
				callAggregationUsingYearAndQuarter(timeperiod.getTimePeriod_NId(),
						timeperiod.getYear().getYearId(), timeperiod.getPeriodReference().getPeriodReferenceId());
			}
//			************************END**************************
			
			System.out.println("Finished quarterly indicator aggreation");
		} catch (Exception e) {
		} finally {
		}
	}
	
	@Override
	public void aggregateDataQuarterly(Integer tpId, Integer yearId, Integer period) {
		try {
			System.out.println("Starting quarterly indicator aggreation");
			callAggregationUsingYearAndQuarter(tpId, yearId, period);
			System.out.println("Finished quarterly indicator aggreation");
		} catch (Exception e) {
		} finally {
		}
	}

	private Year getFinancialYear() {
		Year year = null;
		try {
			Calendar cal = Calendar.getInstance();
			int month = cal.get(Calendar.MONTH);
			int preYear =0, nextYear =0;
			if(month > 2){
				preYear = cal.get(Calendar.YEAR);
				cal.add(Calendar.YEAR, 1);
				nextYear = cal.get(Calendar.YEAR);
			} else {
				cal.add(Calendar.YEAR, -1);
				preYear = cal.get(Calendar.YEAR);
				cal.add(Calendar.YEAR, 1);
				nextYear = cal.get(Calendar.YEAR);
			}
//			System.out.println(preYear+"-"+nextYear);
//			System.out.println(Calendar.MONTH);
			String financialYear = null;
			Integer currentQuarter = getCurrentQuarter();
			if(currentQuarter.intValue()==1) {
				financialYear = (preYear-1)+"-"+(nextYear-1);
			}else {
				financialYear = preYear+"-"+nextYear;
			}
			List<Year> listOfYear = yearRepository.findAll();
			for (Year yr : listOfYear) {
				if(yr.getYearRange().equals(financialYear)) {
					year = yr;
					break;
				}
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return year;
	}
	
	public static int getCurrentQuarter() {
		
	    int month = LocalDate.now().get(ChronoField.MONTH_OF_YEAR);
	    switch (Month.of(month)) {
		    case JANUARY:
		    case FEBRUARY:
		    case MARCH:
		    default:
		      return 4;
		    case APRIL:
		    case MAY:
		    case JUNE:
		      return 1;
		    case JULY:
		    case AUGUST:
		    case SEPTEMBER:
		      return 2;
		    case OCTOBER:
		    case NOVEMBER:
		    case DECEMBER:
		      return 3;
	    }
	  }

	@Override
	public UtTimeperiod createQuaterlyTimePeriod() {
		UtTimeperiod timePeriod = null;
		try {
			Calendar startDateCalendar = Calendar.getInstance();
			startDateCalendar.add(Calendar.MONTH, -3);
			startDateCalendar.set(Calendar.DATE, 1);
			Date strDate = startDateCalendar.getTime();
			String startDateStr = simpleDateformater.format(strDate);
			Date startDate = (Date) formatter.parse(startDateStr + " 00:00:00.000");
			Calendar endDateCalendar = Calendar.getInstance();
			endDateCalendar.add(Calendar.MONTH, -1);
			endDateCalendar.set(Calendar.DATE, endDateCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			Date eDate = endDateCalendar.getTime();
			String endDateStr = simpleDateformater.format(eDate);
			Date endDate = (Date) formatter.parse(endDateStr + " 00:00:00.000");
			
			timePeriod = utTimeperiodRepository.findByStartDateAndEndDate(new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime()));
	
			if (timePeriod == null) {
				String monthRange = (dateFormat.format(startDate) +"-"+dateFormat.format(endDate));
				PeriodReference periodReference = periodReferenceRepository.findByMonthRange(monthRange);
				
				Year year = getFinancialYear();
				
				timePeriod = new UtTimeperiod();
				timePeriod.setStartDate(new java.sql.Date(startDate.getTime()));
				timePeriod.setEndDate(new java.sql.Date(endDate.getTime()));
				timePeriod.setPeriodicity(new Periodicity(1));
				timePeriod.setTimePeriod(simpleDateformat.format(startDate) +" - "+simpleDateformat.format(endDate));
				timePeriod.setShortName((dateFormat.format(startDate) +"-"+dateFormat.format(endDate)+" '"+sdf.format(endDate)));
				timePeriod.setPeriodReference(periodReference);
				timePeriod.setYear(year);
				timePeriod = utTimeperiodRepository.save(timePeriod);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return timePeriod;
		
	}

	@Override
	@Transactional
	public boolean callAggregation(UtTimeperiod timeperiod) {
		try {
			utTimeperiodRepository.aggregateData(timeperiod.getTimePeriod_NId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Transactional
	private void callAggregationUsingYearAndQuarter(Integer timePeriodId, int year, int quarter) {
		
		utTimeperiodRepository.aggregateDataUsingYearAndQuarter(timePeriodId, year, quarter);
		
	}
}
