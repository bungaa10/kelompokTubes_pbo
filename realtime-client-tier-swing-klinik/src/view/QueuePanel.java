package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class QueuePanel extends JPanel {

    private MainFrame mainFrame;

    DefaultTableModel model;
    JTable table;

    JTextField txtNama = new JTextField();
    JComboBox<String> cmbStatus =
            new JComboBox<>(new String[]{"MENUNGGU","DIPERIKSA","SELESAI"});

    Color PURPLE = new Color(124, 58, 237);
    Color LIGHT = new Color(237, 233, 254);

    WebSocketClient socket;

    public QueuePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout(10,10));
        setBackground(LIGHT);
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);

        connectWebSocket();
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(PURPLE,2));

        JLabel title = new JLabel("⏱️ Antrian Klinik Hewan (Realtime)");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PURPLE);
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(1,4,10,10));
        form.setBackground(Color.WHITE);

        form.add(new JLabel("Nama Hewan"));
        form.add(txtNama);
        form.add(new JLabel("Status"));
        form.add(cmbStatus);

        panel.add(form, BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btns.setBackground(Color.WHITE);

        JButton btnTambah = createButton("Tambah");
        JButton btnUpdate = createButton("Update");
        JButton btnHapus = createButton("Hapus");

        btnTambah.addActionListener(e ->
                sendRealtime("ADD|" + txtNama.getText() + "|" + cmbStatus.getSelectedItem())
        );

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0)
                sendRealtime("UPDATE|" + model.getValueAt(row,1) + "|" + cmbStatus.getSelectedItem());
        });

        btnHapus.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0)
                sendRealtime("DELETE|" + model.getValueAt(row,1));
        });

        btns.add(btnTambah);
        btns.add(btnUpdate);
        btns.add(btnHapus);

        panel.add(btns, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(PURPLE,2));

        model = new DefaultTableModel(new String[]{"No","Nama Hewan","Status"},0);
        table = new JTable(model);
        table.setRowHeight(28);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void connectWebSocket() {
        try {
            socket = new WebSocketClient(new URI("ws://localhost:8080")) {
                public void onOpen(ServerHandshake h) {}
                public void onClose(int c,String r,boolean remote){}
                public void onError(Exception ex){}
                public void onMessage(String msg) {
                    SwingUtilities.invokeLater(() -> processMessage(msg));
                }
            };
            socket.connect();
        } catch (Exception ignored) {}
    }

    private void sendRealtime(String msg) {
        if (socket != null && socket.isOpen()) socket.send(msg);
    }

    private void processMessage(String msg) {
        String[] d = msg.split("\\|");

        if (d[0].equals("ADD")) {
            model.addRow(new Object[]{model.getRowCount()+1, d[1], d[2]});
            mainFrame.log("Pasien " + d[1] + " masuk antrian");
        }

        if (d[0].equals("UPDATE")) {
            for (int i=0;i<model.getRowCount();i++)
                if (model.getValueAt(i,1).equals(d[1]))
                    model.setValueAt(d[2], i, 2);
            mainFrame.log("Status " + d[1] + " → " + d[2]);
        }

        if (d[0].equals("DELETE")) {
            for (int i=0;i<model.getRowCount();i++)
                if (model.getValueAt(i,1).equals(d[1])) {
                    model.removeRow(i);
                    break;
                }
            mainFrame.log("Pasien " + d[1] + " dihapus");
        }

        refreshDashboard();
    }

    private void refreshDashboard() {
        int total = model.getRowCount();
        int menunggu = 0, periksa = 0, selesai = 0;

        for (int i=0;i<total;i++) {
            switch (model.getValueAt(i,2).toString()) {
                case "MENUNGGU" -> menunggu++;
                case "DIPERIKSA" -> periksa++;
                case "SELESAI" -> selesai++;
            }
        }

        mainFrame.updateDashboardStats(total, menunggu, periksa, selesai);
    }

    private JButton createButton(String t) {
        JButton b = new JButton(t);
        b.setBackground(PURPLE);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }
}
