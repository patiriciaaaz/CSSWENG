package View.UpdateDatabaseMenu;

import app.MyApp;

import Controller.ItemController;
import Model.Item;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Comparator;

public class UpdateDatabaseView extends JFrame {
    private JButton updateDbButton;
    private JButton memberDbButton;
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
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        // Main Layout
        setLayout(new BorderLayout());

        // Side menu panel (red)
        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(171,19,19)); 
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
           
        }

        // Title/logo at the top of the menu
        JLabel titleLabel = new JLabel("<html><center>SJ Fitness Gym</center></html>", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);

        menuPanel.add(titleLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Buttons
        JButton createButton = new JButton("Create Item");
        JButton updateButton = new JButton("Update Item");
        JButton deleteButton = new JButton("Delete Item");
        JButton backButton = new JButton("Back to Main Menu");

        JButton[] buttons = { createButton, updateButton, deleteButton, backButton };
        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(250, 60)); 
            btn.setBackground(Color.RED);
            btn.setForeground(Color.BLACK);
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
                    btn.setForeground(Color.BLACK);
                }
            });

            menuPanel.add(btn);
            menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        // sorter
        String[] sortOptions = { "Alphabetical", "Lowest Stock", "ID" };
        JComboBox<String> sortCombo = new JComboBox<>(sortOptions);
        sortCombo.setSelectedIndex(0); // default to Alphabetical
        sortCombo.setMaximumSize(new Dimension(250, 60));
        sortCombo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sortCombo.setBackground(Color.WHITE);
        sortCombo.setForeground(Color.BLACK);
        sortCombo.setFocusable(false);
        sortCombo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        

        sortCombo.addActionListener(e -> refreshInventory((String)sortCombo.getSelectedItem()));

        JLabel sortLabel = new JLabel("Sort by:");
        sortLabel.setForeground(Color.WHITE);
        sortLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sortLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sortCombo.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(sortLabel);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        menuPanel.add(sortCombo);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Icons
        ImageIcon addIcon = new ImageIcon(getClass().getResource("../icons/add.png"));
        ImageIcon editIcon = new ImageIcon(getClass().getResource("../icons/edit.png"));
        ImageIcon deleteIcon = new ImageIcon(getClass().getResource("../icons/remove.png"));
        ImageIcon backIcon = new ImageIcon(getClass().getResource("../icons/return.png"));

        ImageIcon addIconScaled = new ImageIcon(addIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        ImageIcon editIconScaled = new ImageIcon(editIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        ImageIcon deleteIconScaled = new ImageIcon(deleteIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        ImageIcon backIconScaled = new ImageIcon(backIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));

        createButton.setIcon(addIconScaled);
        createButton.setIconTextGap(12);
        updateButton.setIcon(editIconScaled);
        updateButton.setIconTextGap(12);
        deleteButton.setIcon(deleteIconScaled);
        deleteButton.setIconTextGap(12);
        backButton.setIcon(backIconScaled);
        backButton.setIconTextGap(12);


        add(menuPanel, BorderLayout.WEST);

        // CENTER: Inventory Card Grid 
        cardGrid = new JPanel(new GridLayout(0, 4, 20, 20)); 
        cardGrid.setBackground(Color.WHITE);
        cardGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        java.util.List<Item> items = itemController.getAllItems();
        Color[] cardColors = {
            new Color(255, 225, 230), 
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
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 23));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel idLabel = new JLabel("ID: " + item.getItemID());
            idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel priceLabel = new JLabel("₱" + item.getPrice());
            priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            priceLabel.setForeground(new Color(180, 0, 0));
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel qtyLabel = new JLabel("Stock: " + item.getQuantity());
            qtyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
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

        // Button Actions 
        createButton.addActionListener(e -> {
            CreateItemView dialog = new CreateItemView(this, itemController);
            dialog.setVisible(true);
            refreshInventory("Alphabetical");
        });
        updateButton.addActionListener(e -> {
            if (selectedItem == null) {
                JOptionPane.showMessageDialog(this, "Please select an item to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
                return;
            }
            UpdateItemView updateItemView = new UpdateItemView(this, itemController, selectedItem);
            updateItemView.setVisible(true);
            refreshInventory("Alphabetical");
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
                refreshInventory("Alphabetical");
                selectedCard = null;
                selectedItem = null;
            }
        });
        backButton.addActionListener(e -> {
            MyApp.showMainMenu();
            dispose();
        });

        
    }

    public void refreshInventory(String sortOption) {
        cardGrid.removeAll();
        java.util.List<Item> items = itemController.getAllItems();

         if ("Alphabetical".equals(sortOption)) {
            items.sort(Comparator.comparing(Item::getItemName, String.CASE_INSENSITIVE_ORDER));
        } else if ("Lowest Stock".equals(sortOption)) {
            items.sort(Comparator.comparingInt(Item::getQuantity));
        } else if ("ID".equals(sortOption)) {
            items.sort(Comparator.comparingInt(Item::getItemID));
        }

        Color[] cardColors = {
            new Color(255, 225, 230), 
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
            nameLabel.setFont(new Font("SansSerif", Font.BOLD, 23));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel idLabel = new JLabel("ID: " + item.getItemID());
            idLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel priceLabel = new JLabel("₱" + item.getPrice());
            priceLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            priceLabel.setForeground(new Color(180, 0, 0));
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel qtyLabel = new JLabel("Stock: " + item.getQuantity());
            qtyLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
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

    public void addMemberDbListener(ActionListener listener) {
        memberDbButton.addActionListener(listener);
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