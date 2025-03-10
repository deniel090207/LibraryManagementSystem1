package library;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class Loading extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private static JProgressBar progressBar;
    private static JLabel loadingValue;
    private JLabel lblNewLabel_2;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Loading frame = new Loading();
                    frame.setVisible(true);
                    frame.startLoading(); // Start progress bar animation
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Loading() {
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Background Panel
        JPanel BackGroundPanel = new JPanel();
        BackGroundPanel.setBounds(0, 0, 900, 500);
        BackGroundPanel.setLayout(null);
        contentPane.add(BackGroundPanel);

        // Background Image
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(Loading.class.getResource("/Icons/Resize image project.png")));
        lblNewLabel.setBounds(0, 0, 900, 513);
        BackGroundPanel.add(lblNewLabel);

        // Logo
        lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setIcon(new ImageIcon(Loading.class.getResource("/Icons/SHS.pngnobg.png")));
        lblNewLabel_2.setBounds(281, 10, 306, 330);
        BackGroundPanel.add(lblNewLabel_2);

        // Library System Text
        JLabel lblNewLabel_1 = new JLabel("LiBar-E Loading");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 40));
        lblNewLabel_1.setForeground(Color.WHITE);
        lblNewLabel_1.setBounds(281, 350, 400, 50);
        BackGroundPanel.add(lblNewLabel_1);

        // Progress Bar
        progressBar = new JProgressBar();
        progressBar.setBounds(0, 475, 900, 14);
        progressBar.setStringPainted(true);
        BackGroundPanel.add(progressBar);

        // Loading Percentage
        loadingValue = new JLabel("0 %");
        loadingValue.setFont(new Font("Tahoma", Font.BOLD, 17));
        loadingValue.setForeground(Color.WHITE);
        loadingValue.setBounds(822, 456, 78, 20);
        BackGroundPanel.add(loadingValue);

        // ✅ Set Z-order AFTER adding all components
        SwingUtilities.invokeLater(() -> {
            BackGroundPanel.setComponentZOrder(loadingValue, 0);
            BackGroundPanel.setComponentZOrder(lblNewLabel_1, 1);
            BackGroundPanel.setComponentZOrder(lblNewLabel_2, 2);
            BackGroundPanel.setComponentZOrder(lblNewLabel, 3);
        });
    }

    /**
     * Simulate loading process
     */
    public void startLoading() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(50); // Delay for smooth loading effect
                        progressBar.setValue(i);
                        loadingValue.setText(i + " %");
                    }
                    // When loading is complete, open the main application
                    dispose(); // Close loading screen
                    Login login = new Login(); // Open login screen
                    login.setVisible(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
