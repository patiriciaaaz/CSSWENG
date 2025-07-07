package View.UpdateDatabaseMenu;

import Controller.ItemController;
import Model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class UpdateItemView extends JFrame {
    private final JTextField nameField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField quantityField = new JTextField();
    private final JLabel statusLabel = new JLabel("");
    private final JList<String> itemList = new JList<>();
    private List<Item> items;

    private final ItemController itemController;

    public UpdateItemView(ItemController itemController) {
        this.itemController = itemController;

        setTitle("Update Existing Item");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // LEFT PANEL: Item list
        JPanel leftPanel = new JPanel(new BorderLayout());
        JLabel listTitle = new JLabel("Select Item:");
        listTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        leftPanel.add(listTitle, BorderLayout.NORTH);
        refreshItemList();
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(250, 300));
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // RIGHT PANEL: Update form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        formPanel.add(new JLabel("New Name (type KEEP to keep):"));
        formPanel.add(nameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        formPanel.add(new JLabel("New Price (type KEEP to keep):"));
        formPanel.add(priceField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        formPanel.add(new JLabel("New Quantity (type KEEP to keep):"));
        formPanel.add(quantityField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton updateButton = new JButton("Update Item");
        JButton backButton = new JButton("Back");
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(updateButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(backButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(statusLabel);

        statusLabel.setForeground(Color.BLUE);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);

        updateButton.addActionListener(this::handleUpdateItem);

        backButton.addActionListener(e -> {
            UpdateDatabaseView menu = new UpdateDatabaseView(itemController);
            menu.setVisible(true);
            dispose();
        });
    }

    private void refreshItemList() {
        items = itemController.getAllItems();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Item item : items) {
            listModel.addElement("[" + item.getItemID() + "] " + item.getItemName() + " - ₱" + item.getPrice() + " - "
                    + item.getQuantity() + " pcs");
        }
        itemList.setModel(listModel);
    }

    private void handleUpdateItem(ActionEvent e) {
        int index = itemList.getSelectedIndex();
        if (index == -1) {
            statusLabel.setText("Please select an item to update.");
            return;
        }
        Item item = items.get(index);

        try {
            String name = nameField.getText().trim();
            if (!name.equalsIgnoreCase("KEEP") && !name.isBlank()) {
                item.setItemName(name);
            }

            String priceInput = priceField.getText().trim();
            if (!priceInput.equalsIgnoreCase("KEEP")) {
                double price = Double.parseDouble(priceInput);
                item.setPrice(price);
            }

            String qtyInput = quantityField.getText().trim();
            if (!qtyInput.equalsIgnoreCase("KEEP")) {
                int qty = Integer.parseInt(qtyInput);
                item.setQuantity(qty);
            }

            itemController.updateItem(item);
            statusLabel.setText("Item updated successfully.");
            clearFields();
            refreshItemList();
        } catch (NumberFormatException ex) {
            statusLabel.setText("Invalid input format.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }
}
