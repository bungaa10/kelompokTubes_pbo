package view;

import javax.swing.*;

public class AboutPanel extends JPanel {

    public AboutPanel() {
        add(new JLabel(
            "<html><h2>Klinik Hewan Realtime</h2>" +
            "<p>Aplikasi berbasis Java Swing & WebSocket</p>" +
            "<p>3-Tier Architecture</p></html>"
        ));
    }
}
