package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JButton updateDbButton;
    private JButton memberDbButton;

    private JButton viewReportsButton;
    private JButton transactionsButton;

    public MainMenu() {
        setTitle("SJ Fitness Gym POS");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Layout
        setLayout(new BorderLayout());

        // Side menu panel (red)
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(171, 19, 19));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(250, 0));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("../View/icons/logo.png"));
            Image logoImg = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuPanel.add(logoLabel);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        } catch (Exception ex) {

        }

        // Title/logo at the top of the menu
        JLabel titleLabel = new JLabel("<html><center>SJ Fitness Gym</center></html>", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Buttons
        updateDbButton = new JButton("Update Database");
        memberDbButton = new JButton("Member Database");

        viewReportsButton = new JButton("Reports");
        transactionsButton = new JButton("Transactions");

        JButton[] buttons = { updateDbButton, memberDbButton, viewReportsButton, transactionsButton };
        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 60));
            btn.setBackground(Color.RED);
            btn.setForeground(new Color(0, 0, 0));
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setOpaque(true);

            // Change color on press
            btn.getModel().addChangeListener(e -> {
                ButtonModel model = btn.getModel();
                if (model.isPressed() || model.isRollover()) {
                    btn.setBackground(Color.WHITE);
                    btn.setForeground(new Color(180, 0, 0));
                } else {
                    btn.setBackground(Color.RED);
                    btn.setForeground(new Color(0, 0, 0));
                }
            });

            menuPanel.add(btn);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        add(menuPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        JLabel welcomeLabel = new JLabel("Welcome! Please select an option from the menu.");
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        centerPanel.add(welcomeLabel);
        add(centerPanel, BorderLayout.CENTER);

        // Icons
        ImageIcon updateDbIcon = new ImageIcon(getClass().getResource("../View/icons/edit-info.png"));
        ImageIcon memberDbIcon = new ImageIcon(getClass().getResource("../View/icons/member.png"));
        ImageIcon viewReportsIcon = new ImageIcon(getClass().getResource("../View/icons/reports.png"));
        ImageIcon transactionsIcon = new ImageIcon(getClass().getResource("../View/icons/transaction.png"));

        ImageIcon updateDbIconScaled = new ImageIcon(
                updateDbIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        ImageIcon memberDbIconScaled = new ImageIcon(
                memberDbIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        ImageIcon viewReportsIconScaled = new ImageIcon(
                viewReportsIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));
        ImageIcon transactionsIconScaled = new ImageIcon(
                transactionsIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH));

        updateDbButton.setIcon(updateDbIconScaled);
        updateDbButton.setIconTextGap(12);
        memberDbButton.setIcon(memberDbIconScaled);
        memberDbButton.setIconTextGap(12);
        viewReportsButton.setIcon(viewReportsIconScaled);
        viewReportsButton.setIconTextGap(12);
        transactionsButton.setIcon(transactionsIconScaled);
        transactionsButton.setIconTextGap(12);
    }

    // Public methods for controller to attach event listeners
    public void addUpdateDbListener(ActionListener listener) {
        updateDbButton.addActionListener(listener);
    }

    public void addMemberDbListener(ActionListener listener) {
        memberDbButton.addActionListener(listener);
    }

    public void addViewReportsListener(ActionListener listener) {
        viewReportsButton.addActionListener(listener);
    }

    public void addTransactionsListener(ActionListener listener) {
        transactionsButton.addActionListener(listener);
    }
}
