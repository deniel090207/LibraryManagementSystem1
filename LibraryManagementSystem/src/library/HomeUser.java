package library;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

public class HomeUser extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeUser frame = new HomeUser();
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
	public HomeUser() {
		setUndecorated(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1365, 778);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton_2 = new JButton("Logout");
		btnNewButton_2.setBounds(37, 469, 244, 59);
		btnNewButton_2.setIcon(new ImageIcon(Home.class.getResource("/Icons/logout.png")));
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton_2.addActionListener(e -> {
			int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?",
					"Logout Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (response == JOptionPane.YES_OPTION) {
				new Login().setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnNewButton_2);

		JLabel lblNewLabel_5 = new JLabel("Welcome, User!");
		lblNewLabel_5.setIcon(new ImageIcon(HomeStudent.class.getResource("/Icons/people-removebg-preview.png")));
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5.setBounds(1148, 10, 176, 35);
		contentPane.add(lblNewLabel_5);

		JButton btnNewButton_4 = new JButton("My Record");
		btnNewButton_4.setBounds(37, 331, 244, 59);
		btnNewButton_4.addActionListener(e -> new ViewRecords().setVisible(true));
		btnNewButton_4.setIcon(new ImageIcon(Home.class.getResource("/Icons/stat.png")));
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnNewButton_4);

		JButton btnNewButton_5 = new JButton("Return Book");
		btnNewButton_5.setBounds(37, 400, 244, 59);
		btnNewButton_5.addActionListener(e -> new returnBook().setVisible(true));
		btnNewButton_5.setIcon(new ImageIcon(getClass().getResource("/Icons/return-book-1-560407.png")));
		btnNewButton_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnNewButton_5);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(21, 31, 118, 89);
		lblNewLabel_2.setIcon(
				new ImageIcon(HomeStudent.class.getResource("/Icons/Adobe_Express_-_file__2_-removebg-preview.png")));
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_4 = new JLabel("LiBar-E");
		lblNewLabel_4.setBounds(114, 51, 187, 51);
		lblNewLabel_4.setFont(new Font("Imprint MT Shadow", Font.BOLD, 30));
		contentPane.add(lblNewLabel_4);

		JLabel lblNewLabel_7 = new JLabel("LMS Dashboard");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_7.setIcon(new ImageIcon(Home.class.getResource("/Icons/LMS.png")));
		lblNewLabel_7.setBounds(84, 193, 244, 59);
		contentPane.add(lblNewLabel_7);

		JButton btnNewButton_3 = new JButton("Request Book");
        btnNewButton_3.setBounds(37, 263, 244, 59);
        btnNewButton_3.addActionListener(e -> new NewBook().setVisible(true));
        btnNewButton_3.setIcon(new ImageIcon(Home.class.getResource("/Icons/booknew.png")));
        btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(btnNewButton_3);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(0, 0, 321, 778);
		lblNewLabel_1.setIcon(new ImageIcon(Home.class.getResource("/Icons/white-paper.jpg")));
		lblNewLabel_1.setBackground(new Color(0, 0, 0));
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, 0, 1365, 778);
		lblNewLabel.setIcon(new ImageIcon(HomeStudent.class.getResource("/Icons/Resize image project.png")));
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setBounds(247, 106, 46, 14);
		contentPane.add(lblNewLabel_3);

	}
}
