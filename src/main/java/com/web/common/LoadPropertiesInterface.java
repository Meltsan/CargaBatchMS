package com.web.common;

import java.util.List;

/**
 * 
 * @author Paulino Mota Hernandez
 * Interface con los metdotos utilizados para crear y cargar tablas temporales
 *
 */
public interface LoadPropertiesInterface {
	
	/**
	 * 
	 * @param qryNoNullFields 
	 * @param schema
	 * @param idLayout
	 * @return Lista con los id's de las coulumnas que no permiten nulos
	 */
	public List<Integer> initNotNullList(String qryNoNullFields, String schema, String idLayout);
	
	/**
	 * 
	 * @param qrySwKey
	 * @param schema
	 * @param idLayout
	 * @return listado de registros que seran actualizados
	 */
	public List<ValuesBeanInsert>  getUpdateCriteriaList(String qrySwKey, String schema, String idLayout);
	
	/**
	 * 
	 * @param qryConstantesDefault
	 * @param schema
	 * @param idLayout
	 * @return
	 */
	public List<CommonMapperConsVo> getConstantsDefault(String qryConstantesDefault, String schema, String idLayout);
	
	/**
	 * 
	 * @param qryValidateAttributes
	 * @param schema
	 * @param idLayout
	 * @return regresa aquellas columnas configuradas con una constante o un valor por defecto
	 */
	public List<ValidateAttribute> getQueryValidateAttr(String qryValidateAttributes, String schema, String idLayout);
	
	/**
	 * 
	 * @param qryUpdateTemplate
	 * @param schema
	 * @param idLayout
	 * @return query para actualizar las tablas temporales
	 */
	public List<ValuesBeanInsert> getQueryUpdateTemplate(String qryUpdateTemplate, String schema, String idLayout);
	
	/**
	 * 
	 * @param qryTable
	 * @param schema
	 * @param idLayout
	 * @return query de un table
	 */
	public String getQryTable(String qryTable, String schema, String idLayout);
	
	/**
	 * 
	 * @param qryInsertTemplate
	 * @param schema
	 * @param idLayout
	 * @return template para el insert
	 */
	public List<ValuesBeanInsert> getQryInsertTemplate(String qryInsertTemplate, String schema, String idLayout);
	
	/**
	 * 
	 * @param qryCatalogFields
	 * @param schema
	 * @param idLayout
	 * @return 
	 */
	public List<CatalogValidatorVo> getQryCatalogFields(String qryCatalogFields, String schema, String idLayout);

	
}
