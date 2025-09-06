# Oracle Schema Advisor

Provides information about your database schema and possible ways to improove it.

## Detects
- Missed foreign key index
- Missed NOT NULL constraint
- Discrapancy of declared column type and actual values (dates, numebrs, varchars)
- Backward compatibility datatype presence (raw, long raw)
- Ability to use moderd datatype (XML/Json) instead of varchar.

## Produces
- HTML formatted report
- Plain text formatted report
- Depencendies graph model (optionally)

## Benefits
- Opened.
- Silent. Just generate report.
- Doesn't write in database anything. 
- Doesn't gather sensitive information from database. 
- Supports java-security profiles.
- Doesn't require SYSDBA privileges.
- Designed like a classic Oracle AWR/ADDM report
- Can be extended with plugins in Scala/SQL

## Requirements
- Java8
- Temporary disk space (not so much)

## Technologies
- Java/Scala/JDBC
- YAML/SQL scripting
