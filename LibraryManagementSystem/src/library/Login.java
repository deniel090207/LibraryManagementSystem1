package library;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Project.ConnectionProvider;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JComboBox<String> roleComboBox;
	private JButton toggleButton;
	private boolean isPasswordVisible = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 735);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setIcon(new ImageIcon(Login.class.getResource("/Icons/SHS.pngnobg.png")));
		lblNewLabel_4.setBounds(258, 196, 300, 300);
		contentPane.add(lblNewLabel_4);

		JLabel lblNewLabel_3 = new JLabel("LiBar-E LOGIN PAGE");
		lblNewLabel_3.setForeground(new Color(0, 0, 0));
		lblNewLabel_3.setBackground(new Color(255, 255, 255));
		lblNewLabel_3.setFont(new Font("Rockwell Condensed", Font.BOLD, 35));
		lblNewLabel_3.setBounds(692, 209, 311, 73);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel = new JLabel("Username:");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(686, 285, 104, 14);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Password:");
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setBounds(686, 323, 114, 14);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 18));
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("User type:");
		lblNewLabel_2.setForeground(new Color(0, 0, 0));
		lblNewLabel_2.setBounds(686, 366, 89, 22);
		lblNewLabel_2.setFont(new Font("Dialog", Font.BOLD, 18));
		contentPane.add(lblNewLabel_2);

		textField = new JTextField();
		textField.setBounds(789, 278, 238, 30);
		textField.setBackground(new Color(192, 192, 192));
		textField.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(789, 318, 238, 30);
		passwordField.setBackground(new Color(192, 192, 192));
		contentPane.add(passwordField);

		// Role ComboBox
		roleComboBox = new JComboBox<>(new String[] { "Select Role", "Admin", "Teacher", "Student", "User" });
		roleComboBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		roleComboBox.setBounds(789, 358, 238, 30);
		contentPane.add(roleComboBox);

		// Eye icon button to toggle password visibility
		toggleButton = new JButton();
		toggleButton.setBounds(1034, 323, 32, 22);
		toggleButton.setBorder(null);
		toggleButton.setContentAreaFilled(false);
		contentPane.add(toggleButton);

		// Set initial eye icon
		toggleButton.setIcon(resizeIcon("/Icons/eye-open.png", 25, 25));

		toggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isPasswordVisible = !isPasswordVisible;
				if (isPasswordVisible) {
					passwordField.setEchoChar((char) 0);
					toggleButton.setIcon(resizeIcon("/Icons/eye-close.png", 25, 25));
				} else {
					passwordField.setEchoChar('•');
					toggleButton.setIcon(resizeIcon("/Icons/eye-open.png", 25, 25));
				}
			}
		});

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogin.setBackground(new Color(255, 255, 255));
		btnLogin.setBounds(788, 445, 104, 23);
		btnLogin.setIcon(new ImageIcon(Login.class.getResource("/Icons/login2-removebg-preview.png")));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField.getText();
				String password = new String(passwordField.getPassword());
				String role = (String) roleComboBox.getSelectedItem();

				if (authenticateUser(username, password, role)) {
					JOptionPane.showMessageDialog(null, "Login Successful!");
					setVisible(false);

					// Open the correct home screen based on role
					if ("Admin".equals(role)) {
						new Home().setVisible(true);
					} else if ("Teacher".equals(role)) {
						new HomeTeacher().setVisible(true);
					} else if ("Student".equals(role)) {
						new HomeStudent().setVisible(true);
					} else if ("User".equals(role)) {
						new HomeUser().setVisible(true);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect Username, Password, or Role.");
				}
			}
		});
		contentPane.add(btnLogin);

		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClose.setBackground(new Color(255, 255, 255));
		btnClose.setBounds(922, 445, 104, 23);
		btnClose.setIcon(new ImageIcon(Login.class.getResource("/Icons/x.png")));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to close?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		contentPane.add(btnClose);

		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(Login.class.getResource("/Icons/people-removebg-preview.png")));
		lblNewLabel_5.setBounds(650, 278, 26, 25);
		contentPane.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon(Login.class.getResource("/Icons/lock-removebg-preview.png")));
		lblNewLabel_6.setBounds(651, 321, 25, 25);
		contentPane.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("");
		lblNewLabel_7.setIcon(new ImageIcon(Login.class.getResource("/Icons/hoho.jpg")));
		lblNewLabel_7.setBounds(621, 202, 445, 294);
		contentPane.add(lblNewLabel_7);

		JLabel lblBackground = new JLabel("");
		lblBackground.setBackground(new Color(255, 255, 255));
		lblBackground.setFont(new Font("Dialog", Font.BOLD, 18));
		lblBackground.setBounds(-22, -25, 1322, 750);
		lblBackground.setIcon(new ImageIcon(Login.class.getResource("/Icons/Resize image project.png")));
		contentPane.add(lblBackground);

	}

	private boolean authenticateUser(String username, String password, String role) {
		try {
			Connection con = ConnectionProvider.getCon();
			PreparedStatement ps = con
					.prepareStatement("SELECT * FROM users WHERE username=? AND password=? AND role=?");
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, role);
			ResultSet rs = ps.executeQuery();

			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private ImageIcon resizeIcon(String path, int width, int height) {
		try {
			ImageIcon icon = new ImageIcon(getClass().getResource(path));
			Image img = icon.getImage();
			Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			return new ImageIcon(resizedImg);
		} catch (Exception e) {
			System.out.println("Icon not found: " + path);
			return null;
		}
	}
}
