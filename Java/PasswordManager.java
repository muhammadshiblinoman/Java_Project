import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class PasswordManager extends JFrame {
    private HashMap<String, Credential> passwordMap = new HashMap<>();

    private JTextField accountField, usernameField;
    private JPasswordField passwordField;
    private DefaultTableModel tableModel;
    private JTable table;

    // Credential class (OOP model)
    private static class Credential {
        String username;
        String password;

        Credential(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    public PasswordManager() {
        setTitle("Simple Password Manager");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Credential"));

        inputPanel.add(new JLabel("Account:"));
        accountField = new JTextField();
        inputPanel.add(accountField);

        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);

        inputPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        inputPanel.add(passwordField);

        JButton addButton = new JButton("Add / Update");
        inputPanel.add(addButton);

        JButton retrieveButton = new JButton("Retrieve Password");
        inputPanel.add(retrieveButton);

        // Table for displaying stored credentials
        String[] columns = {"Account", "Username"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Layout
        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add button action
        addButton.addActionListener(e -> addOrUpdateCredential());

        // Retrieve password action
        retrieveButton.addActionListener(e -> retrievePassword());

        // Table selection fills input fields (except password)
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String account = (String) tableModel.getValueAt(row, 0);
                String username = (String) tableModel.getValueAt(row, 1);
                accountField.setText(account);
                usernameField.setText(username);
                passwordField.setText("");
            }
        });
    }

    private void addOrUpdateCredential() {
        String account = accountField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (account.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fill all fields to add/update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exists = passwordMap.containsKey(account);
        passwordMap.put(account, new Credential(username, password));

        if (!exists) {
            tableModel.addRow(new Object[]{account, username});
        } else {
            // Update existing table entry
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(account)) {
                    tableModel.setValueAt(username, i, 1);
                    break;
                }
            }
        }

        JOptionPane.showMessageDialog(this, "Credential saved successfully.");
        clearFields();
    }

    private void retrievePassword() {
        String account = accountField.getText().trim();
        if (account.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter an account to retrieve password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Credential cred = passwordMap.get(account);
        if (cred != null) {
            JOptionPane.showMessageDialog(this,
                    "Password for account '" + account + "':\n" + cred.password,
                    "Password Retrieved",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No credentials found for this account.", "Not Found", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        accountField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PasswordManager pm = new PasswordManager();
            pm.setVisible(true);
        });
    }
}
