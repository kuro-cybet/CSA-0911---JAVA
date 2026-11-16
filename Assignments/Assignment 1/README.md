# Campus Smart-Parking System (Java)

A console-based Java 17 project implementing the CO1 assignment scenario.

## Features
- Multi-floor parking with unequal slot counts (jagged array).
- Vehicle categories: two-wheeler, car, electric car, e-bike and delivery van.
- Automatic slot allocation and release.
- Unique ticket IDs and duplicate-registration protection.
- Parking fee calculation using started-hour billing.
- EV charging fees.
- Student/staff monthly passes and visitor one-day passes.
- Delivery van double-rate pricing from 9:00 AM (inclusive) to 5:00 PM (exclusive).
- Active-ticket lookup using a `HashMap`.
- Input validation and a demonstration mode.

## Requirements
- JDK 17 or later
- No external libraries or API keys required

## Compile and run (Windows PowerShell)
From the project root:

```powershell
javac -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
java -cp out parking.app.Main
```

## Compile and run (Linux/macOS)
```bash
mkdir -p out
find src -name "*.java" -print0 | xargs -0 javac -d out
java -cp out parking.app.Main
```

## Demo
Run the application and choose `1` for the demonstration. Choose `2` to use the interactive menu.

## Package structure
- `parking.model` - vehicles, tickets and passes
- `parking.facility` - floors, slots and facility allocation
- `parking.service` - parking workflow and pricing rules
- `parking.app` - console entry point

## Notes
This is an educational reference implementation. The sample rates are configurable in the pricing classes and are not institutional tariff data.
