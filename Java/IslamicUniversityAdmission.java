import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Student class (OOP Model)
class Student {
    private String id;
    private String name;
    private String department;

    public Student(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
}

// Main GUI and Application class
public class IslamicUniversityAdmission extends JFrame {
    private final ArrayList<Student> students = new ArrayList<>();

    private JTextField idField, nameField, deptField;
    private DefaultTableModel tableModel;
    private JTable studentTable;

    public IslamicUniversityAdmission() {
        setTitle("Islamic University Student Admission");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Admission Form"));

        inputPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        inputPanel.add(idField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Department:"));
        deptField = new JTextField();
        inputPanel.add(deptField);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        JButton addBtn = new JButton("Add Student");
        JButton editBtn = new JButton("Edit Student");
        JButton removeBtn = new JButton("Remove Student");
        buttonsPanel.add(addBtn);
        buttonsPanel.add(editBtn);
        buttonsPanel.add(removeBtn);

        // Table to display students
        String[] columns = {"Student ID", "Name", "Department"};
        tableModel = new DefaultTableModel(columns, 0);
        studentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        // Layout
        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Button actions
        addBtn.addActionListener(e -> addStudent());
        editBtn.addActionListener(e -> editStudent());
        removeBtn.addActionListener(e -> removeStudent());

        // Table row selection fills form fields
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow >= 0) {
                idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
                nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
                deptField.setText(tableModel.getValueAt(selectedRow, 2).toString());
                idField.setEditable(false);
            }
        });
    }

    private void addStudent() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String dept = deptField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || dept.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check duplicate ID
        for (Student s : students) {
            if (s.getId().equals(id)) {
                JOptionPane.showMessageDialog(this, "Student ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Student student = new Student(id, name, dept);
        students.add(student);
        tableModel.addRow(new Object[]{id, name, dept});
        clearFields();
    }

    private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a student to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = nameField.getText().trim();
        String dept = deptField.getText().trim();
        if (name.isEmpty() || dept.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Department cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = idField.getText();
        Student student = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (student != null) {
            student.setName(name);
            student.setDepartment(dept);

            tableModel.setValueAt(name, selectedRow, 1);
            tableModel.setValueAt(dept, selectedRow, 2);
            clearFields();
            idField.setEditable(true);
        }
    }

    private void removeStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a student to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String id = tableModel.getValueAt(selectedRow, 0).toString();
        students.removeIf(s -> s.getId().equals(id));
        tableModel.removeRow(selectedRow);
        clearFields();
        idField.setEditable(true);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        deptField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IslamicUniversityAdmission app = new IslamicUniversityAdmission();
            app.setVisible(true);
        });
    }
}
