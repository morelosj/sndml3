JUnit tests rely on a property file named `src/test/resouces/junit.properties`. These tests are designed to be run against a ServiceNow Personal Developer Instance (PDI). Some tests will update the ServiceNow instance, so you should not use a production ServiceNow instance for JUnit tests.

JUnit tests assume a directory structure as described in [[Bash Scripts]]. Some JUnit tests are designed to run against multiple database (_e.g._ MySQL, Oracle, etc.), so these tests will require multiple [[connection profiles|Connection Profile]]. The list of configuration directories is specified in the property `junit.datamart.profile_list`.

Some JUnit tests do not write to the JDBC database, so they only require a single [[connection profile|Connection Profile]] for the ServiceNow credentials. This is specified in the property `junit.api.default_profile`.

The current version of the JUnit tests uses a single PDI, and four small AWS RDS databases. The names of these configurations are awsmy, awspg, awsora and awsms.
