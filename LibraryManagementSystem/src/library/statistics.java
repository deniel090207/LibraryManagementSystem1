package library;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import Project.ConnectionProvider;

public class statistics extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableStudents, tableBooks;
    private JLabel lblTotalBooks, lblTotalStudents, lblIssuedBooks, lblDefaulters;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                statistics frame = new statistics();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public statistics() {
    	setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Library Statistics");
        lblTitle.setForeground(new Color(0, 0, 255));
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblTitle.setBounds(400, 10, 200, 30);
        contentPane.add(lblTitle);

        // Statistics Panels
        lblTotalBooks = createStatPanel("No. of Books", 50, 50, Color.ORANGE);
        lblTotalStudents = createStatPanel("No. of Students", 300, 50, Color.YELLOW);
        lblIssuedBooks = createStatPanel("Issued Books", 550, 50, Color.GREEN);
        lblDefaulters = createStatPanel("Defaulter List", 800, 50, Color.MAGENTA);

        // Student Details Table
        JLabel lblStudentDetails = new JLabel("Student Details");
        lblStudentDetails.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblStudentDetails.setBounds(50, 150, 150, 20);
        contentPane.add(lblStudentDetails);

        tableStudents = new JTable();
        JScrollPane scrollStudents = new JScrollPane(tableStudents);
        scrollStudents.setBounds(50, 180, 400, 200);
        contentPane.add(scrollStudents);

        // Book Details Table
        JLabel lblBookDetails = new JLabel("Book Details");
        lblBookDetails.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBookDetails.setBounds(500, 150, 150, 20);
        contentPane.add(lblBookDetails);

        tableBooks = new JTable();
        JScrollPane scrollBooks = new JScrollPane(tableBooks);
        scrollBooks.setBounds(500, 180, 400, 200);
        contentPane.add(scrollBooks);

        // Close Button
     // Close Button with Confirmation
        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnClose.setBackground(Color.RED);
        btnClose.setForeground(Color.WHITE);
        btnClose.setBounds(850, 10, 100, 30);
        btnClose.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to close?", 
                "Close Confirmation", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE);
            
            if (response == JOptionPane.YES_OPTION) {
                dispose(); // Isasara ang window
            }
        });
        contentPane.add(btnClose);


        // Load Data on Start
        loadStatistics();
    }

    private JLabel createStatPanel(String title, int x, int y, Color color) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        panel.setBounds(x, y, 200, 80);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitle.setBounds(10, 10, 180, 20);
        panel.add(lblTitle);

        JLabel lblValue = new JLabel("0");
        lblValue.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblValue.setBounds(10, 40, 180, 30);
        panel.add(lblValue);

        return lblValue;
    }

    private void loadStatistics() {
        try {
            Connection con = ConnectionProvider.getCon();

            // Load No. of Books
            String queryBooks = "SELECT COUNT(*) AS 'No. of Books' FROM book";
            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery(queryBooks);
            if (rs1.next()) lblTotalBooks.setText(rs1.getString("No. of Books"));

            // Load No. of Students
            String queryStudents = "SELECT COUNT(*) AS 'No. of Students' FROM student";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(queryStudents);
            if (rs2.next()) lblTotalStudents.setText(rs2.getString("No. of Students"));

            // Load Issued Books
            String queryIssued = "SELECT COUNT(*) AS 'Issued Books' FROM issue WHERE returnBook = 'NO'";
            Statement st3 = con.createStatement();
            ResultSet rs3 = st3.executeQuery(queryIssued);
            if (rs3.next()) lblIssuedBooks.setText(rs3.getString("Issued Books"));

            // Load Defaulter List
            String queryDefaulters = "SELECT COUNT(*) AS 'Defaulter List' FROM issue WHERE returnBook = 'NO' AND dueDate < CURDATE()";
            Statement st4 = con.createStatement();
            ResultSet rs4 = st4.executeQuery(queryDefaulters);
            if (rs4.next()) lblDefaulters.setText(rs4.getString("Defaulter List"));

            // Load Student Details
            String queryStudentDetails = "SELECT student.name AS 'Student Name', student.strand AS 'Strand' " +
                    "FROM student " +
                    "INNER JOIN issue ON student.name = issue.name " +
                    "GROUP BY student.name, student.strand";

            Statement st5 = con.createStatement();
            ResultSet rs5 = st5.executeQuery(queryStudentDetails);
            tableStudents.setModel(DbUtils.resultSetToTableModel(rs5));

            // Load Book Details
            String queryBookDetails = "SELECT bookID AS 'Book ID', name AS 'Book Name', publisher AS 'Author', quantity AS 'Quantity' " +
                    "FROM book";

            Statement st6 = con.createStatement();
            ResultSet rs6 = st6.executeQuery(queryBookDetails);
            tableBooks.setModel(DbUtils.resultSetToTableModel(rs6));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
