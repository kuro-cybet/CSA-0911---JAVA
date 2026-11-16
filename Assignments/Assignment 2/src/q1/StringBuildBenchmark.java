package q1;

import java.util.StringJoiner;

public class StringBuildBenchmark {
    private static final int WARMUPS = 3;
    private static final int RUNS = 7;

    private static String ticketWithPlus(String[] fields) {
        String result = "";
        for (String field : fields) result += field + "\n";
        return result;
    }

    private static String ticketWithBuilder(String[] fields) {
        StringBuilder result = new StringBuilder();
        for (String field : fields) result.append(field).append('\n');
        return result.toString();
    }

    private static String ticketWithBuffer(String[] fields) {
        StringBuffer result = new StringBuffer();
        for (String field : fields) result.append(field).append('\n');
        return result.toString();
    }

    private static String ticketWithJoiner(String[] fields) {
        StringJoiner joiner = new StringJoiner("\n");
        for (String field : fields) joiner.add(field);
        return joiner.toString();
    }

    private static long measure(Runnable task) {
        for (int i = 0; i < WARMUPS; i++) task.run();
        long total = 0;
        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();
            task.run();
            total += System.nanoTime() - start;
        }
        return total / RUNS;
    }

    private static String[] makeFields(int count) {
        String[] fields = new String[count];
        for (int i = 0; i < count; i++) fields[i] = "FIELD-" + i + "-RAILWAY-DATA";
        return fields;
    }

    private static void workload(String label, int tickets, int fieldsPerTicket) {
        String[][] data = new String[tickets][];
        for (int i = 0; i < tickets; i++) data[i] = makeFields(fieldsPerTicket);

        System.out.println("\n" + label + " (" + tickets + " tickets × " + fieldsPerTicket + " fields)");
        System.out.println("String + avg ns: " + measure(() -> {
            long chars = 0;
            for (String[] row : data) chars += ticketWithPlus(row).length();
            if (chars == -1) System.out.println(chars);
        }));
        System.out.println("StringBuilder avg ns: " + measure(() -> {
            long chars = 0;
            for (String[] row : data) chars += ticketWithBuilder(row).length();
            if (chars == -1) System.out.println(chars);
        }));
        System.out.println("StringBuffer avg ns: " + measure(() -> {
            long chars = 0;
            for (String[] row : data) chars += ticketWithBuffer(row).length();
            if (chars == -1) System.out.println(chars);
        }));
        System.out.println("StringJoiner avg ns: " + measure(() -> {
            long chars = 0;
            for (String[] row : data) chars += ticketWithJoiner(row).length();
            if (chars == -1) System.out.println(chars);
        }));
    }

    public static void main(String[] args) {
        workload("Small workload", 1, 6);
        workload("Large workload", 10_000, 50);
        workload("Custom workload", 2_000, 100);
        System.out.println("\nIntermediate String counts are estimated, not directly measured.");
        System.out.println("Repeated + may create many temporary strings; builders buffer appends.");
    }
}
