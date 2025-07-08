package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JButton updateDbButton;
    private JButton queryOptionsButton;
    private JButton viewReportsButton;
    private JButton transactionsButton;

    public MainMenu() {
        setTitle("SJ Fitness Gym POS");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center window

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = new JLabel("<html><center>SJ Fitness Gym<br>Select an option:</center></html>",
                SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

        updateDbButton = new JButton("1. Update Database");
        queryOptionsButton = new JButton("2. Query Options");
        viewReportsButton = new JButton("3. View Reports");
        transactionsButton = new JButton("4. Transactions");

        updateDbButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        queryOptionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewReportsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        transactionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(updateDbButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(queryOptionsButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(viewReportsButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(transactionsButton);

        add(panel);
    }

    // Public methods for controller to attach event listeners
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
