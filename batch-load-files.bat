set PATH_FILE=%1
set SCHEMA=%2
set LAYOUT=%3
set ID_PROCESO=%4
set CVE_PROCESO=%5
set FILE_NAME=%6
set USER_SESSION=%7
set CVE_TIPO=%8
set SEPARADOR=%9

java -cp C:\shellsPLD\target\dependency-jars\*;C:\shellsPLD\target\Batch-Admin-1.0.jar org.springframework.batch.core.launch.support.CommandLineJobRunner META-INF/spring/batch/jobs/job-load-file.xml loadFile inputDataFile=%PATH_FILE% schema=%SCHEMA% idLayout=%LAYOUT% idProceso=%ID_PROCESO% cveProceso=%CVE_PROCESO% fileName=%FILE_NAME% userSession=%USER_SESSION% cveTipoArchivo=%CVE_TIPO% separador=,