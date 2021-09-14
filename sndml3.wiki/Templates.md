## Introduction
The file `sqltemplates.xml` contains DDL and DML templates which are use to create SQL tables and perform DML operations. By default the application will use the file which is embedded in the JAR (`/src/main/resources/sqltemplates.xml`). You can override this behavior by making a copy of this file and setting the `datamart.templates` property in the [[Connection Profile]]  to point to your modified version of the file.

The templates file is divided into "dialects" for different database management systems. The application will attempt to select a dialect by matching the JDBC URL with the `drivers` section of each dialect. You can override this search logic selecting a specific dialect with the `datamart.dialect` property.

## Edit SQL data types

Edit the `datatypes` section of the templates file to control the SQL column type generated for each ServiceNow data type.
 
## Rename columns

Edit the `fieldnames` section of the templates file to rename columns. If you change the name of a column, it will affect all tables having a field with this name.
