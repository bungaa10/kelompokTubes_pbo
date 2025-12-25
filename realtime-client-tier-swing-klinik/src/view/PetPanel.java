package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import controller.PetController;
import model.Pet;

import java.awt.*;
import java.util.Vector;

public class PetPanel extends JPanel {

    
    DefaultTableModel model;
    JTable table;
    
    JTextField txtNama = new JTextField();
    JTextField txtJenis = new JTextField();
    JTextField txtumur = new JTextField();
    JTextField txtPemilik = new JTextField();
    JComboBox<String> cmbLayanan = new JComboBox<>(
        new String[]{"VAKSIN", "PEMERIKSAAN", "HEMATOLOGY", "OPERASI","RAWAT INAP"}
    );
    JTextField txtHarga = new JTextField();
    private void initComponents() {
        // semua kode layout kamu
        // bikin form, tombol, table, dll

        model = new DefaultTableModel(
            new String[]{"ID","Nama","Jenis","Umur","Pemilik","Layanan","Harga"},
            0
        );

        table = new JTable(model);
        add(new JScrollPane(table));
    }

    // ====== METHOD LOAD DATA ======
    private void loadTable() {
        model.setRowCount(0);

        for (Pet row : PetController.getAll()) {
            model.addRow(new Object[]{
                row.getId(),
                row.getnama(),
                row.getjenis(),
                row.getumur(),
                row.getpemilik(),
                row.getlayanan(),
                row.getharga()
            });
        }
    }

    Color PURPLE = new Color(124, 58, 237);
    Color LIGHT_PURPLE = new Color(237, 233, 254);

    public PetPanel() {
        initComponents(); // bikin UI
        loadTable(); 
        setLayout(new BorderLayout(10, 10));
        setBackground(LIGHT_PURPLE);
        setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        add(createFormPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
    }

    // ================= FORM PANEL =================
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PURPLE,2),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        JLabel title = new JLabel("🐾 Data Hewan Klinik");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(PURPLE);

        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(2,4,10,10));
        form.setBackground(Color.WHITE);

        form.add(new JLabel("Nama Hewan"));
        form.add(txtNama);
        form.add(new JLabel("Jenis"));
        form.add(txtJenis);
        form.add(new JLabel("Umur"));
        form.add(txtumur);
        form.add(new JLabel("Pemilik"));
        form.add(txtPemilik);
        form.add(new JLabel("Layanan"));
        form.add(cmbLayanan);
        form.add(new JLabel("Harga"));
        form.add(txtHarga);

        panel.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        buttons.setBackground(Color.WHITE);

        JButton btnTambah = createButton("Tambah");
        JButton btnEdit   = createButton("Edit");
        JButton btnHapus  = createButton("Hapus");

        buttons.add(btnTambah);
        buttons.add(btnEdit);
        buttons.add(btnHapus);

        panel.add(buttons, BorderLayout.SOUTH);

        // ================== LOGIC ==================
        btnTambah.addActionListener(e -> {
    PetController.insert(
        txtNama.getText(),
        txtJenis.getText(),
        txtumur.getText(),
        txtPemilik.getText(),
        cmbLayanan.getSelectedItem().toString(),
        Integer.parseInt(txtHarga.getText())
    );

    loadTable(); // ⬅️ baca ulang dari DB
});



btnEdit.addActionListener(e -> {
    int row = table.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Pilih data dulu!");
        return;
    }

    int id = (int) model.getValueAt(row, 0); // idHewan

    PetController.update(
        id,
        txtNama.getText(),
        txtJenis.getText(),
        txtumur.getText(),
        txtPemilik.getText(),
        cmbLayanan.getSelectedItem().toString(),
        Integer.parseInt(txtHarga.getText())
    );

    loadTable();
});


    btnHapus.addActionListener(e -> {
    int row = table.getSelectedRow();
    if (row < 0) {
        JOptionPane.showMessageDialog(this, "Pilih data dulu!");
        return;
    }

    int id = (int) model.getValueAt(row, 0); // idHewan

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Yakin hapus data?",
        "Konfirmasi",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        PetController.delete(id);
        loadTable();
    }
});

    
    cmbLayanan.addActionListener(e -> {
        switch (cmbLayanan.getSelectedItem().toString()) {
            case "VAKSIN" -> txtHarga.setText("50000");
            case "PEMERIKSAAN" -> txtHarga.setText("30000");
            case "HEMATOLOGY" -> txtHarga.setText("70000");
            case "OPERASI" -> txtHarga.setText("500000");
            case "RAWAT INAP" -> txtHarga.setText("200000");
            default -> txtHarga.setText("0");
        }
    });

    return panel;
}

    // ================= TABLE PANEL =================
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(PURPLE,2));

        model = new DefaultTableModel(
                new String[]{"ID","Nama Hewan","Jenis","Umur","Pemilik", "Layanan", "Harga"},0
        );

        table = new JTable(model);
        table.setRowHeight(28);
        table.setSelectionBackground(PURPLE);
        table.setSelectionForeground(Color.WHITE);

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtNama.setText(model.getValueAt(row,1).toString());
                txtJenis.setText(model.getValueAt(row,2).toString());
                txtPemilik.setText(model.getValueAt(row,3).toString());
                cmbLayanan.setSelectedItem(model.getValueAt(row,4).toString());
                txtHarga.setText(model.getValueAt(row,5).toString());
            }
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // ================= UTIL =================
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(PURPLE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    // private void clearForm() {
    //     txtNama.setText("");
    //     txtJenis.setText("");
    //     txtPemilik.setText("");
    // }
    



}
