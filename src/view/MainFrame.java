package view;

import model.Hewan;
import model.Layanan;

import api.HewanApi;
import api.LayananApi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    // ===== HEWAN =====
    JTextField txtNama, txtJenis, txtUmur, txtPemilik;
    JTable tableHewan;
    DefaultTableModel modelHewan;
    HewanApi hewanApi = new HewanApi();
    int selectedHewanId = -1;

    // ===== LAYANAN =====
    JTextField txtNamaLayanan, txtHarga;
    JTable tableLayanan;
    DefaultTableModel modelLayanan;
    LayananApi layananApi = new LayananApi();
    int selectedLayananId = -1;

    Color unguMuda = new Color(232, 218, 239);
    Color unguTua = new Color(88, 24, 124);

    public MainFrame() {
        setTitle("Aplikasi Klinik Hewan");
        setSize(800, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        JTabbedPane tabbedPane = new JTabbedPane();

        // ================= TAB DATA HEWAN =================
        JPanel panelHewan = new JPanel(new BorderLayout(10,10));
        panelHewan.setBackground(unguMuda);

        JPanel formHewan = new JPanel(new GridLayout(4,2,10,10));
        formHewan.setBorder(BorderFactory.createTitledBorder("Form Data Hewan"));
        formHewan.setBackground(unguMuda);

        txtNama = createField(fieldFont);
        txtJenis = createField(fieldFont);
        txtUmur = createField(fieldFont);
        txtPemilik = createField(fieldFont);

        formHewan.add(createLabel("Nama", labelFont));
        formHewan.add(txtNama);
        formHewan.add(createLabel("Jenis", labelFont));
        formHewan.add(txtJenis);
        formHewan.add(createLabel("Umur", labelFont));
        formHewan.add(txtUmur);
        formHewan.add(createLabel("Pemilik", labelFont));
        formHewan.add(txtPemilik);

        JPanel btnHewan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnHewan.setBackground(unguMuda);

        JButton btnSimpan = createButton("Simpan");
        JButton btnUpdate = createButton("Update");
        JButton btnHapus  = createButton("Hapus");

        btnHewan.add(btnSimpan);
        btnHewan.add(btnUpdate);
        btnHewan.add(btnHapus);

        modelHewan = new DefaultTableModel(
                new String[]{"ID","Nama","Jenis","Umur","Pemilik"},0);
        tableHewan = new JTable(modelHewan);

        panelHewan.add(formHewan, BorderLayout.NORTH);
        panelHewan.add(new JScrollPane(tableHewan), BorderLayout.CENTER);
        panelHewan.add(btnHewan, BorderLayout.SOUTH);

        tabbedPane.addTab("Data Hewan", panelHewan);

        // ================= TAB LAYANAN =================
        JPanel panelLayanan = new JPanel(new BorderLayout(10,10));
        panelLayanan.setBackground(unguMuda);

        JPanel formLayanan = new JPanel(new GridLayout(2,2,10,10));
        formLayanan.setBorder(BorderFactory.createTitledBorder("Form Layanan Klinik"));
        formLayanan.setBackground(unguMuda);

        txtNamaLayanan = createField(fieldFont);
        txtHarga = createField(fieldFont);

        formLayanan.add(createLabel("Nama Layanan", labelFont));
        formLayanan.add(txtNamaLayanan);
        formLayanan.add(createLabel("Harga", labelFont));
        formLayanan.add(txtHarga);

        JPanel btnLayanan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLayanan.setBackground(unguMuda);

        JButton btnSimpanL = createButton("Simpan");
        JButton btnUpdateL = createButton("Update");
        JButton btnHapusL  = createButton("Hapus");

        btnLayanan.add(btnSimpanL);
        btnLayanan.add(btnUpdateL);
        btnLayanan.add(btnHapusL);

        modelLayanan = new DefaultTableModel(
                new String[]{"ID","Nama","Harga"},0);
        tableLayanan = new JTable(modelLayanan);

        panelLayanan.add(formLayanan, BorderLayout.NORTH);
        panelLayanan.add(new JScrollPane(tableLayanan), BorderLayout.CENTER);
        panelLayanan.add(btnLayanan, BorderLayout.SOUTH);

        tabbedPane.addTab("Layanan Klinik", panelLayanan);

        add(tabbedPane);

        // ================= EVENT HEWAN =================
        btnSimpan.addActionListener(e -> {
            hewanApi.insert(new Hewan(
                    0,
                    txtNama.getText(),
                    txtJenis.getText(),
                    Integer.parseInt(txtUmur.getText()),
                    txtPemilik.getText()
            ));
            loadHewan();
            clearHewan();
        });

        btnUpdate.addActionListener(e -> {
            if (selectedHewanId != -1) {
                hewanApi.update(new Hewan(
                        selectedHewanId,
                        txtNama.getText(),
                        txtJenis.getText(),
                        Integer.parseInt(txtUmur.getText()),
                        txtPemilik.getText()
                ));
                loadHewan();
                clearHewan();
                selectedHewanId = -1;
            }
        });

        btnHapus.addActionListener(e -> {
            int row = tableHewan.getSelectedRow();
            if (row >= 0) {
                hewanApi.delete(Integer.parseInt(
                        modelHewan.getValueAt(row, 0).toString()));
                loadHewan();
                clearHewan();
            }
        });

        tableHewan.getSelectionModel().addListSelectionListener(e -> {
            int row = tableHewan.getSelectedRow();
            if (row >= 0) {
                selectedHewanId = Integer.parseInt(modelHewan.getValueAt(row,0).toString());
                txtNama.setText(modelHewan.getValueAt(row,1).toString());
                txtJenis.setText(modelHewan.getValueAt(row,2).toString());
                txtUmur.setText(modelHewan.getValueAt(row,3).toString());
                txtPemilik.setText(modelHewan.getValueAt(row,4).toString());
            }
        });

        // ================= EVENT LAYANAN =================
        btnSimpanL.addActionListener(e -> {
            layananApi.insert(new Layanan(
                    0,
                    txtNamaLayanan.getText(),
                    Integer.parseInt(txtHarga.getText())
            ));
            loadLayanan();
            clearLayanan();
        });

        btnUpdateL.addActionListener(e -> {
            if (selectedLayananId != -1) {
                layananApi.update(new Layanan(
                        selectedLayananId,
                        txtNamaLayanan.getText(),
                        Integer.parseInt(txtHarga.getText())
                ));
                loadLayanan();
                clearLayanan();
                selectedLayananId = -1;
            }
        });

        btnHapusL.addActionListener(e -> {
            int row = tableLayanan.getSelectedRow();
            if (row >= 0) {
                layananApi.delete(Integer.parseInt(
                        modelLayanan.getValueAt(row,0).toString()));
                loadLayanan();
                clearLayanan();
            }
        });

        tableLayanan.getSelectionModel().addListSelectionListener(e -> {
            int row = tableLayanan.getSelectedRow();
            if (row >= 0) {
                selectedLayananId = Integer.parseInt(modelLayanan.getValueAt(row,0).toString());
                txtNamaLayanan.setText(modelLayanan.getValueAt(row,1).toString());
                txtHarga.setText(modelLayanan.getValueAt(row,2).toString());
            }
        });

        loadHewan();
        loadLayanan();
        setVisible(true);
    }

    // ================= HELPER =================
    JLabel createLabel(String t, Font f) {
        JLabel l = new JLabel(t);
        l.setFont(f);
        l.setForeground(unguTua);
        return l;
    }

    JTextField createField(Font f) {
        JTextField tf = new JTextField();
        tf.setFont(f);
        return tf;
    }

    JButton createButton(String t) {
        JButton b = new JButton(t);
        b.setBackground(new Color(155,89,182));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        return b;
    }

    void loadHewan() {
        modelHewan.setRowCount(0);
        for (Hewan h : hewanApi.getAll()) {
            modelHewan.addRow(new Object[]{
                    h.getIdHewan(), h.getNama(),
                    h.getJenis(), h.getUmur(), h.getPemilik()
            });
        }
    }

    void loadLayanan() {
        modelLayanan.setRowCount(0);
        for (Layanan l : layananApi.getAll()) {
            modelLayanan.addRow(new Object[]{
                    l.getId(), l.getNama(), l.getHarga()
            });
        }
    }

    void clearHewan() {
        txtNama.setText("");
        txtJenis.setText("");
        txtUmur.setText("");
        txtPemilik.setText("");
    }

    void clearLayanan() {
        txtNamaLayanan.setText("");
        txtHarga.setText("");
    }
}
