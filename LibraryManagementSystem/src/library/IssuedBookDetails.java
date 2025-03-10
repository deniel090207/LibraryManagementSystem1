package library;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import Project.ConnectionProvider;

public class IssuedBookDetails extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableIssued;
    private JTextField searchField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                IssuedBookDetails frame = new IssuedBookDetails();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public IssuedBookDetails() {
        setUndecorated(true); // Remove default title bar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblIssuedBooks = new JLabel("Issued Books");
        lblIssuedBooks.setForeground(new Color(0, 0, 255));
        lblIssuedBooks.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblIssuedBooks.setBounds(330, 10, 120, 20);
        contentPane.add(lblIssuedBooks);

        // Table for Issued Books
        tableIssued = new JTable();
        tableIssued.setBackground(new Color(255, 255, 128));
        JScrollPane scrollPane = new JScrollPane(tableIssued);
        scrollPane.setBounds(20, 40, 750, 300);
        contentPane.add(scrollPane);

        // Search Field
        searchField = new JTextField();
        searchField.setBounds(20, 360, 200, 25);
        contentPane.add(searchField);

        // Search Button
        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSearch.setBounds(230, 360, 100, 25);
        btnSearch.addActionListener(e -> searchIssuedBooks());
        contentPane.add(btnSearch);

        // Refresh Button
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRefresh.setBounds(340, 360, 100, 25);
        btnRefresh.addActionListener(e -> {
            searchField.setText("");
            loadIssuedBooks(); // Reload all data
        });
        contentPane.add(btnRefresh);

        // Close Button
        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnClose.setBackground(Color.RED);
        btnClose.setForeground(Color.WHITE);
        btnClose.setBounds(670, 10, 100, 30);
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
        loadIssuedBooks();
    }

    private void loadIssuedBooks() {
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            String query = "SELECT issue.name AS 'Borrower', issue.bookID AS 'Book ID', book.name AS 'Book Name', issue.issueDate AS 'Issue Date', issue.dueDate AS 'Due Date', issue.returnBook AS 'Status' " +
                           "FROM issue " +
                           "INNER JOIN book ON book.bookID = issue.bookID " +
                           "WHERE issue.returnBook='NO'";
            ResultSet rs = st.executeQuery(query);
            tableIssued.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchIssuedBooks() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search term!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection con = ConnectionProvider.getCon();
            PreparedStatement pst = con.prepareStatement(
                "SELECT issue.name AS 'Borrower', issue.bookID AS 'Book ID', book.name AS 'Book Name', issue.issueDate AS 'Issue Date', issue.dueDate AS 'Due Date', issue.returnBook AS 'Status' " +
                "FROM issue " +
                "INNER JOIN book ON book.bookID = issue.bookID " +
                "WHERE issue.returnBook='NO' AND (issue.name LIKE ? OR book.name LIKE ? OR issue.bookID LIKE ?)"
            );
            pst.setString(1, "%" + searchText + "%");
            pst.setString(2, "%" + searchText + "%");
            pst.setString(3, "%" + searchText + "%");

            ResultSet rs = pst.executeQuery();
            tableIssued.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
