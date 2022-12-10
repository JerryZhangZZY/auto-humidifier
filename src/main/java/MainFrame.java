import java.awt.*;

import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import java.util.Objects;

public class MainFrame extends JFrame {

    private final JPanel contentPane;

    private final JButton btnOff;
    private final JButton btnOne;
    private final JButton btnTwo;
    private final JButton btnThree;
    private final JSpinner spinner;
    private final JToggleButton tglbtnAuto;
    private final JLabel lblOnOff;
    private final JLabel lblFan;
    private final JLabel lblHum;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainFrame() {
        Font btnFont = new Font("Helvetica", Font.PLAIN, 13);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 385, 397);
        setResizable(false);
        setTitle("Loading");
        Image iconImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("icon.png"))).getImage();
        setIconImage(iconImage);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panelCtrl = new JPanel();
        panelCtrl.setBackground(new Color(255, 255, 255, 128));
        panelCtrl.setBounds(10, 232, 349, 115);
        contentPane.add(panelCtrl);
        panelCtrl.setLayout(null);

        btnOff = new JButton("Power off");
        btnOff.setBackground(new Color(250, 128, 114));
        btnOff.setBounds(13, 45, 90, 25);
        btnOff.setFont(btnFont);
        panelCtrl.add(btnOff);

        btnOne = new JButton("1");
        btnOne.setBackground(SystemColor.inactiveCaption);
        btnOne.setBounds(129, 11, 90, 25);
        btnOne.setFont(btnFont);
        panelCtrl.add(btnOne);

        btnTwo = new JButton("2");
        btnTwo.setBackground(SystemColor.activeCaption);
        btnTwo.setBounds(129, 45, 90, 25);
        btnTwo.setFont(btnFont);
        panelCtrl.add(btnTwo);

        btnThree = new JButton("3");
        btnThree.setBackground(SystemColor.textHighlight);
        btnThree.setBounds(129, 79, 90, 25);
        btnThree.setFont(btnFont);
        panelCtrl.add(btnThree);

        spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(50, 30, 70, 1));
        spinner.setBounds(245, 30, 90, 25);
        spinner.setFont(btnFont);
        spinner.setEnabled(false);
        panelCtrl.add(spinner);

        tglbtnAuto = new JToggleButton("Auto: Off");
        tglbtnAuto.setBackground(new Color(95, 158, 160));
        tglbtnAuto.setBounds(245, 66, 90, 25);
        tglbtnAuto.setFont(btnFont);
        tglbtnAuto.addActionListener(e -> {
            boolean isSelected = tglbtnAuto.isSelected();
            tglbtnAuto.setText(isSelected ? "Auto: on" : "Auto: off");
            spinner.setEnabled(isSelected);
        });
        tglbtnAuto.setSelected(false);
        panelCtrl.add(tglbtnAuto);

        JLabel lblIcon = new JLabel("");
        lblIcon.setBounds(128, 11, 113, 210);
        ImageIcon icon = new ImageIcon(iconImage.getScaledInstance(113, 210, java.awt.Image.SCALE_SMOOTH));
        lblIcon.setIcon(icon);
        contentPane.add(lblIcon);

        lblOnOff = new JLabel("-");
        lblOnOff.setForeground(Color.WHITE);
        lblOnOff.setHorizontalAlignment(SwingConstants.CENTER);
        lblOnOff.setFont(new Font("Arial", Font.BOLD, 25));
        lblOnOff.setBounds(10, 11, 113, 80);
        contentPane.add(lblOnOff);

        lblFan = new JLabel("-");
        lblFan.setHorizontalAlignment(SwingConstants.CENTER);
        lblFan.setForeground(Color.WHITE);
        lblFan.setFont(new Font("Arial", Font.BOLD, 25));
        lblFan.setBounds(10, 102, 108, 80);
        contentPane.add(lblFan);

        lblHum = new JLabel("-");
        lblHum.setHorizontalAlignment(SwingConstants.CENTER);
        lblHum.setForeground(Color.WHITE);
        lblHum.setFont(new Font("Arial", Font.BOLD, 25));
        lblHum.setBounds(246, 11, 113, 80);
        contentPane.add(lblHum);
    }

    public JButton getBtnOff() {
        return btnOff;
    }

    public JButton getBtnOne() {
        return btnOne;
    }

    public JButton getBtnTwo() {
        return btnTwo;
    }

    public JButton getBtnThree() {
        return btnThree;
    }

    public JSpinner getSpinner() {
        return spinner;
    }

    public JToggleButton getTglbtnAuto() {
        return tglbtnAuto;
    }

    public JLabel getLblOnOff() {
        return lblOnOff;
    }

    public JLabel getLblFan() {
        return lblFan;
    }

    public JLabel getLblHum() {
        return lblHum;
    }
}
