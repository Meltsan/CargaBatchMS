package com.web.annotation;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.web.common.CatalogValidatorVo;
import com.web.common.CommonModel;
import com.web.common.Config;
import com.web.common.LoadCommonPropertiesValidate;
import com.web.common.ValidateAttribute;
import com.web.domain.Jdbc;

/**
 * 
 * Clase para la validacion de registros
 * 
 * @author Paulino Mota Hernandez
 *
 */
public class ValidateMasiveLoad extends Jdbc{
	
	private static final Logger LOG = Logger.getLogger(ValidateMasiveLoad.class);
	
	private StringBuffer errorDetial;
	private static final String NULL = "null";
	private static final String S = "S";
	@Autowired
	private LoadCommonPropertiesValidate propertiesValidate;
	private List<CommonValueWapper> listValuesWrapper;
	
	public String validateLoadMasive(CommonModel model, String idLayout, String schema){
		
		LOG.debug("VALIDATE LOAD MASIVE ----- " + idLayout);
		
		errorDetial = new StringBuffer();
		if(null == propertiesValidate.getFieldsNotNull()){
			propertiesValidate.setIdLayout(idLayout);
			propertiesValidate.setSchema(schema);
			propertiesValidate.init();
		}
		
		loadWrapperListToValidate(model);
		validateNull(model);
		validateDataTypeAndLength(model);
		
		if(errorDetial.length() == 0){
							
		if(propertiesValidate.isCatalogValidate()){
			try {
				try {
					validateCatalog(model);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		
		return errorDetial.toString();
	}
	
	public void loadWrapperListToValidate(CommonModel model){
		listValuesWrapper = new ArrayList<CommonValueWapper>();
		listValuesWrapper.add(new CommonValueWapper());
		
		Class<? extends CommonModel> c = model.getClass();
		int indexDefault = 0;
		int indexConst = 0;
		LOG.info(":::::::LISTA COL --------");
		LOG.info("_____:::::::::::SETEO DE CAMPOS");
		LOG.info("_____::::::::::: DEFAULT VALUES: " + propertiesValidate.getDefaultValue().size());
		LOG.info("_____::::::::::: DEFAULT CONSTANTES: "+ propertiesValidate.getListConstante().size());
		LOG.info("_____::::::::::: ATRIBUTOS CONFIGURADOD: "+ propertiesValidate.getValidateAttributes().size());
		
		if((propertiesValidate.getDefaultValue().size() 
				+ propertiesValidate.getListConstante().size()) 
					!= propertiesValidate.getValidateAttributes().size()){
			
			throw new RuntimeException("Tal vez tienes configurados algunos "
					+ "atributos con un tipo de dato invalido, los tipos de "
					+ "datos validos se encuentran en la metatabla "
					+ "[CAT_TIPODATO_DEFAULT]");
		}
		
		for(ValidateAttribute att : propertiesValidate.getValidateAttributes())
		{
			CommonValueWapper aux = new CommonValueWapper();
			aux.setNombreColumna(att.getNombreColumna());
			try {
				Field f = c.getField("col"+att.getIdSecuencia());
				String auxEval = (String) f.get(model);
				if(propertiesValidate.getListConstante().containsKey(att.getIdSecuencia())){
					
					aux.setValue(propertiesValidate.getListConstante().get(att.getIdSecuencia()));
					LOG.debug("col" + att.getIdSecuencia() + " -> " );
					indexConst++;
				}else{
					
					String def = propertiesValidate.getDefaultValue().get(indexDefault);
					LOG.debug("col" + att.getIdSecuencia() + " -> " + (StringUtils.isBlank(auxEval)?def:auxEval) + " DEFULT VALUE - > " + def);
					aux.setValue(StringUtils.isBlank(auxEval)?def:auxEval);
					indexDefault++;
				}
				
				
				} catch (NoSuchFieldException | SecurityException  
				| IllegalArgumentException | IllegalAccessException
				e) {
					LOG.error(e.getMessage());
			e.printStackTrace();
		}
			listValuesWrapper.add(aux);
		}
		
	}
	
	private boolean validateDataTypeAndLength(CommonModel model){
		int i=1;
		for(ValidateAttribute validate : propertiesValidate.getValidateAttributes()){
			CommonValueWapper aux = listValuesWrapper.get(i);
			String value = aux.getValue();
			 if(StringUtils.isNotBlank(aux.getValue()) && aux.getValue().length() > validate.getLongitud() && (validate.getLongitud() > 0)){
				 errorDetial.append(" LONGITUD MAYOR EN EL CAMPO - ")
				 .append(aux.getNombreColumna())
				 .append(" Actual : ")
				 .append(aux.getValue().length())
				 .append(" Esperado [ ")
				 .append(validate.getLongitud())
				 .append("  ] ");
			 }
			 
			 if(S.equals(validate.getSwTipoDato())){
				 switch(validate.getCveTipoDato()){
				 case Config.ALFANUMERICO:
					 if(!StringUtils.isAlphanumericSpace(value)){
						 errorDetial.append("    ").append(aux.getNombreColumna());
						 aux.setValue("");
					 }
			     break;
				 case Config.FECHA:
					 
					 break;
				 case Config.DECIMAL:
					 
					 break;
				 case Config.ENTERO:
					 if(!StringUtils.isNumeric(aux.getValue())){
						 errorDetial.append("ERROR FORMATO SE ESPERABA UN ENTERO EN EL CAMPO ").append(aux.getNombreColumna());
					 }
				 case Config.MONEDA:
					 	if(null != aux.getValue() && aux.getValue().contains(","))
					 	{
					 		listValuesWrapper.get(i).setValue(aux.getValue().replaceAll(",", ""));
					 		LOG.debug("OCONTAIN COMAS" + aux.getValue());
					 	}
					 break;
				 
				 }
			 }
			 i++;
		}
		return true;
	}
	
	private boolean validateCatalog(CommonModel model) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		final List<CatalogValidatorVo> v = propertiesValidate.getValidCatalogList();
		MapSqlParameterSource params = new MapSqlParameterSource();
		for(CatalogValidatorVo cat : v){
			params.addValue(cat.getIdMapeo(), listValuesWrapper.get(cat.getIdColumna()).getValue());
		}
		 this.namedParameterJdbcTemplate.query(propertiesValidate.getQryValidacionCatalogos(),
				params,
				new ParameterizedRowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int arg1)
						throws SQLException {
					int cols = rs.getMetaData().getColumnCount();
					for(int i=1; i<=cols; i++){
						if("0".equals((rs.getString(i)))){
							CatalogValidatorVo cal = v.get(i-1);
							errorDetial	.append(" ")
							.append(cal.getIdMapeo())
								.append(" - ")
									.append("Campo no catalogado");
						}
					}
					return null;
				}
			});
			
		return true;
	}
	
	private boolean validateNull(CommonModel model){
		boolean result = true;
		Class<? extends CommonModel> c = model.getClass();
		if(propertiesValidate.getFieldsNotNull().size() < 0)
			return result;
		
		for(Integer s : propertiesValidate.getFieldsNotNull()){
			if(LOG.isDebugEnabled()){
				LOG.debug(s);
			}
			String valueAux = listValuesWrapper.get(s).getValue();
			if(StringUtils.isBlank(valueAux) || NULL.equals(valueAux)){
				errorDetial.append("[").append(listValuesWrapper.get(s).getNombreColumna()).append(" ] - REQUERIDO");
				return false;
			}
		}
		return result;
	}

	public void setErrorDetial(StringBuffer errorDetial) {
		this.errorDetial = errorDetial;
	}

	public void setPropertiesValidate(
			LoadCommonPropertiesValidate propertiesValidate) {
		this.propertiesValidate = propertiesValidate;
	}

}
