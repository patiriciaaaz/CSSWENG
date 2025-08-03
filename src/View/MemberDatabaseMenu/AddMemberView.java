package View.MemberDatabaseMenu;

import Controller.MemberController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class AddMemberView extends JDialog {
    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JTextField contactField = new JTextField();

    private final JComboBox<Integer> dobYearBox = new JComboBox<>();
    private final JComboBox<Integer> dobMonthBox = new JComboBox<>();
    private final JComboBox<Integer> dobDayBox = new JComboBox<>();

    private final JComboBox<Integer> regYearBox = new JComboBox<>();
    private final JComboBox<Integer> regMonthBox = new JComboBox<>();
    private final JComboBox<Integer> regDayBox = new JComboBox<>();

    private final JComboBox<String> waiverDropdown = new JComboBox<>(new String[] { "TRUE", "FALSE" });
    private final JComboBox<String> membershipTypeDropdown = new JComboBox<>(
            new String[] { "NORMAL", "PWD", "SENIOR" });
    private final JComboBox<String> statusDropdown = new JComboBox<>(new String[] { "ACTIVE", "INACTIVE" });

    private final JLabel statusLabel = new JLabel("");

    private final MemberController memberController;

    public AddMemberView(MemberController memberController) {
        super((JFrame) null, "Add New Member", true); // Modal dialog
        setSize(600, 750);
        setLocationRelativeTo(null);
        this.memberController = memberController;

        populateDateCombos();

        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(180, 0, 0));
        titleBar.setPreferredSize(new Dimension(500, 50));
        JLabel titleLabel = new JLabel("Add New Member");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleBar.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
        titleBar.add(titleLabel);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        Font labelFont = new Font("SansSerif", Font.BOLD, 15);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 15);

        addField(formPanel, "First Name:", firstNameField, labelFont, fieldFont);
        addField(formPanel, "Last Name:", lastNameField, labelFont, fieldFont);

        addDateDropdown(formPanel, "Date of Birth (YYYY-MM-DD):", dobYearBox, dobMonthBox, dobDayBox, labelFont);
        addField(formPanel, "Contact Info:", contactField, labelFont, fieldFont);
        addDateDropdown(formPanel, "Registration Date (YYYY-MM-DD):", regYearBox, regMonthBox, regDayBox, labelFont);

        addDropdown(formPanel, "Signed Waiver:", waiverDropdown, labelFont);
        addDropdown(formPanel, "Membership Type:", membershipTypeDropdown, labelFont);
        addDropdown(formPanel, "Membership Status:", statusDropdown, labelFont);

        JButton createButton = new JButton("Create Member");
        JButton backButton = new JButton("Back");
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        createButton.setBackground(new Color(180, 0, 0));
        createButton.setForeground(Color.WHITE);
        createButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        createButton.setFocusPainted(false);
        createButton.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));

        backButton.setBackground(new Color(230, 230, 230));
        backButton.setForeground(new Color(120, 0, 0));
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));

        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(createButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(backButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        statusLabel.setForeground(new Color(0, 120, 0));
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(statusLabel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titleBar, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        createButton.addActionListener(this::handleCreateMember);
        backButton.addActionListener(e -> dispose());
    }

    private void populateDateCombos() {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        int currentDay = LocalDate.now().getDayOfMonth();

        for (int year = 1950; year <= currentYear; year++) {
            dobYearBox.addItem(year);
            regYearBox.addItem(year);
        }
        for (int month = 1; month <= 12; month++) {
            dobMonthBox.addItem(month);
            regMonthBox.addItem(month);
        }
        for (int day = 1; day <= 31; day++) {
            dobDayBox.addItem(day);
            regDayBox.addItem(day);
        }

        // Default Registration Date to today
        regYearBox.setSelectedItem(currentYear);
        regMonthBox.setSelectedItem(currentMonth);
        regDayBox.setSelectedItem(currentDay);
    }

    private void addField(JPanel panel, String labelText, JTextField field, Font labelFont, Font fieldFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);

        field.setFont(fieldFont);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        panel.add(field);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addDropdown(JPanel panel, String labelText, JComboBox<String> dropdown, Font labelFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);

        dropdown.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        dropdown.setFont(new Font("SansSerif", Font.PLAIN, 15));
        dropdown.setBackground(Color.WHITE);
        dropdown.setFocusable(false);
        panel.add(dropdown);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void addDateDropdown(JPanel panel, String labelText, JComboBox<Integer> yearBox,
            JComboBox<Integer> monthBox, JComboBox<Integer> dayBox, Font labelFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
        datePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        datePanel.setBackground(Color.WHITE);

        datePanel.add(yearBox);
        datePanel.add(Box.createHorizontalStrut(8));
        datePanel.add(monthBox);
        datePanel.add(Box.createHorizontalStrut(8));
        datePanel.add(dayBox);

        yearBox.setMaximumSize(new Dimension(100, 32));
        monthBox.setMaximumSize(new Dimension(70, 32));
        dayBox.setMaximumSize(new Dimension(70, 32));

        yearBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
        monthBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
        dayBox.setFont(new Font("SansSerif", Font.PLAIN, 15));

        panel.add(datePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void handleCreateMember(ActionEvent e) {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String contact = contactField.getText().trim();

        int dobYear = (int) dobYearBox.getSelectedItem();
        int dobMonth = (int) dobMonthBox.getSelectedItem();
        int dobDay = (int) dobDayBox.getSelectedItem();

        int regYear = (int) regYearBox.getSelectedItem();
        int regMonth = (int) regMonthBox.getSelectedItem();
        int regDay = (int) regDayBox.getSelectedItem();

        try {
            LocalDate dob = LocalDate.of(dobYear, dobMonth, dobDay);
            LocalDate regDate = LocalDate.of(regYear, regMonth, regDay);

            String waiver = (String) waiverDropdown.getSelectedItem();
            String membershipType = (String) membershipTypeDropdown.getSelectedItem();
            String status = (String) statusDropdown.getSelectedItem();

            String result = memberController.createNewMember(
                    firstName, lastName, dob.toString(), contact,
                    regDate.toString(), waiver, membershipType, status);

            statusLabel.setForeground(new Color(0, 120, 0));
            statusLabel.setText(result);

            if (result.toLowerCase().contains("created")) {
                clearFields();
            }

        } catch (Exception ex) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Invalid date entered. Please check the day, month, and year.");
        }
    }

    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        contactField.setText("");

        dobYearBox.setSelectedIndex(0);
        dobMonthBox.setSelectedIndex(0);
        dobDayBox.setSelectedIndex(0);

        regYearBox.setSelectedIndex(0);
        regMonthBox.setSelectedIndex(0);
        regDayBox.setSelectedIndex(0);

        waiverDropdown.setSelectedIndex(0);
        membershipTypeDropdown.setSelectedIndex(0);
        statusDropdown.setSelectedIndex(0);
    }
}
