package com.claro.cpymes.ejb.starup;

import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.claro.cpymes.dao.ParameterDAORemote;
import com.claro.cpymes.job.JobNits;


@Singleton
@Startup
public class LoadNitsOnixEJB {

   private static Logger LOGGER = LogManager.getLogger(LoadNitsOnixEJB.class.getName());

   private static final String JOB_NAME = "jobNitsOnix";

   private static final String GROUP_NAME = "group1";

   private static final String TRIGGER_NAME = "trigger1";

   private Scheduler scheduler;

   private JobDetail job;

   private CronTrigger trigger;

   @EJB
   private ParameterDAORemote parameterDAO;

   @PostConstruct
   @Asynchronous
   public void init() {
      try {
         startScheduler();
         createJob();
         createTrigger();
         schedulerJob();
      } catch (final SchedulerException e) {
         LOGGER.error("Error Ejecutando Job Nits: ", e);
      }

   }

   private void startScheduler() throws SchedulerException {
      scheduler = StdSchedulerFactory.getDefaultScheduler();
      scheduler.start();
   }

   private void createJob() {
      job = JobBuilder.newJob(JobNits.class).withIdentity(JOB_NAME, GROUP_NAME).build();
   }

   private void createTrigger() {
      trigger = getTriggerBuilder();

   }

   private CronTrigger getTriggerBuilder() {
      return TriggerBuilder.newTrigger().withIdentity(TRIGGER_NAME, GROUP_NAME).startNow()
         .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(1, 00)).build();
   }

   private void schedulerJob() throws SchedulerException {
      scheduler.scheduleJob(job, trigger);

   }

}
