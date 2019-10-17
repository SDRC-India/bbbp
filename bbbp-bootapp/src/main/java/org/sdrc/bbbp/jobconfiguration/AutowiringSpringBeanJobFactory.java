package org.sdrc.bbbp.jobconfiguration;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;


/**
 * Adds autowiring support to quartz jobs.
 * Created by Sarita on 2018-08-21.
 */
public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements
        ApplicationContextAware {

    private transient AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(final ApplicationContext context) {
        beanFactory = context.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        final Object job = super.createJobInstance(bundle);
        beanFactory.autowireBean(job);
        return job;
    }
	
	/*private ApplicationContext ctx;
    private SchedulerContext schedulerContext;
    
    @Override
    public void setApplicationContext(final ApplicationContext context)
    {
        this.ctx = context;
    }
    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception
    {
        Job job = ctx.getBean(bundle.getJobDetail().getJobClass());
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(job);
        MutablePropertyValues pvs = new MutablePropertyValues();
        pvs.addPropertyValues(bundle.getJobDetail().getJobDataMap());
        pvs.addPropertyValues(bundle.getTrigger().getJobDataMap());
        if (this.schedulerContext != null)
        {
            pvs.addPropertyValues(this.schedulerContext);
        }
        bw.setPropertyValues(pvs, true);
        return job;
    }
    
    @Override
    public void setSchedulerContext(SchedulerContext schedulerContext)
    {
        this.schedulerContext = schedulerContext;
        super.setSchedulerContext(schedulerContext);
    }*/
}
