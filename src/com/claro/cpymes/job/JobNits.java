package com.claro.cpymes.job;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.claro.cpymes.ejb.remote.ProcessNitsRemote;

/**
 * Job que define el cargue del archivo ONIX IVR
 * @author jbarragan
 *
 */
public class JobNits implements Job {

   private ProcessNitsRemote processNits;

   private static Logger LOGGER = LogManager.getLogger(ProcessNitsRemote.class.getName());

   private static final String NAMING = "java.naming.factory.initial";

   private static final String CONTEXT_FACTORY = "org.apache.openejb.client.LocalInitialContextFactory";

   private static final String PROCESS_NIT_NAME = "ProcessNitsEJBRemote";

   @Override
   public void execute(JobExecutionContext arg0) throws JobExecutionException {
      LOGGER.info("START JOB NITS");
      this.processNits = getProcessNits();
      processNits.processNits();
      LOGGER.info("END JOB NITS");
   }

   private ProcessNitsRemote getProcessNits() {
      try {
         if (processNits == null) {
            Properties p = new Properties();
            p.put(NAMING, CONTEXT_FACTORY);
            InitialContext ctx = new InitialContext(p);
            this.processNits = (ProcessNitsRemote) ctx.lookup(PROCESS_NIT_NAME);
         }
         return this.processNits;

      } catch (NamingException e1) {
         LOGGER.error("Error LookUp ProcessNitsRemote: ", e1);
         return this.processNits;
      }
   }

}
