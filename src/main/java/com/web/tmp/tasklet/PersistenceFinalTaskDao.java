package com.web.tmp.tasklet;

import java.math.BigDecimal;
import java.util.List;

/**
 * Clase que contiene los metodos para persistir y 
 * consultar en la base de datos, todos las operaciones
 * en esta clase tienen relacion con la tarea FinalTask
 * 
 * @author Paulino Mota
 *
 */
public interface PersistenceFinalTaskDao {
	
	/**
	 * Persiste el total de operaciones en la tabla
	 * de reporte final
	 */
	public void persisteMontoOperaciones(BigDecimal montoTotal, String idProceso);

	/**
	 * 
	 * obtiene la sumatoria de los montos de todas las
	 * operaciones correspondienes a la carga de 
	 * un archivo de operaciones
	 * 
	 */
	public BigDecimal conteoTotalDeOperaciones(String idProceso, String schema);

	/**
	 * 
	 * Metodo para obtener el numero de errores
	 * contenidos en un proceso @idProceso
	 * 
	 * @param idProceso
	 * @param schema
	 * @return numero de errores correspontienes al proceso
	 */
	public int numeroDeRegistrosCargadosConError(String idProceso, String schema);

	/**
	 * Recibe sentencias sql para persistir,
	 * el metodo itera la lista de sentencias
	 * regresa el numero de registros insertados
	 * el cual deberia ser igual al numero de sentencias recibidas
	 * entro de la lista
	 *
	 * @param listQuriesToPersistence
	 * @return
	 */
	public int persisteInformacionArrayQuries(List<String> listQuriesToPersistence, String idProceso);
	
	/**
	 * Metodo que contiene el llamado a base de datos para hacer 
	 * el select inser de los registros que no fueron marcados
	 * como update y tampoco fueron rechazados.
	 * 
	 * @param queryInserSelect
	 * @param idProceso
	 * @return numero de registros afectados
	 * @throws Exception 
	 */
	public int persisteInformacionInsertSelect(String queryInserSelect, String idProceso) throws Exception;
	
}
