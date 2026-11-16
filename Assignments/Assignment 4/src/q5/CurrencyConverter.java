package q5;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CurrencyConverter extends JFrame {
    private final JTextField amount = new JTextField(10);
    private final JComboBox<String> target = new JComboBox<>(new String[]{"USD","EUR"});
    private final JLabel result = new JLabel("Enter INR amount.");
    public CurrencyConverter() {
        super("Currency Converter");
        setLayout(new FlowLayout());
        add(new JLabel("INR:")); add(amount); add(target);
        JButton convert = new JButton("Convert"); add(convert); add(result);
        ActionListener listener = e -> convertAmount();
        convert.addActionListener(listener); amount.addActionListener(listener);
        setDefaultCloseOperation(EXIT_ON_CLOSE); pack(); setLocationRelativeTo(null);
    }
    private double rate(String currency) { return currency.equals("USD") ? 0.012 : 0.011; }
    private void convertAmount() {
        try {
            double inr=Double.parseDouble(amount.getText().trim());
            if(!Double.isFinite(inr) || inr<0) throw new NumberFormatException();
            double value=inr*rate((String)target.getSelectedItem());
            result.setText(String.format("%.2f %s",value,target.getSelectedItem()));
        } catch(NumberFormatException ex) { result.setText("Invalid amount"); }
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new CurrencyConverter().setVisible(true)); }
}
