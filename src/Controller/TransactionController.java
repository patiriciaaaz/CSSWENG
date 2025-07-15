package Controller;

import Model.Transaction;
import java.sql.*;

public class TransactionController {
    private Connection conn;

    public TransactionController(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (transactionID, memberID, transaction_date, amount, transaction_type) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getTransactionID());
            if (transaction.getMemberID() != null) {
                stmt.setInt(2, transaction.getMemberID());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setDate(3, transaction.getTransactionDate()); // java.sql.Date
            stmt.setDouble(4, transaction.getAmount());
            stmt.setString(5, transaction.getTransactionType());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // READ
    public Transaction getTransactionByID(int transactionID) {
        String sql = "SELECT * FROM transactions WHERE transactionID = ?";
        Transaction transaction = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                transaction = new Transaction();
                transaction.setTransactionID(rs.getInt("transactionID"));
                transaction.setMemberID(rs.getInt("memberID"));
                transaction.setTransactionDate(rs.getDate("transaction_date"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransactionType(rs.getString("transaction_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaction;
    }

    // UPDATE
    public void updateTransaction(Transaction transaction) {
        String sql = "UPDATE transactions SET memberID = ?, transaction_date = ?, amount = ?, transaction_type = ? " +
                "WHERE transactionID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getMemberID());
            stmt.setDate(2, transaction.getTransactionDate());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setString(4, transaction.getTransactionType());
            stmt.setInt(5, transaction.getTransactionID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DELETE
    public void deleteTransaction(int transactionID) {
        String sql = "DELETE FROM transactions WHERE transactionID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
