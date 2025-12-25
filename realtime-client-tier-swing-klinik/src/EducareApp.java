import view.MainFrame;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;

public class EducareApp {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch(Exception ignored){}

        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
