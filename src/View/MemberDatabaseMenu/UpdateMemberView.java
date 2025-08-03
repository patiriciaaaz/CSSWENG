package View.MemberDatabaseMenu;

import Controller.MemberController;
import Model.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class UpdateMemberView extends JDialog {
    private final JTextField firstNameField = new JTextField();
    private final JTextField lastNameField = new JTextField();
    private final JTextField contactField = new JTextField();

    private final JComboBox<String> membershipTypeDropdown = new JComboBox<>(
            new String[] { "NORMAL", "PWD", "SENIOR" });
    private final JComboBox<String> membershipStatusDropdown = new JComboBox<>(new String[] { "ACTIVE", "INACTIVE" });
    private final JComboBox<String> waiverDropdown = new JComboBox<>(new String[] { "TRUE", "FALSE" });

    private final JComboBox<Integer> birthDayBox = new JComboBox<>();
    private final JComboBox<Integer> birthMonthBox = new JComboBox<>();
    private final JComboBox<Integer> birthYearBox = new JComboBox<>();

    private final JComboBox<Integer> regDayBox = new JComboBox<>();
    private final JComboBox<Integer> regMonthBox = new JComboBox<>();
    private final JComboBox<Integer> regYearBox = new JComboBox<>();

    private final JLabel statusLabel = new JLabel("");

    private final MemberController memberController;
    private final Member member;

    public UpdateMemberView(JFrame parent, MemberController memberController, Member member) {
        super(parent, "Update Member", true);
        this.memberController = memberController;
        this.member = member;

        setSize(600, 750);
        setLocationRelativeTo(parent);

        // Populate date boxes
        for (int i = 1; i <= 31; i++) {
            birthDayBox.addItem(i);
            regDayBox.addItem(i);
        }
        for (int i = 1; i <= 12; i++) {
            birthMonthBox.addItem(i);
            regMonthBox.addItem(i);
        }
        for (int i = 1960; i <= LocalDate.now().getYear(); i++) {
            birthYearBox.addItem(i);
            regYearBox.addItem(i);
        }

        // Title bar
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(180, 0, 0));
        titleBar.setPreferredSize(new Dimension(600, 50));
        JLabel titleLabel = new JLabel("Update Member", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleBar.add(titleLabel, BorderLayout.CENTER);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        Font labelFont = new Font("SansSerif", Font.BOLD, 15);
        Font fieldFont = new Font("SansSerif", Font.PLAIN, 15);

        formPanel.add(createLabeledField("First Name:", firstNameField, labelFont, fieldFont));
        formPanel.add(createLabeledField("Last Name:", lastNameField, labelFont, fieldFont));
        formPanel.add(createDateField("Date of Birth:", birthYearBox, birthMonthBox, birthDayBox, labelFont));
        formPanel.add(createLabeledField("Contact Information:", contactField, labelFont, fieldFont));
        formPanel.add(createDateField("Registration Date:", regYearBox, regMonthBox, regDayBox, labelFont));
        formPanel.add(createLabeledCombo("Membership Type:", membershipTypeDropdown, labelFont));
        formPanel.add(createLabeledCombo("Membership Status:", membershipStatusDropdown, labelFont));
        formPanel.add(createLabeledCombo("Signed Waiver:", waiverDropdown, labelFont));

        JButton updateButton = new JButton("Update Member");
        JButton backButton = new JButton("Back");

        updateButton.setBackground(new Color(180, 0, 0));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        updateButton.setFocusPainted(false);
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton.setBackground(new Color(230, 230, 230));
        backButton.setForeground(new Color(120, 0, 0));
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(updateButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        formPanel.add(backButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        statusLabel.setForeground(new Color(0, 120, 0));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(statusLabel);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(titleBar, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        populateFields();

        updateButton.addActionListener(this::handleUpdateMember);
        backButton.addActionListener(e -> dispose());
    }

    private JPanel createLabeledField(String labelText, JTextField textField, Font labelFont, Font fieldFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        textField.setFont(fieldFont);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(textField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        return panel;
    }

    private JPanel createLabeledCombo(String labelText, JComboBox<String> comboBox, Font labelFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(comboBox);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        return panel;
    }

    private JPanel createDateField(String labelText, JComboBox<Integer> dayBox, JComboBox<Integer> monthBox,
            JComboBox<Integer> yearBox, Font labelFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
        datePanel.setBackground(Color.WHITE);
        dayBox.setMaximumSize(new Dimension(60, 32));
        monthBox.setMaximumSize(new Dimension(80, 32));
        yearBox.setMaximumSize(new Dimension(100, 32));
        datePanel.add(dayBox);
        datePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        datePanel.add(monthBox);
        datePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        datePanel.add(yearBox);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(datePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        return panel;
    }

    private void populateFields() {
        firstNameField.setText(member.getFirstName());
        lastNameField.setText(member.getLastName());
        contactField.setText(member.getContactInformation());

        membershipTypeDropdown.setSelectedItem(member.getMembershipType());
        membershipStatusDropdown.setSelectedItem(member.getMembershipStatus());
        waiverDropdown.setSelectedItem(member.getSignedWaiver());

        LocalDate dob = member.getDateOfBirth();
        LocalDate reg = member.getRegistrationDate();
        if (dob != null) {
            birthDayBox.setSelectedItem(dob.getDayOfMonth());
            birthMonthBox.setSelectedItem(dob.getMonthValue());
            birthYearBox.setSelectedItem(dob.getYear());
        }
        if (reg != null) {
            regDayBox.setSelectedItem(reg.getDayOfMonth());
            regMonthBox.setSelectedItem(reg.getMonthValue());
            regYearBox.setSelectedItem(reg.getYear());
        }
    }

    private void handleUpdateMember(ActionEvent e) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String contact = contactField.getText();
        String type = (String) membershipTypeDropdown.getSelectedItem();
        String status = (String) membershipStatusDropdown.getSelectedItem();
        String waiver = (String) waiverDropdown.getSelectedItem();

        try {
            LocalDate dob = LocalDate.of((int) birthYearBox.getSelectedItem(),
                    (int) birthMonthBox.getSelectedItem(),
                    (int) birthDayBox.getSelectedItem());
            LocalDate reg = LocalDate.of((int) regYearBox.getSelectedItem(),
                    (int) regMonthBox.getSelectedItem(),
                    (int) regDayBox.getSelectedItem());

            String result = memberController.updateExistingMember(member, firstName, lastName, dob, contact, reg,
                    waiver, type, status);
            statusLabel.setText(result);
        } catch (Exception ex) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Invalid date provided.");
        }
    }
}
