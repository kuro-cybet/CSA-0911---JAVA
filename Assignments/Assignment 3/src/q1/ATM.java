package q1;

import java.util.Scanner;

class InvalidWithdrawalException extends RuntimeException {
    InvalidWithdrawalException(String message) { super(message); }
}
class InsufficientFundsException extends RuntimeException {
    InsufficientFundsException(String message) { super(message); }
}

public class ATM {
    private double balance = 5000.0;

    public void withdraw(double amount) {
        if (amount <= 0) throw new InvalidWithdrawalException("Withdrawal amount must be positive.");
        if (amount > balance) throw new InsufficientFundsException("Insufficient account balance.");
        balance -= amount;
        System.out.printf("Please collect Rs %.2f%n", amount);
        System.out.printf("Remaining balance: Rs %.2f%n", balance);
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);
        System.out.println("ATM WITHDRAWAL SIMULATION");
        try {
            System.out.print("Enter account number: ");
            String account = scanner.nextLine().trim();
            if (account.isEmpty()) throw new IllegalArgumentException("Account number cannot be empty.");

            System.out.print("Enter withdrawal amount: ");
            String amountText = scanner.nextLine().trim();
            double amount = Double.parseDouble(amountText);
            atm.withdraw(amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Input error: " + e.getMessage());
        } catch (InvalidWithdrawalException | InsufficientFundsException e) {
            System.out.println("Transaction declined: " + e.getMessage());
        } finally {
            System.out.println("ATM session closed. Thank you.");
            scanner.close();
        }
    }
}
