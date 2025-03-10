package library;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import Project.ConnectionProvider;

public class returnBook extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtBookID, txtStudentName;
    private JButton btnFind, btnReturnBook, btnClose;
    private JLabel lblIssueDate, lblBookID, lblBookName, lblStudentName, lblDueDate;
    private JTable tableIssued;
    private DefaultTableModel modelIssued;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                returnBook frame = new returnBook();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public returnBook() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 500);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Left Panel - Book Details
        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(new Color(128, 128, 128));
        panelLeft.setBounds(0, 0, 400, 500);
        panelLeft.setLayout(null);
        contentPane.add(panelLeft);

        JLabel lblDetails = new JLabel("Book Details");
        lblDetails.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblDetails.setForeground(Color.WHITE);
        lblDetails.setBounds(130, 20, 200, 30);
        panelLeft.add(lblDetails);

        lblIssueDate = new JLabel("Issue Date: ");
        lblIssueDate.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblIssueDate.setBounds(30, 80, 300, 25);
        lblIssueDate.setForeground(Color.WHITE);
        panelLeft.add(lblIssueDate);

        lblBookID = new JLabel("Book ID: ");
        lblBookID.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBookID.setBounds(30, 120, 300, 25);
        lblBookID.setForeground(Color.WHITE);
        panelLeft.add(lblBookID);

        lblBookName = new JLabel("Book Name: ");
        lblBookName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblBookName.setBounds(30, 160, 300, 25);
        lblBookName.setForeground(Color.WHITE);
        panelLeft.add(lblBookName);

        lblStudentName = new JLabel("Student Name: ");
        lblStudentName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblStudentName.setBounds(30, 200, 300, 25);
        lblStudentName.setForeground(Color.WHITE);
        panelLeft.add(lblStudentName);

        lblDueDate = new JLabel("Due Date: ");
        lblDueDate.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblDueDate.setBounds(30, 240, 300, 25);
        lblDueDate.setForeground(Color.WHITE);
        panelLeft.add(lblDueDate);

        // Right Panel - Input Fields & Buttons
        JLabel lblTitle = new JLabel("Return Book");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBounds(600, 20, 200, 30);
        contentPane.add(lblTitle);

        JLabel lblBookIDLabel = new JLabel("Book ID:");
        lblBookIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblBookIDLabel.setBounds(470, 78, 100, 25);
        contentPane.add(lblBookIDLabel);

        txtBookID = new JTextField();
        txtBookID.setBounds(600, 80, 200, 25);
        contentPane.add(txtBookID);

        JLabel lblStudentNameLabel = new JLabel("Student Name:");
        lblStudentNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblStudentNameLabel.setBounds(470, 118, 120, 25);
        contentPane.add(lblStudentNameLabel);

        txtStudentName = new JTextField();
        txtStudentName.setBounds(600, 120, 200, 25);
        contentPane.add(txtStudentName);

        btnFind = new JButton("FIND");
        btnFind.setBounds(600, 160, 200, 35);
        btnFind.setBackground(new Color(128, 128, 128));
        btnFind.setForeground(Color.WHITE);
        contentPane.add(btnFind);

        btnReturnBook = new JButton("RETURN BOOK");
        btnReturnBook.setBounds(600, 220, 200, 35);
        btnReturnBook.setBackground(new Color(128, 128, 128));
        btnReturnBook.setForeground(Color.WHITE);
        contentPane.add(btnReturnBook);

        btnClose = new JButton("Close");
        btnClose.setBounds(600, 280, 200, 30);
        btnClose.setBackground(Color.RED);
        btnClose.setForeground(Color.WHITE);
        btnClose.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to close?", 
                "Close Confirmation", 
                JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
        contentPane.add(btnClose);

        // Table for Issued Books
        String[] columns = {"Book ID", "Book Name", "Student Name", "Issue Date", "Due Date"};
        modelIssued = new DefaultTableModel(columns, 0);
        tableIssued = new JTable(modelIssued);
        JScrollPane scrollPane = new JScrollPane(tableIssued);
        scrollPane.setBounds(820, 20, 360, 450);
        contentPane.add(scrollPane);

        loadIssuedBooks();

        btnFind.addActionListener(e -> findBookDetails());
        btnReturnBook.addActionListener(e -> returnBook());
    }

    private void loadIssuedBooks() {
    	try (Connection con = ConnectionProvider.getCon();
    	         Statement st = con.createStatement();
    	         ResultSet rs = st.executeQuery("SELECT issue.bookID, book.name AS book_name, issue.name, issue.issueDate, issue.dueDate " +
    	                 "FROM issue JOIN book ON issue.bookID = book.bookID WHERE issue.returnBook = 'NO'")) {

    	        modelIssued.setRowCount(0);
    	        while (rs.next()) {
    	            modelIssued.addRow(new Object[]{
    	                    rs.getString("bookID"),
    	                    rs.getString("book_name"), // Book Name
    	                    rs.getString("name"), // Student Name
    	                    rs.getString("issueDate"),
    	                    rs.getString("dueDate")
    	            });
    	        }
    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }
    	}

    private void findBookDetails() {
        try {
            Connection con = ConnectionProvider.getCon();
            String sql = "SELECT issue.bookID, book.name AS book_name, issue.name, issue.issueDate, issue.dueDate " +
                    "FROM issue JOIN book ON issue.bookID = book.bookID " +
                    "WHERE issue.bookID = ? AND issue.name = ? AND issue.returnBook = 'NO'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, txtBookID.getText());
            pst.setString(2, txtStudentName.getText());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblIssueDate.setText("Issue Date: " + rs.getString("issueDate"));
                lblBookID.setText("Book ID: " + rs.getString("bookID"));
                lblBookName.setText("Book Name: " + rs.getString("book_name"));
                lblStudentName.setText("Student Name: " + rs.getString("name"));
                lblDueDate.setText("Due Date: " + rs.getString("dueDate"));
            } else {
                JOptionPane.showMessageDialog(this, "No records found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returnBook() {
    	try {
            Connection con = ConnectionProvider.getCon();
            
            // Update issue table to mark book as returned
            String sql = "UPDATE issue SET returnBook = 'Yes' WHERE bookID = ? AND name = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, txtBookID.getText());
            pst.setString(2, txtStudentName.getText());
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                // Dagdagan ang quantity sa book table
                String updateQty = "UPDATE book SET quantity = quantity + 1 WHERE bookID = ?";
                PreparedStatement pstUpdate = con.prepareStatement(updateQty);
                pstUpdate.setString(1, txtBookID.getText());
                pstUpdate.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book Returned Successfully!");
                loadIssuedBooks(); // Refresh issued books table
            } else {
                JOptionPane.showMessageDialog(this, "Error! No matching record found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
