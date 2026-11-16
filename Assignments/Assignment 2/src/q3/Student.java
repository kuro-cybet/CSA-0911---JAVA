package q3;

import java.util.Objects;

public final class Student {
    private final String regNo;
    private final String name;

    public Student(String regNo, String name) {
        if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("Registration number required");
        this.regNo = regNo.trim();
        this.name = Objects.requireNonNull(name);
    }

    public String getRegNo() { return regNo; }
    public String getName() { return name; }

    @Override public boolean equals(Object o) {
        return this == o || (o instanceof Student other && regNo.equals(other.regNo));
    }

    @Override public int hashCode() { return regNo.hashCode(); }

    @Override public String toString() { return regNo + " " + name; }
}
