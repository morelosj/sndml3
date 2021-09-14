Version 3.2.0 adds support for domain separation. The assumption is that, if you are using a domain separated instance, you will require a separate [[Connection Profile]] for each domain.

Domains are specified using the property `servicenow.domain` in the connection profile. This property can contain either a single **sys_id**, or a comma separated list of values.