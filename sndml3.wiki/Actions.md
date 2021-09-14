## Update

This is the default action (unless `truncate: true` is specified). It is effectively an "Upsert" (_i.e._ Update/Insert). The loader will first try to update the target table based on the primary key (`sys_id`). If the update is unsuccessful because the target record is missing then an insert will be performed.

This action may be combined with `created`, `since`, `filter` or `partition`. 

For incremental loads since the last run refer to [[Metrics]]. 

## Insert

This is the default action if `truncate: true` is specified. The loader will insert the record into the target table. If a primary key violation occurs then the record will be skipped.

This action may be combined with `created`, `since`, `filter` or `partition`.

For loading history from large tables refer to [[Partition]].

## Sync

This action compares the timestamps (`sys_updated_on`) in the source and target tables and based on this comparison it will insert, update, or delete the target record. The target record will be skipped if `sys_updated_on` matches.

This action may be combined with a `created` parameter if you only want to compare records created within a date range or since a certain date.

Since 3.4.0 the use of `filter` is permitted. Records which do not match the filter will be deleted.

For large tables the use of `partition` is recommended.

**Warning:** `action: sync` does **NOT** do a full record compare.

## Create

This action creates an empty table if one does not already exist. Available since 3.4.0. If `drop: true` is specified then any existing table will first be dropped.

    tables:
    - action: create
      source: incident
      target: incident_temp
      drop: true

## Execute

This action executes an arbitrary SQL statement. It is typically used to call a stored procedure. Available since 3.4.0.

    tables:
    - action: execute
      sql: call my_stored_proc()

## Prune

This action deletes records in the target table if those records have been deleted in the source table. The list of deleted records is determined by reading `sys_audit_delete`.

This action is typically combined with the `since` parameter, in which case the query will filter on `sys_audit_delete.sys_created_on`.

    tables:
    - {source: change_task, action: prune, since: last}

This action is deprecated since 3.4.0. It is recommended to use `action: sync` to delete records. **Prune** is problematic because it requires access to **sys_audit_delete**, and because some tables are not audited.

## DropTable

This action drops a target table. It is intended for use in test scripts and benchmarks. It is not generally used in production.

    metrics: incident_load.metrics
    tables:
    - {name: droptable, action: droptable, target: incident}
    - {name: loadtable, action: insert, target: incident, source: incident, action: insert, truncate: false}
