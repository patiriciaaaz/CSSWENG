package View.ReportsMenu;

import app.MyApp;
import Controller.SaleController;
import Controller.ItemController;
import Controller.TransactionController;

import javax.swing.*;
import java.awt.*;

public class ViewReportsView extends JFrame {
    public ViewReportsView(TransactionController transactionController, SaleController saleController,
            ItemController itemController) {
        setTitle("SJ Fitness Gym - View Reports");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Left red sidebar
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
        } catch (Exception e) {
            System.out.println("Logo not found.");
        }

        JLabel titleLabel = new JLabel("<html><center>SJ Fitness Gym</center></html>", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Buttons
        JButton salesReportsBtn = new JButton("Sales");
        JButton revenueReportsBtn = new JButton("Revenue Reports");
        JButton memberTransactionsBtn = new JButton("Member Transactions");
        JButton backButton = new JButton("Back to Main Menu");

        JButton[] buttons = { salesReportsBtn, revenueReportsBtn, memberTransactionsBtn, backButton };

        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 60));
            btn.setBackground(Color.RED);
            btn.setForeground(Color.BLACK);
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 17));
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setOpaque(true);

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

        // Icons
        ImageIcon salesReportsIcon = new ImageIcon(getClass().getResource("../icons/sales-report.png"));
        ImageIcon memberReportsIcon = new ImageIcon(getClass().getResource("../icons/member-report.png"));
        ImageIcon transactionReportsIcon = new ImageIcon(getClass().getResource("../icons/transaction-report.png"));
        ImageIcon backIcon = new ImageIcon(getClass().getResource("../icons/return.png"));

        ImageIcon salesReportsIconScaled = new ImageIcon(
                salesReportsIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        ImageIcon memberReportsIconScaled = new ImageIcon(
                memberReportsIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        ImageIcon transactionReportsIconScaled = new ImageIcon(
                transactionReportsIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        ImageIcon backIconScaled = new ImageIcon(
                backIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));

        salesReportsBtn.setIcon(salesReportsIconScaled);
        salesReportsBtn.setIconTextGap(12);
        revenueReportsBtn.setIcon(memberReportsIconScaled);
        revenueReportsBtn.setIconTextGap(12);
        memberTransactionsBtn.setIcon(transactionReportsIconScaled);
        memberTransactionsBtn.setIconTextGap(12);
        backButton.setIcon(backIconScaled);
        backButton.setIconTextGap(12);

        add(menuPanel, BorderLayout.WEST);

        // Center placeholder
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        add(centerPanel, BorderLayout.CENTER);

        // Actions
        salesReportsBtn.addActionListener(e -> {
            new SalesReportView(this, saleController, itemController).setVisible(true);
            dispose();
        });

       revenueReportsBtn.addActionListener(e -> {
            new RevenueReportView(this, transactionController).setVisible(true);
            dispose();
        });

        memberTransactionsBtn.addActionListener(e -> {
            new MemberTransactionsView(this, transactionController).setVisible(true);
            dispose();
        });

        backButton.addActionListener(e -> {
            MyApp.showMainMenu();
            dispose();
        });
    }
}
