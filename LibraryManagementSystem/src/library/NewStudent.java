package library;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;  // ❗ Kailangan para sa filtering

import Project.ConnectionProvider;


public class NewStudent extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtStudentName;
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> comboBoxStrand;
    private JButton btnAdd, btnEdit, btnDelete, btnClear, btnClose;
    private JLabel lblNewLabel;
    private JTextField txtStudentID;


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NewStudent frame = new NewStudent();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public NewStudent() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblStudentName = new JLabel("Student Name");
        lblStudentName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblStudentName.setBounds(20, 50, 120, 25);
        contentPane.add(lblStudentName);
        
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblSearch.setBounds(20, 130, 80, 25);
        contentPane.add(lblSearch);

        JTextField txtSearch = new JTextField();
        txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtSearch.setBounds(100, 130, 250, 25);
        contentPane.add(txtSearch);
        
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchStudent(txtSearch.getText());
            }
        });



        txtStudentName = new JTextField();
        txtStudentName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtStudentName.setBounds(150, 50, 200, 25);
        contentPane.add(txtStudentName);

        JLabel lblStrand = new JLabel("Strand");
        lblStrand.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblStrand.setBounds(20, 100, 120, 25);
        contentPane.add(lblStrand);
        
        txtStudentID = new JTextField();
        txtStudentID.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtStudentID.setBounds(150, 20, 200, 25);
        txtStudentID.setEditable(false); // Ginawang read-only
        txtStudentID.setVisible(false);
        contentPane.add(txtStudentID);

        comboBoxStrand = new JComboBox<>(new String[]{"HE", "HUMSS", "GAS", "ICT"});
        comboBoxStrand.setBounds(150, 100, 200, 25);
        contentPane.add(comboBoxStrand);

        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnAdd.setBounds(20, 160, 100, 30);
        contentPane.add(btnAdd);

        btnEdit = new JButton("Edit");
        btnEdit.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnEdit.setBounds(130, 160, 100, 30);
        contentPane.add(btnEdit);

        btnDelete = new JButton("Delete");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnDelete.setBounds(240, 160, 100, 30);
        contentPane.add(btnDelete);

        btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnClear.setBounds(350, 160, 100, 30);
        contentPane.add(btnClear);

     // Close Button with Confirmation
        btnClose = new JButton("Close");
        btnClose.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnClose.setBounds(460, 160, 100, 30);
        btnClose.setBackground(Color.RED);
        btnClose.setForeground(Color.WHITE);
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


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 210, 740, 250);
        contentPane.add(scrollPane);

        table = new JTable();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Student ID", "Name", "Strand"});
        table.setModel(model);
        scrollPane.setViewportView(table);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(NewStudent.class.getResource("/Icons/Resize image project.png")));
        lblNewLabel.setBounds(0, 0, 800, 500);
        contentPane.add(lblNewLabel);

        loadStudents();

        btnAdd.addActionListener(e -> addStudent());
        btnEdit.addActionListener(e -> editStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearFields());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtStudentID.setText(model.getValueAt(row, 0).toString());
                txtStudentName.setText(model.getValueAt(row, 1).toString());
                comboBoxStrand.setSelectedItem(model.getValueAt(row, 2).toString());
            }
        });
    }

    private void loadStudents() {
        try (Connection con = ConnectionProvider.getCon();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM student");
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{rs.getInt("studentID"), rs.getString("name"), rs.getString("strand")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addStudent() {
        String name = txtStudentName.getText();
        String strand = comboBoxStrand.getSelectedItem().toString();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student name.");
            return;
        }
        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement pst = con.prepareStatement("INSERT INTO student (name, strand) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, name);
            pst.setString(2, strand);
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                txtStudentID.setText(String.valueOf(rs.getInt(1)));
            }
            JOptionPane.showMessageDialog(this, "Student added successfully.");
            loadStudents();
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.");
            return;
        }

        String studentID = txtStudentID.getText();  // Kinuha ang Student ID
        String newName = txtStudentName.getText();
        String newStrand = comboBoxStrand.getSelectedItem().toString();

        if (newName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter student name.");
            return;
        }

        try (Connection con = ConnectionProvider.getCon();
             PreparedStatement pst = con.prepareStatement("UPDATE student SET name = ?, strand = ? WHERE studentID = ?")) {

            pst.setString(1, newName);
            pst.setString(2, newStrand);
            pst.setInt(3, Integer.parseInt(studentID)); // Convert Student ID to Integer
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student updated successfully.");
            loadStudents();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }

        String studentID = txtStudentID.getText();  // Kinuha ang Student ID

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete?", "Confirm", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection con = ConnectionProvider.getCon();
                 PreparedStatement pst = con.prepareStatement("DELETE FROM student WHERE studentID = ?")) {

                pst.setInt(1, Integer.parseInt(studentID));  // Convert Student ID to Integer
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Student deleted successfully.");
                loadStudents();
                clearFields();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }


    private void clearFields() {
        txtStudentID.setText(""); // Linisin ang Student ID field
        txtStudentName.setText("");
        comboBoxStrand.setSelectedIndex(0);
    }
    private void searchStudent(String keyword) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + keyword, 1)); // Search sa "Name" column (index 1)
    }
}
