## Connection Profile
The Connection Profile is a properties file that looks like this:

    servicenow.instance=dev00000
    servicenow.username=admin
    servicenow.password=secret
    datamart.url=jdbc:mysql://localhost/sndm
    datamart.schema=sndm
    datamart.username=admin
    datamart.password=secret

The connection profile is specified with the `-p` command line option.

<pre>
java -jar sndm-3.<i>n.n</i>-mysql.jar -p <i>profile</i> -y <i>yamlfile</i>
</pre>

At a minimum, the connection profile must contain the seven values shown above, but it may also contain other settings.

## Database Schema

Prior to 3.2 `datamart.schema` was optional. As of 3.2 it is recommended that `datamart.schema` always be specified in the connection profile. Problems can occur if the database server contains tables with the same name in different schemas. The value of `datamart.schema` will vary based on the DBMS.

* For MySQL, `datamart.schema` should be the name of the database
* For Oracle, `datamart.schema` should be the name of the user
* For Microsoft SQL Server, `datamart.schema` should be `dbo`
* For SQLite, `datamart.schema` should be `main`

## Variable Substitution 

As of 3.1.2 the loader will interpolate environment variables using the notation `${varname}`. For example, if the environment variable `SNDML_CONFIG` has been defined, then you can do something like this:

    loader.metrics_folder=${SNDML_CONFIG}/metrics

If the entire property value is enclosed in backticks, then it will be passed to `Runtime.exec()` for evaluation. For example, if you have the LastPass CLI installed in `/usr/local/bin`, then you may be able to do something like this:

    servicenow.password=`/usr/local/bin/lpass show --password my.servicenow.password`
    datamart.password=`/usr/local/bin/lpass show --password my.database.password`

## Reference
| Property Name | Added | Notes / Description |
| ------------- | ----- | ----------- |
| `datamart.dialect` | 3.0 | Specifies a section in the templates file. If not specified, the loader will infer the correct section using the <drivers> element of the template and the JDBC prefix of the datamart URL. |
| `datamart.password` | 3.0 | Required. |
| `datamart.schema` | 3.0 | Recommended. Refer to notes above. |
| `datamart.templates` | 3.0 | The name of an XML file to use for generating SQL statements. If not specified, the loader will use `sqltemplates.xml` which is embedded in the JAR. For information refer to [[Templates]]. |
| `datamart.url` | 3.0 | Required. JDBC URL for the target database. Refer to the documentation for your selected JDBC driver. |
| `datamart.username` | 3.0 | Required. User name for the target database. |
| `daemon.agent` | 3.4.0 | Name of the Agent record in the ServiceNow DataPump App. Only applicable for <tt>&#8209;&#8209;scan</tt> or <tt>&#8209;&#8209;daemon</tt>. |
| `daemon.continue` | 3.4.0 | Controls whether or not the daemon will continue running after encountering an IO error in communication with ServiceNow or the database. If “true” then it writes an error to the log and goes back to sleep. If “false” then it will immediately abort. |
| `daemon.interval` | 3.4.0 | Number of seconds. Controls the daemon polling interval. Only applicable for <tt>&#8209;&#8209;daemon</tt>. ||
| `daemon.scope` | 3.4.1 | Name of the ServiceNow application scope. Only applicable for <tt>&#8209;&#8209;scan</tt> or <tt>&#8209;&#8209;daemon</tt>. |
| `daemon.threads` | 3.4.0 | Maximum number of jobs that the daemon can process concurrently. Additional jobs will wait until a thread becomes available. |
| `loader.metrics_folder` | 3.1.2 | The name of a directory where metrics files are located. If not specified, metrics files are assumed to reside in the current working directory. For information refer to [[Metrics]]. |
| `loader.warn_on_truncate` | 3.0 | Causes a warning will be written to the log whenever a field is truncated when written to the database. Default is **true**. |
| `servicenow.domain` | 3.2 | For use in domain separated instances. This can be a single **sys_id** or a comma separated list of domains. Refer to [[Domain Separation]]. |
| `servicenow.instance` | 3.0 | Required. You can specify the ServiceNow instance name (_e.g._ `dev12345`) or a URL (_e.g._ `https://dev12345.service-now.com`) |
| `servicenow.pagesize` | 3.4.0 | Number of records to be processed at a time if not overridden at the table level. Default is 200. |
| `servicenow.password` | 3.0 | Required. |
| `servicenow.username` | 3.0 | Required. |
| `servicenow.verify_session` | 3.0 | Causes the loader, at startup, to verify that it can connect to ServiceNow by reading the **sys_user** record of the connecting user. Default is **true**. |
| `servicenow.verify_timezone` | 3.1.2 | Causes an exception will be thrown if the ServiceNow user time zone is not "GMT". Default is **false**. |
