package ui;

import model.AVLTree;
import javax.swing.*;
import java.awt.*;

public class UserManagerGUI extends JFrame {

//    private final AVLTree<Integer, String> tree;

    private final JTextField idField = new JTextField(10);
    private final JTextField nameField = new JTextField(10);
    private final JTextArea outputArea = new JTextArea(10, 30);

    public UserManagerGUI() {
        super("User Manager");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // top panel
        JPanel formPanel = new JPanel();
        formPanel.add(new JLabel("User ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        add(formPanel, BorderLayout.NORTH);

        // output area
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // center window
        setVisible(true);
    }
}
