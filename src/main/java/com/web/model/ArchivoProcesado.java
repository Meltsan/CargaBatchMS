package com.web.model;

import java.math.BigDecimal;

public class ArchivoProcesado {

	private String idproceso;
	private String nombreArchivo;
	private int registrosInsertados;
	private int registrosActualizados;
	private String cveTipoArvchivo;
	private String usuarioOperacion;
	private BigDecimal montoRegValidos;
	
	public  ArchivoProcesado(){
		montoRegValidos = new BigDecimal(0);
	}
	
	public BigDecimal getMontoRegValidos() {
		return montoRegValidos;
	}
	public void setMontoRegValidos(BigDecimal montoRegValidos) {
		this.montoRegValidos = montoRegValidos;
	}
	public String getIdproceso() {
		return idproceso;
	}
	public void setIdproceso(String idproceso) {
		this.idproceso = idproceso;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public int getRegistrosInsertados() {
		return registrosInsertados;
	}
	public void setRegistrosInsertados(int registrosInsertados) {
		this.registrosInsertados = registrosInsertados;
	}
	public int getRegistrosActualizados() {
		return registrosActualizados;
	}
	public void setRegistrosActualizados(int registrosActualizados) {
		this.registrosActualizados = registrosActualizados;
	}
	public String getCveTipoArvchivo() {
		return cveTipoArvchivo;
	}
	public void setCveTipoArvchivo(String cveTipoArvchivo) {
		this.cveTipoArvchivo = cveTipoArvchivo;
	}
	public String getUsuarioOperacion() {
		return usuarioOperacion;
	}
	public void setUsuarioOperacion(String usuarioOperacion) {
		this.usuarioOperacion = usuarioOperacion;
	}
	
	
	
}
