package ui;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    JTextField txtUsername;
    JPasswordField txtPassword;

    // Warna ungu (sama tema)
    Color unguTua = new Color(88, 24, 124);
    Color unguMuda = new Color(232, 218, 239);

    public LoginFrame() {
        setTitle("Login Admin");
        setSize(350, 220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        ((JComponent) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );
        getContentPane().setBackground(unguMuda);

        // ===== PANEL FORM =====
        JPanel panelForm = new JPanel(new GridLayout(2, 2, 10, 10));
        panelForm.setBackground(unguMuda);

        panelForm.add(new JLabel("Username"));
        txtUsername = new JTextField();
        panelForm.add(txtUsername);

        panelForm.add(new JLabel("Password"));
        txtPassword = new JPasswordField();
        panelForm.add(txtPassword);

        // ===== BUTTON =====
        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(unguTua);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setPreferredSize(new Dimension(90, 30));
        
        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelButton.setBackground(unguMuda);
        panelButton.add(btnLogin);

        add(panelForm, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);

        // ===== EVENT LOGIN =====
        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    void login() {
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());

        if (username.equals("admin") && password.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Login berhasil!");
            new MainFrame(); // buka aplikasi utama
            dispose();      // tutup login
        } else {
            JOptionPane.showMessageDialog(this, "Username / Password salah!");
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
