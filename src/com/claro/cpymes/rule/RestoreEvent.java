package com.claro.cpymes.rule;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import com.claro.cpymes.dto.LogDTO;
import com.claro.cpymes.dto.RestoreEventAlarmDTO;
import com.claro.cpymes.util.Constant;


/**
 * Clase que ejecuta las reglas de filtrado de Drools
 * @author jbarragan
 *
 */
public class RestoreEvent {

   private static Logger LOGGER = LogManager.getLogger(RestoreEvent.class.getName());

   KnowledgeBase kbase;

   StatefulKnowledgeSession ksession;

   public void initialize() throws Exception {
      kbase = RestoreEvent.readKnowledgeBase(Constant.PATH_DRL_FILE_RESTORE);
      ksession = kbase.newStatefulKnowledgeSession();
   }

   public ArrayList<RestoreEventAlarmDTO> restoreEvent(ArrayList<LogDTO> listLog) {
      for (LogDTO log : listLog) {
         ksession.insert(log);
      }
      ksession.fireAllRules();
      
      Collection<Object> listEventRestore = ksession.getObjects();
      RestoreEventAlarmDTO restoreEventAlarm;
      ArrayList<RestoreEventAlarmDTO> listRestoreEventsAlarm = new ArrayList<RestoreEventAlarmDTO>();
      for (Object obj : listEventRestore) {
         if (obj instanceof RestoreEventAlarmDTO) {
            restoreEventAlarm = ((RestoreEventAlarmDTO) obj);
            listRestoreEventsAlarm.add(restoreEventAlarm);
            ksession.retract(ksession.getFactHandle(obj));
         }
      }
      return listRestoreEventsAlarm;
   }

   private static KnowledgeBase readKnowledgeBase(String drlFile) throws Exception {
      FileInputStream fis = new FileInputStream(drlFile);

      KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
      kbuilder.add(ResourceFactory.newInputStreamResource(fis), ResourceType.DRL);
      KnowledgeBuilderErrors errors = kbuilder.getErrors();
      if (errors.size() > 0) {
         for (KnowledgeBuilderError error : errors) {
            LOGGER.info(error);
         }
         throw new IllegalArgumentException("Could not parse knowledge.");
      }
      KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
      kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
      return kbase;

   }

}
