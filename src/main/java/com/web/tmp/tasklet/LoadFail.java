package com.web.tmp.tasklet;

import static com.web.common.Constantes.TERMINADO_ERRORES;
import static com.web.utils.UtilsProccess.replaceSchema;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.web.common.LoadCommonPropertiesValidate;
import com.web.domain.Jdbc;

public class LoadFail extends Jdbc implements Tasklet{

	private static final Logger LOG = Logger.getLogger(LoadFail.class);
	
	

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
	private String insDetalleErrorIndivudal;
	
	@Override
	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunckContext)
			throws Exception {
			
			LOG.error("ERROR TASKLET EN EL PROCESO DE CARGA  :::<<<--->>");
			LOG.error(chunckContext.getAttribute("ERROR"));
			LOG.error(schema);
			LOG.error(updateStatusBitacora);
			this.jdbcTemplate.update(replaceSchema(updateStatusBitacora, schema), new Object[]{
				"F",
				userSession,
				"ESTRUCTURA DE ARCHIVO INCORRECTO",
				idProceso
			});
			
			
			this.jdbcTemplate.update(replaceSchema(insDetalleErrorIndivudal, schema), new Object[]{
				idProceso,
				0,
				"ESTRUCTURA DE ARCHIVO INCORRECTO"
			});
			
		return RepeatStatus.FINISHED;
	}

	public PersistenceFinalTaskDao getPersistenceFinalTask() {
		return persistenceFinalTask;
	}

	public void setPersistenceFinalTask(PersistenceFinalTaskDao persistenceFinalTask) {
		this.persistenceFinalTask = persistenceFinalTask;
	}

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

	public String getInsDetalleErrorIndivudal() {
		return insDetalleErrorIndivudal;
	}

	public void setInsDetalleErrorIndivudal(String insDetalleErrorIndivudal) {
		this.insDetalleErrorIndivudal = insDetalleErrorIndivudal;
	}

	
	
	
	
	
}
