package View.TransactionsMenu;

import Controller.ItemController;
import Controller.SaleController;
import Controller.TransactionController;
import Model.Transaction;
import app.MyApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class TransactionsView extends JFrame {
    private JButton updateDbButton;
    private JButton queryOptionsButton;
    private JButton viewReportsButton;
    private JButton transactionsButton;

    private JPanel transactionListPanel;

    public TransactionsView(TransactionController transactionController, SaleController saleController,
            ItemController itemController) {
        setTitle("SJ Fitness Gym POS - Transactions");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(171,19,19));
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
           
        }

        JLabel titleLabel = new JLabel("<html><center>SJ Fitness Gym</center></html>", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton createButton = new JButton("New Transaction");
        JButton backButton = new JButton("Back");

        JButton[] menuButtons = { createButton, backButton };
        for (JButton btn : menuButtons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 60));
            btn.setBackground(Color.RED);
            btn.setForeground(Color.BLACK);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setOpaque(true);
            btn.setFocusPainted(false);

            // Change color on press
            btn.getModel().addChangeListener(e -> {
                ButtonModel model = btn.getModel();
                if (model.isPressed() || model.isRollover()) {
                    btn.setBackground(Color.WHITE);
                    btn.setForeground(new Color(180, 0, 0));
                } else {
                    btn.setBackground(Color.RED);
                    btn.setForeground(Color.BLACK);
                }
            });

            menuPanel.add(btn);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        add(menuPanel, BorderLayout.WEST);

        // Icons
        ImageIcon createIcon = new ImageIcon(getClass().getResource("../icons/new_transaction.png"));
        ImageIcon backIcon = new ImageIcon(getClass().getResource("../icons/return.png"));

        ImageIcon createIconScaled = new ImageIcon(createIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        ImageIcon backIconScaled = new ImageIcon(backIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));

        createButton.setIcon(createIconScaled);
        createButton.setIconTextGap(12);
        backButton.setIcon(backIconScaled);
        backButton.setIconTextGap(12);

        // Center: Transactions list
        transactionListPanel = new JPanel();
        transactionListPanel.setLayout(new BoxLayout(transactionListPanel, BoxLayout.Y_AXIS));
        transactionListPanel.setBackground(Color.WHITE);
        transactionListPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        List<Transaction> transactions = transactionController.getAllTransactions();
        for (Transaction t : transactions) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true));
            card.setBackground(new Color(255, 248, 225));
            card.setMaximumSize(new Dimension(1600, 95));
            card.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel idLabel = new JLabel("Transaction ID: " + t.getTransactionID());
            JLabel memberLabel = new JLabel("Member ID: " + t.getMemberID());
            JLabel dateLabel = new JLabel("Date: " + t.getTransactionDate());
            JLabel amountLabel = new JLabel("Amount: ₱" + t.getAmount());

            idLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            memberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            card.add(idLabel);
            card.add(memberLabel);
            card.add(dateLabel);
            card.add(amountLabel);
            transactionListPanel.add(card);
            transactionListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane scrollPane = new JScrollPane(transactionListPanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true),
                "Transactions",
                0, 0,
                new Font("SansSerif", Font.BOLD, 18),
                new Color(180, 0, 0)));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // Button actions
        backButton.addActionListener(e -> {
            MyApp.showMainMenu();
            dispose();
        });

        createButton.addActionListener(e -> {
            NewTransactionView newTransactionView = new NewTransactionView(transactionController, saleController,
                    itemController, this);
            newTransactionView.setVisible(true);
            refreshTransactionList(transactionController);
            this.setVisible(true);
        });

    }

    private void refreshTransactionList(TransactionController transactionController) {
        transactionListPanel.removeAll();
        List<Transaction> transactions = transactionController.getAllTransactions();
        for (Transaction t : transactions) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true));
            card.setBackground(new Color(255, 248, 225));
            card.setMaximumSize(new Dimension(1600, 95));
            card.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel idLabel = new JLabel("Transaction ID: " + t.getTransactionID());
            JLabel memberLabel = new JLabel("Member ID: " + t.getMemberID());
            JLabel dateLabel = new JLabel("Date: " + t.getTransactionDate());
            JLabel amountLabel = new JLabel("Amount: ₱" + t.getAmount());

            idLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            memberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            card.add(idLabel);
            card.add(memberLabel);
            card.add(dateLabel);
            card.add(amountLabel);
            transactionListPanel.add(card);
            transactionListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        transactionListPanel.revalidate();
        transactionListPanel.repaint();
    }

    // For controller access
    public void addUpdateDbListener(ActionListener listener) {
        updateDbButton.addActionListener(listener);
    }

    public void addQueryOptionsListener(ActionListener listener) {
        queryOptionsButton.addActionListener(listener);
    }

    public void addViewReportsListener(ActionListener listener) {
        viewReportsButton.addActionListener(listener);
    }

    public void addTransactionsListener(ActionListener listener) {
        transactionsButton.addActionListener(listener);
    }
}
