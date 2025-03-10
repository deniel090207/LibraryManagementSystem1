package library;

import java.sql.*;
import Project.ConnectionProvider;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class NewBook extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField, textField_1, textField_2, textField_3;
    private JTable table;
    private DefaultTableModel model;
    private JButton addButton, editButton, deleteButton, clearButton, closeButton;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NewBook frame = new NewBook();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public NewBook() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 911, 621);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Book ID");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel.setBounds(10, 89, 102, 14);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Book Name");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_1.setBounds(10, 136, 102, 14);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Author Name");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_2.setBounds(10, 188, 102, 14);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Quantity");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_3.setBounds(10, 246, 102, 14);
        contentPane.add(lblNewLabel_3);
        
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSearch.setBounds(500, 80, 100, 25);
        contentPane.add(lblSearch);

        JTextField txtSearch = new JTextField();
        txtSearch.setBounds(560, 80, 140, 25);
        contentPane.add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(710, 80, 100, 25);
        btnSearch.addActionListener(e -> searchBook(txtSearch.getText()));
        contentPane.add(btnSearch);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.BOLD, 14));
        textField.setBounds(191, 88, 200, 20);
        contentPane.add(textField);

        textField_1 = new JTextField();
        textField_1.setFont(new Font("Tahoma", Font.BOLD, 14));
        textField_1.setBounds(191, 135, 200, 20);
        contentPane.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setFont(new Font("Tahoma", Font.BOLD, 14));
        textField_2.setBounds(191, 187, 200, 20);
        contentPane.add(textField_2);

        textField_3 = new JTextField();
        textField_3.setFont(new Font("Tahoma", Font.BOLD, 14));
        textField_3.setBounds(191, 245, 200, 20);
        contentPane.add(textField_3);

        addButton = new JButton("Add");
        addButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        addButton.setBounds(50, 300, 110, 40);
        contentPane.add(addButton);

        editButton = new JButton("Edit");
        editButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        editButton.setBounds(180, 300, 110, 40);
        contentPane.add(editButton);

        deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        deleteButton.setBounds(310, 300, 110, 40);
        contentPane.add(deleteButton);

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        clearButton.setBounds(440, 300, 110, 40);
        contentPane.add(clearButton);

        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        closeButton.setBounds(570, 300, 110, 40);
        try {
        	closeButton.setIcon(new ImageIcon(NewBook.class.getResource("/Icons/x.png")));
        } catch (NullPointerException e) {
            System.out.println("Error: x.png not found!");
        }
        contentPane.add(closeButton);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(50, 360, 750, 200);
        contentPane.add(scrollPane);

        table = new JTable();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Book ID", "Book Name", "Author", "Quantity"});
        table.setModel(model);
        scrollPane.setViewportView(table);

        loadBooks();

        addButton.addActionListener(e -> addBook());
        editButton.addActionListener(e -> editBook());
        deleteButton.addActionListener(e -> deleteBook());
        clearButton.addActionListener(e -> clearFields());

        closeButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to close?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                setVisible(false);
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                textField.setText(model.getValueAt(row, 0).toString());
                textField_1.setText(model.getValueAt(row, 1).toString());
                textField_2.setText(model.getValueAt(row, 2).toString());
                textField_3.setText(model.getValueAt(row, 3).toString());
            }
        });

        // ✅ BACKGROUND IMAGE FIX
        try {
            JLabel background = new JLabel(new ImageIcon(NewBook.class.getResource("/Icons/Resize image project.png")));
            background.setBounds(0, 0, 911, 621);
            contentPane.add(background);
        } catch (NullPointerException e) {
            System.out.println("Error: background.png not found!");
        }
    }

    private void loadBooks() {
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM book");
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("bookID"),
                        rs.getString("name"),
                        rs.getString("publisher"),
                        rs.getString("quantity")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addBook() {
    	try (Connection con = ConnectionProvider.getCon();
                PreparedStatement pst = con.prepareStatement("INSERT INTO book VALUES (?, ?, ?, ?)")) {

               pst.setString(1, textField.getText());
               pst.setString(2, textField_1.getText());
               pst.setString(3, textField_2.getText());
               pst.setString(4, textField_3.getText());
               pst.executeUpdate();
               JOptionPane.showMessageDialog(this, "Book Added Successfully!");
               loadBooks();
               clearFields();

           } catch (Exception e) {
               e.printStackTrace();
           }
    }

    private void editBook() {
    	try (Connection con = ConnectionProvider.getCon();
                PreparedStatement pst = con.prepareStatement("UPDATE book SET name=?, author=?, quantity=? WHERE bookID=?")) {

               pst.setString(1, textField_1.getText());
               pst.setString(2, textField_2.getText());
               pst.setString(3, textField_3.getText());
               pst.setString(4, textField.getText());
               pst.executeUpdate();
               JOptionPane.showMessageDialog(this, "Book Updated Successfully!");
               loadBooks();

           } catch (Exception e) {
               e.printStackTrace();
           }
    }

    private void deleteBook() {
    	try (Connection con = ConnectionProvider.getCon();
                PreparedStatement pst = con.prepareStatement("DELETE FROM book WHERE bookID=?")) {

               pst.setString(1, textField.getText());
               pst.executeUpdate();
               JOptionPane.showMessageDialog(this, "Book Deleted Successfully!");
               loadBooks();
               clearFields();

           } catch (Exception e) {
               e.printStackTrace();
           }
    }

    private void clearFields() {
        textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
        textField_3.setText("");
    }
    private void searchBook(String keyword) {
        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement pst = con.prepareStatement("SELECT * FROM book WHERE name LIKE ?")) {

            pst.setString(1, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("bookID"),
                        rs.getString("name"),
                        rs.getString("publisher"),
                        rs.getString("quantity")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
