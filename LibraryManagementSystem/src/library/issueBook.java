package library;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import Project.ConnectionProvider;

public class issueBook extends JFrame {
    private JPanel contentPane;
    private JTextField txtBookID, txtStudentName;
    private JLabel lblBookName, lblAuthor, lblQuantity, lblStudentName, lblStrand;
    private JDateChooser issueDateChooser, dueDateChooser;
    private JButton btnFind, btnIssueBook, btnClose;
    private JTable tableBooks;
    private DefaultTableModel bookModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                issueBook frame = new issueBook();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public issueBook() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1250, 675);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // **Panel - Book Details**
        JPanel panelBook = new JPanel();
        panelBook.setBackground(new Color(128, 128, 128));
        panelBook.setBounds(0, 0, 300, 600);
        panelBook.setLayout(null);
        contentPane.add(panelBook);

        JLabel lblBookDetails = new JLabel("Book Details");
        lblBookDetails.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblBookDetails.setForeground(Color.WHITE);
        lblBookDetails.setBounds(90, 20, 200, 30);
        panelBook.add(lblBookDetails);

        lblBookName = new JLabel("Book Name: ");
        lblBookName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblBookName.setBounds(20, 80, 260, 25);
        lblBookName.setForeground(Color.WHITE);
        panelBook.add(lblBookName);

        lblAuthor = new JLabel("Author: ");
        lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblAuthor.setBounds(20, 120, 260, 25);
        lblAuthor.setForeground(Color.WHITE);
        panelBook.add(lblAuthor);

        lblQuantity = new JLabel("Quantity: ");
        lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblQuantity.setBounds(20, 160, 260, 25);
        lblQuantity.setForeground(Color.WHITE);
        panelBook.add(lblQuantity);

        // **Panel - Student Details**
        JPanel panelStudent = new JPanel();
        panelStudent.setBackground(new Color(192, 192, 192));
        panelStudent.setBounds(300, 0, 300, 600);
        panelStudent.setLayout(null);
        contentPane.add(panelStudent);

        JLabel lblStudentDetails = new JLabel("Student Details");
        lblStudentDetails.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblStudentDetails.setForeground(Color.BLACK);
        lblStudentDetails.setBounds(90, 20, 200, 30);
        panelStudent.add(lblStudentDetails);

        lblStudentName = new JLabel("Student Name:");
        lblStudentName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblStudentName.setBounds(20, 80, 260, 25);
        lblStudentName.setForeground(Color.BLACK);
        panelStudent.add(lblStudentName);

        lblStrand = new JLabel("Strand:");
        lblStrand.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblStrand.setBounds(20, 120, 260, 25);
        lblStrand.setForeground(Color.BLACK);
        panelStudent.add(lblStrand);

        // **Panel - Input Fields & Buttons**
        JPanel panelWhite = new JPanel();
        panelWhite.setBackground(Color.WHITE);
        panelWhite.setBounds(600, 0, 300, 600);
        panelWhite.setLayout(null);
        contentPane.add(panelWhite);

        JLabel lblTitle = new JLabel("Issue Book");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setForeground(Color.BLACK);
        lblTitle.setBounds(90, 20, 200, 30);
        panelWhite.add(lblTitle);

        JLabel lblBookID = new JLabel("Book ID:");
        lblBookID.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblBookID.setBounds(20, 80, 100, 25);
        panelWhite.add(lblBookID);

        txtBookID = new JTextField();
        txtBookID.setBounds(120, 80, 140, 25);
        panelWhite.add(txtBookID);

        JLabel lblStudentInput = new JLabel("Student Name:");
        lblStudentInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblStudentInput.setBounds(20, 120, 100, 25);
        panelWhite.add(lblStudentInput);

        txtStudentName = new JTextField();
        txtStudentName.setBounds(120, 120, 140, 25);
        panelWhite.add(txtStudentName);

        JLabel lblIssueDate = new JLabel("Issue Date:");
        lblIssueDate.setBounds(20, 160, 100, 25);
        panelWhite.add(lblIssueDate);

        issueDateChooser = new JDateChooser();
        issueDateChooser.setBounds(120, 160, 140, 25);
        panelWhite.add(issueDateChooser);

        JLabel lblDueDate = new JLabel("Due Date:");
        lblDueDate.setBounds(20, 200, 100, 25);
        panelWhite.add(lblDueDate);

        dueDateChooser = new JDateChooser();
        dueDateChooser.setBounds(120, 200, 140, 25);
        panelWhite.add(dueDateChooser);

        btnFind = new JButton("Find");
        btnFind.setBounds(120, 240, 140, 30);
        btnFind.addActionListener(e -> findBookDetails());
        panelWhite.add(btnFind);

        btnIssueBook = new JButton("Issue Book");
        btnIssueBook.setBounds(120, 280, 140, 30);
        btnIssueBook.addActionListener(e -> issueBook());
        panelWhite.add(btnIssueBook);

        btnClose = new JButton("Close");
        btnClose.setBounds(120, 320, 140, 30);
        btnClose.setBackground(Color.RED);
        btnClose.setForeground(Color.WHITE);

        // Add confirmation dialog
        btnClose.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to close?", 
                "Close Confirmation", 
                JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                dispose();
            }
        });

        panelWhite.add(btnClose);


        // **Table for Available Books**
        String[] columns = {"Book ID", "Book Name", "Author", "Quantity"};
        bookModel = new DefaultTableModel(columns, 0);
        tableBooks = new JTable(bookModel);
        JScrollPane scrollPane = new JScrollPane(tableBooks);
        scrollPane.setBounds(920, 20, 300, 500);
        contentPane.add(scrollPane);

        loadAvailableBooks();
    }

    private void loadAvailableBooks() {
        try (Connection con = ConnectionProvider.getCon();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT bookID, name, publisher, quantity FROM book WHERE quantity > 0")) {

            bookModel.setRowCount(0);
            while (rs.next()) {
                bookModel.addRow(new Object[]{
                        rs.getString("bookID"),
                        rs.getString("name"),
                        rs.getString("publisher"),
                        rs.getInt("quantity")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void findBookDetails() {
        try {
            Connection con = ConnectionProvider.getCon();
            String sql = "SELECT name, publisher, quantity FROM book WHERE bookID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, txtBookID.getText());
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblBookName.setText("Book Name: " + rs.getString("name"));
                lblAuthor.setText("Author: " + rs.getString("publisher"));
                lblQuantity.setText("Quantity: " + rs.getInt("quantity"));
            } else {
                JOptionPane.showMessageDialog(this, "Book not found!");
            }

            String sqlStudent = "SELECT name, strand FROM student WHERE name = ?";
            PreparedStatement pstStudent = con.prepareStatement(sqlStudent);
            pstStudent.setString(1, txtStudentName.getText());
            ResultSet rsStudent = pstStudent.executeQuery();

            if (rsStudent.next()) {
                lblStudentName.setText("Student Name: " + rsStudent.getString("name"));
                lblStrand.setText("Strand: " + rsStudent.getString("strand"));
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void issueBook() {
        try {
            Connection con = ConnectionProvider.getCon();

            // Check if the book is available
            String checkQuantity = "SELECT quantity FROM book WHERE bookID = ?";
            PreparedStatement pstCheck = con.prepareStatement(checkQuantity);
            pstCheck.setString(1, txtBookID.getText());
            ResultSet rs = pstCheck.executeQuery();

            if (rs.next()) {
                int quantity = rs.getInt("quantity");

                if (quantity > 0) {
                    // Insert the issue record
                    String sql = "INSERT INTO issue (bookID, name, issueDate, dueDate, returnBook) VALUES (?, ?, ?, ?, 'No')";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, txtBookID.getText());
                    pst.setString(2, txtStudentName.getText());
                    pst.setDate(3, new java.sql.Date(issueDateChooser.getDate().getTime()));
                    pst.setDate(4, new java.sql.Date(dueDateChooser.getDate().getTime()));
                    pst.executeUpdate();

                    // Deduct book quantity
                    String updateQty = "UPDATE book SET quantity = quantity - 1 WHERE bookID = ?";
                    PreparedStatement pstUpdate = con.prepareStatement(updateQty);
                    pstUpdate.setString(1, txtBookID.getText());
                    pstUpdate.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Book issued successfully!");
                    
                    // Refresh the available books table
                    loadAvailableBooks();
                } else {
                    JOptionPane.showMessageDialog(this, "Book is out of stock!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
