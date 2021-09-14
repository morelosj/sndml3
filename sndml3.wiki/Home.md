The ServiceNow Data Mart Loader is a Java application which uses the ServiceNow REST API to extract meta-data and data from ServiceNow. The Loader creates tables in an SQL database based on ServiceNow meta-data. It supports a variety of load and synchronization operation. 

SNDML can be configured using YAML. Refer to [[YAML Configuration]] and [[Options]].

Beginning with version 3.4 there is a contributed app, DataPump, on the ServiceNow Developer Share Site which can be used to configure, schedule and monitor SNDML jobs. Refer to the Community Articles below. 

### Contents
#### Tutorial
* [[About]]
* [[Getting Started]] - Install and test the application using a MySQL database
* [[YAML Configuration]]
#### Reference
* [[Connection Profile]]
* [[Options]]
* [[Actions]] - Update, Insert, Sync, _etc._
* [[Dates and Date Ranges]]
* [[Time Zone]]
* [[Templates]]
* [[Bash Scripts]]
* [[JUnit]]
#### Use Cases
* [[Partition]] - Loading historical data from large tables
* [[Metrics]] - Incremental loads since the last run
* [[Non-admin]] - Using a ServiceNow account that does not have admin role
* [[Domain Separation]] - Using a domain separated ServiceNow instance
* [[FAQ]] - Frequently Asked Questions
#### DataPump App (ServiceNow Community Articles)
* [Exporting to MySQL, Oracle or SQL Server with DataPump](https://community.servicenow.com/community?id=community_article&sys_id=90628858db7f2010030d443039961918)
* [Scheduling database exports with DataPump](https://community.servicenow.com/community?id=community_article&sys_id=8574206cdbbb2010fb1e0b55ca9619d7)
* [Optimizing database exports with DataPump](https://community.servicenow.com/community?id=community_article&sys_id=97a7e620db7760101cd8a345ca961916)

### Disclaimer
This program is freely distributed software. You are welcome to redistribute and/or modify it. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY, explicit or implied. 
