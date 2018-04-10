package com.web.common;

import static com.web.utils.UtilsProccess.replaceSchema;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.web.domain.Jdbc;

public class LoadPropertiesImpl extends Jdbc implements LoadPropertiesInterface{
	
	private static final Logger LOG = Logger.getLogger(LoadCommonPropertiesValidate.class);

	@Override
	public List<ValuesBeanInsert>  getUpdateCriteriaList(String qrySwKey, String schema, String idLayout){
		List<ValuesBeanInsert> updateCriteriaList = this.jdbcTemplate.query(
				replaceSchema(qrySwKey, schema), new Object[] { idLayout },
				COL_TABLE_MAPPER_UPDATE);
		
		return updateCriteriaList;
	}
	
	@Override
	public List<Integer> initNotNullList(String qryNoNullFields, String schema, String idLayout) {
		List<Integer> fieldsNotNull = null;
		LOG.debug("query : " + qryNoNullFields);
		LOG.debug("schema : " + schema);

		fieldsNotNull = this.jdbcTemplate.query(
				replaceSchema(qryNoNullFields, schema),
				new Object[] { idLayout },
				new ParameterizedRowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int arg1)
							throws SQLException {
						return rs.getInt(1);
					}
				});
		LOG.info(" initNotNullList -======= > " + fieldsNotNull.size());
		return fieldsNotNull;
	}

	@Override
	public List<CommonMapperConsVo> getConstantsDefault(String qryConstantesDefault, String schema, String idLayout) {
		
		List<CommonMapperConsVo> listGetter = this.jdbcTemplate.query(
				replaceSchema(qryConstantesDefault, schema),
				new Object[] { idLayout }, MAPPER_CONS);
		
		LOG.debug("VALORES CONTANTES Y DEFAULT....");
		StringBuffer logSB = new StringBuffer();
		for(CommonMapperConsVo vo : listGetter){
			logSB.append("\n").append(vo);
		}
		
		LOG.debug(logSB.toString());
		
		return listGetter;
	}
	
	@Override
	public List<ValidateAttribute> getQueryValidateAttr(String qryValidateAttributes, String schema, String idLayout) {
		List<ValidateAttribute> resulList = this.jdbcTemplate.query(
				replaceSchema(qryValidateAttributes, schema),
				new Object[] { idLayout },
				new ParameterizedRowMapper<ValidateAttribute>() {
					@Override
					public ValidateAttribute mapRow(ResultSet rs, int arg1)
							throws SQLException {
						ValidateAttribute result = new ValidateAttribute();

						result.setIdSecuencia(rs.getString(1));
						result.setLongitud(rs.getInt(2));
						result.setCveTipoDato(rs.getString(3));
						result.setSwTipoDato(rs.getString(4));
						result.setNombreColumna(rs.getString(5));

						return result;
					}

				});
		return resulList;
	}
	
	
	@Override
	public List<ValuesBeanInsert> getQueryUpdateTemplate(String qryUpdateTemplate, String schema, String idLayout) {
		List<ValuesBeanInsert> result = this.jdbcTemplate.query(
				replaceSchema(qryUpdateTemplate, schema),
				new Object[] { idLayout }, COL_TABLE_MAPPER_UPDATE);
		return result;
	}

	@Override
	public String getQryTable(String qryTable, String schema, String idLayout) {
		String result = this.jdbcTemplate.queryForObject(
				replaceSchema(qryTable, schema), new Object[] { idLayout },
				String.class);
		return result;
	}

	@Override
	public List<ValuesBeanInsert> getQryInsertTemplate(String qryInsertTemplate, String schema, String idLayout) {
		List<ValuesBeanInsert> result = this.jdbcTemplate.query(
				replaceSchema(qryInsertTemplate, schema),
				new Object[] { idLayout }, COL_TABLE_MAPPER);
		return result;
	}
	
	public static ParameterizedRowMapper<ValuesBeanInsert> COL_TABLE_MAPPER = new ParameterizedRowMapper<ValuesBeanInsert>() {
		@Override
		public ValuesBeanInsert mapRow(ResultSet rs, int arg1)
				throws SQLException {
			ValuesBeanInsert lb = new ValuesBeanInsert();
			lb.setLabel(rs.getString(1));
			lb.setValue(rs.getString(2));
			lb.setDataType(rs.getString(3));
			lb.setMascara(rs.getString(4));
			return lb;
		}
	};
	
	public static ParameterizedRowMapper<ValuesBeanInsert> COL_TABLE_MAPPER_UPDATE = new ParameterizedRowMapper<ValuesBeanInsert>() {
		@Override
		public ValuesBeanInsert mapRow(ResultSet rs, int arg1)
				throws SQLException {

			ValuesBeanInsert lb = new ValuesBeanInsert();
			lb.setLabel(rs.getString(1));
			lb.setValue(rs.getString(2));
			lb.setDataType(rs.getString(3));
			lb.setSwKey(rs.getString(4));
			lb.setMascara(rs.getString(5));
			return lb;
		}
	};

	public ParameterizedRowMapper<CommonMapperConsVo> MAPPER_CONS = new ParameterizedRowMapper<CommonMapperConsVo>() {
		@Override
		public CommonMapperConsVo mapRow(ResultSet rs, int arg1)
				throws SQLException {
			CommonMapperConsVo result = new CommonMapperConsVo();

			result.setCons(rs.getString("CONSTANTE"));
			result.setDefualt(rs.getString("VALOR_DEFAULT"));
			result.setDefaultDataType(rs.getString("DEF_TIPO"));
			result.setSecMapeo(rs.getString("ID_SECUENCIA_MAPEO"));

			return result;
		}
	};

	@Override
	public List<CatalogValidatorVo> getQryCatalogFields(String qryCatalogFields, String schema, String idLayout) {
		List<CatalogValidatorVo> result = this.jdbcTemplate.query(
				replaceSchema(qryCatalogFields, schema),
				new Object[] { idLayout },
				new ParameterizedRowMapper<CatalogValidatorVo>() {
					@Override
					public CatalogValidatorVo mapRow(ResultSet rs, int arg1)
							throws SQLException {
						CatalogValidatorVo result = new CatalogValidatorVo();
						result.setIdColumna(rs.getInt("ID_ORDEN"));
						result.setIdMapeo(rs.getString("NOMBRE_COLUMNA"));
						result.setStringQuery(rs.getString("STRINGQUERY"));
						return result;
					}
				});
		return result;
	}

}
