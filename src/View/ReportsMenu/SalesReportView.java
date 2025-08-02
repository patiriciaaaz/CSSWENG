package View.ReportsMenu;

import Controller.SaleController;
import Controller.ItemController;
import Model.Sale;
import Model.Item;
import app.MyApp;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SalesReportView extends JFrame {
    private JPanel saleListPanel;

    public SalesReportView(SaleController saleController, ItemController itemController) {
        setTitle("SJ Fitness Gym POS - Sales Report");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel menuPanel = createSidebar();
        add(menuPanel, BorderLayout.WEST);

        // Sale List Panel
        saleListPanel = new JPanel();
        saleListPanel.setLayout(new BoxLayout(saleListPanel, BoxLayout.Y_AXIS));
        saleListPanel.setBackground(Color.WHITE);
        saleListPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Sale> sales = saleController.getAllSales();
        for (Sale s : sales) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true));
            card.setBackground(new Color(255, 248, 225));
            card.setMaximumSize(new Dimension(1600, 95));
            card.setAlignmentX(Component.CENTER_ALIGNMENT);

            Item item = itemController.getItemByID(s.getItemID());

            JLabel saleIDLabel = new JLabel("Sale ID: " + s.getSalesID());
            JLabel transactionIDLabel = new JLabel("Transaction ID: " + s.getTransactionID());
            JLabel itemLabel = new JLabel("Item: " + (item != null ? item.getItemName() : "Unknown"));
            JLabel quantityLabel = new JLabel("Quantity: " + s.getQuantity());

            saleIDLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            transactionIDLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            itemLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            quantityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            card.add(saleIDLabel);
            card.add(transactionIDLabel);
            card.add(itemLabel);
            card.add(quantityLabel);
            saleListPanel.add(card);
            saleListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane scrollPane = new JScrollPane(saleListPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true),
                "Sales",
                0, 0,
                new Font("SansSerif", Font.BOLD, 18),
                new Color(180, 0, 0)));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(171, 19, 19));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(250, 0));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("../icons/logo.png"));
            Image logoImg = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuPanel.add(logoLabel);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JLabel titleLabel = new JLabel("<html><center>SJ Fitness Gym</center></html>", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setMaximumSize(new Dimension(250, 60));
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        backButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setOpaque(true);
        backButton.setFocusPainted(false);

        backButton.getModel().addChangeListener(e -> {
            ButtonModel model = backButton.getModel();
            if (model.isPressed() || model.isRollover()) {
                backButton.setBackground(Color.WHITE);
                backButton.setForeground(new Color(180, 0, 0));
            } else {
                backButton.setBackground(Color.RED);
                backButton.setForeground(Color.BLACK);
            }
        });

        backButton.addActionListener(e -> {
            MyApp.showMainMenu();
            dispose();
        });

        menuPanel.add(backButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        return menuPanel;
    }
}
