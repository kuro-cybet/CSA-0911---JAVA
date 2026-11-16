package q2;

import java.io.IOException;

class ExamException extends Exception {
    ExamException(String message) { super(message); }
    ExamException(String message, Throwable cause) { super(message, cause); }
}
class SubmissionStorageException extends ExamException {
    SubmissionStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
class ExamWindowClosedException extends ExamException {
    ExamWindowClosedException(String message) { super(message); }
}
class CourseNotRegisteredException extends ExamException {
    CourseNotRegisteredException(String message) { super(message); }
}
class FileTooLargeException extends ExamException {
    FileTooLargeException(String message) { super(message); }
}

class Storage {
    void save(String fileName, long sizeKb) throws SubmissionStorageException {
        try {
            if (sizeKb > 500) throw new IOException("Disk quota exceeded");
            System.out.println("Saved " + fileName);
        } catch (IOException e) {
            throw new SubmissionStorageException("Could not store " + fileName, e);
        }
    }
}

class SubmissionService {
    private final Storage storage = new Storage();

    void submit(String fileName, long sizeKb, boolean examOpen, boolean registered)
            throws ExamException {
        if (!examOpen) throw new ExamWindowClosedException("Exam window has closed.");
        if (!registered) throw new CourseNotRegisteredException("Student is not registered.");
        if (sizeKb > 500) throw new FileTooLargeException("Answer file exceeds 500 KB.");
        storage.save(fileName, sizeKb);
    }
}

public class StorageDemo {
    public static void main(String[] args) {
        Storage storage = new Storage();
        try {
            storage.save("answer01.pdf", 120);
            storage.save("answer02.pdf", 800);
        } catch (ExamException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Cause: " + e.getCause().getMessage());
        }

        System.out.println("\nPropagation examples:");
        SubmissionService service = new SubmissionService();
        try {
            service.submit("late.pdf", 100, false, true);
        } catch (ExamException e) {
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}
