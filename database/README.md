# Database management
Airport management application Liquibase projct to perform database management e.g. create new tables sequences, etc.

## Technologies
* Java 11
* SQL
* Maven
* Liquibase

## Create core database
To create application database SQL scripts should be run in the following order:

1. init.sql
2. sequences.sql
3. schema.sql
4. cargo_schema.sql
5. auth_schema.sql
6. initial_data.sql

## Liquibase usage
Use the following Maven goals to perform liquibase changelog generation as well as change tracking.

Create a new Liquibase changelog:
```
mvn org.liquibase:liquibase-maven-plugin:generateChangeLog
```

Update Liquibase changelog and track database changes:
```
mvn org.liquibase:liquibase-maven-plugin:update
```

Liquibase clear chesk sums:
```
mvn org.liquibase:liquibase-maven-plugin:clearCheckSums
```
