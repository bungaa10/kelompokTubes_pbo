package view;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    JTextArea logArea;

    JLabel lblTotal = new JLabel("0");
    JLabel lblMenunggu = new JLabel("0");
    JLabel lblPeriksa = new JLabel("0");
    JLabel lblSelesai = new JLabel("0");

    Color PURPLE = new Color(124, 58, 237);
    Color LIGHT = new Color(237, 233, 254);
    

    public DashboardPanel() {
        
        setLayout(new BorderLayout(15,15));
        setBackground(LIGHT);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        add(createHeader(), BorderLayout.NORTH);
        add(createStats(), BorderLayout.CENTER);
        add(createLog(), BorderLayout.SOUTH);
    }

    private JLabel createHeader() {
        JLabel title = new JLabel("📊 Dashboard Klinik Hewan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(PURPLE);
        return title;
    }

    private JPanel createStats() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 50, 50));
    panel.setOpaque(false);

    panel.add(card("🐶 Total Pasien",lblTotal));
    panel.add(card("⏳ Menunggu", lblMenunggu));
    panel.add(card("🩺 Diperiksa", lblPeriksa));
    panel.add(card("✅ Selesai", lblSelesai));

    return panel;
}


    private JPanel card(String title, JLabel value) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createLineBorder(PURPLE,2));
        p.setPreferredSize(new Dimension(125, 125));
        p.setMaximumSize(new Dimension(500, 1100));
        JLabel t = new JLabel(title, SwingConstants.CENTER);
        t.setForeground(PURPLE);
        value.setFont(new Font("Segoe UI", Font.BOLD, 28));
        value.setHorizontalAlignment(SwingConstants.CENTER);

        p.add(t, BorderLayout.NORTH);
        p.add(value, BorderLayout.CENTER);
        return p;
    }

    private JPanel createLog() {
        JPanel panel = new JPanel(new BorderLayout(5,5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(PURPLE,2));

        JLabel title = new JLabel("📡 Aktivitas Realtime");
        title.setForeground(PURPLE);

        logArea = new JTextArea(5,20);
        logArea.setEditable(false);

        panel.add(title, BorderLayout.NORTH);
        panel.add(new JScrollPane(logArea), BorderLayout.CENTER);

        return panel;
    }

    public void addLog(String text) {
        logArea.append("• " + text + "\n");
    }

    public void updateStats(int total, int menunggu, int periksa, int selesai) {
        lblTotal.setText(String.valueOf(total));
        lblMenunggu.setText(String.valueOf(menunggu));
        lblPeriksa.setText(String.valueOf(periksa));
        lblSelesai.setText(String.valueOf(selesai));
    }
}
