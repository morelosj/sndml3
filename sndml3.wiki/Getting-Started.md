This Quick Start Guide assumes that you are using a Personal Developer Instance of ServiceNow with admin role, and a MySQL 
database named "sndm". You will need a copy of the JAR file <code>sndml-3.<var>n.n</var>-mysql.jar</code>. For other databases you will need to use a different JAR file and make adjustments to the [[connection profile]].

You can download the JAR files from this location:
* https://github.com/gflewis/sndml3/releases

The ZIP file should contain JARs for MySQL, PostgreSQL, Oracle, and Microsoft SQL Server.

To use the Datamart Loader you need a properties file ([[connection profile]]) that looks like this:

    servicenow.instance=dev00000
    servicenow.username=admin
    servicenow.password=secret
    datamart.url=jdbc:mysql://localhost/sndm
    datamart.schema=sndm
    datamart.username=admin
    datamart.password=secret

Beginning with version 3.2 it is recommended that `datamart.schema` be specified in the connection profile. The value of this property will vary based on the DBMS.

* For MySQL, the schema should be the name of the database
* For Oracle, the schema should be the name of the user
* For Microsoft SQL Server, the schema should be `dbo`

You can specify the ServiceNow instance using an instance name, or a full URL.

    servicenow.instance=https://dev00000.service-now.com

Be sure to verify that the designated database user has `CREATE TABLE` permissions.

To test the Datamart Loader type the following command:

<pre>
java -jar <var>jarfile</var> -p <var>profile</var> -t cmn_location
</pre>

At this point you should see the loader issue a `CREATE TABLE` statement for `cmn_location` and it should start loading the table with data. If you execute the command a second time it will detect that `cmn_location` already exists, so no `CREATE TABLE` will be issued.

We chose `cmn_location` because it is typically small. However, the command should work with any ServiceNow table.

The `-t` command line option is fine for testing connectivity or loading small tables. But in most cases you will want to create a [[YAML Configuration]] file which is specified with the `-y` command line option.


| Option | Argument type | Description |
| ------ | ------------- | ----------- |
| &#8209;p&nbsp;<i>or</i>&nbsp;&#8209;&#8209;profile | Java Properties file  | Connection parameters for database and ServiceNow instance. See [[Connection Profile]]. |
| &#8209;t&nbsp;<i>or</i>&nbsp;&#8209;&#8209;table | Table name | Load a single table. |
| &#8209;y&nbsp;<i>or</i>&nbsp;&#8209;&#8209;yaml | YAML configuration file | List of tables to be synchronized and other configuration instructions. See [[YAML Configuration]]. |


## Next
[[YAML Configuration]]
