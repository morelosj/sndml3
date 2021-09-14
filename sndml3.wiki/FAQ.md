### How can I keep my SQL table up to date without reloading everything?

Use a [[Metrics]] file to keep track of the last time the loader ran, and specify `since: last` to only load records inserted or updated since the last run.

OR

Use `action: sync` to compare timestamps (`sys_updated_on`) between the source and the target.

### How do I get display values for reference fields?

You will need to load the reference table and use a join to include the display value in your SQL query.

The recommended architecture is to use two separate database schemas:
* A schema with the raw data from ServiceNow
* A schema with database views for end user reporting

The reporting schema should handle any necessary joins, and only include appropriate rows and columns.

### What happens if a new field is added to the ServiceNow source table?

SNDML will **not** add columns to an existing database table. However, you can use `ALTER TABLE` to manually add columns to  a database table.

When SNDML starts processing, it compares the list of fields in the source table with the list of columns in the target table. It loads those columns where the field names match. If you have altered your database table by adding or dropping columns, then it will affect which columns are loaded by SNDML.

### What if I do not want certain fields to be accessible?

Use `ALTER TABLE` to drop the columns.

### What if the name of a ServiceNow field is longer than the database field name limit?

You can rename a field by editing the `fieldmaps` section of `sqltemplates.xml` as describe in [[Templates]].

### Why are records with empty sys_created_on not loaded?

The default `created` range is `[void, start]`, which causes the loader to **not** load records inserted after the loader starts running. However, the resulting encoded query also causes records to not be loaded at all if `sys_created_on` is empty. The solution is to use `[void, void]` to override the default `created` range and a `filter` to specify the records that need to be loaded.

    created: [void, void], filter: sys_created_onISEMPTY

For additional details refer to [[Dates and Date Ranges]].
