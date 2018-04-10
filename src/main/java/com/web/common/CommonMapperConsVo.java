package com.web.common;

public class CommonMapperConsVo {

	private String cons;
	private String defualt;
	private String secMapeo;
	private String defaultDataType;
	
	public String toString(){
		return String.format("Cons [%s] Default [%s] SecMapeo [%s] DefaultDataType [%s]", this.cons, this.defualt, this.secMapeo, this.defaultDataType);
	}
	
	public CommonMapperConsVo(){}
	public CommonMapperConsVo(String secMapeo, String cons){
		this.secMapeo = secMapeo;
		this.cons = cons;
	} 
	
	public String getCons() {
		return cons;
	}
	public void setCons(String cons) {
		this.cons = cons;
	}
	public String getDefualt() {
		return defualt;
	}
	public void setDefualt(String defualt) {
		this.defualt = defualt;
	}
	public String getSecMapeo() {
		return secMapeo;
	}
	public void setSecMapeo(String secMapeo) {
		this.secMapeo = secMapeo;
	}
	public String getDefaultDataType() {
		return defaultDataType;
	}
	public void setDefaultDataType(String defaultDataType) {
		this.defaultDataType = defaultDataType;
	}

}
