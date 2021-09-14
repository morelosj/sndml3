## Introduction
As of version 3.4, the only available [[YAML|YAML Configuration]] global setting is `metrics`. Support has been removed for global threads in the YAML configuration, which allowed multiple tables to be loaded concurrently. If you are using YAML and have a requirement to load multiple tables concurrently, simply run multiple loader processes.

Please see [[Connection Profile]] for a list of other global settings.

## Metrics
Specification of a metrics file. For additional information refer to [[Metrics]].

#### Example
    metrics: /sndml/profile/user.metrics

