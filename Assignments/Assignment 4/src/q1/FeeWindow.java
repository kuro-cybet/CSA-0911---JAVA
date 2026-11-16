package q1;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicLong;

/** Demonstrates a responsive fee-payment GUI with separated view and service layers.
 * The in-memory DAO keeps this example runnable without external database setup.
 */
public class FeeWindow extends JFrame {
    private final JTextField regNo = new JTextField(14);
    private final JTextField amount = new JTextField(10);
    private final JLabel status = new JLabel("Enter details and select Pay.");
    private final FeeService service = new FeeService();

    public FeeWindow() {
        super("Fee Payment");
        setLayout(new GridLayout(4, 2, 8, 8));
        add(new JLabel("Register number:")); add(regNo);
        add(new JLabel("Amount (INR):")); add(amount);
        JButton pay = new JButton("Pay");
        JButton clear = new JButton("Clear");
        JButton receipt = new JButton("Receipt");
        add(pay); add(clear); add(receipt); add(status);
        pay.addActionListener(e -> onPay(pay));
        clear.addActionListener(e -> { regNo.setText(""); amount.setText(""); status.setText("Cleared."); });
        receipt.addActionListener(e -> {
            PaymentResult r = service.lastReceiptFor(regNo.getText().trim());
            status.setText(r == null ? "No receipt found." : r.message);
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(480, 190);
        setLocationRelativeTo(null);
    }

    private void onPay(JButton pay) {
        String id = regNo.getText().trim(), value = amount.getText().trim();
        pay.setEnabled(false);
        status.setText("Processing...");
        new SwingWorker<PaymentResult, Void>() {
            @Override protected PaymentResult doInBackground() { return service.pay(id, value); }
            @Override protected void done() {
                try {
                    PaymentResult r = get();
                    status.setText(r.message);
                    if (r.success) JOptionPane.showMessageDialog(FeeWindow.this, r.message, "Payment", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    status.setText("Payment failed: " + ex.getMessage());
                } finally { pay.setEnabled(true); }
            }
        }.execute();
    }

    static final class PaymentResult {
        final boolean success; final String message;
        PaymentResult(boolean success, String message) { this.success = success; this.message = message; }
    }
    static final class FeeService {
        private final FeeDao dao = new FeeDao();
        PaymentResult pay(String reg, String amountText) {
            if (reg == null || !reg.matches("[A-Za-z0-9-]{4,20}")) return new PaymentResult(false, "Enter a valid register number.");
            final double value;
            try { value = Double.parseDouble(amountText); }
            catch (Exception ex) { return new PaymentResult(false, "Enter a valid amount."); }
            if (!Double.isFinite(value) || value <= 0) return new PaymentResult(false, "Amount must be positive.");
            return new PaymentResult(true, "Payment successful. Receipt: " + dao.insert(reg, value));
        }
        PaymentResult lastReceiptFor(String reg) { return dao.lastFor(reg); }
    }
    static final class FeeDao {
        private static final AtomicLong NEXT = new AtomicLong(1000);
        private String lastReg, lastRef;
        synchronized String insert(String reg, double amount) {
            lastReg = reg; lastRef = "FEE-" + NEXT.incrementAndGet();
            return lastRef;
        }
        synchronized PaymentResult lastFor(String reg) {
            return reg.equals(lastReg) ? new PaymentResult(true, "Last receipt: " + lastRef) : null;
        }
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new FeeWindow().setVisible(true)); }
}
