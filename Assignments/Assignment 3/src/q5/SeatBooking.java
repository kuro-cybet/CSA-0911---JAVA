package q5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class SeatBooking {
    private final boolean[] booked;
    private int bookingsMade = 0;

    SeatBooking(int seats) { booked = new boolean[seats]; }

    public synchronized boolean book(int seat) {
        if (seat < 0 || seat >= booked.length) throw new IllegalArgumentException("Invalid seat.");
        if (booked[seat]) return false;
        booked[seat] = true;
        bookingsMade++;
        return true;
    }

    public int getBookingsMade() { return bookingsMade; }

    public static void main(String[] args) throws InterruptedException {
        SeatBooking screen = new SeatBooking(10);
        Thread[] users = new Thread[100];
        CountDownLatch ready = new CountDownLatch(users.length);
        CountDownLatch start = new CountDownLatch(1);

        for (int i = 0; i < users.length; i++) {
            final int seat = i % 10;
            users[i] = new Thread(() -> {
                ready.countDown();
                try {
                    start.await();
                    screen.book(seat);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            users[i].start();
        }
        ready.await();
        start.countDown();
        for (Thread t : users) t.join();
        System.out.println("Seats sold: " + screen.getBookingsMade());
    }
}
