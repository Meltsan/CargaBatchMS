PATH_FILE=$1
SCHEMA=$2
LAYOUT=$3
ID_PROCESO=$4
CVE_PROCESO=$5
FILE_NAME=$6
USER_SESSION=$7
CVE_TIPO=$8
SEPARADOR=$9
HEADLINES=$10
nohup java -cp "target/dependency-jars/*:target/Batch-Admin-1.0.jar" org.springframework.batch.core.launch.support.CommandLineJobRunner META-INF/spring/batch/jobs/job-load-file.xml loadFile inputDataFile=$PATH_FILE schema=$SCHEMA idLayout=$LAYOUT idProceso=$ID_PROCESO cveProceso=$CVE_PROCESO fileName=$FILE_NAME userSession=$USER_SESSION cveTipoArchivo=$CVE_TIPO separador=$SEPARADOR headLines=$HEADLINES > salida.out &