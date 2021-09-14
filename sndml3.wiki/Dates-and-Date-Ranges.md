## Date Format

Dates and timestamps must be in "YYYY-MM-DD" or "YYYY-MM-DD HH:MM:SS" format. Dates and timestamps are assumed to be UTC. For additional details refer to [[Time Zone]].

## Variables

The following pre-defined variables can be used instead of a hard-coded date.

| Variable | Description |
|----------|-------------|
| `start`  | The start time of this load. |
| `today`  | The start time of this load truncated to midnight UTC. |
| `last`   | The start time of the last load (requires a metrics file). See [[Metrics]] for more information. |
| `void`   | No date. (See note under [Date Range](#date-range)) |

## Relative Dates (Date Expressions) 

You can construct a date expression by appending a relative interval to a date variable (except `void`). The interval must be preceded by `-` or `+` with no spaces. Units are expressed using one of the following:
* `m` - Minutes
* `h` - Hours
* `d` - Days

| Expression  | Description |
|-------------|-------------|
| `start-1d`  | Start time minus 1 day |
| `today-1d`  | Today minus 1 day, (_i.e._ yesterday) |
| `today+4h`  | 4 AM UTC |
| `start-3d`  | Start time minus 3 days |
| `start-36h` | 36 hours ago |
| `last-15m`  | 15 minutes before the last load (based on the start time in the metrics file) |
 
If the variable is `start`, then it can be omitted:

| Expression | Description |
|------------|-------------|
| `-2d` | 2 days ago |
| `-2h` | 2 hours ago |

## Date Range
A date range is an array containing two dates: a starting date and an ending date. The starting date is inclusive. The ending date is exclusive. The following two examples (which are equivalent) represent all records created during the month of January 2017 (UTC).

    created: [2017-01-01, 2017-02-01]

    created: ["2017-01-01 00:00:00", "2017-02-01 00:00:00"]

If the second date is omitted, it assumed to be `start`. These three equivalent examples represent all records created after December 2016.

    created: 2017-01-01

    created: [2017-01-01]

    created: [2017-01-01, start]

The reserved variable `void` is used to represent no date. The following example represents all records created before January 2017.

    created: [void, 2017-01-01]

The following example represents all records created after December 2016, potentially including records created after the current load starts executing.

    created: [2016-12-31, void]

## Default Created Range

The default Created Range is `[void, start]`. In other words, by default the application will not load records created after it starts running. This is done in order to reduce the risk of a paging error which can occur if the source table is being modified while the application is trying to read it.

A side effect of the default created range is that any record will **not** (by default) be loaded if `sys_created_on` is empty. This is an abnormal condition, but if you have records where `sys_created_on` is empty, is it recommended that you load them separately by using the following parameters. The first part of this directive effectively cancels the default created range. The second part restricts the load to records where `sys_created_on` is empty.

    created: [void, void], filter: "sys_created_onISEMPTY"

## Examples
Here are some examples of using relative dates and date ranges.

Load all incidents updated since 1:00 AM EST (6:00 AM UTC)

    table:
    - {source: incident, since: today+6h}

Load all incidents created or updated in the last 36 hours

    tables:
    - {source: incident, since: -36h}

Load all incidents created or updated since the last incident load

    metrics: incident.metrics
    tables:
    - {source: incident, since: last}

Load all sys_email and sys_email_log records created since the last load

    metrics: email_update.metrics
    - {source: sys_email, created: last}
    - {source: sys_email_log, created: last}

Load all incidents created in January or February 2017

    tables:
    - {source: incident, created: [2017-01-01, 2017-03-01]}

Load all incidents created in January or February 2017 EST (UTC-5)

    tables:
    - {source: incident, created: ["2017-01-01 05:00:00", "2017-03-01 05:00:00"]}
 
