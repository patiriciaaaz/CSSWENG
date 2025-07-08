package View.UpdateDatabaseMenu;

import app.MyApp;

import Controller.ItemController;
import Model.Item;
import View.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class UpdateDatabaseView extends JFrame {
    private final ItemController itemController;

    public UpdateDatabaseView(ItemController itemController) {
        this.itemController = itemController;

        setTitle("Update Database Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null); // Center

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Select an Update Option:");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton createButton = new JButton("Create Item");
        JButton updateButton = new JButton("Update Item");
        JButton deleteButton = new JButton("Delete Item");
        JButton backButton = new JButton("Back to Main Menu");

        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(createButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(updateButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(deleteButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(backButton);

        add(panel);

        createButton.addActionListener(e -> {
            CreateItemView createItemView = new CreateItemView(itemController);
            createItemView.setVisible(true);
            dispose();
        });

        updateButton.addActionListener(e -> {
            UpdateItemView updateItemView = new UpdateItemView(itemController);
            updateItemView.setVisible(true);
            dispose();
        });

        deleteButton.addActionListener(e -> {
            DeleteItemView deleteItemView = new DeleteItemView(itemController);
            deleteItemView.setVisible(true);
            dispose();
        });

        backButton.addActionListener(e -> {
            MyApp.showMainMenu();
            dispose();
        });
    }
}