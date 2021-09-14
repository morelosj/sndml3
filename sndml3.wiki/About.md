**SNDML** (ServiceNow Data Mart Loader) evolved from a project started in 2012 to create a Java application that could load data from ServiceNow into an SQL database. Work on version 3.x begin in 2017. The application uses the REST and SOAP APIs to extract meta-data and data from ServiceNow. It creates tables in an SQL database based on ServiceNow meta-data. 

This site provides pre-built JAR files for Oracle, Microsoft SQL Server, MySQL and PostgreSQL. Because **sndml3** relies on JDBC for database interaction, it should work with any relational database.

Beginning with version 3.4, SNDML can run in four modes
* `--table` - Simple table load. The name of a ServiceNow table is passed as a command line argument.
* `--yaml` - YAML configuration. The name of a YAML file is passed as a command line argument. For details refer to [[YAML Configuration]].
* `--scan` - Connect to ServiceNow and query the [[DataPump Application|https://developer.servicenow.com/connect.do#!/share/contents/7145124_datapump_export_to_oracle_sql_server_mysql_or_postresql?v=1&t=PRODUCT_DETAILS]] for any jobs in "Ready" state. If jobs are found then process them and terminate.
* `--daemon` - Same a "scan" except that the program runs in an endless loop periodically polling the DataPump application for jobs ready to run.

SNDML supports several different load operations. For details refer to [[Actions]]. The different actions are not available for simple table load. If the mode is `--table` then `action: update` is implied.

The "DataPump" application can be downloaded from the [[ServiceNow Developers Share Site|https://developer.servicenow.com/connect.do#!/share/user/content]].

This program is freely distributed software. You are welcome to redistribute and/or modify it. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY, explicit or implied. 

## Next
[[Getting Started]]