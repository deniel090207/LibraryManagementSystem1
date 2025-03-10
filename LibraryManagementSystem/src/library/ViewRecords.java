package library;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import Project.ConnectionProvider;

public class ViewRecords extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableRecords;
    private JTextField searchField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ViewRecords frame = new ViewRecords();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ViewRecords() {
    	setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 500);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("All Records");
        lblTitle.setForeground(new Color(0, 0, 255));
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTitle.setBounds(380, 10, 200, 30);
        contentPane.add(lblTitle);

        // Search Field
        searchField = new JTextField();
        searchField.setBounds(20, 50, 200, 25);
        contentPane.add(searchField);

        // Search Button
        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSearch.setBounds(230, 50, 100, 25);
        btnSearch.addActionListener(e -> searchRecords());
        contentPane.add(btnSearch);

        // Refresh Button
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRefresh.setBounds(340, 50, 100, 25);
        btnRefresh.addActionListener(e -> {
            searchField.setText("");
            loadRecords();
        });
        contentPane.add(btnRefresh);

        // Table for Records
        tableRecords = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableRecords);
        scrollPane.setBounds(20, 90, 850, 350);
        contentPane.add(scrollPane);

        // Close Button
     // Close Button with Confirmation
        JButton btnClose = new JButton("Close");
        btnClose.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnClose.setBackground(Color.RED);
        btnClose.setForeground(Color.WHITE);
        btnClose.setBounds(770, 10, 100, 30);
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
        loadRecords();
    }

    private void loadRecords() {
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            String query = "SELECT issue.bookID AS 'Book ID', book.name AS 'Book Name', issue.name AS 'Student Name', " +
                           "issue.issueDate AS 'Issue Date', issue.dueDate AS 'Due Date', issue.returnBook AS 'Status' " +
                           "FROM issue " +
                           "INNER JOIN book ON issue.bookID = book.bookID";
            ResultSet rs = st.executeQuery(query);
            tableRecords.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Connection error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchRecords() {
        String searchText = searchField.getText().trim();
        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search term!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection con = ConnectionProvider.getCon();
            PreparedStatement pst = con.prepareStatement(
                "SELECT issue.bookID AS 'Book ID', book.name AS 'Book Name', issue.name AS 'Student Name', " +
                "issue.issueDate AS 'Issue Date', issue.dueDate AS 'Due Date', issue.returnBook AS 'Status' " +
                "FROM issue " +
                "INNER JOIN book ON issue.bookID = book.bookID " +
                "WHERE issue.name LIKE ? OR book.name LIKE ? OR issue.issueDate LIKE ?");
            pst.setString(1, "%" + searchText + "%");
            pst.setString(2, "%" + searchText + "%");
            pst.setString(3, "%" + searchText + "%");

            ResultSet rs = pst.executeQuery();
            tableRecords.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Search error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
