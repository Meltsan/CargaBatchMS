package com.web.common;

public class CatalogValidatorVo {

	private String idMapeo;
	private Integer idColumna;
	private String StringQuery;

	@Override
	public String toString(){
		return String.format("idMapeo [%s] idColumna [%s] StringQuery [%s]", idMapeo, idColumna, StringQuery);
	}
	
	public String getIdMapeo() {
		return idMapeo;
	}
	public void setIdMapeo(String idMapeo) {
		this.idMapeo = idMapeo;
	}
	public Integer getIdColumna() {
		return idColumna;
	}
	public void setIdColumna(Integer idColumna) {
		this.idColumna = idColumna;
	}
	public String getStringQuery() {
		return StringQuery;
	}
	public void setStringQuery(String stringQuery) {
		StringQuery = stringQuery;
	}
	
}
