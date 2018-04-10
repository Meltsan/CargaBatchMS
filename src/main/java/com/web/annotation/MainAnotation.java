package com.web.annotation;


import com.web.common.CommonModel;


public class MainAnotation {

	public static void main(String[] args){
		ValidateMasiveLoad masiveValid = new ValidateMasiveLoad();
		masiveValid.validateLoadMasive(new CommonModel(), null, null);	
	}
	
	public static void mainDos(String[] args) {
		
		
	}
	
}
