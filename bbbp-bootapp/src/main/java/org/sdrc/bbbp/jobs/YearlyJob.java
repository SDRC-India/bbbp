
package org.sdrc.bbbp.jobs;

/**
 * @author Debi Prasad
 * @author Sarita Panigrahi (sarita@sdrc.co.in)
 * Created On: 1-Aug-2018 6:24:29 PM
 * This job is getting executed on a yearly basis (on start of January)  to create yearly timeperiods as per CSR
 * commented this job as csr time will create while uploading data
 */
//@Component
//@PersistJobDataAfterExecution
//@DisallowConcurrentExecution
public class YearlyJob {
	/*
implements Job{
	@Autowired
	private JobService jobService;
	
	@Value("${yearly.cron.frequency.jobwithcrontrigger}")
    private String frequency;
	
	@Value("${yearly.job.group.name}")
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
			
			//required task -- invoke it from a service
			jobService.yearlyCSRTimeperiodJob();
			
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
	
	@Bean(name = "yearlyJobBean")
    public JobDetailFactoryBean quarterlyJob() {
		JobDetailFactoryBean jBean = ConfigureQuartz.createJobDetail(this.getClass());
		jBean.setRequestsRecovery(true);
		jBean.setGroup(jobGroupName); //get these from prop file
		jBean.setDescription("Creates yearly timeperiod to enable CSR data upload");
		//initialize job data map
		jBean.setJobDataMap(jMap);
		jMap.putAsString(jobDataMapKey, 0);
        return jBean;
    }
	
    @Bean(name = "yearlyJobTrigger")
    public CronTriggerFactoryBean quarterlyJobTrigger(@Qualifier("yearlyJobBean") JobDetailFactoryBean jdfb) {
    	CronTriggerFactoryBean cronTriggerFactoryBean = ConfigureQuartz.createCronTrigger(jdfb.getObject(),frequency);
    	cronTriggerFactoryBean.setGroup(triggerGroupName);
    	cronTriggerFactoryBean.setDescription("Yearly on January 1st");
    	return cronTriggerFactoryBean;
    }*/
}
