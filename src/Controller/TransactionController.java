package Controller;

import Model.Transaction;
import java.sql.*;

public class TransactionController {
    private Connection conn;

    public TransactionController(Connection conn) {
        this.conn = conn;
    }

    //Create
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (itemID, itemName, price, quantity) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getItemID());
            stmt.setString(2, transaction.getItemName());
            stmt.setDouble(3, transaction.getPrice());
            stmt.setInt(4, transaction.getQuantity());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Read
    public Transaction getTransactionByID(int itemID) {
        String sql = "SELECT * FROM transactions WHERE itemID = ?";
        Transaction transaction = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                transaction = new Transaction();
                transaction.setItemID(rs.getInt("itemID"));
                transaction.setItemName(rs.getString("itemName"));
                transaction.setPrice(rs.getDouble("price"));
                transaction.setQuantity(rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    //Update
    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET itemName = ?, price = ?, quantity = ? WHERE itemID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, transaction.getItemName());
            stmt.setDouble(2, transaction.getPrice());
            stmt.setInt(3, transaction.getQuantity());
            stmt.setInt(4, transaction.getItemID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Delete
    public void deleteTransaction(int itemID) {
        String sql = "DELETE FROM transactions WHERE itemID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
