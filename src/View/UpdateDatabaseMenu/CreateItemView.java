package View.UpdateDatabaseMenu;

import Controller.ItemController;
import Model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class CreateItemView extends JFrame {
    private final JTextField nameField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField quantityField = new JTextField();
    private final JLabel statusLabel = new JLabel("");

    private final ItemController itemController;

    public CreateItemView(ItemController itemController) {
        this.itemController = itemController;

        setTitle("Create New Item");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 300);
        setLocationRelativeTo(null); // center

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Item Name:"));
        panel.add(nameField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton createButton = new JButton("Create Item");
        JButton backButton = new JButton("Back");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(createButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(backButton);

        statusLabel.setForeground(Color.BLUE);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(statusLabel);

        add(panel);

        // Action Listeners
        createButton.addActionListener(this::handleCreateItem);

        backButton.addActionListener(e -> {
            UpdateDatabaseView menu = new UpdateDatabaseView(itemController);
            menu.setVisible(true);
            dispose();
        });
    }

    private void handleCreateItem(ActionEvent e) {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            int itemID = new Random().nextInt(100000);
            Item item = new Item();
            item.setItemID(itemID);
            item.setItemName(name);
            item.setPrice(price);
            item.setQuantity(quantity);

            itemController.addItem(item);
            statusLabel.setText("Item created with ID: " + itemID);
            clearFields();
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid price or quantity input.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }
}
