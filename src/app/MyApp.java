package app;

import Controller.DBConnector;
import Controller.ItemController;
import Controller.MemberController;
import Controller.SaleController;
import Controller.TransactionController;
import View.MainMenu;
import View.MemberDatabaseMenu.MemberDatabaseView;
import View.ReportsMenu.ViewReportsView;
import View.TransactionsMenu.TransactionsView;
import java.sql.Connection;

public class MyApp {

    public static void main(String[] args) {

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
        MemberController memberController = new MemberController(conn);

        menu.addUpdateDbListener(e -> {
            new View.UpdateDatabaseMenu.UpdateDatabaseView(itemController).setVisible(true);
            menu.dispose();
        });

        menu.addMemberDbListener(e -> {
            new MemberDatabaseView(memberController).setVisible(true);
            menu.dispose();
        });

        menu.addViewReportsListener(e -> {
            new ViewReportsView(transactionController, saleController, itemController).setVisible(true);
            menu.dispose();
        });

        menu.addTransactionsListener(e -> {
            new TransactionsView(transactionController, saleController, itemController).setVisible(true);
            menu.dispose();
        });
    }

}
