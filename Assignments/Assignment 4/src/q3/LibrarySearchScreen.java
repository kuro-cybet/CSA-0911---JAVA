package q3;
import javax.swing.*;
import java.awt.*;

public class LibrarySearchScreen extends JFrame {
    private final JTextField search = new JTextField(20);
    private final JComboBox<String> category = new JComboBox<>(new String[]{"All","Book","Journal","DVD"});
    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> results = new JList<>(model);
    private final JLabel status = new JLabel("Ready");
    public LibrarySearchScreen() {
        super("Campus Library Search");
        setLayout(new BorderLayout(8,8));
        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        north.add(new JLabel("Search:")); north.add(search); north.add(category);
        JButton find = new JButton("Search"); north.add(find); add(north,BorderLayout.NORTH);
        add(new JScrollPane(results),BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout());
        for(String label : new String[]{"Prev","Next","Reserve"}) buttons.add(new JButton(label));
        JPanel south = new JPanel(new BorderLayout()); south.add(buttons,BorderLayout.NORTH); south.add(status,BorderLayout.SOUTH);
        add(south,BorderLayout.SOUTH);
        find.addActionListener(e -> doSearch());
        search.addActionListener(e -> doSearch());
        setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(650,420); setLocationRelativeTo(null);
    }
    private void doSearch() {
        model.clear(); String q=search.getText().trim();
        if(q.isEmpty()){ status.setText("Enter a search term."); return; }
        for(int i=1;i<=15;i++) model.addElement(category.getSelectedItem()+" result "+i+" for \""+q+"\"");
        status.setText(model.size()+" sample results (demo data)");
    }
    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new LibrarySearchScreen().setVisible(true)); }
}
