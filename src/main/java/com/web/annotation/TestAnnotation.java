package com.web.annotation;

@Anotation(id=1)
@Otra(description="descripcion")
public class TestAnnotation {
	
	@Otra
	(description="anotacion en el metodo")
	public void mimetodo(){
		
	}
}
