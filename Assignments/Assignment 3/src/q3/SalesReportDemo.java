package q3;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SalesReportDemo {
    private static List<String> readManually(List<Path> paths) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = null;
        try {
            for (Path path : paths) {
                reader = Files.newBufferedReader(path);
                String line;
                while ((line = reader.readLine()) != null) lines.add(line);
                reader.close();
                reader = null;
            }
        } finally {
            if (reader != null) reader.close();
        }
        return lines;
    }

    private static List<String> readSafely(List<Path> paths) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Path path : paths) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) lines.add(line);
            }
        }
        return lines;
    }

    public static void main(String[] args) throws IOException {
        Path dir = Files.createTempDirectory("sales-report-");
        Path a = dir.resolve("store-a.txt");
        Path b = dir.resolve("store-b.txt");
        Files.writeString(a, "Store A,1200\nStore A,900\n");
        Files.writeString(b, "Store B,750\n");
        List<Path> inputs = List.of(a, b);

        List<String> manual = readManually(inputs);
        List<String> safe = readSafely(inputs);
        Path report = dir.resolve("summary.txt");
        Files.write(report, safe);

        System.out.println("Manual report lines: " + manual.size());
        System.out.println("Try-with-resources lines: " + safe.size());
        System.out.println("Reports match: " + manual.equals(safe));
        System.out.println("Summary written: " + report);
    }
}
