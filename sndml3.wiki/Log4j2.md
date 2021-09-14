SNDML3 uses [Apache Log4j2](https://logging.apache.org/log4j/2.x/) for logging. Sample Log4J2 configuration files can be found in the directory **/src/test/resources**.

For debugging, use the file `log4j2-debug.xml`. This file writes detailed diagnostic information to an external log file, the name of which is passed in via the system property `logFileName`. For example,

    logfile=/tmp/sndml3.log
    log4jconfig=src/test/resources/log4j-debug.xml
    java -ea -Dlog4j.configurationFile=$log4jconfig -DlogFileName=$logfile -jar ...

If you are using a Log4j Pattern Layout, then the following MDC variables are available:

| Name | Description |
|------|-------------|
| `%X{table}` | Name of the ServiceNow table being accessed (`source`) |
| `%X{job}` | Name of the table loader (`name`) or the target table (`target`). For `--daemon` or `--scan` this will be the job run number. |
| `%X{uri}` | URI of the REST, SOAP or JSONv2 API |
| `%X{method}` | Name of the HTTP method (_e.g._ GET, PUT, POST) or the SOAP method (_e.g._ getKeys, getRecords, update) |

`%marker` will be set to one of the following values
* INIT
* FINISH
* SCHEMA
* REQUEST
* RESPONSE
* BIND
* PROCESS
* TEST

