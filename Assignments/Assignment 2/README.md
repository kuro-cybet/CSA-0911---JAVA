# Programming in Java – Assignment 2 (CO2)

Java 17+ reference implementation for Q1–Q7 of the uploaded assignment.

## Contents
- `q1`: String, StringBuilder and StringBuffer ticket-generation benchmark
- `q2`: ArrayList, LinkedList and ArrayDeque playlist operations
- `q3`: Student equality/hashCode contract and HashSet tests
- `q4`: HashMap, TreeMap and LinkedHashMap pharmacy inventory
- `q5`: Generic leaderboard and reusable Comparator
- `q6`: PriorityQueue emergency triage with FIFO tie-breaking
- `q7`: Diagnose/redesign/validate example using an indexed inventory lookup

## Requirements
- JDK 17 or later
- No external libraries

## Compile (Windows PowerShell)
```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
```

## Run
```powershell
java -cp out q1.StringBuildBenchmark
java -cp out q2.PlaylistExperiment
java -cp out q3.Attendance
java -cp out q4.PharmacyInventoryDemo
java -cp out q5.LeaderboardDemo
java -cp out q6.Triage
java -cp out q7.InventoryRedesignDemo
```

On Linux/macOS:
```bash
mkdir -p out
find src -name "*.java" -print0 | xargs -0 javac -d out
java -cp out q1.StringBuildBenchmark
```

## Important experimental note
Benchmark timings depend on CPU, JVM warm-up, background processes and heap state. The programs print measurements when run. Do not copy sample timings into the report as if they were measured on your computer. Record your own repeated runs and include screenshots/raw output.

## Suggested GitHub structure
Keep each question in its own folder when submitting, and commit progressively. Add your name, register number, Java version and your own execution screenshots before submission.
