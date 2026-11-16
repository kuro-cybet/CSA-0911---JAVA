package q6;

class Wallet {
    private final int id;
    private int balance;
    Wallet(int id, int balance) { this.id = id; this.balance = balance; }
    int getId() { return id; }
    int getBalance() { return balance; }
    void debit(int amount) { balance -= amount; }
    void credit(int amount) { balance += amount; }
}

public class DeadlockSafeTransferDemo {
    static void transfer(Wallet from, Wallet to, int amount) {
        Wallet first = from.getId() < to.getId() ? from : to;
        Wallet second = from.getId() < to.getId() ? to : from;
        synchronized (first) {
            synchronized (second) {
                if (amount < 0 || from.getBalance() < amount) throw new IllegalArgumentException("Invalid transfer.");
                from.debit(amount);
                to.credit(amount);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Wallet a = new Wallet(1, 1000), b = new Wallet(2, 1000);
        Thread t1 = new Thread(() -> { for (int i=0;i<1000;i++) transfer(a,b,1); });
        Thread t2 = new Thread(() -> { for (int i=0;i<1000;i++) transfer(b,a,1); });
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println("Final balances: " + a.getBalance() + ", " + b.getBalance());
        System.out.println("Total preserved: " + (a.getBalance() + b.getBalance()));
    }
}
