package q7;

import java.util.*;

/*
 * Q7 redesign example:
 * Original approach: scan a List<Medicine> for every code lookup, O(n).
 * Revised approach: maintain a HashMap keyed by medicine code, expected O(1).
 * The map costs O(n) extra references and must be updated with every stock change.
 */
public class InventoryRedesignDemo {
    record Medicine(String code, String name, int quantity) {}

    public static void main(String[] args) {
        List<Medicine> original = new ArrayList<>();
        Map<String, Medicine> redesigned = new HashMap<>();

        for (int i = 0; i < 100_000; i++) {
            Medicine m = new Medicine("M" + i, "Medicine-" + i, i % 500);
            original.add(m);
            redesigned.put(m.code(), m);
        }

        String target = "M99999";
        int repeats = 100_000;

        long start = System.nanoTime();
        for (int i = 0; i < repeats; i++) {
            for (Medicine m : original) {
                if (m.code().equals(target)) break;
            }
        }
        long linearNs = System.nanoTime() - start;

        start = System.nanoTime();
        for (int i = 0; i < repeats; i++) redesigned.get(target);
        long mapNs = System.nanoTime() - start;

        System.out.println("Repeated lookup count: " + repeats);
        System.out.println("List linear scan: " + linearNs + " ns");
        System.out.println("HashMap indexed lookup: " + mapNs + " ns");
        System.out.println("Found: " + redesigned.get(target));
        System.out.println("Interpretation: compare on the same machine; HashMap uses extra O(n) index space.");
    }
}
