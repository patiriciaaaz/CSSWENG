package View.MemberDatabaseMenu;

import Controller.MemberController;
import Model.Member;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class DeleteMemberView extends JFrame {
    private final JLabel statusLabel = new JLabel("");
    private final MemberController memberController;
    private final JList<String> memberList = new JList<>();
    private List<Member> members;

    public DeleteMemberView(MemberController memberController) {
        this.memberController = memberController;

        setTitle("Delete Member");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Select a member to delete:");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainPanel.add(title, BorderLayout.NORTH);

        refreshMemberList();
        memberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(memberList);
        scrollPane.setPreferredSize(new Dimension(250, 300));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton deleteButton = new JButton("Delete Selected Member");
        JButton backButton = new JButton("Back");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(statusLabel);

        statusLabel.setForeground(Color.BLUE);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        deleteButton.addActionListener(this::handleDeleteMember);

        backButton.addActionListener(e -> dispose());
    }

    private void refreshMemberList() {
        members = memberController.getAllMembers();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Member member : members) {
            String fullName = member.getFirstName() + " " + member.getLastName();
            listModel.addElement("[" + member.getMemberID() + "] " + fullName + " - " + member.getMembershipType());
        }
        memberList.setModel(listModel);
    }

    private void handleDeleteMember(ActionEvent e) {
        int index = memberList.getSelectedIndex();
        if (index == -1) {
            statusLabel.setText("Please select a member to delete.");
            return;
        }
        Member selectedMember = members.get(index);
        memberController.deleteMember(selectedMember.getMemberID());
        statusLabel.setText("Member deleted: " + selectedMember.getFirstName() + " " + selectedMember.getLastName());
        refreshMemberList();
    }
}
