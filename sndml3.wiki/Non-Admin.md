## Using a ServiceNow account that does not have admin role

If you use SNDML with a non-admin ServiceNow account, then you must ensure that you have configured your ServiceNow Access Controls to grant read access to all tables and columns that you intend to replicate.  In addition, in order to generate table definitions, SNDML requires read access to these tables:
* `sys_db_object`
* `sys_dictionary`
* `sys_glide_object` 
