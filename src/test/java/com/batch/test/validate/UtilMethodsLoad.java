package com.batch.test.validate;

import static org.junit.Assert.assertEquals;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import com.web.utils.UtilsProccess;


@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = { "classpath*:launch-context.xml",
		"classpath*:META-INF/spring/batch/jobs/job-load-file.xml", 
		"classpath:spring/batch/config/test-context.xml" 
		})
public class UtilMethodsLoad {
	
	private UtilsProccess utils = new UtilsProccess();
	
	@Before
	public void setup(){
		
	}

	@Test
	public void posValue(){
		String result = utils.postValue("1");
		assertEquals("01", result);
	}
	
	@Test 
	public void replaceSchema(){
		String res = utils.replaceSchema("SELECT * FROM [schema].TABLAS", "SOFOM2");
		assertEquals("SELECT * FROM SOFOM2.TABLAS", res);
	}
	
	@Test 
	public void replaceSchemaBreakLine(){
		String res = utils.replaceSchema("\n SELECT COUNT(*) FROM [schema].MTS_CARGAS_TEMPORALES WHERE CVE_STATUS = 'R' AND ID_PROCESO = ? \n", "SOFOM8");
		assertEquals("\n SELECT COUNT(*) FROM SOFOM8.MTS_CARGAS_TEMPORALES WHERE CVE_STATUS = 'R' AND ID_PROCESO = ? \n", res);
	}
	
}
