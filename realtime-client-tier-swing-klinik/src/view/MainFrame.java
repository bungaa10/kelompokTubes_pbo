package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel content = new JPanel(cardLayout);

    private DashboardPanel dashboardPanel;
    private QueuePanel queuePanel;

    public MainFrame() {
        
        setTitle("Sistem Klinik Hewan Realtime");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        dashboardPanel = new DashboardPanel();

JScrollPane dashboardScroll = new JScrollPane(dashboardPanel);
dashboardScroll.setBorder(null);
dashboardScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
dashboardScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
dashboardScroll.getVerticalScrollBar().setUnitIncrement(16);
dashboardScroll.getViewport().setBackground(new Color(237, 233, 254));

// ⬇️ YANG DITAMBAHKAN KE CARDLAYOUT ADALAH SCROLL-NYA
content.add(dashboardScroll, "dashboard");


        queuePanel = new QueuePanel(this);

        content.add(dashboardPanel, "dashboard");
        content.add(new PetPanel(), "pet");
        content.add(queuePanel, "queue");
        content.add(new AboutPanel(), "about");

        add(createSidebar(), BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        cardLayout.show(content, "dashboard");
    }

    private JPanel createSidebar() {
        JPanel panel = new JPanel(new GridLayout(4,1,10,10));
        panel.setBackground(new Color(124,58,237));
        panel.setPreferredSize(new Dimension(220,0));
        panel.setBorder(BorderFactory.createEmptyBorder(30,10,30,10));

        panel.add(navButton("🏠 Dashboard","dashboard"));
        panel.add(navButton("🐶 Data Hewan","pet"));
        panel.add(navButton("⏱️ Antrian","queue"));
        panel.add(navButton("ℹ️ Tentang","about"));

        return panel;
    }

    private JButton navButton(String text, String target) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(124,58,237));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(15,20,15,20));
        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.addActionListener(e -> cardLayout.show(content, target));
        return btn;
    }

    // ======= DIPANGGIL DARI QUEUE PANEL =======
    public void updateDashboardStats(int total, int menunggu, int diperiksa, int selesai) {
        dashboardPanel.updateStats(total, menunggu, diperiksa, selesai);
    }

    public void log(String msg) {
        dashboardPanel.addLog(msg);
    }
}
