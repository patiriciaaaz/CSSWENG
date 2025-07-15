package Controller;

import Model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemController {
    private Connection conn;

    public ItemController(Connection conn) {
        this.conn = conn;
    }

    public String createItem(String name, String priceStr, String quantityStr) {
        try {
            name = name.trim();
            double price = Double.parseDouble(priceStr);
            int quantity = Integer.parseInt(quantityStr);

            if (price < 0 || quantity < 0) {
                return "Price and quantity must be non-negative.";
            }

            int itemID = new Random().nextInt(100000);
            Item item = new Item();
            item.setItemID(itemID);
            item.setItemName(name);
            item.setPrice(price);
            item.setQuantity(quantity);

            addItem(item);
            return "Item created with ID: " + itemID;
        } catch (NumberFormatException e) {
            return "Invalid input: please enter valid numbers for price and quantity.";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while creating the item.";
        }
    }

    // CREATE
    public void addItem(Item item) {
        String sql = "INSERT INTO items (itemID, item_name, price, quantity) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getItemID());
            pstmt.setString(2, item.getItemName());
            pstmt.setDouble(3, item.getPrice());
            pstmt.setInt(4, item.getQuantity());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read by ID
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

    // Read
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
}
