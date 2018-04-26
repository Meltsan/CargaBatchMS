package com.web.tmp.tasklet;

import static com.web.common.Constantes.TERMINADO_ERRORES;
import static com.web.common.Constantes.ERROR_PROCESO;
import static com.web.utils.UtilsProccess.replaceSchema;
import static com.web.utils.UtilsProccess.determinaStatusCargaPorErrores;

import java.io.File;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.web.common.LoadCommonPropertiesValidate;
import com.web.domain.Jdbc;
import com.web.model.ArchivoProcesado;
import com.web.utils.ErrorsProcessor;

/**
 * Ultima tarea en el proces de carga de archivos
 * la funcion de esta clase es reportar el estatus final de la clase
 * y persistir en tablas la informacion de la carga mediante 
 * la implementacion de @PersistenceFinalTaskDao
 * 
 * @author Paulino Mota Hernandez
 *
 */
public class LoadFinalTask extends Jdbc implements Tasklet{
	
	private static final Logger LOG = Logger.getLogger(LoadFinalTask.class);
	private static final String OPERACION_CVE = "O";
	
	
	private PersistenceFinalTaskDao persistenceFinalTask;
	
	private LoadCommonPropertiesValidate propertiesValidate;
	private String schema;
	private String pathLoadFile;	
	private String idProceso;
	private String fileName;
	private String insArchProcesados;
	private String userSession;
	private String insDetalleError;
	private String cveTipoArchivo;
	private String updateStatusBitacora;
	private String qryErrors;
	private String qryCifrasControlValida;
	private String idLayout;
	
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)
			throws Exception {
		
		LOG.debug(String.format(
				"**** INICIA LA TAREA FINAL DE CARGA "
				+ "\n CVE TIPO DE CARGA [%s]"
				+ "\n CVE TABLA ISERT [%s]",
				cveTipoArchivo, propertiesValidate.getTableNameInsertUpdate()));
		
		/**
		 * variables auxiliares
		 * numero de inserts
		 * numero de update
		 */
		int insertedRows = 0;
		
		/**
		 * 
		 * obtiene la lista de queries para generar un update dinamico
		 * de acuerdo a la tabla correspondiente al archivo que se
		 * esta cargando
		 * 
		 */
		List<String> qriesUpdate = propertiesValidate.getDefaultDataUpdate();

		int registrosPersistidos 
			= persistenceFinalTask.persisteInformacionArrayQuries(qriesUpdate, idProceso);
		
		
		/**
		 * 
		 * Obtiene el numero de registros con estatus R
		 * Rechazados en la validacion del proceso anterior
		 * 
		 */
		int registrosError = persistenceFinalTask
				.numeroDeRegistrosCargadosConError(idProceso, schema);
		
		 
		
		/**
		 * Obtiene el estatus de la carga dentro a partir del 
		 * del numero de errores persitidos
		 */
		String statusCarga = determinaStatusCargaPorErrores(registrosError);
		
		try{
			
			/**
			 * Persiste informacion select insert
			 * y obtiene informacion con el numero
			 * de registros persistidos.
			 */
			LOG.debug("QUERY TO INSERT: " + 
					propertiesValidate.getInsertSelect());
			
			insertedRows  = persistenceFinalTask
							.persisteInformacionInsertSelect(
									propertiesValidate.getInsertSelect(), idProceso);
			
				LOG.info(String.format(
						"FASE [1] : \n REGISTROS ACTUALIZADOS [%d] "
						+ "\n TOTAL DE REGISTROS PARA ACTUALIZAR [%d]"
						+ "\n REGISTROS CON STATUS RECHAZADO [%d]"
						+ "\n ESTATUS DE LA CARGA [%s]"
						+ "\n NUMERO DE REGISTROS INSERTADOS [%d]" , 
						registrosPersistidos, 
						qriesUpdate.size(), 
						registrosError, 
						statusCarga, 
						insertedRows));
			
			persisteDetalleDeCarga(registrosError, insertedRows);
			
		}catch(Exception e){
			LOG.debug("QUERY TO Error......: " + e.getCause().getMessage());
			if (LoadCommonPropertiesValidate.evalException(e.getCause().getMessage())){
				
				
				LOG.info(String.format(
						"FASE [1] : \n REGISTROS ACTUALIZADOS [%d] "
						+ "\n TOTAL DE REGISTROS PARA ACTUALIZAR [%d]"
						+ "\n REGISTROS CON STATUS RECHAZADO [%d]"
						+ "\n ESTATUS DE LA CARGA [%s]"
						+ "\n NUMERO DE REGISTROS INSERTADOS [%d]" , 
						registrosPersistidos, qriesUpdate.size(), 
						registrosError, statusCarga, insertedRows));
				e.printStackTrace();
				LOG.error("Error Controldao por la Exception sql Server ::::::  ");
				persisteDetalleDeCarga(registrosError, insertedRows);
				
			}else{
				
				ErrorsProcessor error = ErrorsProcessor.createClassFromError(e.getMessage());
				LOG.error(String.format("TIPO ERROR....... [%s] \n MENSAJE", error.getTipoError(), error.getMessage()));
					
				chunkContext.setAttribute("ERROR", error.getTipoError());

				statusCarga = ERROR_PROCESO;
				return null;
			}
		}
		
		//BLOQUE PARA ELIMINAR EL ARCHIVO CARGADO Y PERSISTIR EL ESTATUS FINAL
		//EN LA BITACORA DE PROCESO DE CARGA
		persisteEnBitacoraStatusLimpiaArchivo(statusCarga, registrosError);;
		
		
		return null;
	}
	
	/**
	 * Inserta el detalle de la carga
	 * de acuerdo al tipo de archivo cargado.
	 * 
	 * @param registrosError
	 * @param insertedRows
	 */
	private void persisteDetalleDeCarga(int registrosError, int insertedRows){
		
		int updatedRows = 0;
		
		LOG.debug("\n ======== CARGA DE ARCHIVO CON ERRORES : "
				+ "\n CARGA DE ARCHIVOS CON ERRORES ["+cargaArchivoConErrores(registrosError)+"]"
				+ "\n ISOPERACIONES ["+isOperaciones()+"]"
				+ "\n QUERY UPDATESELECT ["+propertiesValidate.getUpdateSelect()+"]"
				+ "\n QUERY ARCHIVOS PROCESADOS ["+insArchProcesados+"]"
				+ "\n INSERT SELECT insDetalleError ["+insDetalleError+"] ");
		
		ArchivoProcesado archivoProcesado = new ArchivoProcesado();
		archivoProcesado.setCveTipoArvchivo(cveTipoArchivo);
		archivoProcesado.setIdproceso(idProceso);
		archivoProcesado.setNombreArchivo(fileName);
		archivoProcesado.setUsuarioOperacion(userSession);
		
			/**
			 * Valida si el archivo pertenece a operaciones,
			 * para persistir la informacion correspondiente a este proceso
			 */
			if(!isOperaciones()){
				updatedRows = this.jdbcTemplate.update(propertiesValidate.getUpdateSelect(), new Object[]{idProceso, idProceso});
				
				LOG.debug(String.format("/n ********** PERSISTE INFORMACION DETALLE ***********"
						+ "/n REGISTROS PERSISTIDOS [%d]"
						+ "/n QUERY PARA LA INSERCION [%s]"
						, updatedRows, propertiesValidate.getUpdateSelect()));
			}else{
				archivoProcesado.setMontoRegValidos(persistenceFinalTask.conteoTotalDeOperaciones(idProceso, schema));	
			}
			
			archivoProcesado.setRegistrosActualizados(updatedRows);
			archivoProcesado.setRegistrosInsertados(obtieneRegistrosPeristidosPorProceso());
			
			
			persisteInformacionArchivosProcesados(archivoProcesado);
			
		if(LOG.isDebugEnabled()){ 
			LOG.debug(String.format("DETALLE DE ERROR [%s] "
					+ "\n QUERY TO INSERT DETALLE [%s]", idProceso, replaceSchema(insDetalleError, schema)));
		}
		
		
		/**
		 * CARGA EL DETALLO CON ERRORES EN LA TABLA
		 * MTS_LAYOUT_DETALLE_VALIDACIONES
		 */
		this.jdbcTemplate.update(replaceSchema(insDetalleError, schema), new Object[]{idProceso});
		
	}
	
	
	private void persisteInformacionArchivosProcesados(ArchivoProcesado archivoProcesado) {
		LOG.debug("antes del error :::::::");
		LOG.debug("ERROR ::: " + replaceSchema(insArchProcesados, schema));
		
		try{
		int rowsUpdates = 
				this.jdbcTemplate.update
				(replaceSchema(insArchProcesados, schema), new Object[]{
						archivoProcesado.getIdproceso(), 
						archivoProcesado.getNombreArchivo(),
						archivoProcesado.getIdproceso(), 
						archivoProcesado.getIdproceso(), 
						archivoProcesado.getIdproceso(), 
						archivoProcesado.getRegistrosInsertados(), 
						archivoProcesado.getRegistrosActualizados(), 
						archivoProcesado.getCveTipoArvchivo(),
						archivoProcesado.getUsuarioOperacion(),
						archivoProcesado.getMontoRegValidos()
				});
		}catch(Exception e){
			LOG.debug(e.getCause().getMessage());
			LOG.debug(e.getCause());
			
		}
		
	}
	
	private int obtieneRegistrosPeristidosPorProceso(){
		
		String queryDinamicoCount = creaQueryParaConteoDeRegistrosEnTablasFinales();
		
		return this.jdbcTemplate.queryForInt(
				queryDinamicoCount,
				new Object[]{idProceso}
				);
	}
	
	public String creaQueryParaConteoDeRegistrosEnTablasFinales(){
		StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ");
				sb.append(schema)
					.append(".")
						.append(propertiesValidate.getTableNameInsertUpdate())
							.append(" where id_proceso = ?");
				return sb.toString();
	}
	
	/**
	 * Elimina el archivo cargado
	 *
	 */
	private void eliminaArchivoCargado(){
		String[] parts = pathLoadFile.split(":");
		String part2 = parts[1]; 
		
		
		File file = new File(part2);
		if(file.delete())
		{
			LOG.info("EL ARCHIVO SE ELIMINO DE FORMA CORRECTA");
		}
	}
	
	/**
	 * 
	 * @param statusCarga
	 * @param registrosError
	 */
	private void persisteEnBitacoraStatusLimpiaArchivo(String statusCarga, int registrosError){
		
		LOG.debug(String.format("TERMINA PROCESO DE CARGA "
				+ "\n LIMPIA ARCHIVO Y PERSISTE EN BITACORA "
				+ "\n Status[%s] REGISTROS ERROR [%d]"
				+ "\n ID PROCESO [%s]"
				+ "\n QUERY TO INSERT %s", 
				statusCarga, registrosError, idProceso, replaceSchema(updateStatusBitacora, schema)));
		
		try{
			
			this.jdbcTemplate.update(replaceSchema(updateStatusBitacora, schema), new Object[]{
				statusCarga,
				userSession,
				(statusCarga.equals(TERMINADO_ERRORES) && !cargaArchivoConErrores(registrosError))?"Error en CC":"",
				idProceso
			});
			
		}catch(Exception e){
			//TODO 
		}
		
		limpiaTablasTemporales();
		eliminaArchivoCargado();
	}
	
	/**
	 * Limpia las tablas temporales para finalizar el proceso
	 */
	private void limpiaTablasTemporales(){
		try{
			
			LOG.info("Limpiando Tablas TEMPORALES...");
			this.jdbcTemplate.update(propertiesValidate.getCleanTmpTables());
			
		}catch(Exception e){
			
			LOG.error("Ocurio un error en el borradod de las tablas temporales...");
			e.printStackTrace();
			
		}
	}
	
	public boolean isOperaciones(){
		return OPERACION_CVE.equals(cveTipoArchivo);
	}

	/**
	 * 
	 * @param errores
	 * @return
	 */
	public boolean cargaArchivoConErrores(int errores){
		boolean res = true;
		String sw_cc = this.jdbcTemplate.queryForObject(
				replaceSchema(qryCifrasControlValida, schema),
				new Object[]{idLayout},
				String.class
				);
		
		if(("S".equals(sw_cc)) && (errores > 0)){
			res = false;
		} 
		
		return res;
	}
	
	/**
	 * 
	 * GETTERS AND SETTERS AREA
	 * 
	 */
	
	public LoadCommonPropertiesValidate getPropertiesValidate() {
		return propertiesValidate;
	}

	public void setPropertiesValidate(
			LoadCommonPropertiesValidate propertiesValidate) {
		this.propertiesValidate = propertiesValidate;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getPathLoadFile() {
		return pathLoadFile;
	}

	public void setPathLoadFile(String pathLoadFile) {
		this.pathLoadFile = pathLoadFile;
	}

	public String getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(String idProceso) {
		this.idProceso = idProceso;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getInsArchProcesados() {
		return insArchProcesados;
	}

	public void setInsArchProcesados(String insArchProcesados) {
		this.insArchProcesados = insArchProcesados;
	}

	public String getUserSession() {
		return userSession;
	}

	public void setUserSession(String userSession) {
		this.userSession = userSession;
	}

	public String getInsDetalleError() {
		return insDetalleError;
	}

	public void setInsDetalleError(String insDetalleError) {
		this.insDetalleError = insDetalleError;
	}

	public String getCveTipoArchivo() {
		return cveTipoArchivo;
	}

	public void setCveTipoArchivo(String cveTipoArchivo) {
		this.cveTipoArchivo = cveTipoArchivo;
	}

	public String getUpdateStatusBitacora() {
		return updateStatusBitacora;
	}

	public void setUpdateStatusBitacora(String updateStatusBitacora) {
		this.updateStatusBitacora = updateStatusBitacora;
	}

	public String getQryErrors() {
		return qryErrors;
	}

	public void setQryErrors(String qryErrors) {
		this.qryErrors = qryErrors;
	}

	public String getQryCifrasControlValida() {
		return qryCifrasControlValida;
	}

	public void setQryCifrasControlValida(String qryCifrasControlValida) {
		this.qryCifrasControlValida = qryCifrasControlValida;
	}

	public String getIdLayout() {
		return idLayout;
	}

	public void setIdLayout(String idLayout) {
		this.idLayout = idLayout;
	}

	public PersistenceFinalTaskDao getPersistenceFinalTask() {
		return persistenceFinalTask;
	}

	public void setPersistenceFinalTask(PersistenceFinalTaskDao persistenceFinalTask) {
		this.persistenceFinalTask = persistenceFinalTask;
	}

}
