package q5;

import java.util.*;

record Team(String name, int points, int goalDiff) {
    @Override public String toString() { return name + "(" + points + "," + goalDiff + ")"; }
}

class Leaderboard<T> {
    private final List<T> items = new ArrayList<>();
    private final Comparator<? super T> order;

    Leaderboard(Comparator<? super T> order) { this.order = Objects.requireNonNull(order); }
    void add(T item) { items.add(Objects.requireNonNull(item)); }

    List<T> ranking() {
        List<T> copy = new ArrayList<>(items);
        copy.sort(order);
        return copy;
    }

    void addAll(List<? extends T> values) { items.addAll(values); }
}

public class LeaderboardDemo {
    public static void main(String[] args) {
        Comparator<Team> byRank =
                Comparator.comparingInt(Team::points).reversed()
                        .thenComparing(Comparator.comparingInt(Team::goalDiff).reversed())
                        .thenComparing(Team::name);

        Leaderboard<Team> board = new Leaderboard<>(byRank);
        board.add(new Team("Panthers", 9, 4));
        board.add(new Team("Eagles", 9, 6));
        board.add(new Team("Cobras", 6, 1));
        board.add(new Team("Bisons", 9, 4));
        System.out.println(board.ranking());

        List<Team> tieTest = List.of(
                new Team("Alpha", 10, 2), new Team("Beta", 10, 2),
                new Team("Gamma", 10, 5), new Team("Delta", 8, 9));
        board.addAll(tieTest);
        System.out.println("Second data set: " + board.ranking());
    }
}
