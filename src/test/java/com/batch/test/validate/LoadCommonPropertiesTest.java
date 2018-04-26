package com.batch.test.validate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import com.web.annotation.ValidateMasiveLoad;
import com.web.common.CommonMapperConsVo;
import com.web.common.CommonModel;
import com.web.common.LoadCommonPropertiesValidate;
import com.web.common.LoadPropertiesInterface;
import com.web.common.ValidateAttribute;
import com.web.common.ValuesBeanInsert;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = { "classpath*:launch-context.xml",
		"classpath*:META-INF/spring/batch/jobs/job-load-file.xml", 
		"classpath:spring/batch/config/test-context.xml" 
		})
public class LoadCommonPropertiesTest {

	private static final Logger LOG = Logger.getLogger(LoadCommonPropertiesTest.class);
	
	LoadCommonPropertiesValidate loadCommon = new LoadCommonPropertiesValidate();
	
	@Mock
	LoadPropertiesInterface propertiesInterface;

	ValidateMasiveLoad masiveLoad = new ValidateMasiveLoad();
	
	private static final String SCHEMA_SOFOM = "SOFOM8";
	
	@Before
	public void setup(){
		
		loadCommon.setQryNoNullFields("SELECT * FROM ...");
		loadCommon.setSchema("SOFOM8");
		
		when(propertiesInterface.initNotNullList(null, SCHEMA_SOFOM, null))
			.thenReturn(new ArrayList<Integer>());
		
		loadCommon.setLoadProperties(propertiesInterface);
		
		//VALUES UDATE
		List<ValuesBeanInsert> valuesUpdateSettingList 
			= new ArrayList<ValuesBeanInsert>();
		valuesUpdateSettingList.add(new ValuesBeanInsert("DS_SUCURSAL", "2", "A", null,null));
		valuesUpdateSettingList.add(new ValuesBeanInsert("PAISID", 		"3", "A", null,null));
		valuesUpdateSettingList.add(new ValuesBeanInsert("ESTADOID", 	"4", "A", null,null));
		valuesUpdateSettingList.add(new ValuesBeanInsert("MUNICIPIOID", "5", "A", null,null));
		valuesUpdateSettingList.add(new ValuesBeanInsert("COLONIAID", 	"6", "A", null,null));
		valuesUpdateSettingList.add(new ValuesBeanInsert("SUCURSALCP", 	"7", "A", null,null));
		valuesUpdateSettingList.add(new ValuesBeanInsert("SUCURSALDIR", "8", "A", null,null));
		valuesUpdateSettingList.add(new ValuesBeanInsert("NO_EXTERIOR", "9", "A", null,null));
		valuesUpdateSettingList.add(new ValuesBeanInsert("NO_INTERIOR", "10", "A", null,null));
		valuesUpdateSettingList.add(new ValuesBeanInsert("VIGENCIA", 	"11", "A", null,null));
		
		
		when(propertiesInterface.getQueryUpdateTemplate(null, SCHEMA_SOFOM, null))
			.thenReturn(valuesUpdateSettingList);
		
		//CRITERIA UPDATE LIST
		List<ValuesBeanInsert> criteriaList 
			= new ArrayList<ValuesBeanInsert>();
		criteriaList.add(new ValuesBeanInsert("SUCURSALID", "1", "A", "S",null));
		
		when(propertiesInterface.getUpdateCriteriaList(null, SCHEMA_SOFOM, null))
			.thenReturn(criteriaList);
		
		
		//ATRIBUTES PROPS
		List<ValidateAttribute> attrList = new ArrayList<ValidateAttribute>();
			attrList.add(new ValidateAttribute("1", 8, "S", "A", "SUCURALID","112"));
			attrList.add(new ValidateAttribute("2", 50, "S", "A", "DS_SUCURSAL","112"));
			attrList.add(new ValidateAttribute("3", 8, "S", "A", "PAISID","112"));
			attrList.add(new ValidateAttribute("4", 8, "S", "A", "ESTADOID","112"));
			attrList.add(new ValidateAttribute("5", 8, "S", "A", "MUNICIPIOID","112"));
			attrList.add(new ValidateAttribute("6", 8, "S", "A", "COLONIAID","112"));
			attrList.add(new ValidateAttribute("7", 5, "S", "A", "SUCURSALCP","112"));
			attrList.add(new ValidateAttribute("8", 50, "S", "A", "SUCURSALDIR","112"));
			attrList.add(new ValidateAttribute("9", 20, "S", "A", "NO_EXTERIOR","112"));
			attrList.add(new ValidateAttribute("10", 20, "S", "A", "NO_INTERIOR","112"));
			attrList.add(new ValidateAttribute("11", 2, "S", "A", "VIGENCIA","112"));

			when(propertiesInterface.getQueryValidateAttr(null, SCHEMA_SOFOM, null))
			.thenReturn(attrList);
			
		//PREPARE CONS-DEFAULT LIST
		List<CommonMapperConsVo> constList = new ArrayList<CommonMapperConsVo>();
		constList.add(new CommonMapperConsVo("1",""));
		constList.add(new CommonMapperConsVo("2",""));
		constList.add(new CommonMapperConsVo("3",""));
		constList.add(new CommonMapperConsVo("4","A"));
		constList.add(new CommonMapperConsVo("5","A"));
		constList.add(new CommonMapperConsVo("6",""));
		constList.add(new CommonMapperConsVo("7",""));
		constList.add(new CommonMapperConsVo("8",""));
		constList.add(new CommonMapperConsVo("9",""));
		constList.add(new CommonMapperConsVo("10",""));
		constList.add(new CommonMapperConsVo("11","A"));
		
		
		
		when(propertiesInterface.getConstantsDefault(null, SCHEMA_SOFOM, null))
			.thenReturn(constList);
		
	}
	
	@Test
	public void createQueryUpdate(){
		loadCommon.createQueryUpdateSelect("MTS_DSUCURSALES");
		assertEquals("UPDATE SOFOM8.MTS_DSUCURSALES SET DS_SUCURSAL = TMP2.VALOR_02 , PAISID = TMP2.VALOR_03 , ESTADOID = TMP2.VALOR_04 , MUNICIPIOID = TMP2.VALOR_05 , COLONIAID = TMP2.VALOR_06 , SUCURSALCP = TMP2.VALOR_07 , SUCURSALDIR = TMP2.VALOR_08 , NO_EXTERIOR = TMP2.VALOR_09 , NO_INTERIOR = TMP2.VALOR_10 , VIGENCIA = TMP2.VALOR_11 ,  ID_PROCESO = ?  FROM SOFOM8.MTS_DSUCURSALES CON, SOFOM8.MTS_CARGAS_TEMPORALES TMP2 WHERE TMP2.ID_PROCESO = ?  AND TMP2.CVE_STATUS <> 'R'   AND CON.SUCURSALID =  TMP2.VALOR_01 ".trim(), 
				loadCommon.getUpdateSelect().trim());
	}
	
	@Test
	public void createDefaultAndConst(){
		
		loadCommon.createDefaultAndConst("");
		assertEquals(3, loadCommon.getDefaultDataUpdate().size());
	}
	
	@Test
	public void createDefault(){
		
		loadCommon.createDefaultAndConst("");
		
		assertEquals(8, loadCommon.getDefaultValue().size());
	}
	
	@Test
	public void createSetCondition(){
		String setSentence = loadCommon.createSetCondition("1", "TABLE_1");
		assertEquals(" UPDATE SOFOM8.MTS_CARGAS_TEMPORALES SET VALOR_01 = 'TABLE_1' WHERE VALOR_01 IS NULL OR VALOR_01 =  ''  AND  ID_PROCESO = ? ",
				setSentence);
	}
	
	@Test
	public void creteQueryInsertSelect(){
		loadCommon.creteQueryInsertSelect("MTS_HOPERACIONESCNTR");
		String insertSelect = loadCommon.getInsertSelect();
		String cleanTmpTables = loadCommon.getCleanTmpTables();
		
		
		assertNotEquals(
				"INSERT INTO SOFOM8.(ID_PROCESO)SELECT ID_PROCESO FROM SOFOM8.MTS_CARGAS_TEMPORALES WHERE CVE_STATUS = 'V' AND     ID_PROCESO  = ? AND     NOT EXISTS (SELECT  NULL FROM SOFOM8. CONT2 WHERE  CONT2.SUCURSALID = VALOR_01)",
				loadCommon.getInsertSelect());
	}
	
	@Test
	public void validateMasiveLoad(){
		CommonModel model = new CommonModel();
		loadCommon.createDefaultAndConst("");
		loadCommon.createListValidateAttributes();
		
		masiveLoad.setPropertiesValidate(loadCommon);
		masiveLoad.loadWrapperListToValidate(model);
		
		assertEquals(8, loadCommon.getDefaultValue().size());
	}
	
}
