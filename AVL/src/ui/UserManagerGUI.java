package ui;

import model.AVLTree;
import javax.swing.*;
import java.awt.*;

public class UserManagerGUI extends JFrame {

    private final AVLTree<Integer, String> tree = new AVLTree<>();

    private final JTextField idField = new JTextField(10);
    private final JTextField nameField = new JTextField(10);
    private final JTextArea outputArea = new JTextArea(10, 30);

    public UserManagerGUI() {
        super("User Manager");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- Top Panel ----------
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("User ID:"), gbc);

        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        // ---------- Buttons ----------
        JButton insertButton = new JButton("Insert");
        JButton removeButton = new JButton("Remove");
        JButton clearButton = new JButton("Clear All");
        JButton showButton = new JButton("Show Users");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(insertButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(showButton);
        buttonPanel.add(clearButton);

        formPanel.add(buttonPanel, gbc);
        add(formPanel, BorderLayout.NORTH);

        // ---------- Output Area ----------
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // ---------- Button Actions ----------
        insertButton.addActionListener(e -> insertUser());
        removeButton.addActionListener(e -> removeUser());
        clearButton.addActionListener(e -> clearUsers());
        showButton.addActionListener(e -> showUsers());

        setMinimumSize(new Dimension(500, 400));
        pack();
        setLocationRelativeTo(null); // center window
        setVisible(true);
    }

    private void insertUser() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                showMessage("Name cannot be empty.");
                return;
            }
            tree.insert(id, name);
            showMessage("Inserted: " + name);
            idField.setText("");
            nameField.setText("");
            showUsers();
        } catch (NumberFormatException ex) {
            showMessage("Invalid ID. Must be an integer.");
        }
    }

    private void removeUser() {
        try {
            int id = Integer.parseInt(idField.getText());
            String user = tree.get(id);
            if (user == null) {
                showMessage("User not found.");
                return;
            }
            tree.remove(id);
            showMessage("Removed: " + user);
            idField.setText("");
            nameField.setText("");
            showUsers();
        } catch (NumberFormatException ex) {
            showMessage("Invalid ID. Must be an integer.");
        }
    }

    private void clearUsers() {
        if (tree.isEmpty()) {
            showMessage("No users to clear.");
            return;
        }
        tree.clear();
        showMessage("All users cleared.");
        outputArea.setText("");
    }

    private void showUsers() {
        if (tree.isEmpty()) {
            outputArea.setText("No users.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        tree.inOrder((key, value) -> sb.append("ID ").append(key).append(": ").append(value).append("\n"));
        outputArea.setText(sb.toString());
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(UserManagerGUI::new);
    }
}
