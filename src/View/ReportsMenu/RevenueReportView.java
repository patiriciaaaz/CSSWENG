package View.ReportsMenu;

import Controller.TransactionController;
import Model.Transaction;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class RevenueReportView extends JFrame {
    private JFrame parentFrame;
    private TransactionController transactionController;

    public RevenueReportView(JFrame parentFrame, TransactionController transactionController) {
        this.parentFrame = parentFrame;
        this.transactionController = transactionController;

        setTitle("SJ Fitness Gym POS - Revenue Report");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        JPanel menuPanel = createSidebar();
        add(menuPanel, BorderLayout.WEST);

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel, BorderLayout.CENTER);

        // Revenue Content
        JPanel revenuePanel = new JPanel();
        revenuePanel.setLayout(new BoxLayout(revenuePanel, BoxLayout.Y_AXIS));
        revenuePanel.setBackground(Color.WHITE);
        revenuePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Title
        JLabel titleLabel = new JLabel("Revenue Report", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        revenuePanel.add(titleLabel);
        revenuePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        List<Transaction> transactions = transactionController.getAllTransactions();

        // Filter transactions for current month
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();
        int currentMonth = now.getMonthValue();

        List<Transaction> currentMonthTransactions = transactions.stream()
                .filter(t -> {
                    LocalDate date = t.getTransactionDate().toLocalDateTime().toLocalDate();
                    return date.getYear() == currentYear && date.getMonthValue() == currentMonth;
                })
                .collect(Collectors.toList());

        double currentMonthRevenue = currentMonthTransactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();

        JLabel currentMonthRevenueLabel = new JLabel(
                "This Month's Revenue: ₱" + String.format("%.2f", currentMonthRevenue));
        currentMonthRevenueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        currentMonthRevenueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        revenuePanel.add(currentMonthRevenueLabel);
        revenuePanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Revenue per day
        Map<Integer, Double> revenuePerDay = new TreeMap<>();
        for (Transaction t : currentMonthTransactions) {
            LocalDate date = t.getTransactionDate().toLocalDateTime().toLocalDate();
            int day = date.getDayOfMonth();
            revenuePerDay.put(day, revenuePerDay.getOrDefault(day, 0.0) + t.getAmount());
        }

        for (Map.Entry<Integer, Double> entry : revenuePerDay.entrySet()) {
            String dayLabel = now.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + entry.getKey();
            JLabel label = new JLabel(dayLabel + ": ₱" + String.format("%.2f", entry.getValue()));
            label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            revenuePanel.add(label);
            revenuePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JScrollPane scrollPane = new JScrollPane(revenuePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true),
                "Revenue Summary",
                0, 0,
                new Font("SansSerif", Font.BOLD, 18),
                new Color(180, 0, 0)));

        centerPanel.add(scrollPane, BorderLayout.CENTER);
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
