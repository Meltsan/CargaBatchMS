package com.web.annotation;

public class CommonValueWapper {

	private int idMapeo;
	private String value;
	private String nombreColumna;
	
	public int getIdMapeo() {
		return idMapeo;
	}
	public void setIdMapeo(int idMapeo) {
		this.idMapeo = idMapeo;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getNombreColumna() {
		return nombreColumna;
	}
	public void setNombreColumna(String nombreColumna) {
		this.nombreColumna = nombreColumna;
	} 
	
	public String toString(){
		return "idMapeo : " + this.idMapeo
				+"value : " + this.value
				+"nombreColumna : " + this.nombreColumna;
	}
	
}
