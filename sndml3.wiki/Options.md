* [Action](#action)
* [Columns](#columns)
* [Created](#created)
* [Filter](#filter)
* [MaxRows](#maxrows)
* [MinRows](#minrows)
* [Name](#name)
* [PageSize](#pagesize)
* [Partition](#partition)
* [Since](#since)
* [Source](#source)
* [SQL](#sql)
* [Target](#target)
* [Threads](#threads)
* [Truncate](#truncate)

### Action
Action is set to one of the following values:
* [[update|Actions#Update]] - Update an existing record based on the `sys_id`. If no record exists a new one will be inserted.
* [[insert|Actions#Insert]] - Insert a new record. If there is is a constraint violation (_e.g._ duplicate key) then the record will be skipped.
* [[sync|Actions#Sync]] - Compare the values for `sys_updated_on` and insert, update, delete or skip target records as appropriate.
* [[create|Actions#Create]] - Create an empty table. (Available since 3.4.0.)
* [[execute|Actions#Execute]] - Execute an arbitrary SQL statement. (Available since 3.4.0.)
* [[prune|Actions#Prune]] - Scan `sys_audit_delete` and delete from the target any records which have been deleted in the source. (Deprecated since 3.4.0.)

The default action is `update` unless `truncate` is true, in which case the default action is `insert`.

Refer to [[Actions]] for additional information.

### Columns

Allows specification of a list of columns to be loaded. A restricted list of columns can significantly improve performance when loading large tables. Beginning in 3.4.0 this option will also affect target table creation. However, SNDML will **not** alter an existing target table.

You can specify either a comma delimited list or a space delimited list of names. (A space delimited list may be more convenient for YAML because it does not require you to put the argument in quotes.) The fields `sys_id`, `sys_created_on` and `sys_updated_on` are always included, regardless of whether or not they are explicitly included in the list.

    # Update category and assignment for all records created last week
    tables: 
    - {source: incident, created: [-14d, -7d], columns: category assignment_group assigned_to}

### Created
This is either a single date or a list of two dates as described in [[Dates and Date Ranges]]. It causes a `sys_created_on` filter to be appended to the [Filter](#Filter). All values are GMT. The ending value is exclusive; so, for example, the following example will load all incident records created 2016-01-01 00:00:00 GMT through 2016-12-31 23:59:59 GMT.

    # Load all records created in 2016
    tables: 
    - {source: incident, created: [2016-01-01, 2017-01-01]} 

### Filter
This is an encoded query which will be used to restrict what is loaded. Please refer to https://docs.servicenow.com/search?q=encoded+query+strings.

#### Example
    tables:
    - source: incident
      filter: "category=network"

### Name
This value is used to prefix the name of properties in the [[Metrics]] file. If not specified this will default to [Target](#target).

### MaxRows

The loader will fail if this number of records is exceeded. 
You may want to use this option to prevent the loader from running too long if an unexpectedly large number of rows are encountered.

### MinRows

The loader will fail if fewer than this number of records are processed.
Since the loader normally dies with an HTTP 403 error if the table cannot be read, the purpose of this option is unclear.

### PageSize

Records are normally processed in chunks of 200 at a time. If you are loading a table that has a small number of fields, you can improve the performance by using a larger page size.

#### Example

    tables:
    - {source: sys_user_grmember, action: insert, truncate: true, pagesize: 1000}

### Partition

The [[Partition]] option is used when loading history from large task based tables.

#### Example
    # Load all incidents since the beginning of time
    tables:
    - {source: incident, action: insert, partition: month}

### Since
Process all records created or updated since the specified date. The value is a date or variable as described in [[Dates and Date Ranges]]. It causes a `sys_updated_on` filter to be appended to the [Filter](#filter).

#### Example
    # Load all records updated in the last 24 hours
    tables: 
    - {source: incident, since: start-24h} 

For additional examples refer to [[Dates and Date Ranges]]. For information about incremental loads see [[Metrics]].

### Source
Specify the name of the source table in ServiceNow. If not specified this will default to [Target](#target). You must specify either **Source** or **Target** (or both).

### SQL
Only applicable for `action: execute`. Specifies the SQL statement to be executed.

#### Example

    # Reload all August 2017 Incident records
    tables: 
    - name: delete_incidents
      action: execute
      sql: >
          delete from incident 
          where sys_created_on >= '2017-08-01'
          and sys_created_on < '2017-09-01'
    - name: load_incidents
      action: insert
      source: incident
      target: incident
      created: [2017-08-01, 2017-09-01]

### SQLAfter
Deprecated since 3.4.0 (Use `action: execute` instead). Specify an SQL statement to be executed after the table is loaded. 

### SQLBefore
Deprecated since 3.4.0 (Use `action: execute` instead). Specify an SQL statement to be executed before the table is loaded. 

### Target
Specify the name of the SQL table to be loaded. If not specified, this will default to [Source](#source). You must specify either **Source** or **Target** (or both).

#### Example
    # Load 2016 records into incident_2016
    tables:
    - source: incident
      target: incident_2016
      created: [2016-01-01, 2017-01-01]

### Threads
Experimental. Available since 3.4.1. Only applicable if [Partition](#partition) is specified. Causes each partition to be loaded in a separate thread and to use a separate ServiceNow session. This is the maximum number of threads that can run concurrently. Can improve performance when loading large tables. Use with caution.

#### Example
    tables:
    - {target: incident, truncate: true, partition: month, threads: 3}

### Truncate
If "true", causes the target table to be truncated prior to loading. The default value is "false".

#### Example
    tables:
    - {source: sys_user_grmember, truncate: true} 