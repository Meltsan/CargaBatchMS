package com.web.tmp.app;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TempLoadApp {

    private static ClassPathXmlApplicationContext context;  
    
    public static void main(String[] args) {  
  
         String exitStatus = "Exit Status: ";  
         
         String[] springConfiguration = { "launch-context.xml",  
                   "META-INF/spring/batch/jobs/job-load-file.xml" };  
         context = new ClassPathXmlApplicationContext(springConfiguration);  
         JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");  
         Job job = (Job) context.getBean("loadFile");  
         long start = System.currentTimeMillis();
         try {  
              JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();  
              jobParametersBuilder.addString("dateTime", (new Date()).toString());  
              jobParametersBuilder.addString("inputDataFile",  
              "file:/Users/mottaman85/Documents/PLD/BATCH/Admin/springBatchAdminTC/src/main/resources/csv/sucursales.csv");  
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
  
         System.out.println(exitStatus);
         System.out.println("Job Finished");
         System.out.println("Tiempo Total : " + (System.currentTimeMillis() - start));
    }  

}
