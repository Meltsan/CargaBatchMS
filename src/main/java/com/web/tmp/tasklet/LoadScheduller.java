package com.web.tmp.tasklet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.web.common.Config;

@Component
public class LoadScheduller {
	
	private static final Logger LOG = Logger.getLogger(LoadScheduller.class);
	
	 @Autowired
	  private JobLauncher jobLauncher;
	 
	  @Autowired
	  private Job job;
	
	  private String exitStatus;
	
	public void run(){
		LOG.info("RUN..");
		scanDirectoriesForFiles();
	}
	
	public void executeJobLoadFile(String filePath, String schema, String idLayout)
	{
		if(LOG.isDebugEnabled()){
			LOG.debug(job);
			LOG.debug(jobLauncher);
			LOG.debug("Running");
		}
		long start = System.currentTimeMillis();
		 JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();  
         jobParametersBuilder.addString("schema", schema);  
         jobParametersBuilder.addString("idLayout", idLayout);  
         jobParametersBuilder.addString("inputDataFile",  
         "file:" + filePath);                
         JobParameters param = jobParametersBuilder.toJobParameters();  

         try {
			JobExecution execution = jobLauncher.run(job, param);
			exitStatus += execution.getStatus().toString();
            exitStatus += execution.getAllFailureExceptions();
		} catch (Exception e) {
			e.printStackTrace();
		}
         
         LOG.info(exitStatus);
         LOG.info("Job Finished");
         LOG.info("Tiempo total : " + (System.currentTimeMillis() - start));
	}
	
	/**
	 * 
	 * @return lista de folders dentro de la ruta de carga automatica
	 * 
	 */
	public List<String> getListSofomFolders()
	{
		List<String> folders = new ArrayList<String>();
		File pathToList = new File(Config.RUTA_CARGA_OPERACIONES_BASE);
		File[] files = pathToList.listFiles();
		for(File f : files)
		{
			folders.add(f.getName());
		}
		
		return folders;
	}
	
	
	/**
	 * Busca los archivos dentro de la ruta de cada sofom
	 */
	public void scanDirectoriesForFiles()
	{
		List<String> folderSofomList = getListSofomFolders();
		for(String folder : folderSofomList)
		{
			StringBuilder sb = new StringBuilder(Config.RUTA_CARGA_OPERACIONES_BASE);
			sb.append(folder);
			File filesOnFolder = new File(sb.toString());
			File[] files = filesOnFolder.listFiles();
			if(LOG.isDebugEnabled()){
				LOG.debug(folder);
			}
			if(files != null){
				
				for(File file : files )
				{
					String fileName = file.getName();
					if(validateFileName(fileName))
					{	
						if(LOG.isDebugEnabled()){
							LOG.debug(fileName);
							LOG.debug(file.getAbsolutePath());
						}
						String idLayout = typeOfLayout(fileName);
						if(StringUtils.isNotBlank(idLayout))
						{
							executeJobLoadFile(file.getAbsolutePath(), folder, idLayout);
						}else{
							LOG.error("No existe un layout para el nombre del archivo");
						}
					}
				}

			}
		}
	}
	
	/**
	 * 
	 * @param fileName nombre del archivo a validar
	 * @return true si el nombre del archivo cumple con el regex
	 */
	public boolean validateFileName(String fileName){
		return StringUtils.contains(fileName, Config.SEPARATOR_NAME_FILE);
	}
	
	/*
	 * Obtiene en id del layout a partir del nombre del archivo
	 */
	public String typeOfLayout(String fileName)
	{
		String idLayout = "";
		String[] nameSplit = fileName.split(Config.SEPARATOR_NAME_FILE);
		switch (nameSplit[0]) {
		case Config.CONTRATANTES_NAME_LAYOUT:
			idLayout = "3";
			break;

		default:
		
			break;
		}
		return idLayout;
		
	}
	
	
}
