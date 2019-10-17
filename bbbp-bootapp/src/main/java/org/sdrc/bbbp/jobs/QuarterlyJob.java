package org.sdrc.bbbp.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.sdrc.bbbp.jobconfiguration.ConfigureQuartz;
import org.sdrc.bbbp.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author Debi Prasad
 * @author Sarita Panigrahi (sarita@sdrc.co.in) 
 * This job is getting executed on a yearly (on start of financial year) basis to create yearly timeperiods
 */
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class QuarterlyJob implements Job{
	
	@Autowired
	private JobService jobService;
	
	@Value("${quarterly.cron.frequency.jobwithcrontrigger}")
    private String frequency;
	
	@Value("${quarterly.job.group.name}")
    private String jobGroupName;
	
	@Value("${timeperiod.cron.trigger.group.name}")
    private String triggerGroupName;

	@Value("${jobdatamap.key}")
    private String jobDataMapKey;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	JobDataMap jMap = new JobDataMap();
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		int count = dataMap.getIntValue(jobDataMapKey);
		
		logger.info("YO HOOOOOOOOOOO.............");
		
		// allow 5 retries
		if (count >= 5) {
			logger.info("count grean than equal 5..........");
			JobExecutionException e = new JobExecutionException("Retries exceeded");
			// make sure it doesn't run again
			e.setUnscheduleAllTriggers(true);
			throw e;
		}

		try {
			// reset counter back to 0
			dataMap.putAsString(jobDataMapKey, 0);
			
			jobService.quarterlyJob();
			
		} catch (Exception e) {
			count++;
			dataMap.putAsString(jobDataMapKey, count);
			JobExecutionException e2 = new JobExecutionException(e);

			try {
				Thread.sleep(600000);
			} catch (InterruptedException e1) {
				logger.error("Error while thread sleep, Interrupted!! : ", e1);
				Thread.currentThread().interrupt();
			} // sleep for 10 mins

			// fire it again
			e2.setRefireImmediately(true);
			throw e2;
		}
	}
	
	@Bean(name = "quarterlyJobBean")
    public JobDetailFactoryBean quarterlyJob() {
		JobDetailFactoryBean jBean = ConfigureQuartz.createJobDetail(this.getClass());
		jBean.setRequestsRecovery(true);
		jBean.setGroup(jobGroupName); //get these from prop file
		jBean.setDescription("Creates yearly timeperiod to enable data entry");
		//initialize job data map
		jBean.setJobDataMap(jMap);
		jMap.putAsString(jobDataMapKey, 0);
        return jBean;
    }
	
    @Bean(name = "quarterlyJobTrigger")
    public CronTriggerFactoryBean quarterlyJobTrigger(@Qualifier("quarterlyJobBean") JobDetailFactoryBean jdfb) {
    	
    	CronTriggerFactoryBean cronTriggerFactoryBean = ConfigureQuartz.createCronTrigger(jdfb.getObject(),frequency);
    	cronTriggerFactoryBean.setGroup(triggerGroupName);
    	cronTriggerFactoryBean.setDescription("Yearly on April 1");
    	return cronTriggerFactoryBean;
    }

}
