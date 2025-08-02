package View.TransactionsMenu;

import Controller.ItemController;
import Controller.SaleController;
import Controller.TransactionController;
import Model.Item;
import Model.Sale;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewTransactionView extends JDialog {
    private JTextField memberIDField;
    private JComboBox<String> itemDropdown;
    private JTextField quantityField;
    private JButton addToCartButton;

    private JButton confirmButton;
    private JButton backButton;

    private JList<String> cartPreview;
    private DefaultListModel<String> cartModel;

    private JLabel totalLabel;

    private List<Sale> cart;
    private List<Item> items;
    private double totalAmount = 0.0;

    public NewTransactionView(TransactionController transactionController, SaleController saleController,
            ItemController itemController, JFrame parentFrame) {
        super(parentFrame, "SJ Fitness Gym POS - New Transaction", true);
        setSize(900, 600);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout());

        cart = new ArrayList<>();
        items = itemController.getAllItems();

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(171, 19, 19));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("../icons/logo.png"));
            Image logoImg = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            sidebar.add(logoLabel);
            sidebar.add(Box.createRigidArea(new Dimension(0, 15)));
        } catch (Exception ex) {

        }

        JLabel titleLabel = new JLabel("<html><center>SJ Fitness Gym</center></html>", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        sidebar.add(titleLabel);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));

        backButton = new JButton("Back");
        styleSidebarButton(backButton);
        sidebar.add(backButton);

        add(sidebar, BorderLayout.WEST);

        // Icon
        ImageIcon backIcon = new ImageIcon(getClass().getResource("../icons/return.png"));
        ImageIcon backIconScaled = new ImageIcon(backIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        backButton.setIcon(backIconScaled);
        backButton.setIconTextGap(12);

        // Main Content
        JPanel mainContentPanel = new JPanel(new BorderLayout());
        mainContentPanel.setBackground(Color.WHITE);

        // Left Form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(Color.WHITE);

        memberIDField = new JTextField();
        memberIDField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        memberIDField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        memberIDField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        JLabel memberIDLabel = new JLabel("Member ID (optional):");
        memberIDLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(memberIDLabel);
        formPanel.add(memberIDField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        itemDropdown = new JComboBox<>();
        for (Item item : items) {
            itemDropdown
                    .addItem(item.getItemID() + " - " + item.getItemName() + " - P" + item.getPrice() + " ("
                            + item.getQuantity() + " pcs" + ")");
        }
        itemDropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        itemDropdown.setFont(new Font("Segoe UI", Font.BOLD, 16));

        quantityField = new JTextField();
        quantityField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        quantityField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        quantityField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        JLabel selectItemLabel = new JLabel("Select Item:");
        selectItemLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(selectItemLabel);
        formPanel.add(itemDropdown);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(quantityLabel);
        formPanel.add(quantityField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        addToCartButton = new JButton("Add to Cart");
        addToCartButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        addToCartButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        addToCartButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        addToCartButton.setBackground(new Color(180, 0, 0));
        addToCartButton.setForeground(Color.WHITE);
        formPanel.add(addToCartButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10))); // adds spacing

        JButton removeButton = new JButton("Remove Item");
        removeButton.setBackground(new Color(120, 0, 0));
        removeButton.setForeground(Color.WHITE);
        removeButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        removeButton.setFocusPainted(false);
        removeButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        removeButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        removeButton.addActionListener(e -> {
            int selectedIndex = cartPreview.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < cart.size()) {
                cart.remove(selectedIndex);
                recalculateCart(itemController);
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item to remove.", "Remove Item",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        formPanel.add(removeButton);
        formPanel.add(Box.createVerticalGlue());

        mainContentPanel.add(formPanel, BorderLayout.CENTER);

        // Right Cart Panel
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        cartPanel.setBackground(new Color(245, 245, 245));
        cartPanel.setPreferredSize(new Dimension(300, 0));

        JLabel cartLabel = new JLabel("Cart Preview:");
        cartLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cartPanel.add(cartLabel);
        cartPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        cartModel = new DefaultListModel<>();
        cartPreview = new JList<>(cartModel);
        cartPreview.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cartPreview.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cartPreview.setBorder(BorderFactory.createLineBorder(Color.RED));
        cartPanel.add(new JScrollPane(cartPreview));
        cartPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        totalLabel = new JLabel("Total: ₱0.00");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cartPanel.add(totalLabel);
        cartPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        confirmButton = new JButton("Confirm Transaction");
        confirmButton.setBackground(new Color(180, 0, 0));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        confirmButton.setFocusPainted(false);
        confirmButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        cartPanel.add(confirmButton);

        cartPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainContentPanel.add(cartPanel, BorderLayout.EAST);
        add(mainContentPanel, BorderLayout.CENTER);

        // Logic
        addToCartButton.addActionListener(e -> {
            int selectedIndex = itemDropdown.getSelectedIndex();
            if (selectedIndex < 0 || selectedIndex >= items.size())
                return;
            Item item = items.get(selectedIndex);

            int qty;
            try {
                qty = Integer.parseInt(quantityField.getText());
                if (qty <= 0) {
                    throw new NumberFormatException();
                }

                if (qty > item.getQuantity()) {
                    JOptionPane.showMessageDialog(this, "Insufficient stock. Only " + item.getQuantity() + " left.",
                            "Stock Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean found = false;
            for (Sale existingSale : cart) {
                if (existingSale.getItemID() == item.getItemID()) {
                    int newQty = existingSale.getQuantity() + qty;

                    if (newQty > item.getQuantity()) {
                        JOptionPane.showMessageDialog(this,
                                "Insufficient stock. Only " + item.getQuantity()
                                        + " available including previously added.",
                                "Stock Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    existingSale.setQuantity(newQty);
                    found = true;
                    break;
                }
            }

            if (!found) {
                Sale sale = new Sale();
                sale.setSalesID(new Random().nextInt(100000));
                sale.setItemID(item.getItemID());
                sale.setQuantity(qty);
                cart.add(sale);
            }

            recalculateCart(itemController);

            totalLabel.setText("Total: ₱" + String.format("%.2f", totalAmount));
            quantityField.setText("");
        });

        confirmButton.addActionListener(e -> {
            Integer memberID = null;
            try {
                if (!memberIDField.getText().isBlank()) {
                    memberID = Integer.parseInt(memberIDField.getText().trim());
                }
            } catch (NumberFormatException ignored) {
                JOptionPane.showMessageDialog(this, "Invalid Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = transactionController.processTransaction(memberID, cart, itemController, saleController);
            if (success) {
                JOptionPane.showMessageDialog(this, "Transaction recorded.");
                if (parentFrame instanceof TransactionsView) {
                    ((TransactionsView) parentFrame).refreshTransactionList(transactionController);
                }
                parentFrame.setVisible(true);
                dispose();
            }
        });

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
    }

    private void styleSidebarButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 60));
        button.setBackground(Color.RED);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setFocusPainted(false);
    }

    private void recalculateCart(ItemController itemController) {
        totalAmount = 0.0;
        cartModel.clear();

        for (Sale sale : cart) {
            Item item = itemController.getItemByID(sale.getItemID());
            if (item != null) {
                double lineTotal = item.getPrice() * sale.getQuantity();
                totalAmount += lineTotal;

                String entry = sale.getQuantity() + "x " + item.getItemName() + " - P"
                        + String.format("%.0f", lineTotal);
                cartModel.addElement(entry);
            }
        }

        totalLabel.setText("Total: ₱" + String.format("%.2f", totalAmount));
    }

}
