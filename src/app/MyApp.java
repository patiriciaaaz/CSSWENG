package app;

import Controller.DBConnector;
import Controller.ItemController;
import Controller.SaleController;
import Controller.TransactionController;
import Model.Item;
import Model.Sale;
import Model.Transaction;
import View.MainMenu;
import View.UpdateDatabaseMenu.UpdateDatabaseView;

import java.sql.Connection;
import java.util.*;

import javax.swing.JOptionPane;

public class MyApp {

    public static void main(String[] args) {

        DBConnector db = new DBConnector();
        Connection conn = db.getConnection();
        ItemController itemController = new ItemController(conn);
        SaleController saleController = new SaleController(conn);
        TransactionController transactionController = new TransactionController(conn);

        javax.swing.SwingUtilities.invokeLater(() -> {
            showMainMenu();

        });
    }

    public static void showMainMenu() {
        MainMenu menu = new MainMenu();
        menu.setVisible(true);

        DBConnector db = new DBConnector();
        Connection conn = db.getConnection();

        ItemController itemController = new ItemController(conn);
        SaleController saleController = new SaleController(conn);
        TransactionController transactionController = new TransactionController(conn);

        menu.addUpdateDbListener(e -> {
            new View.UpdateDatabaseMenu.UpdateDatabaseView(itemController).setVisible(true);
            menu.dispose();
        });

        menu.addQueryOptionsListener(e -> {
            System.out.println("Query Options selected");
        });

        menu.addViewReportsListener(e -> {
            System.out.println("View Reports selected");
        });

        menu.addTransactionsListener(e -> {
            System.out.println("Transactions selected");
        });
    }

}
