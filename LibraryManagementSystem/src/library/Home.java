package library;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

public class Home extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Home frame = new Home();
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
    public Home() {
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1365, 778);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);

        JButton btnNewButton = new JButton("Manage Students");
        btnNewButton.setBounds(37, 278, 244, 59);
        btnNewButton.addActionListener(e -> new NewStudent().setVisible(true));
        contentPane.setLayout(null);
        
        JButton btnNewButton_6 = new JButton("View Issued Books");
        btnNewButton_6.setBounds(37, 625, 244, 59);
        btnNewButton_6.addActionListener(e -> new IssuedBookDetails().setVisible(true));
        
        
    
        JButton btnNewButton_7 = new JButton("View Records");
        btnNewButton_7.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton_7.setBounds(37, 208, 244, 59);
        btnNewButton_7.setIcon(new ImageIcon(Home.class.getResource("/Icons/save.png"))); // Palitan ng tamang icon
        btnNewButton_7.addActionListener(e -> new ViewRecords().setVisible(true));
        contentPane.add(btnNewButton_7);

        btnNewButton_6.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnNewButton_6.setIcon(new ImageIcon(Home.class.getResource("/Icons/issue book.png")));
        contentPane.add(btnNewButton_6);
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnNewButton.setIcon(new ImageIcon(Home.class.getResource("/Icons/student.png")));
        contentPane.add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Issue");
        btnNewButton_1.setBounds(37, 488, 244, 59);
        btnNewButton_1.addActionListener(e -> new issueBook().setVisible(true));
        btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnNewButton_1.setIcon(new ImageIcon(Home.class.getResource("/Icons/issue1.png")));
        contentPane.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Logout");
        btnNewButton_2.setBounds(37, 708, 244, 59);
        btnNewButton_2.setIcon(new ImageIcon(Home.class.getResource("/Icons/logout.png")));
        btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnNewButton_2.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to logout?", 
                "Logout Confirmation", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
            
            if (response == JOptionPane.YES_OPTION) {
                new Login().setVisible(true);
                dispose();
            }
        });
        contentPane.add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("Manage Books");
        btnNewButton_3.setBounds(37, 348, 244, 59);
        btnNewButton_3.addActionListener(e -> new NewBook().setVisible(true));
        btnNewButton_3.setIcon(new ImageIcon(Home.class.getResource("/Icons/booknew.png")));
        btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(btnNewButton_3);

        JButton btnNewButton_4 = new JButton("Statistics");
        btnNewButton_4.setBounds(37, 418, 244, 59);
        btnNewButton_4.addActionListener(e -> new statistics().setVisible(true));
        btnNewButton_4.setIcon(new ImageIcon(Home.class.getResource("/Icons/stat.png")));
        btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(btnNewButton_4);

        JButton btnNewButton_5 = new JButton("Return Book");
        btnNewButton_5.setBounds(37, 558, 244, 59);
        btnNewButton_5.addActionListener(e -> new returnBook().setVisible(true));
        btnNewButton_5.setIcon(new ImageIcon(getClass().getResource("/Icons/return-book-1-560407.png")));
        btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(btnNewButton_5);

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setBounds(0, 11, 96, 89);
        lblNewLabel_2.setIcon(new ImageIcon(Home.class.getResource("/Icons/SHS.png_3nobg.png")));
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_4 = new JLabel("LMS");
        lblNewLabel_4.setBounds(106, 30, 76, 51);
        lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 30));
        contentPane.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("SHS 3");
        lblNewLabel_5.setBounds(100, 87, 96, 37);
        lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 30));
        contentPane.add(lblNewLabel_5);
        
        JLabel lblNewLabel_7 = new JLabel("LMS Dashboard");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel_7.setIcon(new ImageIcon(Home.class.getResource("/Icons/LMS.png")));
        lblNewLabel_7.setBounds(37, 138, 244, 59);
        contentPane.add(lblNewLabel_7);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(0, 0, 321, 778);
        lblNewLabel_1.setIcon(new ImageIcon(Home.class.getResource("/Icons/white-paper.jpg")));
        lblNewLabel_1.setBackground(new Color(0, 0, 0));
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(0, 0, 1365, 778);
        lblNewLabel.setIcon(new ImageIcon(getClass().getResource("/Icons/Library_Book_532388_1366x768.jpg")));
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_3 = new JLabel("New label");
        lblNewLabel_3.setBounds(247, 106, 46, 14);
        contentPane.add(lblNewLabel_3);

    }
}
