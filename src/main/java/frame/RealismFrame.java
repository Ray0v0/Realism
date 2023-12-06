package frame;

import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class RealismFrame extends JFrame {

    float opacity = 0.45f;

    public static void init() {
        FlatDarkLaf.setup();

        UIManager.put("Button.arc", 0);
    }

    public RealismFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.add(new JButton("test"));
        setContentPane(mainPanel);
        setUndecorated(true);
        setOpacity();

        setVisible(true);
    }


    public void setOpacity() {
        super.setOpacity(opacity);
    }

    @Override
    public void setOpacity(float opacity) {
        this.opacity = opacity;
        setOpacity();
    }

    public enum Theme {
        DARK, LIGHT
    }
}
