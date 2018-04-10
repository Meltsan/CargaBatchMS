package com.web.utils;

/**
 * Clase util para la validacion de los errores
 * debido a los errores genericos dentros del 
 * driver utilizado para la conexion a la base de datos
 * es necesaria esta clase para determinar el tipo de error
 * 
 * @author Paulino Mota Hernandez
 *
 */
public class ErrorsProcessor {

	public ErrorsProcessor(){}
	
	/**
	 * Metodo estatico parta generar una instancia
	 * del error a partir de un mensaje
	 * 
	 * @param message
	 * @return
	 */
	public static ErrorsProcessor 
		createClassFromError(String message){
		
		ErrorsProcessor errorInstance 
			= new ErrorsProcessor();
		
		errorInstance.parseMessageError(message);
		
		return errorInstance;
		
	}

	private String tipoError;
	private String message;
	
	
	/**
	 * Determina el tipo de error a partir del mensaje
	 * recibido desde el driver de jdbcTemplate
	 */
	private void parseMessageError(String message){
		if(message.contains("Violation of PRIMARY KEY constraint")){
			this.tipoError = "CONSTRAIN PRIMARY KEY";
			this.message = message;
		}
	}
	
	
	public String getTipoError() {
		return tipoError;
	}

	public void setTipoError(String tipoError) {
		this.tipoError = tipoError;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
}
