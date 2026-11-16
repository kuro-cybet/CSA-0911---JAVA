package q2;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class AppointmentDesk extends JFrame {
    private final JTextField patientId = new JTextField(12);
    private final JComboBox<String> department = new JComboBox<>(new String[]{"Cardiology","Neurology","Orthopaedics"});
    private final JComboBox<String> doctor = new JComboBox<>();
    private final JComboBox<String> slot = new JComboBox<>();
    private final JButton book = new JButton("Book appointment");
    private final JLabel status = new JLabel("Select a department and doctor.");
    private final Map<String, java.util.List<String>> doctors = Map.of(
        "Cardiology", java.util.List.of("Dr. Anand","Dr. Meera"),
        "Neurology", java.util.List.of("Dr. Kumar","Dr. Nisha"),
        "Orthopaedics", java.util.List.of("Dr. Ravi","Dr. Leela"));
    public AppointmentDesk() {
        super("Outpatient Appointment Desk");
        setLayout(new BorderLayout(8,8));
        JPanel form = new JPanel(new GridLayout(4,2,6,6));
        form.add(new JLabel("Patient ID")); form.add(patientId);
        form.add(new JLabel("Department")); form.add(department);
        form.add(new JLabel("Doctor")); form.add(doctor);
        form.add(new JLabel("Time slot")); form.add(slot);
        add(form, BorderLayout.NORTH);
        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT)); south.add(book); south.add(status); add(south, BorderLayout.SOUTH);
        department.addActionListener(e -> refreshDoctors());
        doctor.addActionListener(e -> refreshSlots());
        book.addActionListener(e -> bookAppointment());
        refreshDoctors();
        setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(520,220); setLocationRelativeTo(null);
    }
    private void refreshDoctors() {
        doctor.removeAllItems();
        for (String d : doctors.get((String)department.getSelectedItem())) doctor.addItem(d);
        refreshSlots();
    }
    private void refreshSlots() {
        slot.removeAllItems();
        if (doctor.getSelectedItem() != null) {
            slot.addItem("09:00"); slot.addItem("10:30"); slot.addItem("14:00");
        }
        book.setEnabled(slot.getItemCount() > 0);
    }
    private void bookAppointment() {
        String id = patientId.getText().trim();
        if (!id.matches("[A-Za-z0-9-]{4,20}")) { status.setText("Enter a valid patient ID."); return; }
        if (doctor.getSelectedItem()==null || slot.getSelectedItem()==null) { status.setText("Select doctor and slot."); return; }
        book.setEnabled(false); status.setText("Booking...");
        new SwingWorker<String,Void>() {
            protected String doInBackground() throws Exception { Thread.sleep(250); return "APT-" + System.currentTimeMillis(); }
            protected void done() {
                try { String ref=get(); status.setText("Booked: " + ref); JOptionPane.showMessageDialog(AppointmentDesk.this,"Appointment confirmed: "+ref); }
                catch(Exception ex) { status.setText("Booking failed."); }
                finally { book.setEnabled(true); }
            }
        }.execute();
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new AppointmentDesk().setVisible(true)); }
}
