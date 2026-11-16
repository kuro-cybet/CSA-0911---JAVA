# Programming in Java – Assignment 3 (CO3)

Java 17+ implementation starter for all seven questions in the supplied CO3 assignment.

## Questions
- Q1: ATM exception handling and validation
- Q2: User-defined exceptions, propagation and exception chaining
- Q3: Resource safety using manual close and try-with-resources
- Q4: Thread creation, lifecycle, Thread and Runnable
- Q5: Cinema seat booking race condition and synchronization
- Q6: Deadlock prevention and bounded producer-consumer token counter
- Q7: Concurrent fault-tolerant result-file processor using ExecutorService, Callable and Future

## Compile
Windows PowerShell:
```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
```
Linux/macOS:
```bash
mkdir -p out
find src -name "*.java" -print0 | xargs -0 javac -d out
```

## Run
```bash
java -cp out q1.ATM
java -cp out q2.StorageDemo
java -cp out q3.SalesReportDemo
java -cp out q4.ScannerStations
java -cp out q5.SeatBooking
java -cp out q6.TokenCounter
java -cp out q6.DeadlockSafeTransferDemo
java -cp out q7.ResultProcessor
```

## Notes
- JDK 17 or later is required; no external dependencies are used.
- Q1–Q6 include runnable demonstrations. Q7 creates a sample input directory with valid, corrupt and missing-file cases and processes files with configurable pool sizes.
- Timing and scheduler order vary by machine and run. Record your own repeated measurements and screenshots; do not report generated sample timings as measured evidence.
- This is a pair-work assignment. Both students must execute all tasks in their own GitHub repositories and label Student A / Student B contributions in the report.
