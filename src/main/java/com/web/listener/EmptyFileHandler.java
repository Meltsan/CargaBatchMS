package com.web.listener;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;

public class EmptyFileHandler {

	private static final Logger log = Logger.getLogger(EmptyFileHandler.class);  
    @AfterStep 
    public ExitStatus afterStep(StepExecution execution) {  
         if (execution.getReadCount() > 0) {
        	 log.error("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
              return execution.getExitStatus();  
         } else {  
              log.error("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");  
              log.error("EMPTY FILE");  
              log.error("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");  
              return ExitStatus.FAILED;  
         }  
    } 
	
}
