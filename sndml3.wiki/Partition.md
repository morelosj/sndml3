Task based tables may contain a large amount of history, and may take a long time to initially load. In order to provide a robust mechanism for loading these tables, reduce timeout errors, and enable recoverability, the loader allows these tables to be loaded based on the record creation date (`sys_created_on`) using the **partition** option.

The partition may be specified as `quarter`, `month`, `week`, `day` or `hour`. When performing a partition load, the loader starts by determining the minimum and maximum values of `sys_created_on`, and constructing a set of date partitions accordingly. The partitions are an internal construct that the loader uses to manage work. All partitions will be written to the same target table in the SQL database. The loader proceeds in reverse chronological order, loading the most recent partition first. If the loader fails due to an HTTP error mid-way through the load, it is generally possible to determine which partition the loader was working on at the time. The [[configuration file|YAML Configuration]] can then be modified to use a more restrictive `created` date range, eliminating the need to reprocess partitions which were successfully loaded.

### Examples
The following will cause the **incident** table to be loaded with all records created in 2016, beginning with those records created in the last week of 2016 and working backwards to the beginning of the year.

    tables:
    - {source: incident, created: [2016-01-01, 2017-01-01], partition: week}

The following will cause the **incident** table to be loaded with all records, beginning with those records created in the current month and working backwards month by month.

    tables:
    - {source: incident, partition: month}
