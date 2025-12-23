package ui;

import dao.HewanDao;
import model.Hewan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    JTextField txtNama, txtJenis, txtUmur, txtPemilik;
    JTable table;
    DefaultTableModel model;
    HewanDao dao = new HewanDao();

    int selectedId = -1;

    // 🎨 WARNA UNGU
    Color unguTua = new Color(88, 24, 124);
    Color unguMuda = new Color(232, 218, 239);
    Color putih = Color.WHITE;

    public MainFrame() {
        setTitle("Aplikasi Klinik Hewan");
        setSize(750, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        ((JComponent) getContentPane()).setBorder(
        BorderFactory.createEmptyBorder(15, 15, 15, 15)
);


        // ===== PANEL FORM =====
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 12, 12));
        panelForm.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(unguTua, 2),
            "Form Data Hewan"
        ),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)
    )
);

        panelForm.setBackground(unguMuda);

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 13);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 13);

        panelForm.add(createLabel("Nama", fontLabel));
        txtNama = createTextField(fontField);
        panelForm.add(txtNama);

        panelForm.add(createLabel("Jenis", fontLabel));
        txtJenis = createTextField(fontField);
        panelForm.add(txtJenis);

        panelForm.add(createLabel("Umur", fontLabel));
        txtUmur = createTextField(fontField);
        panelForm.add(txtUmur);

        panelForm.add(createLabel("Pemilik", fontLabel));
        txtPemilik = createTextField(fontField);
        panelForm.add(txtPemilik);

        // ===== PANEL BUTTON =====
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelButton.setBackground(unguMuda);

        JButton btnSimpan = createButton("Simpan", new Color(155, 89, 182));
        JButton btnUpdate = createButton("Update", new Color(125, 60, 152));
        JButton btnHapus  = createButton("Hapus",  new Color(108, 52, 131));

        panelButton.add(btnSimpan);
        panelButton.add(btnUpdate);
        panelButton.add(btnHapus);

        // ===== PANEL ATAS =====
        JPanel panelAtas = new JPanel(new BorderLayout());
        panelAtas.add(panelForm, BorderLayout.CENTER);
        panelAtas.add(panelButton, BorderLayout.SOUTH);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new String[]{"ID", "Nama", "Jenis", "Umur", "Pemilik"}, 0
        );
        table = new JTable(model);
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(unguTua);
        table.getTableHeader().setForeground(putih);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(unguTua, 2),
            "Data Hewan"
        ),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    )
);


        add(panelAtas, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);

        // ===== LOAD DATA =====
        loadData();

        // ===== EVENT SIMPAN =====
        btnSimpan.addActionListener(e -> {
            dao.insert(new Hewan(
                    0,
                    txtNama.getText(),
                    txtJenis.getText(),
                    Integer.parseInt(txtUmur.getText()),
                    txtPemilik.getText()
            ));
            loadData();
            clearForm();
        });

        // ===== EVENT UPDATE =====
        btnUpdate.addActionListener(e -> {
            if (selectedId != -1) {
                dao.update(new Hewan(
                        selectedId,
                        txtNama.getText(),
                        txtJenis.getText(),
                        Integer.parseInt(txtUmur.getText()),
                        txtPemilik.getText()
                ));
                loadData();
                clearForm();
                selectedId = -1;
            } else {
                JOptionPane.showMessageDialog(this, "Pilih data di tabel dulu!");
            }
        });

        // ===== EVENT HAPUS =====
        btnHapus.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int idHewan = Integer.parseInt(model.getValueAt(row, 0).toString());
                dao.delete(idHewan);
                loadData();
                clearForm();
            }
        });

        // ===== KLIK TABEL =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                selectedId = Integer.parseInt(model.getValueAt(row, 0).toString());
                txtNama.setText(model.getValueAt(row, 1).toString());
                txtJenis.setText(model.getValueAt(row, 2).toString());
                txtUmur.setText(model.getValueAt(row, 3).toString());
                txtPemilik.setText(model.getValueAt(row, 4).toString());
            }
        });

        getContentPane().setBackground(unguMuda);
        setVisible(true);
    }

    // ===== HELPER UI =====
    JLabel createLabel(String text, Font f) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(f);
        lbl.setForeground(unguTua);
        return lbl;
    }

    JTextField createTextField(Font f) {
        JTextField tf = new JTextField();
        tf.setFont(f);
        return tf;
    }

    JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        return btn;
    }

    void loadData() {
        model.setRowCount(0);
        ArrayList<Hewan> list = dao.getAll();
        for (Hewan h : list) {
            model.addRow(new Object[]{
                    h.getIdHewan(),
                    h.getNama(),
                    h.getJenis(),
                    h.getUmur(),
                    h.getPemilik()
            });
        }
    }

    void clearForm() {
        txtNama.setText("");
        txtJenis.setText("");
        txtUmur.setText("");
        txtPemilik.setText("");
    }

}
