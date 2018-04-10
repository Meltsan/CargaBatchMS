package com.web.app;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class File2DBApp {

    private static ClassPathXmlApplicationContext context;  
    private static final Logger LOG = Logger.getLogger(File2DBApp.class);
    public static void main(String[] args) {  
    	
         String exitStatus = "Exit Status: ";  
         long start = System.currentTimeMillis();
         String[] springConfiguration = { 
        		 "launch-context.xml",  
                 "META-INF/spring/batch/jobs/job-load-file.xml" 
        		 };  
         context = new ClassPathXmlApplicationContext(springConfiguration);  
         JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");  
         Job job = (Job) context.getBean("loadFile");  
         LOG.debug("iniciando proceso....");
         try {  
        	 
              JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();  
      		  jobParametersBuilder.addString("inputDataFile","file:/Users/mottaman/Downloads/operaciones-28122016-2.csv");
              jobParametersBuilder.addString("schema", "SOFOM117");  
              jobParametersBuilder.addString("idLayout", "16");
              jobParametersBuilder.addString("idProceso", "1111111111111111111111146");	
              jobParametersBuilder.addString("cveProceso", "LOAD_CATALOG");
              jobParametersBuilder.addString("fileName", "operaciones-28122016-2.csv"); 
              jobParametersBuilder.addString("userSession", "MOTTAMAN"); 
              jobParametersBuilder.addString("cveTipoArchivo", "O"); 
              jobParametersBuilder.addString("separador", ",");
              jobParametersBuilder.addString("headLines", "1");
              JobParameters param = jobParametersBuilder.toJobParameters();

              JobExecution execution = jobLauncher.run(job, param);  
              exitStatus += execution.getStatus().toString();
              exitStatus += execution.getAllFailureExceptions();
              
         } catch (Exception e) {  
        	 e.printStackTrace();  
         } finally {  
              if (context != null)  
                   context.close();  
         }  
         	LOG.info(exitStatus);
         	LOG.info("Job Finished");
         	LOG.info("Tiempo total : " + (System.currentTimeMillis() - start));
    }  
	
}
