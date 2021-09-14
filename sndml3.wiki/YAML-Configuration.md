## Overview
The YAML configuration file name is specified with a `-y` option on the command line as in this example:
<pre>
java -jar <i>jarfile</i> -p dev.profile -y users.yaml
</pre>

The YAML contains an optional [[Metrics]] file name, followed by a list of tables to be loaded. The metrics file (if specified) will be written when the load completes, and will contain start and end times and number of records processed.

<pre>
metrics: user.metrics
tables:
- source: sys_user
- source: sys_user_group
- {source: sys_user_grmember, truncate: true}
</pre>

To specify multiple [[Options]] for a table, you must indent according to YAML conventions:

<pre>
tables:
- source: sys_user_grmember
  target: group_member
  truncate: true
</pre>

or use curly brackets:

<pre>
tables:
- {source: sys_user_grmember, target: group_member, truncate: true}
</pre>

JSON can also be used since YAML is a superset of JSON:

<pre>
{"tables":[{"source":"sys_user_grmember","target":"group_member","truncate":"true"}]}
</pre>

Keys and values are mostly case insensitive, except for table names which are case sensitive. This is okay:

    Tables:
    - {Source: incident}

This will **not** work:

    Tables:
    - {Source: Incident}

## Next
[[Options]]
