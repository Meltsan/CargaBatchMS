package com.web.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Anotation {	
	int id() default 0;
	String comentario() default "comentario default";
}

@Retention(RetentionPolicy.RUNTIME)
@interface Otra{
	String description();
}
