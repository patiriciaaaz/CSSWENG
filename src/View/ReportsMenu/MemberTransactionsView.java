package View.ReportsMenu;

import Controller.TransactionController;
import Model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MemberTransactionsView extends JFrame {
    private JPanel transactionListPanel;
    private JFrame parentFrame;
    private JTextField memberIdField;
    private TransactionController transactionController;
    private JPanel centerPanel;
    private JScrollPane currentScrollPane;

    public MemberTransactionsView(JFrame parentFrame, TransactionController transactionController) {
        this.parentFrame = parentFrame;
        this.transactionController = transactionController;

        setTitle("SJ Fitness Gym POS - Member Transactions");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel menuPanel = createSidebar();
        add(menuPanel, BorderLayout.WEST);

        // Center Panel with Input and Scrollable Transaction List
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel, BorderLayout.CENTER);

        // Member ID Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel promptLabel = new JLabel("Enter Member ID:");
        promptLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        inputPanel.add(promptLabel);

        memberIdField = new JTextField(10);
        memberIdField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        inputPanel.add(memberIdField);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        searchButton.setBackground(new Color(180, 0, 0));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        inputPanel.add(searchButton);

        searchButton.addActionListener(e -> {
            String input = memberIdField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a Member ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int memberID = Integer.parseInt(input);
                displayTransactions(memberID);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Member ID. Please enter a number.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        centerPanel.add(inputPanel, BorderLayout.NORTH);
    }

    private void displayTransactions(int memberID) {

        if (currentScrollPane != null) {
            centerPanel.remove(currentScrollPane);
        }

        transactionListPanel = new JPanel();
        transactionListPanel.setLayout(new BoxLayout(transactionListPanel, BoxLayout.Y_AXIS));
        transactionListPanel.setBackground(Color.WHITE);
        transactionListPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        List<Transaction> transactions = transactionController.getTransactionsByMember(memberID);

        // Title Label
        JLabel titleLabel = new JLabel("Transactions for Member ID: " + memberID, SwingConstants.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        transactionListPanel.add(titleLabel);

        // Summary below title
        int totalTransactions = transactions.size();
        double totalSpent = transactions.stream().mapToDouble(Transaction::getAmount).sum();

        JLabel summaryLabel = new JLabel(
                "Total Transactions: " + totalTransactions + " | Total Spent: ₱" + String.format("%.2f", totalSpent));
        summaryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        summaryLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
        summaryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        transactionListPanel.add(summaryLabel);

        // Transaction cards
        if (transactions.isEmpty()) {
            JLabel noDataLabel = new JLabel("No transactions found for this member.");
            noDataLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            noDataLabel.setForeground(Color.GRAY);
            transactionListPanel.add(noDataLabel);
        } else {
            for (Transaction t : transactions) {
                JPanel card = new JPanel();
                card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
                card.setBorder(BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true));
                card.setBackground(new Color(255, 248, 225));
                card.setMaximumSize(new Dimension(2600, 95));
                card.setAlignmentX(Component.CENTER_ALIGNMENT);

                JLabel idLabel = new JLabel("Transaction ID: " + t.getTransactionID());
                JLabel dateLabel = new JLabel("Date: " + t.getTransactionDate());
                JLabel amountLabel = new JLabel("Amount: ₱" + t.getAmount());

                idLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
                dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

                card.add(idLabel);
                card.add(dateLabel);
                card.add(amountLabel);
                transactionListPanel.add(card);
                transactionListPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }

        currentScrollPane = new JScrollPane(transactionListPanel);
        currentScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true),
                "Transaction History",
                0, 0,
                new Font("SansSerif", Font.BOLD, 18),
                new Color(180, 0, 0)));
        currentScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        centerPanel.add(currentScrollPane, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();

        // Scroll to top just in case
        SwingUtilities.invokeLater(() -> currentScrollPane.getVerticalScrollBar().setValue(0));
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

        try {
            ImageIcon backIcon = new ImageIcon(getClass().getResource("../icons/return.png"));
            Image backImg = backIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
            backButton.setIcon(new ImageIcon(backImg));
            backButton.setIconTextGap(12);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        menuPanel.add(backButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        return menuPanel;
    }
}
