package com.web.tmp.tasklet;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class LoadFail implements Tasklet{

	private static final Logger LOG = Logger.getLogger(LoadFail.class);
	
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunckContext)
			throws Exception {
			
			LOG.error("ERROR TASKLET EN EL PROCESO DE CARGA");
			LOG.error(chunckContext.getAttribute("ERROR"));
		
		return RepeatStatus.FINISHED;
	}

}
