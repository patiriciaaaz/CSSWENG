package Controller;

import Model.Sale;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleController {
    private Connection conn;

    public SaleController(Connection conn) {
        this.conn = conn;
    }

    // Create
    public void addSale(Sale sale) {
        String sql = "INSERT INTO sales (transactionID, itemID, quantity) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sale.getTransactionID());
            stmt.setInt(2, sale.getItemID());
            stmt.setInt(3, sale.getQuantity());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read
    public Sale getSaleByID(int salesID) {
        String sql = "SELECT * FROM sales WHERE salesID = ?";
        Sale sale = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, salesID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                sale = new Sale();
                sale.setSalesID(rs.getInt("salesID"));
                sale.setTransactionID(rs.getInt("transactionID"));
                sale.setItemID(rs.getInt("itemID"));
                sale.setQuantity(rs.getInt("quantity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sale;
    }

    // Update
    public void updateSale(Sale sale) {
        String sql = "UPDATE sales SET transactionID = ?, itemID = ?, quantity = ? WHERE salesID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sale.getTransactionID());
            stmt.setInt(2, sale.getItemID());
            stmt.setInt(3, sale.getQuantity());
            stmt.setInt(4, sale.getSalesID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public void deleteSale(int salesID) {
        String sql = "DELETE FROM sales WHERE salesID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, salesID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Sale> getAllSales() {
        List<Sale> salesList = new ArrayList<>();
        String sql = "SELECT * FROM sales";

        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sale sale = new Sale();
                sale.setSalesID(rs.getInt("salesID"));
                sale.setTransactionID(rs.getInt("transactionID"));
                sale.setItemID(rs.getInt("itemID"));
                sale.setQuantity(rs.getInt("quantity"));
                salesList.add(sale);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesList;
    }
}
