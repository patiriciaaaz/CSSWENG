package View.UpdateDatabaseMenu;

import Controller.ItemController;
import Model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UpdateItemView extends JDialog {
    private final JTextField nameField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField quantityField = new JTextField();
    private final JLabel statusLabel = new JLabel("");

    private final ItemController itemController;
    private final Item item;

    public UpdateItemView(JFrame parent, ItemController itemController, Item item) {
        super(parent, "Update Item", true);
        this.itemController = itemController;
        this.item = item;

        setSize(550, 450);
        setLocationRelativeTo(parent);

        // Title Bar
        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(180, 0, 0));
        titleBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        titleBar.setPreferredSize(new Dimension(420, 50));
        JLabel titleLabel = new JLabel("Update Item");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleBar.setLayout(new BorderLayout());
        titleBar.add(titleLabel, BorderLayout.CENTER);

        // Main Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 15);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 15);

        JLabel nameLabel = new JLabel("Item Name:");
        nameLabel.setFont(labelFont);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(nameLabel);

        nameField.setFont(fieldFont);
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        nameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        nameField.setText(item.getItemName());
        formPanel.add(nameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(labelFont);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(priceLabel);

        priceField.setFont(fieldFont);
        priceField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        priceField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        priceField.setText(String.valueOf(item.getPrice()));
        formPanel.add(priceField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel qtyLabel = new JLabel("Quantity:");
        qtyLabel.setFont(labelFont);
        qtyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(qtyLabel);

        quantityField.setFont(fieldFont);
        quantityField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        quantityField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        quantityField.setText(String.valueOf(item.getQuantity()));
        formPanel.add(quantityField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        JButton updateButton = new JButton("Update Item");
        JButton backButton = new JButton("Back");
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        updateButton.setBackground(new Color(180, 0, 0));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        updateButton.setFocusPainted(false);
        updateButton.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));

        backButton.setBackground(new Color(230, 230, 230));
        backButton.setForeground(new Color(120, 0, 0));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));

        formPanel.add(updateButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(backButton);

        statusLabel.setForeground(new Color(0, 120, 0));
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(statusLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(titleBar, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        // Action Listeners
        updateButton.addActionListener(this::handleUpdateItem);
        backButton.addActionListener(e -> dispose());
    }

    private void handleUpdateItem(ActionEvent e) {
        String name = nameField.getText();
        String price = priceField.getText();
        String quantity = quantityField.getText();

        String result = itemController.updateExistingItem(item, name, price, quantity);
        statusLabel.setText(result);
    }

    private void clearFields() {
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }
}
