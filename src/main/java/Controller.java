import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {

    private final int BUFFER = 2;
    private final long REFRESH_PERIOD = 1L;

    private final MainFrame mainFrame;
    private final JPanel contentPane;
    private final JButton btnOff;
    private final JButton btnOne;
    private final JButton btnTwo;
    private final JButton btnThree;
    private final JButton[] btns;
    private final JSpinner spinner;
    private final JToggleButton tglbtnAuto;
    private final JLabel lblOnOff;
    private final JLabel lblFan;
    private final JLabel lblHum;
    private final Color bgOn = new Color(95, 158, 160);
    private final Color bgOff = new Color(250, 128, 114);

    private boolean onOff;
    private int fan;
    private int hum;

    private boolean init = false;
    private boolean initAuto = false;

    public Controller() {
        mainFrame = new MainFrame();
        contentPane = (JPanel) mainFrame.getContentPane();
        btnOff = mainFrame.getBtnOff();
        btnOne = mainFrame.getBtnOne();
        btnTwo = mainFrame.getBtnTwo();
        btnThree = mainFrame.getBtnThree();
        btns = new JButton[]{btnOff, btnOne, btnTwo, btnThree};
        spinner = mainFrame.getSpinner();
        tglbtnAuto = mainFrame.getTglbtnAuto();
        lblOnOff = mainFrame.getLblOnOff();
        lblFan = mainFrame.getLblFan();
        lblHum = mainFrame.getLblHum();

        for (int i = 0; i < btns.length; i++) {
            int finalI = i;
            btns[i].addActionListener(e -> setFan(finalI));
        }

        spinner.addChangeListener(e -> initAuto = false);

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(5);
        ses.scheduleAtFixedRate(() -> {
            update();
            if (tglbtnAuto.isSelected()) {
                // auto humidity activated
                int target = (int) spinner.getValue();
                int dif = target - hum;

                if (!initAuto) {
                    if (dif < BUFFER && dif > -BUFFER) {
                        setFan(dif > 0 ? 1 : 0);
                    }
                    initAuto = true;
                }

                if (dif > 15) {
                    setFan(3);
                } else if (dif > BUFFER) {
                    setFan(2);
                } else if (dif >= BUFFER) {
                    setFan(1);
                } else if (dif <= -BUFFER) {
                    setFan(0);
                }
            } else {
                initAuto = false;
            }
        }, 0, REFRESH_PERIOD, TimeUnit.SECONDS);

        display();
    }

    public void display() {
        mainFrame.setVisible(true);
    }

    public void update() {
        ArrayList<Object> status = Tool.getStatus();
        if (status != null) {
            System.out.println(status);
            boolean newOnOff = (boolean) status.get(0);
            int newFan = (int) status.get(1);
            int newHum = (int) status.get(2);
            if (newOnOff != onOff || !init) {
                onOff = newOnOff;
                lblOnOff.setText(onOff ? "ON" : "OFF");
                contentPane.setBackground(onOff ? bgOn : bgOff);
                init = true;
            }
            if (newFan != fan) {
                fan = newFan;
                lblFan.setText(String.valueOf(fan));
            }
            if (newHum != hum) {
                hum = newHum;
                lblHum.setText(hum + "%");
            }
        }
        if (init)
            mainFrame.setTitle("Auto Humidity Control System");
    }

    public void setFan(int level) {
        if (level == 0) {
            // power off
            if (onOff)
                Tool.setOff();
        } else {
            // set fan level
            if (!onOff)
                Tool.setOn();
            if (fan != level)
                Tool.setFan(level);
        }
    }
}
