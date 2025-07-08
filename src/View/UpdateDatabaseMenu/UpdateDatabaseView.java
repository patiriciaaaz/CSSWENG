package View.UpdateDatabaseMenu;

import app.MyApp;

import Controller.ItemController;
import Model.Item;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class UpdateDatabaseView extends JFrame {
    private JButton updateDbButton;
    private JButton queryOptionsButton;
    private JButton viewReportsButton;
    private JButton transactionsButton;
    private final ItemController itemController;
    private JPanel cardGrid;
    private JPanel selectedCard = null;
    private Item selectedItem = null;

    public UpdateDatabaseView(ItemController itemController) {
        this.itemController = itemController;

        setTitle("SJ Fitness Gym POS");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        // Main Layout
        setLayout(new BorderLayout());

        // Side menu panel (red)
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(180, 0, 0)); // Red shade
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setPreferredSize(new Dimension(250, 0));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("../logo.png"));
            Image logoImg = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuPanel.add(logoLabel);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        } catch (Exception ex) {
           
        }

        // Title/logo at the top of the menu
        JLabel titleLabel = new JLabel("<html><center>SJ Fitness<br>Gym</center></html>", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Buttons
        updateDbButton = new JButton("Update Database");
        queryOptionsButton = new JButton("Query Options");
        viewReportsButton = new JButton("View Reports");
        transactionsButton = new JButton("Transactions");

        JButton[] buttons = { updateDbButton, queryOptionsButton, viewReportsButton, transactionsButton };
        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 40)); 
            btn.setBackground(Color.RED);
            btn.setForeground(new Color(0, 0, 0));
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 15)); 
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

        // CENTER: Inventory Card Grid 
        cardGrid = new JPanel(new GridLayout(0, 4, 20, 20)); 
        cardGrid.setBackground(Color.WHITE);
        cardGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        java.util.List<Item> items = itemController.getAllItems();
        Color[] cardColors = {
            new Color(255, 235, 238), 
            new Color(232, 245, 233), 
            new Color(232, 234, 246), 
            new Color(255, 249, 196)  
        };

        Dimension cardSize = new Dimension(180, 140); 

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(cardColors[i % cardColors.length]);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            card.setPreferredSize(cardSize);
            card.setMaximumSize(cardSize);
            card.setMinimumSize(cardSize);

            JLabel nameLabel = new JLabel(item.getItemName());
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel idLabel = new JLabel("ID: " + item.getItemID());
            idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel priceLabel = new JLabel("₱" + item.getPrice());
            priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            priceLabel.setForeground(new Color(180, 0, 0));
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel qtyLabel = new JLabel("Stock: " + item.getQuantity());
            qtyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            qtyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            card.add(nameLabel);
            card.add(Box.createRigidArea(new Dimension(0, 8)));
            card.add(idLabel);
            card.add(priceLabel);
            card.add(qtyLabel);

            cardGrid.add(card);

            card.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // Deselect previous
                    if (selectedCard != null) {
                        selectedCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true),
                            BorderFactory.createEmptyBorder(15, 15, 15, 15)
                        ));
                    }
                    // Select new
                    selectedCard = card;
                    selectedItem = item;
                    card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(255, 140, 0), 4, true), 
                        BorderFactory.createEmptyBorder(13, 13, 13, 13)
                    ));
                }
            });
        }

        JScrollPane cardScroll = new JScrollPane(cardGrid);
        cardScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
        cardScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true),
            "Inventory",
            0, 0,
            new Font("SansSerif", Font.BOLD, 18),
            new Color(180, 0, 0)
        ));
        cardScroll.getVerticalScrollBar().setUnitIncrement(16);

        add(cardScroll, BorderLayout.CENTER);

        // RIGHT: Action Buttons 
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setPreferredSize(new Dimension(180, 0));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

        JButton createButton = new JButton("Create Item");
        JButton updateButton = new JButton("Update Item");
        JButton deleteButton = new JButton("Delete Item");
        JButton backButton = new JButton("Back to Main Menu");

        JButton[] actionButtons = { createButton, updateButton, deleteButton, backButton };
        for (JButton btn : actionButtons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(180, 50));
            btn.setFont(new Font("SansSerif", Font.BOLD, 15));
            btn.setBackground(new Color(180, 0, 0));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setOpaque(true);
            rightPanel.add(btn);
            rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }
        add(rightPanel, BorderLayout.EAST);

        // Button Actions 
        createButton.addActionListener(e -> {
            CreateItemView dialog = new CreateItemView(this, itemController);
            dialog.setVisible(true);
            refreshInventory();
        });
        updateButton.addActionListener(e -> {
            if (selectedItem == null) {
                JOptionPane.showMessageDialog(this, "Please select an item to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            UpdateItemView updateItemView = new UpdateItemView(this, itemController, selectedItem);
            updateItemView.setVisible(true);
            refreshInventory();
            selectedCard = null;
            selectedItem = null;
        });
        deleteButton.addActionListener(e -> {
            if (selectedItem == null) {
                JOptionPane.showMessageDialog(this, "Please select an item to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete \"" + selectedItem.getItemName() + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                itemController.deleteItem(selectedItem.getItemID());
                refreshInventory();
                selectedCard = null;
                selectedItem = null;
            }
        });
        backButton.addActionListener(e -> {
            MyApp.showMainMenu();
            dispose();
        });

        
    }

    public void refreshInventory() {
        cardGrid.removeAll();
        java.util.List<Item> items = itemController.getAllItems();
        Color[] cardColors = {
            new Color(255, 235, 238), 
            new Color(232, 245, 233), 
            new Color(232, 234, 246), 
            new Color(255, 249, 196)  
        };
        Dimension cardSize = new Dimension(180, 140); 

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(cardColors[i % cardColors.length]);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            card.setPreferredSize(cardSize);
            card.setMaximumSize(cardSize);
            card.setMinimumSize(cardSize);

            JLabel nameLabel = new JLabel(item.getItemName());
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel idLabel = new JLabel("ID: " + item.getItemID());
            idLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel priceLabel = new JLabel("₱" + item.getPrice());
            priceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            priceLabel.setForeground(new Color(180, 0, 0));
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel qtyLabel = new JLabel("Stock: " + item.getQuantity());
            qtyLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
            qtyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            card.add(nameLabel);
            card.add(Box.createRigidArea(new Dimension(0, 8)));
            card.add(idLabel);
            card.add(priceLabel);
            card.add(qtyLabel);

            cardGrid.add(card);

            card.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // Deselect previous
                    if (selectedCard != null) {
                        selectedCard.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(180, 0, 0), 2, true),
                            BorderFactory.createEmptyBorder(15, 15, 15, 15)
                        ));
                    }
                    // Select new
                    selectedCard = card;
                    selectedItem = item;
                    card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(255, 140, 0), 4, true), 
                        BorderFactory.createEmptyBorder(13, 13, 13, 13)
                    ));
                }
            });
        }
        cardGrid.revalidate();
        cardGrid.repaint();
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