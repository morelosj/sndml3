## Time Zone

All date and timestamp processing is done in UTC. Dates and timestamps are stored in UTC in the SQL database. The `sys_user` record for the ServiceNow account which is used by SNDML should specify the Time Zone as "GMT".

Modern databases have time zone translation capabilities built-in. For displaying dates and times in your local time, let the DBMS do the translation. This allows users in different time zones to share the same database.

For MySQL, use the following to display all dates and times in US/Eastern:

    set time_zone = 'US/Eastern';
