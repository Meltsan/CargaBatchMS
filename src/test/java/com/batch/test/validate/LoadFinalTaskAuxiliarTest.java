package com.batch.test.validate;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import com.web.common.LoadCommonPropertiesValidate;
import com.web.tmp.tasklet.LoadFinalTask;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = { "classpath*:launch-context.xml",
		"classpath*:META-INF/spring/batch/jobs/job-load-file.xml", 
		"classpath:spring/batch/config/test-context.xml" 
		})
public class LoadFinalTaskAuxiliarTest {
	
	@Mock
	private LoadCommonPropertiesValidate propertiesValidate;
	
	LoadFinalTask loadFinalTask = new LoadFinalTask();
	
	@Before
	public void setup(){
		
		when(propertiesValidate.getTableNameInsertUpdate()).thenReturn("MTS_HOPERACIONESCNTR");
		
		loadFinalTask.setSchema("SOFOM117");
		loadFinalTask.setPropertiesValidate(propertiesValidate);
	}
	
	
	@Test
	public void validateCreateQueryConteoInsertTablasFinales(){
		String query = loadFinalTask.creaQueryParaConteoDeRegistrosEnTablasFinales();
		System.out.println(query);
		assertEquals("SELECT COUNT(*) FROM SOFOM117.MTS_HOPERACIONESCNTR where id_proceso = ?", query);
	}
	
}
