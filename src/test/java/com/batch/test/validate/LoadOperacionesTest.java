package com.batch.test.validate;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:launch-context.xml",
		"classpath*:META-INF/spring/batch/jobs/job-load-file.xml", 
		"classpath:spring/batch/config/test-context.xml" 
		})
public class LoadOperacionesTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	JobParametersBuilder jobParametersBuilder;

	@Before
	public void setup() {
		
		jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString("inputDataFile","file:/Users/mottaman/Downloads/operaciones-26012016_1.csv");
		jobParametersBuilder.addString("schema", "SOFOM117");
		jobParametersBuilder.addString("idLayout", "16");
		jobParametersBuilder.addString("idProceso", "000000000000000011231231238");
		jobParametersBuilder.addString("cveProceso", "LOAD_CATALOG");
		jobParametersBuilder.addString("fileName", "operaciones-26012016_1.csv");
		jobParametersBuilder.addString("userSession", "MOTTAMAN");
		jobParametersBuilder.addString("cveTipoArchivo", "O");
		jobParametersBuilder.addString("separador", ",");
		jobParametersBuilder.addString("headLines", "1");
		
	}
	@Test
	public void launchJob() throws Exception {
		
		/*
		JobParameters param = jobParametersBuilder.toJobParameters();
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(param);
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

		 */
	}
	
	
}
