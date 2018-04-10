package com.web.common;

import static com.web.utils.UtilsProccess.postValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.web.domain.Jdbc;

/**
 * 
 * Clase encargada de cargar los meta-datos para generar las validaciones de los
 * Layouts
 * 
 * @author Paulino Mota Hernandez
 *
 */
public class LoadCommonPropertiesValidate extends Jdbc {

	private static final Logger LOG = Logger.getLogger(LoadCommonPropertiesValidate.class);
	
	/**
	 * STATIC CONS VALUES TO PROCCESS TEMPLATES FOR LOAD
	 */
	private static final String TABLE_TEMPORAL = "MTS_CARGAS_TEMPORALES";
	private static final String PREFIX_TMP_TABLE = "TMP2.";
	private static final String PREFIX_MAIN_TABLE = "CON";
	private static final String PREFIX_VALOR = "VALOR_";
	private static final String EQUALS_VALUE = " = ";
	private static final String SET = " SET ";
	private static final String COMA_VALUE = "'";
	private static final String WHERE = " WHERE ";
	private static final String OR = " OR ";
	private static final String AND = " AND ";
	private static final String UPDATE = " UPDATE ";
	private static final String POINT = ".";
	private static final Object QRY_PROCESO_CRITERIA = " ID_PROCESO = ? ";
	private static final String UPDATE_TEXT_ENG = "A result set was generated for update.";
	private static final String UPDATE_TEXT_ESP = "Se ha generado un conjunto de resultados para actualización.";

	/**
	 * Queries and setting params
	 */
	private String qryNoNullFields;
	private String qryValidacionCatalogos;
	private String qryValidateAttributes;
	private String insertSelect;
	private String updateSelect;
	private String schema;
	private String qryTable;
	private String qryInsertTemplate;
	private String qryUpdateTemplate;
	private String idLayout;
	private String qrySwKey;
	private String qryConstantesDefault;
	
	
	private String cleanTmpTables;
	private String qryCreateDefaultData;
	private String qrySetConstantes;

	private boolean catalogValidate;
	private String qryCatalogFields;
	private List<CatalogValidatorVo> validCatalogList;
	private List<ValidateAttribute> validateAttributes;
	private List<String> defaultDataUpdate;
	private List<String> defaultValue;
	private Map<String, String> listConstante;

	private LoadPropertiesInterface loadProperties;
	
	/**
	 * Campos requeridos
	 */
	private List<Integer> fieldsNotNull;
	/**
	 * REGEX para cada campo que requiere validacion
	 */
	private List<LabelValueBean> validateFields;

	/**
	 * inicializa las variable requeridas
	 */
	public void init() {

		initNotNullList();
		createCatalogValidateQuery();
		createListValidateAttributes();

		String tableNameInsertUpdate = getTableNameInsertUpdate();
		creteQueryInsertSelect(tableNameInsertUpdate);
		createQueryUpdateSelect(tableNameInsertUpdate);
		createDefaultAndConst(tableNameInsertUpdate);

		if(LOG.isDebugEnabled()){
			LOG.debug("-------- UPDATE DEFAULT VALUES ------");
		}
		for(String s : defaultDataUpdate){
			if(LOG.isDebugEnabled()){
				LOG.debug("test :" + s);
			}
		}
	}
	
	/**
	 * Funcion para crear los default y constantes para el insert
	 * 
	 * 	actualiza los campos:
	  	defaultDataUpdate = Campos con valore default 
	 	defaultValue = Valores Default
	 	listConstante = Constantes dentro del ciclo
	 	
	 * @param mainTable
	 */
	public void createDefaultAndConst(String mainTable) {
		
		LOG.debug(qryConstantesDefault + " " + schema + " " + idLayout);
		
		List<CommonMapperConsVo> listGetter =
				loadProperties.getConstantsDefault(qryConstantesDefault, schema, idLayout);
		
		defaultDataUpdate = new ArrayList<String>();
		defaultValue = new ArrayList<String>();
		listConstante = new HashMap<String, String>();
		
		if(LOG.isDebugEnabled()){
			LOG.debug(":::::::::. CREANDO DEFAULT Y CONSTANTES");
		}
		
		for (CommonMapperConsVo map : listGetter) {
			LOG.debug("ID : " + map.getSecMapeo());
			if (StringUtils.isNotBlank(map.getCons())
					|| StringUtils.isNotBlank(map.getDefaultDataType())
					|| StringUtils.isNotBlank(map.getDefualt())) {

				if (StringUtils.isNotBlank(map.getCons())) {
					
					defaultDataUpdate.add(
							createSetCondition(
									map.getSecMapeo(), 
									map.getCons()
									)
							);
					if(LOG.isDebugEnabled()){
						LOG.debug("AGRENDANDO CONSTANTE " + map.getSecMapeo() + " - " + map.getCons() );
					}
					
					listConstante.put(map.getSecMapeo(), map.getCons());
				} else if (StringUtils.isNotBlank(map.getDefualt())) {
					defaultDataUpdate.add(
							createSetCondition(
									map.getSecMapeo(), 
									map.getDefualt()
									)
							);
					defaultValue.add(map.getDefualt());
					if(LOG.isDebugEnabled()){
						LOG.debug("AGRENDANDO DEF VAL " + map.getSecMapeo() + " - " + map.getDefualt() );
					}
					
				} else if (StringUtils.isNotBlank(map.getDefaultDataType())){
					defaultDataUpdate.add(
							createSetCondition(
									map.getSecMapeo(), 
									map.getDefaultDataType() 
									)
							);
					defaultValue.add(map.getDefaultDataType());
					if(LOG.isDebugEnabled()){
						LOG.debug("AGRENDANDO DEF TIPO " + map.getSecMapeo() + " - " + map.getDefaultDataType() );
					}
				}else{
					if(LOG.isDebugEnabled()){
						LOG.debug("AGRENDANDO DEF TIPO ");
					}
					defaultValue.add("");
				}
			}else{
				if(LOG.isDebugEnabled()){
					LOG.debug("AGRENDANDO DEF TIPO ");
				}
				defaultValue.add("");
			}
		}

	}
	
	/**
	 * Metodo para crear un set a la tabla indicada en los argumentows
	 * @param index INDICE DE LA COLUMNA
	 * @param value VALOR A SETEATR
	 * @return Sentencia SET SQL
	 */
	public String createSetCondition(
			String index, String value 
			){
		
		String val_index = postValue(index);

		StringBuilder sbVal = 
				new StringBuilder(UPDATE)
				.append(schema)
				.append(POINT)
				.append(TABLE_TEMPORAL)
				.append(SET)
				.append(PREFIX_VALOR)
				.append(val_index)
				.append(EQUALS_VALUE)
				.append(COMA_VALUE)
				.append(value)
				.append(COMA_VALUE)
				.append(WHERE)
				.append(PREFIX_VALOR)
				.append(val_index)
				.append(" IS NULL")
				.append(OR)
				.append(PREFIX_VALOR)
				.append(val_index)
				.append(EQUALS_VALUE)
				.append(" '' ")
				.append(AND)
				.append(QRY_PROCESO_CRITERIA);
		return sbVal.toString();
	}

	/**
	 * Crea la lista de atributos configurados.
	 */
	public void createListValidateAttributes() {

		validateAttributes =
				loadProperties.getQueryValidateAttr
					(qryValidateAttributes, schema, idLayout);
		
		if(LOG.isDebugEnabled()){
			StringBuffer logSB = new StringBuffer();
			LOG.debug(":::: CREATE VALIDATE ATTRIBUTES ::::::");
			for(ValidateAttribute a : validateAttributes){
				logSB.append("\n")
					.append(String.format("CVE[%s] SECUENCIA [%s] LONG[%s] "
							+ "NOMBRE[%s] TIPO DATO[%s]", a.getCveTipoDato(), 
							a.getIdSecuencia(),
								a.getLongitud(), a.getNombreColumna(), 
								a.getSwTipoDato()));
			}
		
			LOG.debug(logSB.toString());
			
		}

	}

	/**
	 * Actualiza el parametro UpdateSelect con el valor de los queries
	 * generados de forma dinamica
	 * @param tableName Tabla sobre la que trabajara el query
	 */
	public void createQueryUpdateSelect(String tableName) {

		List<ValuesBeanInsert> tmpUpdate =
				loadProperties.getQueryUpdateTemplate(qryUpdateTemplate, schema, idLayout);

		List<ValuesBeanInsert> updateCriteriaList =
				loadProperties.getUpdateCriteriaList(qrySwKey, schema, idLayout);

		if(LOG.isDebugEnabled()){
			
			LOG.debug("===== CREATE QUERY UPDATE");
			StringBuffer sbUpt = new StringBuffer();
			for(ValuesBeanInsert up : tmpUpdate){
				sbUpt.append("\n").append(up);
			}
			
			LOG.debug(sbUpt);
			
			LOG.debug("===== UPDATE CRITERIA LIST");
			StringBuffer sbCriteria = new StringBuffer();
			for(ValuesBeanInsert cr : updateCriteriaList){
				sbCriteria.append("\n").append(cr);
			}
			LOG.debug(sbCriteria.toString());
			
		}
		
		StringBuilder sbQry = new StringBuilder("UPDATE ").append(schema)
				.append(".").append(tableName).append(" SET ");

		for (ValuesBeanInsert lb : tmpUpdate) {
			sbQry.append(lb.getLabel()).append(EQUALS_VALUE);

			if ("F".equals(lb.getDataType())) {
				sbQry.append("(SELECT convert(datetime,")
						.append(PREFIX_VALOR)
						.append(lb.getValue().length() == 1 ? "0"
								+ lb.getValue() : lb.getValue()).append(",")
						.append(lb.getMascara()).append(")), ");
			} else if ("M".equals(lb.getDataType())) {
				sbQry.append("(SELECT REPLACE(")
						.append(PREFIX_VALOR)
						.append(lb.getValue().length() == 1 ? "0"
								+ lb.getValue() : lb.getValue())
						.append(",',','')), ");
			} else {
				sbQry.append(PREFIX_TMP_TABLE)
						.append(PREFIX_VALOR)
						.append(lb.getValue().length() == 1 ? "0"
								+ lb.getValue() : lb.getValue()).append(" ")
						.append(", ");
			}

		}

		sbQry.append(QRY_PROCESO_CRITERIA).append(" FROM ").append(schema)
				.append(".").append(tableName).append(" ")
				.append(PREFIX_MAIN_TABLE).append(", ").append(schema)
				.append(".").append(TABLE_TEMPORAL).append(" ").append("TMP2")
				.append(" WHERE TMP2.ID_PROCESO = ? ")
				.append(" AND TMP2.CVE_STATUS <> 'R'  ");

		for (ValuesBeanInsert upl : updateCriteriaList) {
			sbQry.append(replaceLeftRigth(upl.getLabel(), upl.getValue()));
		}

		setUpdateSelect(sbQry.toString());
		
		if(LOG.isDebugEnabled()){
			LOG.debug("------------ UPDATE SELECT ------------");
			LOG.debug(sbQry.toString());
			LOG.debug("---------------------------------------");	
		}
		
	}

	/**
	 * 
	 * @param valLeft 
	 * @param valRigth 
	 * @return remplaza los strings izquierda y derecha 
	 */
	public String replaceLeftRigth(String valLeft, String valRigth) {

		valRigth = PREFIX_VALOR
				+ (valRigth.length() == 1 ? "0" + valRigth : valRigth);
		String result = " AND CON.[left] =  TMP2.[rigth] ".replace("[left]",
				valLeft);

		return result.replace("[rigth]", valRigth);

	}

	public String replaceNotExist(String mainTable) {

		return "AND EXISTS (SELECT NULL " + "FROM" + schema
				+ " .MTS_DCONTRATANTE CONT2 WHERE CONT2.ID_PROCESO "
				+ " = TMP2.ID_PROCESO)".replace("[table]", mainTable);

	}

	public String getTableNameInsertUpdate() {

		String tableName =
			loadProperties.getQryTable(qryTable, schema, idLayout);
		return tableName;
	}

	
	/**
	 * 
	 * Crea los queries para insertar informacion dentro de las tablas
	 * @param tableName
	 * 
	 */
	public void creteQueryInsertSelect(String tableName) {

		List<ValuesBeanInsert> updateCriteriaList =
				loadProperties.getUpdateCriteriaList(qrySwKey, schema, idLayout);

		StringBuilder sb = new StringBuilder("INSERT INTO ").append(schema)
				.append(".").append(tableName).append("(");

		StringBuilder sbValores = new StringBuilder("SELECT ");

		List<ValuesBeanInsert> tmpIns =
			loadProperties.getQryInsertTemplate(qryInsertTemplate, schema, idLayout);
				
		ValuesBeanInsert cveProceso = new ValuesBeanInsert();
		cveProceso.setLabel("ID_PROCESO");
		cveProceso.setValue("ID_PROCESO");

		tmpIns.add(cveProceso);

		int count = 1;
		for (ValuesBeanInsert lb : tmpIns) {
			sb.append(lb.getLabel());

			if ("F".equals(lb.getDataType())) {
				sbValores
						.append("(SELECT convert(datetime,")
						.append(PREFIX_VALOR)
						.append(lb.getValue().length() == 1 ? "0"
								+ lb.getValue() : lb.getValue()).append(",")
						.append(lb.getMascara()).append("))");
			} else if ("M".equals(lb.getDataType())) {
				sbValores
						.append("(SELECT REPLACE(")
						.append(PREFIX_VALOR)
						.append(lb.getValue().length() == 1 ? "0"
								+ lb.getValue() : lb.getValue())
						.append(",',',''))");
			} else {
				if (count != tmpIns.size()) {
					sbValores.append(PREFIX_VALOR).append(
							lb.getValue().length() == 1 ? "0" + lb.getValue()
									: lb.getValue());
				} else {
					sbValores.append(lb.getValue());
				}
			}

			if (count != tmpIns.size()) {
				sb.append(",");
				sbValores.append(",");
			}
			count++;
		}

		sb.append(")");
		sbValores.append(" FROM ").append(schema)
				.append(".MTS_CARGAS_TEMPORALES WHERE CVE_STATUS = 'V'")
				.append(" AND     ID_PROCESO  = ?")
				.append(" AND     NOT EXISTS (SELECT  NULL").append(" FROM ")
				.append(schema).append(".").append(tableName).append(" CONT2");
		boolean whereParam = true;
		for (ValuesBeanInsert vbi : updateCriteriaList) {
			if (null != vbi.getSwKey() && "S".equals(vbi.getSwKey())) {
				sbValores.append(whereParam ? " WHERE " : " AND ").append(
						" CONT2.");
				sbValores
						.append(vbi.getLabel())
						.append(" = ")
						.append(PREFIX_VALOR)
						.append(vbi.getValue().length() == 1 ? "0"
								+ vbi.getValue() : vbi.getValue());
				whereParam = false;
			}
		}
		sb.append(sbValores);
		sb.append(")");

		setInsertSelect(sb.toString());
		if(LOG.isDebugEnabled()){
			LOG.debug("--------------- INSERT SELECT ---------------");
			LOG.debug(getInsertSelect());
			LOG.debug("---------------------------------------------");
		}
		
		

		StringBuffer sbDelte = new StringBuffer("DELETE FROM ").append(schema)
				.append(".MTS_CARGAS_TEMPORALES");
		setCleanTmpTables(sbDelte.toString());
	}


	/**
	 * Creacion del qry para la validacion de catalogos
	 * Se guarda en la varibale qryValidacionCatalogos
	 */
	public void createCatalogValidateQuery() {
		StringBuffer sf = new StringBuffer("SELECT ");
		validCatalogList = 
			loadProperties.getQryCatalogFields(qryCatalogFields, schema, idLayout);
		
		if(LOG.isDebugEnabled()){
			
			LOG.debug("QUERY CATALOG FIELDS....");
			for(CatalogValidatorVo cat : validCatalogList){
				
			}
			
		}
		
		catalogValidate = validCatalogList.size() > 0;

		for (int i = 0; i < validCatalogList.size(); i++) {
			CatalogValidatorVo cat = validCatalogList.get(i);
			sf.append(" (").append(cat.getStringQuery()).append(")");
			if ((i + 1) != validCatalogList.size()) {
				sf.append(",");
			}
		}
		qryValidacionCatalogos = sf.toString();
		if (qryValidacionCatalogos != null) {
			qryValidacionCatalogos = qryValidacionCatalogos.replace("[Schema]",
					schema);
			qryValidacionCatalogos = qryValidacionCatalogos.replace("SCHEMA",
					schema);
		}
	}
	
	public void initNotNullList() {

		LOG.debug("query : " + qryNoNullFields);
		LOG.debug("schema : " + schema);

		fieldsNotNull = loadProperties.initNotNullList(qryNoNullFields, schema, idLayout);
		LOG.info(" initNotNullList -======= > " + fieldsNotNull.size());
	}
	
	public static boolean evalException(String message){
		boolean MsgSuccess = false;
		
		if (UPDATE_TEXT_ENG.equals(message) || UPDATE_TEXT_ESP.equals(message)){
			MsgSuccess = true;
		}
		
		
		return MsgSuccess;
		
	}
	
	public LoadPropertiesInterface getLoadProperties() {
		return loadProperties;
	}

	public void setLoadProperties(LoadPropertiesInterface loadProperties) {
		this.loadProperties = loadProperties;
	}

	public List<Integer> getFieldsNotNull() {
		return fieldsNotNull;
	}

	public void setFieldsNotNull(List<Integer> fieldsNotNull) {
		this.fieldsNotNull = fieldsNotNull;
	}

	public List<LabelValueBean> getValidateFields() {
		return validateFields;
	}

	public void setValidateFields(List<LabelValueBean> validateFields) {
		this.validateFields = validateFields;
	}

	public void setQryNoNullFields(String qryNoNullFields) {
		this.qryNoNullFields = qryNoNullFields;
	}

	public void setQryCatalogFields(String qryCatalogFields) {
		this.qryCatalogFields = qryCatalogFields;
	}

	public boolean isCatalogValidate() {
		return catalogValidate;
	}

	public void setCatalogValidate(boolean catalogValidate) {
		this.catalogValidate = catalogValidate;
	}

	public String getQryNoNullFields() {
		return qryNoNullFields;
	}

	public String getQryCatalogFields() {
		return qryCatalogFields;
	}

	public List<CatalogValidatorVo> getValidCatalogList() {
		return validCatalogList;
	}

	public void setValidCatalogList(List<CatalogValidatorVo> validCatalogList) {
		this.validCatalogList = validCatalogList;
	}

	public String getQryValidacionCatalogos() {
		return qryValidacionCatalogos;
	}

	public void setQryValidacionCatalogos(String qryValidacionCatalogos) {
		this.qryValidacionCatalogos = qryValidacionCatalogos;
	}

	public String getQryValidateAttributes() {
		return qryValidateAttributes;
	}

	public void setQryValidateAttributes(String qryValidateAttributes) {
		this.qryValidateAttributes = qryValidateAttributes;
	}

	public List<ValidateAttribute> getValidateAttributes() {
		return validateAttributes;
	}

	public void setValidateAttributes(List<ValidateAttribute> validateAttributes) {
		this.validateAttributes = validateAttributes;
	}

	public String getInsertSelect() {
		return insertSelect;
	}

	public void setInsertSelect(String insertSelect) {
		this.insertSelect = insertSelect;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getQryTable() {
		return qryTable;
	}

	public void setQryTable(String qryTable) {
		this.qryTable = qryTable;
	}

	public String getQryInsertTemplate() {
		return qryInsertTemplate;
	}

	public void setQryInsertTemplate(String qryInsertTemplate) {
		this.qryInsertTemplate = qryInsertTemplate;
	}

	public String getIdLayout() {
		return idLayout;
	}

	public void setIdLayout(String idLayout) {
		this.idLayout = idLayout;
	}

	public String replaceSchema(String qry, String schema) {
		return qry.replace("[schema]", schema);
	}

	public String getCleanTmpTables() {
		return cleanTmpTables;
	}

	public void setCleanTmpTables(String cleanTmpTables) {
		this.cleanTmpTables = cleanTmpTables;
	}

	public String getUpdateSelect() {
		return updateSelect;
	}

	public void setUpdateSelect(String updateSelect) {
		this.updateSelect = updateSelect;
	}

	public String getQryUpdateTemplate() {
		return qryUpdateTemplate;
	}

	public void setQryUpdateTemplate(String qryUpdateTemplate) {
		this.qryUpdateTemplate = qryUpdateTemplate;
	}

	public String getQrySwKey() {
		return qrySwKey;
	}

	public void setQrySwKey(String qrySwKey) {
		this.qrySwKey = qrySwKey;
	}

	public String getQryConstantesDefault() {
		return qryConstantesDefault;
	}

	public void setQryConstantesDefault(String qryConstantesDefault) {
		this.qryConstantesDefault = qryConstantesDefault;
	}

	public String getQryCreateDefaultData() {
		return qryCreateDefaultData;
	}

	public void setQryCreateDefaultData(String qryCreateDefaultData) {
		this.qryCreateDefaultData = qryCreateDefaultData;
	}

	public String getQrySetConstantes() {
		return qrySetConstantes;
	}

	public void setQrySetConstantes(String qrySetConstantes) {
		this.qrySetConstantes = qrySetConstantes;
	}

	public List<String> getDefaultDataUpdate() {
		return defaultDataUpdate;
	}

	public void setDefaultDataUpdate(List<String> defaultDataUpdate) {
		this.defaultDataUpdate = defaultDataUpdate;
	}


	public Map<String, String> getListConstante() {
		return listConstante;
	}

	public void setListConstante(Map<String, String> listConstante) {
		this.listConstante = listConstante;
	}

	public List<String> getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(List<String> defaultValue) {
		this.defaultValue = defaultValue;
	}
 
}
