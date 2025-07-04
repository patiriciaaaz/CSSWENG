package Controller;

import Model.Item;
import java.sql.*;

public class ItemController {
    private Connection conn;

    public ItemController(Connection conn) {
        this.conn = conn;
    }

    //Create
    public void addItem(Item item) {
        String sql = "INSERT INTO items (itemID, itemName, price, quantity) VALUES (?, ?, ?, ?)";

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

    //Read
    public Item getItemByID(int itemID) {
        String sql = "SELECT * FROM items WHERE itemID = ?";
        Item item = null;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                
                item = new Item();
                item.setItemID(rs.getInt("itemID"));
                item.setItemName(rs.getString("itemName"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    //Update
    public void updateItem(Item item) {
        String sql = "UPDATE items SET itemName = ?, price = ?, quantity = ? WHERE itemID = ?";

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

    //Delete
    public void deleteItem(int itemID) {
        String sql = "DELETE FROM items WHERE itemID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, itemID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
