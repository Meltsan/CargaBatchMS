package com.web.common;

public class ValuesBeanInsert {

	private String label;
	private String value;
	private String dataType;
	private String swKey;
	private String mascara;
	
	public ValuesBeanInsert(){}
	public ValuesBeanInsert(String label, String value,
			String dataType, String swKey, String mascara){
		this.label = label;
		this.value = value;
		this.dataType = dataType;
		this.swKey = swKey;
		this.mascara = mascara;
	}
	
	public String toString(){
		return String.format("LABEL [%s] VALUE [%s]"
				+ "DATATYPE [%s] SWKEY [%s] MASCARA [%s]", 
				this.label, this.value, this.dataType,
				this.swKey, this.mascara);
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getSwKey() {
		return swKey;
	}
	public void setSwKey(String swKey) {
		this.swKey = swKey;
	}
	public String getMascara() {
		return mascara;
	}
	public void setMascara(String mascara) {
		this.mascara = mascara;
	}
	
}
