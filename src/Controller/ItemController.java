package Controller;

import Model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemController {
    private Connection conn;

    public ItemController(Connection conn) {
        this.conn = conn;
    }

    // Create
    public int addItem(Item item) {
        String sql = "INSERT INTO items (item_name, price, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setDouble(2, item.getPrice());
            pstmt.setInt(3, item.getQuantity());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedID = generatedKeys.getInt(1);
                        item.setItemID(generatedID);
                        return generatedID;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Read
    public Item getItemByID(int itemID) {
        String sql = "SELECT * FROM items WHERE itemID = ?";
        Item item = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                item = new Item();
                item.setItemID(rs.getInt("itemID"));
                item.setItemName(rs.getString("item_name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    // Update
    public void updateItem(Item item) {
        String sql = "UPDATE items SET item_name = ?, price = ?, quantity = ? WHERE itemID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getItemName());
            pstmt.setDouble(2, item.getPrice());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setInt(4, item.getItemID());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public void deleteItem(int itemID) {
        String sql = "DELETE FROM items WHERE itemID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read all items
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Item item = new Item();
                item.setItemID(rs.getInt("itemID"));
                item.setItemName(rs.getString("item_name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public String createNewItem(String name, String priceText, String quantityText) {
        if (name.isBlank()) {
            return "Item name cannot be empty.";
        }

        if (itemNameExists(name, null)) {
            return "An item with this name already exists.";
        }

        try {
            double price = Double.parseDouble(priceText.trim());
            int quantity = Integer.parseInt(quantityText.trim());

            if (price <= 0)
                return "Price must be greater than 0.";
            if (quantity < 0)
                return "Quantity cannot be negative.";

            Item item = new Item();
            item.setItemName(name.trim());
            item.setPrice(price);
            item.setQuantity(quantity);

            int generatedID = addItem(item);
            if (generatedID > 0) {
                return "Item created with ID: " + generatedID;
            } else {
                return "Failed to create item.";
            }

        } catch (NumberFormatException e) {
            return "Invalid price or quantity input.";
        }
    }

    public String updateExistingItem(Item item, String newName, String priceText, String quantityText) {
        if (!newName.equalsIgnoreCase("KEEP")) {
            if (newName.isBlank())
                return "Item name cannot be empty.";

            if (itemNameExists(newName, item.getItemID())) {
                return "Another item with this name already exists.";
            }

            item.setItemName(newName.trim());
        }

        try {
            if (!priceText.equalsIgnoreCase("KEEP")) {
                double price = Double.parseDouble(priceText.trim());
                if (price <= 0)
                    return "Price must be greater than 0.";
                item.setPrice(price);
            }

            if (!quantityText.equalsIgnoreCase("KEEP")) {
                int qty = Integer.parseInt(quantityText.trim());
                if (qty < 0)
                    return "Quantity cannot be negative.";
                item.setQuantity(qty);
            }

            updateItem(item);
            return "Item updated successfully.";
        } catch (NumberFormatException e) {
            return "Invalid input format.";
        }
    }

    public boolean itemNameExists(String name, Integer excludeID) {
        String sql = "SELECT COUNT(*) FROM items WHERE LOWER(item_name) = ?";

        // If excludeID is not null, add a condition to skip that item (for updates)
        if (excludeID != null) {
            sql += " AND itemID != ?";
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name.toLowerCase().trim());

            if (excludeID != null) {
                pstmt.setInt(2, excludeID);
            }

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
