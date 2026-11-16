package q2;

import java.util.*;

public class PlaylistExperiment {
    private static long timeNanos(Runnable operation) {
        long start = System.nanoTime();
        operation.run();
        return System.nanoTime() - start;
    }

    private static void runSize(int size) {
        List<Integer> array = new ArrayList<>();
        List<Integer> linked = new LinkedList<>();
        for (int i = 0; i < size; i++) { array.add(i); linked.add(i); }

        int repetitions = Math.max(100, size / 5);
        long arrayEnd = timeNanos(() -> { for (int i=0;i<repetitions;i++) array.add(i); });
        long linkedEnd = timeNanos(() -> { for (int i=0;i<repetitions;i++) linked.add(i); });

        long arrayFront = timeNanos(() -> { for (int i=0;i<repetitions;i++) array.add(0, i); });
        long linkedFront = timeNanos(() -> { for (int i=0;i<repetitions;i++) linked.add(0, i); });

        long arrayGet = timeNanos(() -> { long sum=0; for (int i=0;i<repetitions;i++) sum += array.get(array.size()/2); if(sum<0)System.out.println(sum); });
        long linkedGet = timeNanos(() -> { long sum=0; for (int i=0;i<repetitions;i++) sum += linked.get(linked.size()/2); if(sum<0)System.out.println(sum); });

        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i=0;i<size;i++) deque.addLast(i);
        long dequeEnds = timeNanos(() -> { for(int i=0;i<repetitions;i++){deque.addFirst(i); deque.removeLast();} });

        System.out.printf("n=%d | add-end ArrayList=%d ns LinkedList=%d ns | add-front ArrayList=%d ns LinkedList=%d ns | middle-get ArrayList=%d ns LinkedList=%d ns | ArrayDeque end-ops=%d ns%n",
                size,arrayEnd,linkedEnd,arrayFront,linkedFront,arrayGet,linkedGet,dequeEnds);
    }

    public static void main(String[] args) {
        System.out.println("Playlist collection experiment (single-run indicative timings)");
        runSize(1_000);
        runSize(10_000);
        runSize(50_000);
        System.out.println("For formal evidence, repeat runs, warm up the JVM and average results.");
    }
}
