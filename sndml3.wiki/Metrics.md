## Introduction
A metrics file is a Java properties file which contains metrics from the loader, including the start time, the finish time, and the number of records processed. All Date/Time values in the metrics file are UTC.

The name of the metrics file is specified in the [[YAML configuration]].

The metrics file is written when the loader completes. However, it is also read (if it exists) at startup. The metrics file can thus be used to determine the start time of the last load.

As of version 3.1.2 you can use the `loader.metrics_folder` property in the [[Connection Profile]] to specify the directory where the metrics file is located. 

## Incremental Updates

Consider the following two YAML files. The first file, **incident_load.yaml**, reads and loads all records from the incident table. When it completes, metrics will be written to the file **incident.metrics**.

The second file, **incident_update.yaml** reads and loads only those incident records which have been created or updated since the prior load started. The keyword `last` is a reference to the prior start time, _i.e._ the value of the property `start` from the metrics file. When the second load completes, the metrics file will be overwritten with fresh metrics including a new start time. Thus, this second job can be run repeatedly, and with each run it will perform an incremental load.

For additional information see [[Dates and Date Ranges]].

### incident_load.yaml

    metrics: incident.metrics
    tables: 
    - {source: incident, truncate: true, partition: month}

### incident_update.yaml

    metrics: incident.metrics
    tables:
    - {source: incident, since: last}

## Created since last run
Some system tables do not have a `sys_updated_on` field because the records are never updated after their initial creation. For these tables you can use `created: last` to load records inserted since the last run.

## Incremental Deletes
The `since: last` option can also be used with the `prune` action. The `prune` action reads `sys_audit_delete` to determine which records have been deleted.

The following example will delete, insert or update any records in `change_request` or `change_task` which have been deleted, inserted or updated since the last run.

### change_update.yaml
    
    metrics: change_request.metrics
    tables:
    - {source: change_request, action: prune, since: last}
    - {source: change_task,    action: prune, since: last}
    - {source: change_request, action: update, since: last}
    - {source: change_task,    action: update, since: last}
