package com.web.sucursal.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SucursalModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2316578832771252651L;
	
	@Size(min = 0, max = 15, message = "Invalid code value")  
	private String idSucursal;
	
	@NotNull(message="${mensaje.error}")
	@Pattern(regexp="[A-Z a-z-]", message="${mensaje.error}")
	private String sucursal;
	
	private String pais;
	private String estado;
	private String municipio;
	private String colonia;
	private String codigoPostal; 
	private String calle;
	private String numExt;
	private String numInt;
	private String Estatus;
	private String localidad;
	
	public String getIdSucursal() {
		return idSucursal;
	}
	public void setIdSucursal(String idSucursal) {
		this.idSucursal = idSucursal;
	}
	public String getSucursal() {
		return sucursal;
	}
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getNumExt() {
		return numExt;
	}
	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}
	public String getNumInt() {
		return numInt;
	}
	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}
	public String getEstatus() {
		return Estatus;
	}
	public void setEstatus(String estatus) {
		Estatus = estatus;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	
	public String toString(){
		return  "[Estatus] -> " + this.Estatus +
				" [numExt] -> " + this.numExt +
				" [localidad] -> " + this.localidad +
				" [idSucursal] -> " + this.idSucursal +
				" [sucursal] -> " + this.sucursal ;
	}

}
