package com.web.tmp.tasklet;

import static com.web.utils.UtilsProccess.replaceSchema;

import java.math.BigDecimal;
import java.util.List;

import static com.web.utils.UtilsProccess.replaceSchema;

import org.apache.log4j.Logger;

import com.mysql.jdbc.StringUtils;
import com.web.common.LoadCommonPropertiesValidate;
import com.web.domain.Jdbc;
import com.web.model.ArchivoProcesado;

import freemarker.template.utility.StringUtil;

/**
 * 
 * Implementacion de PersistenceFinalTaskDao
 * con acceso a la base de datos
 * 
 * @author Paulino Mota Hernandez
 *
 */
public class PersistenceFinalTaskDaoImplementation extends Jdbc implements PersistenceFinalTaskDao{

	
	private static final Logger LOG = Logger.getLogger(PersistenceFinalTaskDaoImplementation.class);
	
	/**
	 * 
	 * Query con la sumatoria de la columna @MONTOCNTR
	 * y basado en el id de la carga @ID_PROCESO
	 *  
	 */
	private String sumaMontoOperationsLoaded;
	
	private String updateSumatoriaOperaciones;
	
	/**
	 * Query para obtener el numero de errores mediante
	 * un idProceso
	 */
	private String qryErrors;
	
	
	@Override
	public void persisteMontoOperaciones(BigDecimal montoTotal, String idProceso) {
		this.jdbcTemplate.update(
				updateSumatoriaOperaciones, 
				new Object[]{montoTotal, idProceso});
	}
	
	@Override
	public BigDecimal conteoTotalDeOperaciones(String idProceso, String schema) {
		String conteoString =  this.jdbcTemplate.queryForObject(
				 replaceSchema(sumaMontoOperationsLoaded, schema),
				new String[]{idProceso},
				String.class);
		
		if(StringUtils.isNullOrEmpty(conteoString))
			conteoString = "0";
		
		return new BigDecimal(conteoString);
		
	}

	@Override
	public int numeroDeRegistrosCargadosConError(String idProceso, String schema) {
		
		if(LOG.isDebugEnabled()){
			LOG.debug("QUERY PARA OBTENER ERRORES >> " + qryErrors);	
		}
		
		
		
		
		int registrosError = this.jdbcTemplate.queryForObject(
				replaceSchema(qryErrors, schema), 
				new Object[]{idProceso},
				Integer.class);
		
		return registrosError;
	}
	
	@Override
	public int persisteInformacionInsertSelect(String queryInserSelect, String idProceso) throws Exception {
		int result = 0;
		try{
		result = this.jdbcTemplate.update(
				queryInserSelect, new Object[]{idProceso}
				);
		}catch(Exception e){
			if (LoadCommonPropertiesValidate.evalException(e.getCause().getMessage())){
			}else{
				throw new Exception(e);
			}
			
		}
		
		return result;
	}

	
	@Override
	public int persisteInformacionArrayQuries(List<String> listQuriesToPersistence, String idProceso) {
		int registrosPersistidos = 0;
		
		LOG.debug("PRESISTE INFORMACION ARRAY QUERY");
		
		for(String updateInsert : listQuriesToPersistence){
			  int auxPersitido= this.jdbcTemplate.update(updateInsert, new Object[]{idProceso});
			LOG.debug(String.format("UPDATED[%d] PROCESO [%S] QUERY = %s", auxPersitido, idProceso, updateInsert));
			registrosPersistidos += auxPersitido;
		}
		return registrosPersistidos;
	}
	
	public String getSumaMontoOperationsLoaded() {
		return sumaMontoOperationsLoaded;
	}

	public void setSumaMontoOperationsLoaded(String sumaMontoOperationsLoaded) {
		this.sumaMontoOperationsLoaded = sumaMontoOperationsLoaded;
	}

	public String getQryErrors() {
		return qryErrors;
	}

	public void setQryErrors(String qryErrors) {
		this.qryErrors = qryErrors;
	}

	public String getUpdateSumatoriaOperaciones() {
		return updateSumatoriaOperaciones;
	}

	public void setUpdateSumatoriaOperaciones(String updateSumatoriaOperaciones) {
		this.updateSumatoriaOperaciones = updateSumatoriaOperaciones;
	}

}
