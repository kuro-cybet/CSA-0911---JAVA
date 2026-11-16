package q7;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

record Result(String fileName, boolean success, int score, String message) {}

public class ResultProcessor {
    private static Result process(Path file) {
        try {
            if (!Files.exists(file)) throw new NoSuchFileException(file.toString());
            String text = Files.readString(file).trim();
            if (text.isEmpty()) throw new IOException("Empty score file");
            int score = Integer.parseInt(text);
            if (score < 0 || score > 100) throw new IllegalArgumentException("Score outside 0–100");
            return new Result(file.getFileName().toString(), true, score, "Processed");
        } catch (Exception e) {
            return new Result(file.getFileName().toString(), false, 0,
                    e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    private static void createSampleFiles(Path dir) throws IOException {
        Files.createDirectories(dir);
        for (int i = 0; i < 20; i++) {
            Path file = dir.resolve("score-" + i + ".txt");
            if (i == 4) Files.writeString(file, "corrupt-score");
            else if (i == 9) Files.writeString(file, "145");
            else Files.writeString(file, Integer.toString((i * 7) % 101));
        }
        // score-19 is intentionally deleted to simulate a missing file.
        Files.deleteIfExists(dir.resolve("score-19.txt"));
    }

    public static void main(String[] args) throws Exception {
        Path dir = Paths.get("sample-scores");
        createSampleFiles(dir);
        int[] poolSizes = {1, 2, 4, 8};

        for (int poolSize : poolSizes) {
            ExecutorService executor = Executors.newFixedThreadPool(poolSize);
            ConcurrentLinkedQueue<Result> results = new ConcurrentLinkedQueue<>();
            List<Future<Result>> futures = new ArrayList<>();

            long start = System.nanoTime();
            for (int i = 0; i < 20; i++) {
                Path file = dir.resolve("score-" + i + ".txt");
                Callable<Result> task = () -> process(file);
                futures.add(executor.submit(task));
            }

            for (Future<Result> future : futures) {
                try { results.add(future.get()); }
                catch (ExecutionException e) {
                    results.add(new Result("unknown", false, 0, "Task failure: " + e.getCause()));
                }
            }
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);
            long elapsed = System.nanoTime() - start;

            long success = results.stream().filter(Result::success).count();
            long failure = results.size() - success;
            System.out.printf("Pool=%d | success=%d | failure=%d | elapsed=%d ns%n",
                    poolSize, success, failure, elapsed);
            results.stream().filter(r -> !r.success()).forEach(r ->
                    System.out.println("  Failed " + r.fileName() + ": " + r.message()));
        }
    }
}
