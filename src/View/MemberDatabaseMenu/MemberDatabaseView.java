package View.MemberDatabaseMenu;

import app.MyApp;
import Controller.MemberController;
import Model.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class MemberDatabaseView extends JFrame {
    private MemberController memberController;
    private JPanel cardGrid;
    private JPanel selectedCard = null;
    private Member selectedMember = null;

    public MemberDatabaseView(MemberController memberController) {
        this.memberController = memberController;

        setTitle("SJ Fitness Gym POS - Member Database");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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
        } catch (Exception ignored) {
        }

        JLabel titleLabel = new JLabel("<html><center>SJ Fitness Gym</center></html>", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton addBtn = createMenuButton("Add Member", e -> showAddMemberDialog());
        JButton updateBtn = createMenuButton("Update Member", e -> showUpdateMemberDialog(memberController,
                selectedMember));
        JButton deleteBtn = createMenuButton("Delete Member", e -> deleteSelectedMember());
        JButton backBtn = createMenuButton("Back to Main Menu", e -> {
            MyApp.showMainMenu();
            dispose();
        });

        for (JButton btn : new JButton[] { addBtn, updateBtn, deleteBtn, backBtn }) {
            menuPanel.add(btn);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        add(menuPanel, BorderLayout.WEST);

        // Use BoxLayout for full-width cards
        cardGrid = new JPanel();
        cardGrid.setLayout(new BoxLayout(cardGrid, BoxLayout.Y_AXIS));
        cardGrid.setBackground(Color.WHITE);
        cardGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        populateMemberCards();

        JScrollPane scroll = new JScrollPane(cardGrid);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    private JButton createMenuButton(String text, ActionListener l) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(250, 60));
        btn.setBackground(Color.RED);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(l);

        btn.getModel().addChangeListener(e -> {
            ButtonModel m = btn.getModel();
            if (m.isPressed() || m.isRollover()) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(180, 0, 0));
            } else {
                btn.setBackground(Color.RED);
                btn.setForeground(Color.BLACK);
            }
        });

        return btn;
    }

    private void populateMemberCards() {
        cardGrid.removeAll();
        List<Member> members = memberController.getAllMembers();
        Color[] colors = { new Color(255, 225, 230), new Color(232, 245, 233), new Color(232, 234, 246) };
        for (int i = 0; i < members.size(); i++) {
            Member m = members.get(i);
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(colors[i % colors.length]);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 0, 0), 2),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 75));

            JLabel name = new JLabel(m.getFirstName() + " " + m.getLastName());
            name.setFont(new Font("Segoe UI", Font.BOLD, 18));
            name.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel id = new JLabel("ID: " + m.getMemberID());
            id.setFont(new Font("Segoe UI", Font.BOLD, 18));
            id.setAlignmentX(Component.CENTER_ALIGNMENT);

            card.add(name);
            card.add(id);

            card.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (selectedCard != null) {
                        selectedCard.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(180, 0, 0), 2),
                                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    }
                    selectedCard = card;
                    selectedMember = m;
                    card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(255, 140, 0), 4),
                            BorderFactory.createEmptyBorder(8, 8, 8, 8)));

                    // Double-click to show dialog
                    if (e.getClickCount() == 2) {
                        String info = String.format(
                                "Member ID: %d\nName: %s %s\nDate of Birth: %s\nContact: %s\nRegistration Date: %s\nSigned Waiver: %s\nMembership Type: %s\nMembership Status: %s",
                                m.getMemberID(),
                                m.getFirstName(),
                                m.getLastName(),
                                m.getDateOfBirth(),
                                m.getContactInformation(),
                                m.getRegistrationDate(),
                                m.getSignedWaiver(),
                                m.getMembershipType(),
                                m.getMembershipStatus());
                        JOptionPane.showMessageDialog(card, info, "Member Information",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });

            cardGrid.add(card);
            cardGrid.add(Box.createVerticalStrut(15)); // Add spacing between cards
        }
        cardGrid.revalidate();
        cardGrid.repaint();
    }

    private void showAddMemberDialog() {
        AddMemberView addMemberView = new AddMemberView(memberController);
        addMemberView.setVisible(true);
        populateMemberCards();
    }

    private void showUpdateMemberDialog(MemberController memberController, Member member) {
        if (selectedMember == null) {
            JOptionPane.showMessageDialog(this, "Please select a member to update.", "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        UpdateMemberView updateMemberDialog = new UpdateMemberView(this, memberController, selectedMember);
        updateMemberDialog.setVisible(true);

        // Refresh cards after update
        populateMemberCards();
    }

    private void deleteSelectedMember() {
        if (selectedMember == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a member to delete.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fullName = selectedMember.getFirstName() + " " + selectedMember.getLastName();
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "<html>Are you sure you want to <b>delete</b> <i>" + fullName + "</i> from the database?</html>",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = memberController.deleteMember(selectedMember.getMemberID());
            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        fullName + " was successfully deleted.",
                        "Member Deleted",
                        JOptionPane.INFORMATION_MESSAGE);
                selectedMember = null;
                selectedCard = null;
                populateMemberCards();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "An error occurred while deleting the member. Please try again.",
                        "Deletion Failed",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
