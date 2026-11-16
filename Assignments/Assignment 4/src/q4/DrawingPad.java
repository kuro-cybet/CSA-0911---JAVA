package q4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DrawingPad extends JPanel {
    private final List<Point> points = new ArrayList<>();
    private int count;
    public DrawingPad() {
        setPreferredSize(new Dimension(600,400));
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                points.add(e.getPoint()); count++; repaint();
            }
        });
    }
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for(Point p:points) g.fillOval(p.x-4,p.y-4,8,8);
        g.drawString("Clicks: "+count,10,20);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f=new JFrame("Drawing Pad"); f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(new DrawingPad()); f.pack(); f.setLocationRelativeTo(null); f.setVisible(true);
        });
    }
}
