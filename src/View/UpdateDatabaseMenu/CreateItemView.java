package View.UpdateDatabaseMenu;

import Controller.ItemController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreateItemView extends JDialog {
    private final JTextField nameField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField quantityField = new JTextField();
    private final JLabel statusLabel = new JLabel("");

    private final ItemController itemController;

    public CreateItemView(JFrame parent, ItemController itemController) {
        super(parent, "Create New Item", true);
        setSize(450, 450);
        setLocationRelativeTo(parent);

        this.itemController = itemController;

        // Title Bar
        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(180, 0, 0));
        titleBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        titleBar.setPreferredSize(new Dimension(420, 50));
        JLabel titleLabel = new JLabel("Create New Item");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBar.setLayout(new BorderLayout());
        titleBar.add(titleLabel, BorderLayout.CENTER);

        // Main Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        Font labelFont = new Font("SansSerif", Font.BOLD, 15);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 15);

        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setFont(labelFont);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT); // Doesnt work idk why
        formPanel.add(nameLabel);

        nameField.setFont(fieldFont);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        formPanel.add(nameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        priceLabel.setFont(labelFont);
        formPanel.add(priceLabel);

        priceField.setFont(fieldFont);
        priceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        priceField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        formPanel.add(priceField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        qtyLabel.setFont(labelFont);
        formPanel.add(qtyLabel);

        quantityField.setFont(fieldFont);
        quantityField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        quantityField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        formPanel.add(quantityField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        JButton createButton = new JButton("Create Item");
        JButton backButton = new JButton("Back");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        createButton.setBackground(new Color(180, 0, 0));
        createButton.setForeground(Color.WHITE);
        createButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        createButton.setFocusPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));

        backButton.setBackground(new Color(230, 230, 230));
        backButton.setForeground(new Color(120, 0, 0));
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));

        formPanel.add(createButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(backButton);

        statusLabel.setForeground(new Color(0, 120, 0));
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(statusLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(titleBar, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Action Listeners
        createButton.addActionListener(this::handleCreateItem);

        backButton.addActionListener(e -> {
            dispose();
        });
    }

    private void handleCreateItem(ActionEvent e) {
        String name = nameField.getText();
        String price = priceField.getText();
        String quantity = quantityField.getText();

        String result = itemController.createNewItem(name, price, quantity);
        statusLabel.setText(result);

        if (result.startsWith("Item created")) {
            clearFields();
        }
    }

    private void clearFields() {
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }
}
