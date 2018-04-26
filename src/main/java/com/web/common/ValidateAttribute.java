package com.web.common;

public class ValidateAttribute {

	private String idSecuencia;
	private int longitud;
	private String swTipoDato;
	private String cveTipoDato;
	private String nombreColumna;
	private String mascara;
	
	public ValidateAttribute(){}
	public ValidateAttribute(String secuencia, int longitud, String tipoDato, String cveTipo, String nombreColumna, String mascara){
		this.idSecuencia = secuencia;
		this.longitud = longitud;
		this.swTipoDato = tipoDato;
		this.cveTipoDato = cveTipo;
		this.nombreColumna = nombreColumna;
		this.mascara = mascara;
	}
	
	public String getIdSecuencia() {
		return idSecuencia;
	}
	public void setIdSecuencia(String idSecuencia) {
		this.idSecuencia = idSecuencia;
	}
	public int getLongitud() {
		return longitud;
	}
	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
	public String getSwTipoDato() {
		return swTipoDato;
	}
	public void setSwTipoDato(String swTipoDato) {
		this.swTipoDato = swTipoDato;
	}
	public String getCveTipoDato() {
		return cveTipoDato;
	}
	public void setCveTipoDato(String cveTipoDato) {
		this.cveTipoDato = cveTipoDato;
	}

	public String getNombreColumna() {
		return nombreColumna;
	}
	public void setNombreColumna(String nombreColumna) {
		this.nombreColumna = nombreColumna;
	}
	
	public String getMascara() {
		return mascara;
	}
	public void setMascara(String mascara) {
		this.mascara = mascara;
	}
	public String toString(){
		
		return "\n****** " + this.nombreColumna + " *********"
				+"\n idSecuencia : " + idSecuencia
				+"\n cveTipoDato : " + this.cveTipoDato
				+"\n longitud : " + this.longitud
				+"\n swTipoDato : " + this.swTipoDato
				+"\n cveTipoDato : " + this.cveTipoDato;
		
	}
	
}