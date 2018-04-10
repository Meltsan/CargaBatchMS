package com.batch.test.validate;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:launch-context.xml",
		"classpath*:META-INF/spring/batch/jobs/job-load-file.xml", "classpath:spring/batch/config/test-context.xml", })
public class StepsTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	JobParametersBuilder jobParametersBuilder;

	@Before
	public void setup() {
		
		jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addString("inputDataFile","file:/Users/mottaman/Downloads/sucursales-01012017.csv");
		jobParametersBuilder.addString("schema", "SOFOM8");
		jobParametersBuilder.addString("idLayout", "1");
		jobParametersBuilder.addString("idProceso", "16461");
		jobParametersBuilder.addString("cveProceso", "LOAD_CATALOG");
		jobParametersBuilder.addString("fileName", "sucursales-10122015.csv");
		jobParametersBuilder.addString("userSession", "MOTTAMAN");
		jobParametersBuilder.addString("cveTipoArchivo", "C");
		jobParametersBuilder.addString("separador", ",");
		jobParametersBuilder.addString("headLines", "1");

	}

	
	@Test
	public void FailFail(){
		
		JobExecution jobExecution = null;
		try{
			jobExecution = jobLauncherTestUtils.launchStep("loadFileToTmpTables");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println(jobExecution.getStatus());
		assertEquals("FAILED", jobExecution.getStatus());
	}
	
	
	
}
