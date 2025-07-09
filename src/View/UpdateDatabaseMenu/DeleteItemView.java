package View.UpdateDatabaseMenu;

import Controller.ItemController;
import Model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class DeleteItemView extends JFrame {
    private final JLabel statusLabel = new JLabel("");
    private final ItemController itemController;
    private final JList<String> itemList = new JList<>();
    private List<Item> items;

    public DeleteItemView(ItemController itemController) {
        this.itemController = itemController;

        setTitle("Delete Item");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Select an item to delete:");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainPanel.add(title, BorderLayout.NORTH);

        refreshItemList();
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setPreferredSize(new Dimension(250, 300));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton deleteButton = new JButton("Delete Selected Item");
        JButton backButton = new JButton("Back");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(statusLabel);

        statusLabel.setForeground(Color.BLUE);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        deleteButton.addActionListener(this::handleDeleteItem);

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

    private void handleDeleteItem(ActionEvent e) {
        int index = itemList.getSelectedIndex();
        if (index == -1) {
            statusLabel.setText("Please select an item to delete.");
            return;
        }
        Item selectedItem = items.get(index);
        itemController.deleteItem(selectedItem.getItemID());
        statusLabel.setText("Item deleted: " + selectedItem.getItemName());
        refreshItemList();
    }
}
