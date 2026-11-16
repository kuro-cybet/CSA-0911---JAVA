package q4;

class ScannerStation extends Thread {
    private final String stationName;
    private final int scripts;
    ScannerStation(String stationName, int scripts) {
        this.stationName = stationName;
        this.scripts = scripts;
    }
    @Override public void run() {
        try {
            Thread.sleep(100L + (long)(Math.random() * 300));
            System.out.println(stationName + " scanned " + scripts + " scripts.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(stationName + " interrupted.");
        }
    }
}

class RunnableStation implements Runnable {
    private final String stationName;
    private final int scripts;
    RunnableStation(String stationName, int scripts) {
        this.stationName = stationName;
        this.scripts = scripts;
    }
    @Override public void run() {
        System.out.println(stationName + " (Runnable) scanned " + scripts + " scripts.");
    }
}

public class ScannerStations {
    public static void main(String[] args) throws InterruptedException {
        Thread[] stations = {
            new ScannerStation("Station 1", 25),
            new ScannerStation("Station 2", 30),
            new ScannerStation("Station 3", 20),
            new ScannerStation("Station 4", 28)
        };
        for (Thread station : stations) station.start();
        for (Thread station : stations) {
            System.out.println(station.getName() + " alive before join: " + station.isAlive());
            station.join();
        }
        int total = 25 + 30 + 20 + 28;
        System.out.println("All stations finished. Total scripts: " + total);

        Thread runnableExample = new Thread(new RunnableStation("Extra station", 12));
        runnableExample.start();
        runnableExample.join();
    }
}
