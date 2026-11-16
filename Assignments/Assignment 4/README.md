# CO4 Java Assignment 4 – Event-Driven GUI Design

This project contains runnable Swing/AWT demonstrations for Tasks 1–6.

## Requirements
- JDK 11 or newer
- No third-party libraries are required.
- Task 1 and Task 6 use in-memory demo persistence so they run without MySQL credentials. Their DAO/service boundaries are designed so JDBC can replace the in-memory implementation.

## Compile
From this directory:

```bash
javac -d out src/q1/FeeWindow.java src/q2/AppointmentDesk.java src/q3/LibrarySearchScreen.java src/q4/DrawingPad.java src/q5/CurrencyConverter.java src/q6/EventRegistrationApp.java
```

## Run
```bash
java -cp out q1.FeeWindow
java -cp out q2.AppointmentDesk
java -cp out q3.LibrarySearchScreen
java -cp out q4.DrawingPad
java -cp out q5.CurrencyConverter
java -cp out q6.EventRegistrationApp
```

Run one command at a time. These are desktop GUI applications and require a graphical environment.

## Notes
- Task 1 demonstrates separation of view/service/DAO, validation, per-button listeners, and SwingWorker.
- Task 2 demonstrates department → doctor → slot dependent controls and asynchronous booking.
- Task 3 demonstrates BorderLayout, FlowLayout, nested panels, and a scrollable result list.
- Task 4 uses the correctly overridden `mouseClicked` method and redraws retained points in `paintComponent`.
- Task 5 reuses one lambda ActionListener for both the Convert button and Enter key.
- Task 6 is a runnable in-memory event-registration prototype with duplicate prevention, event filtering, SwingWorker operations, and CSV export.
