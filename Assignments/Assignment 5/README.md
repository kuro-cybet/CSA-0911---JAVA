# Java Assignment 5 – CO5: Data Persistence Engineering

**Student:** Sahil J.S  
**Register number:** 192525200  
**Course code:** CSA0911  
**Course:** Programming in Java

## Requirements
- JDK 17+ (records are used in Task01)
- MySQL 8.x for Tasks 03, 05, 07 and 08 database demonstrations
- MySQL Connector/J on the classpath for JDBC execution

## Compile all Java source
```bash
javac -d out Task01/*.java Task02/*.java Task03/*.java Task04/*.java Task05/*.java Task06/*.java Task07/*.java Task08/*.java
```
The JDBC programs compile against the JDK JDBC API; add MySQL Connector/J at runtime.

## Run file/serialization demonstrations
```bash
java -cp out Task01.BusPassFileDesign
java -cp out Task02.LineCounter
java -cp out Task04.Hostel
java -cp out Task06.PassStore
java -cp out Task06.BufferingBenchmark
```

## Database configuration
Run `sql/schema.sql` in MySQL. Set environment variables:
- `DB_URL` e.g. `jdbc:mysql://localhost:3306/co5_transport`
- `DB_USER`
- `DB_PASSWORD`

Add Connector/J to the runtime classpath, then run the JDBC classes. Example:
```bash
java -cp "out;mysql-connector-j.jar" Task07.LoginSecurityDemo
java -cp "out;mysql-connector-j.jar" Task08.DatabaseMigration input/bus_pass_sample.csv
```
(Use `:` instead of `;` as the classpath separator on Linux/macOS.)

## Important evidence notes
The supplied experiment log is a blank record sheet. Fill it with measurements from your own machine and MySQL server. No database timings, live database results, GitHub URL or screenshots are fabricated. Task08 migration input format is the five-column form used by the Task B1 starter program: pass_id, reg_no, name, route_no, amount. The full seven-field design is explained in the report and implemented in Task01.

## Task map
- Task01: seven-field text-file layout, escaping, add/find/route listing.
- Task02: unbuffered and buffered byte-based line counting.
- Task03: generic DAO and StudentDao mapping.
- Task04: serializable Hostel object graph; password transient.
- Task05: atomic wallet transfer using JDBC transaction.
- Task06: completed PassStore TODO and buffering benchmark.
- Task07: unsafe SQL vs PreparedStatement demonstration.
- Task08: completed batch loader and migration/metadata inspection.
