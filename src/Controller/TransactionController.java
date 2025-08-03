package Controller;

import Model.Item;
import Model.Sale;
import Model.Transaction;
import java.sql.*;
import java.util.List;


import javax.swing.JOptionPane;

public class TransactionController {
    private Connection conn;

    public TransactionController(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public void addTransaction(Transaction transaction, List<Sale> sales, ItemController itemController) {
        String sql = "INSERT INTO transactions (memberID, transaction_date, amount, transaction_type) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            int index = 1;
            if (transaction.getMemberID() != null) {
                stmt.setInt(index++, transaction.getMemberID());
            } else {
                stmt.setNull(index++, java.sql.Types.INTEGER);
            }
            stmt.setTimestamp(index++, transaction.getTransactionDate());
            stmt.setDouble(index++, transaction.getAmount());
            stmt.setString(index++, transaction.getTransactionType());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedID = generatedKeys.getInt(1);
                        transaction.setTransactionID(generatedID);
                    }
                }
            }

            for (Sale sale : sales) {
                int itemID = sale.getItemID();
                int quantitySold = sale.getQuantity();

                Item item = itemController.getItemByID(itemID);
                if (item != null) {
                    int updatedQuantity = item.getQuantity() - quantitySold;

                    if (updatedQuantity < 0) {
                        throw new IllegalStateException("Insufficient stock for item: " + item.getItemName());
                    }

                    item.setQuantity(updatedQuantity);
                    itemController.updateItem(item);
                }
            }

        } catch (SQLException | IllegalStateException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving transaction: " + e.getMessage());
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
                transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
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
            stmt.setTimestamp(2, transaction.getTransactionDate());
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

    // LIST ALL TRANSACTIONS
    public java.util.List<Transaction> getAllTransactions() {
        java.util.List<Transaction> transactions = new java.util.ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionID(rs.getInt("transactionID"));

                int memberID = rs.getInt("memberID");
                if (rs.wasNull()) {
                    transaction.setMemberID(null);
                } else {
                    transaction.setMemberID(memberID);
                }

                transaction.setTransactionDate(rs.getTimestamp("transaction_date"));
                transaction.setAmount(rs.getDouble("amount"));
                transaction.setTransactionType(rs.getString("transaction_type"));
                transactions.add(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    // TRANSACTION PROCESSING
    public boolean processTransaction(Integer memberID, List<Sale> cart, ItemController itemController,
            SaleController saleController) {
        try {

            double totalAmount = 0;
            for (Sale sale : cart) {
                Item item = itemController.getItemByID(sale.getItemID());
                if (item == null || item.getQuantity() < sale.getQuantity()) {
                    throw new IllegalStateException(
                            "Insufficient stock for item: " + (item != null ? item.getItemName() : "Unknown"));
                }
                totalAmount += item.getPrice() * sale.getQuantity();
            }

            Transaction transaction = new Transaction();
            transaction.setMemberID(memberID);
            transaction.setTransactionType("consumables");
            transaction.setTransactionDate(new java.sql.Timestamp(System.currentTimeMillis()));
            transaction.setAmount(totalAmount);

            addTransaction(transaction, cart, itemController);

            for (Sale s : cart) {
                s.setTransactionID(transaction.getTransactionID());
                saleController.addSale(s);
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to record transaction: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
