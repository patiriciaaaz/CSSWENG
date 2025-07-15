package View.TransactionsMenu;

import Controller.ItemController;
import Controller.SaleController;
import Controller.TransactionController;
import Model.Item;
import Model.Sale;
import Model.Transaction;

import javax.swing.*;
import java.awt.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewTransactionView extends JFrame {
    private JComboBox<String> transactionTypeBox;
    private JTextField memberIDField;
    private JComboBox<String> itemDropdown;
    private JTextField quantityField;
    private JButton addToCartButton;
    private JButton confirmButton;
    private JButton backButton;

    private JTextArea cartPreview;
    private JLabel totalLabel;

    private List<Sale> cart;
    private double totalAmount = 0.0;

    public NewTransactionView(TransactionController transactionController, SaleController saleController,
            ItemController itemController, JFrame parentFrame) {
        setTitle("SJ Fitness Gym POS - New Transaction");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cart = new ArrayList<>();

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(180, 0, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        JLabel titleLabel = new JLabel("<html><center>SJ Fitness<br>Gym</center></html>", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        sidebar.add(titleLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));

        backButton = new JButton("Back");
        styleSidebarButton(backButton);
        sidebar.add(backButton);

        add(sidebar, BorderLayout.WEST);

        // Center panel for transaction form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        memberIDField = new JTextField();
        memberIDField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        formPanel.add(new JLabel("Member ID (optional):"));
        formPanel.add(memberIDField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        transactionTypeBox = new JComboBox<>(new String[] {
                "membership_purchase", "membership_renewal", "consumables", "merchandise"
        });
        transactionTypeBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        formPanel.add(new JLabel("Transaction Type:"));
        formPanel.add(transactionTypeBox);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Item and quantity
        List<Item> items = itemController.getAllItems();
        itemDropdown = new JComboBox<>();
        for (Item item : items) {
            itemDropdown.addItem(item.getItemID() + " - " + item.getItemName());
        }
        itemDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        quantityField = new JTextField();
        quantityField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        formPanel.add(new JLabel("Select Item:"));
        formPanel.add(itemDropdown);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        addToCartButton = new JButton("Add to Cart");
        addToCartButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(addToCartButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        cartPreview = new JTextArea(10, 40);
        cartPreview.setEditable(false);
        cartPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        formPanel.add(new JLabel("Cart Preview:"));
        formPanel.add(new JScrollPane(cartPreview));
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        totalLabel = new JLabel("Total: ₱0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formPanel.add(totalLabel);

        confirmButton = new JButton("Confirm Transaction");
        confirmButton.setBackground(new Color(180, 0, 0));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        confirmButton.setFocusPainted(false);
        confirmButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(confirmButton);

        add(formPanel, BorderLayout.CENTER);

        // Logic
        addToCartButton.addActionListener(e -> {
            int selectedIndex = itemDropdown.getSelectedIndex();
            if (selectedIndex < 0 || selectedIndex >= items.size())
                return;
            Item item = items.get(selectedIndex);

            int qty;
            try {
                qty = Integer.parseInt(quantityField.getText());
                if (qty <= 0)
                    throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Sale sale = new Sale();
            sale.setSalesID(new Random().nextInt(100000));
            sale.setItemID(item.getItemID());
            sale.setQuantity(qty);
            cart.add(sale);

            totalAmount += item.getPrice() * qty;
            cartPreview.append(item.getItemName() + " x" + qty + "\n");
            totalLabel.setText("Total: ₱" + String.format("%.2f", totalAmount));
            quantityField.setText("");
        });

        confirmButton.addActionListener(e -> {
            int transactionID = new Random().nextInt(100000);
            Integer memberID = null;
            try {
                if (!memberIDField.getText().isBlank()) {
                    memberID = Integer.parseInt(memberIDField.getText().trim());
                }
            } catch (NumberFormatException ignored) {
            }

            Transaction transaction = new Transaction();
            transaction.setTransactionID(transactionID);
            transaction.setMemberID(memberID);
            transaction.setTransactionType((String) transactionTypeBox.getSelectedItem());
            transaction.setTransactionDate(new Date(System.currentTimeMillis()));
            transaction.setAmount(totalAmount);
            transactionController.addTransaction(transaction, cart, itemController);

            for (Sale s : cart) {
                s.setTransactionID(transactionID);
                saleController.addSale(s);
            }

            JOptionPane.showMessageDialog(this, "Transaction recorded.");
            new TransactionsView(transactionController, saleController, itemController).setVisible(true);
            dispose();
        });

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
    }

    private void styleSidebarButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 40));
        button.setBackground(Color.RED);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
    }
}
