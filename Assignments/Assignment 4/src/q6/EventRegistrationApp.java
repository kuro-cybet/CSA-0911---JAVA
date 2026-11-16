package q6;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

public class EventRegistrationApp extends JFrame {
    private final JTextField regNo = new JTextField(12), studentName = new JTextField(12);
    private final JComboBox<Event> eventBox = new JComboBox<>();
    private final JComboBox<Event> filterBox = new JComboBox<>();
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Register No","Name","Event","Timestamp"},0);
    private final JTable table = new JTable(model);
    private final JLabel status = new JLabel("Ready");
    private final RegistrationService service = new RegistrationService();
    private final JButton register = new JButton("Register");
    public EventRegistrationApp() {
        super("Campus Event Registration");
        Event[] events={new Event(1,"Tech Expo",100),new Event(2,"Literary Fest",80),new Event(3,"Sports Meet",120)};
        for(Event e:events){eventBox.addItem(e);filterBox.addItem(e);}
        JPanel form=new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Register No:"));form.add(regNo);form.add(new JLabel("Name:"));form.add(studentName);
        form.add(new JLabel("Event:"));form.add(eventBox);form.add(register);
        JPanel committee=new JPanel(new BorderLayout());
        JPanel top=new JPanel(new FlowLayout(FlowLayout.LEFT));top.add(new JLabel("Filter:"));top.add(filterBox);
        JButton export=new JButton("Export CSV");top.add(export);
        committee.add(top,BorderLayout.NORTH);committee.add(new JScrollPane(table),BorderLayout.CENTER);
        add(form,BorderLayout.NORTH);add(committee,BorderLayout.CENTER);add(status,BorderLayout.SOUTH);
        register.addActionListener(e->registerStudent());
        filterBox.addActionListener(e->refresh());
        export.addActionListener(e->exportCsv());
        JMenuBar bar=new JMenuBar();JMenu file=new JMenu("File");JMenuItem refresh=new JMenuItem("Refresh");
        refresh.addActionListener(e->refresh());JMenuItem exit=new JMenuItem("Exit");exit.addActionListener(e->dispose());
        file.add(refresh);file.add(exit);bar.add(file);setJMenuBar(bar);
        setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(850,480);setLocationRelativeTo(null);refresh();
    }
    private void registerStudent() {
        String id=regNo.getText().trim(),name=studentName.getText().trim();Event ev=(Event)eventBox.getSelectedItem();
        if(!id.matches("[A-Za-z0-9-]{4,20}")||name.isBlank()||ev==null){status.setText("Enter valid register number, name and event.");return;}
        register.setEnabled(false);status.setText("Registering...");
        new SwingWorker<String,Void>(){
            protected String doInBackground(){return service.register(id,name,ev);}
            protected void done(){try{status.setText(get());refresh();}catch(Exception ex){status.setText("Registration failed.");}finally{register.setEnabled(true);}}
        }.execute();
    }
    private void refresh() {
        Event ev=(Event)filterBox.getSelectedItem();if(ev==null)return;
        new SwingWorker<List<Registration>,Void>(){
            protected List<Registration> doInBackground(){return service.findByEvent(ev.id);}
            protected void done(){try{model.setRowCount(0);for(Registration r:get())model.addRow(new Object[]{r.regNo,r.name,r.eventName,r.time});status.setText(model.getRowCount()+" registrations found");}catch(Exception ex){status.setText("Load failed.");}}
        }.execute();
    }
    private void exportCsv() {
        JFileChooser chooser=new JFileChooser();
        if(chooser.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION)return;
        File file=chooser.getSelectedFile();Event ev=(Event)filterBox.getSelectedItem();
        new SwingWorker<Void,Void>(){
            protected Void doInBackground() throws Exception {
                List<Registration> rows=service.findByEvent(ev.id);
                try(BufferedWriter w=Files.newBufferedWriter(file.toPath(),StandardCharsets.UTF_8)){
                    w.write("Register No,Name,Event,Timestamp\n");
                    for(Registration r:rows)w.write(csv(r.regNo)+","+csv(r.name)+","+csv(r.eventName)+","+csv(r.time)+"\n");
                }return null;
            }
            protected void done(){try{get();status.setText("CSV exported: "+file.getName());}catch(Exception ex){status.setText("Export failed: "+ex.getMessage());}}
        }.execute();
    }
    private static String csv(String s){return "\""+s.replace("\"","\"\"")+"\"";}
    static final class Event {final int id,capacity;final String name;Event(int i,String n,int c){id=i;name=n;capacity=c;}public String toString(){return name;}}
    static final class Registration {final String regNo,name,eventName,time;final int eventId;Registration(String r,String n,Event e){regNo=r;name=n;eventId=e.id;eventName=e.name;time=new Date().toString();}}
    static final class RegistrationService {
        private final Map<Integer,List<Registration>> data=new ConcurrentHashMap<>();
        private final Set<String> unique=ConcurrentHashMap.newKeySet();
        String register(String id,String name,Event e){
            String key=id+"#"+e.id;
            synchronized(this){
                if(unique.contains(key))return "Already registered for this event.";
                int total=data.getOrDefault(e.id,Collections.emptyList()).size();
                if(total>=e.capacity)return "Event capacity is full.";
                unique.add(key);data.computeIfAbsent(e.id,k->new CopyOnWriteArrayList<>()).add(new Registration(id,name,e));
            }
            return "Registered successfully.";
        }
        List<Registration> findByEvent(int id){return new ArrayList<>(data.getOrDefault(id,Collections.emptyList()));}
    }
    public static void main(String[] args){SwingUtilities.invokeLater(()->new EventRegistrationApp().setVisible(true));}
}
