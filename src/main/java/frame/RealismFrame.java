package frame;

import com.formdev.flatlaf.FlatDarkLaf;
import org.yaml.snakeyaml.Yaml;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RealismFrame extends JFrame{


    JPanel mainPanel = new JPanel();
    JLabel testLabel = new JLabel("————————");


    // bound
    int x = 100, y = 100, width = 300, height = 150;

    // opacity
    double opacityLost = 0.45, opacityFocus = 0.9;


    // 鼠标移动
    Point init;


    public static void init() {
        FlatDarkLaf.setup();
    }

    public RealismFrame() {
        super("Realism");

        loadSettings();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(x, y, width, height);

        mainPanel.add(testLabel);
        setContentPane(mainPanel);
        setUndecorated(true);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setOpacity((float) opacityFocus);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setOpacity((float) opacityLost);
            }
        });



        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 记录按下的位置
                init = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x = (int) getLocation().getX();
                y = (int) getLocation().getY();
                saveSettings();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                // 计算移动的位置
                setLocation((int) (p.getX() + (e.getX() - init.getX())), (int) (p.getY() + e.getY() - init.getY()));
            }
        });

        testLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setState(JFrame.ICONIFIED);
            }
        });

        setAlwaysOnTop(true);
        setResizable(true);
        setVisible(true);
    }

    private void loadSettings() {
        try {
            Yaml yaml = new Yaml();
            InputStream settingsFile = new FileInputStream("settings.yaml");
            Map<String, Object> settings = yaml.load(settingsFile);

            //noinspection unchecked
            Map<String, Integer> bound = (Map<String, Integer>) settings.get("bound");
            x = bound.get("x");
            y = bound.get("y");
            width = bound.get("width");
            height = bound.get("height");

            //noinspection unchecked
            Map<String, Double> opacity = (Map<String, Double>) settings.get("opacity");
            opacityLost = opacity.get("lost");
            opacityFocus = opacity.get("focus");

        } catch (Exception ignore) {
        }
    }

    private void saveSettings() {
        try {
            Yaml yaml = new Yaml();
            Writer writer = new FileWriter("settings.yaml");
            Map<String, Object> settings = new HashMap<>();
            Map<String, Integer> bound = new HashMap<>();
            bound.put("x", x);
            bound.put("y", y);
            bound.put("width", width);
            bound.put("height", height);

            Map<String, Double> opacity = new HashMap<>();
            opacity.put("lost", opacityLost);
            opacity.put("focus", opacityFocus);

            settings.put("bound", bound);
            settings.put("opacity", opacity);

            yaml.dump(settings, writer);

        } catch (Exception ignore) {
        }
    }

}
