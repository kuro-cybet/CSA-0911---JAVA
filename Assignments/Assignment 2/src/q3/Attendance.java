package q3;

import java.util.HashSet;
import java.util.Set;

public class Attendance {
    public static void main(String[] args) {
        Set<Student> present = new HashSet<>();
        present.add(new Student("192311001", "Arun"));
        present.add(new Student("192311002", "Divya"));
        present.add(new Student("192311001", "Arun"));
        System.out.println("Unique students: " + present.size());

        Set<Student> differentNamesSameId = new HashSet<>();
        differentNamesSameId.add(new Student("192311003", "Kavi"));
        differentNamesSameId.add(new Student("192311003", "Kavitha"));
        System.out.println("Same ID, different name: " + differentNamesSameId.size());

        System.out.println("Equal objects have equal hashes: " +
                (new Student("192311004", "Mohan").hashCode()
                 == new Student("192311004", "M").hashCode()));
    }
}
