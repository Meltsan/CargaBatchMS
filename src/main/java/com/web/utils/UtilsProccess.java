package com.web.utils;

import static com.web.common.Constantes.TERMINADO_ERRORES;
import static com.web.common.Constantes.TERMINADO_EXITO;

/**
 * Clase con metodos utilitarios
 * los cuales son requeridos multiples clases 
 * durante el proceso de carga de informacion 
 * 
 * @author Paulino Mota Hernandez
 *
 */
public class UtilsProccess {

	/**
	 * Remplaza el schema dummy, por el correspondiente
	 * a la SOFOM en uso
	 * 
	 * @param qry  el query con el schema dummy [schemma]
	 * @param schema esquema a sustituir
	 * @return el query con el nuevo esquema
	 */
	public static String replaceSchema(String qry, String schema) {
		return qry.replace("[schema]", schema);
	}
	
	/**
	 * agrega un prefijo con el 0 
	 * cuando string @val solo tiene un digito de
	 * longitud 
	 * 
	 * @param val
	 * @return
	 */
	public static String postValue(String val) {
		return val.length() == 1 ? "0" + val : val;
	}
	
	/**
	 * 
	 * Determina el estatus de la carga
	 * a partir del numero de errores @registrosError
	 * 
	 * @param registrosError
	 * 
	 */
	public static String determinaStatusCargaPorErrores(int registrosError){
		return  (registrosError == 0) ? TERMINADO_EXITO : TERMINADO_ERRORES;		
	}
	
}
