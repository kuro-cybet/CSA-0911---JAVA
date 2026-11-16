package q4;

import java.util.*;

public class PharmacyInventoryDemo {
    record Medicine(String code, String name, int quantity) {}

    public static void main(String[] args) {
        Map<String, Medicine> hash = new HashMap<>();
        Map<String, Medicine> tree = new TreeMap<>();
        Map<String, Medicine> linked = new LinkedHashMap<>();

        Medicine[] stock = {
            new Medicine("MED103", "Paracetamol", 120),
            new Medicine("MED101", "Amoxicillin", 60),
            new Medicine("MED105", "Cetirizine", 80),
            new Medicine("MED102", "ORS", 45),
            new Medicine("MED104", "Antacid", 90)
        };

        for (Medicine m : stock) {
            hash.put(m.code(), m);
            tree.put(m.code(), m);
            linked.put(m.code(), m);
        }

        System.out.println("HashMap lookup MED102: " + hash.get("MED102"));
        System.out.println("TreeMap (code order): " + tree.values());
        System.out.println("LinkedHashMap (insertion order): " + linked.values());

        int lookups = 100_000;
        long start = System.nanoTime();
        for (int i = 0; i < lookups; i++) hash.get("MED102");
        long elapsed = System.nanoTime() - start;
        System.out.println("HashMap " + lookups + " lookups: " + elapsed + " ns");

        System.out.println("TreeMap naturally provides sorted keys; LinkedHashMap preserves insertion order.");
    }
}
