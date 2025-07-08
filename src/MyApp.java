import Controller.DBConnector;
import Controller.ItemController;
import Controller.SaleController;
import Controller.TransactionController;
import Model.Item;
import Model.Sale;
import Model.Transaction;

import java.sql.Connection;
import java.util.*;

public class MyApp {
    private final String menuString = """
            Welcome to MyApp! Please select an option from the menu below.
            1. Update Database
            2. Query Options
            3. View Reports
            4. Transactions

            (Enter 0 to exit):
            """;
    private final String op1MenuString = """
            Update Database Menu:
            1. Create New Entry
            2. Update Existing Entry
            3. Delete Entry

            (Enter 0 to return to main menu):
            """;

    private int userMenuInput;

    public int getUserMenuInput() {
        return userMenuInput;
    }

    public void setUserMenuInput(int userMenuInput) {
        this.userMenuInput = userMenuInput;
    }

    public static void main(String[] args) {
        System.out.println("Running MyApp...");
        MyApp app = new MyApp();
        Scanner scanner = new Scanner(System.in);
        app.setUserMenuInput(-1);

        // Initialize DB connection and controller
        DBConnector db = new DBConnector();
        Connection conn = db.getConnection();
        ItemController itemController = new ItemController(conn);
        SaleController saleController = new SaleController(conn);
        TransactionController transactionController = new TransactionController(conn);

        do {
            System.out.println("-----------------------------------");
            System.out.print(app.menuString);

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }

            app.setUserMenuInput(scanner.nextInt());

            switch (app.getUserMenuInput()) {
                case 1:
                    System.out.println("Option 1 selected.");
                    if (app.correctPassword("dummyPassword")) {
                        app.updateDatabaseMenu(scanner, itemController);
                    } else {
                        System.out.println("Incorrect password. Returning to main menu.");
                    }
                    break;
                case 2:
                    System.out.println("Option 2 selected.");
                    app.queryOptionsMenu(scanner, itemController);
                    break;
                case 3:
                    System.out.println("Option 3 selected.");
                    break;
                case 4:
                    app.transactionSaleMenu(scanner, transactionController, saleController, itemController);
                    break;
                case 0:
                    System.out.println("Exit Option Selected.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
            System.out.println("-----------------------------------");
        } while (app.getUserMenuInput() != 0);

        scanner.close();
        System.out.println("Exiting MyApp.");
    }

    public boolean correctPassword(String password) {
        return true; // TODO: add actual password logic
    }

    // Update Menu (uses ItemController)
    public void updateDatabaseMenu(Scanner scanner, ItemController itemController) {
        int op1MenuInput = -1;

        while (op1MenuInput != 0) {
            System.out.print(op1MenuString);

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }

            op1MenuInput = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (op1MenuInput) {
                case 1 -> {
                    // Create Item
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();

                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // clear newline

                    int itemID = new Random().nextInt(100000);
                    Item item = new Item();
                    item.setItemID(itemID);
                    item.setItemName(name);
                    item.setPrice(price);
                    item.setQuantity(quantity);

                    itemController.addItem(item);
                    System.out.println("Item added.");
                }
                case 2 -> {
                    // Update Item
                    System.out.print("Enter item ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    Item item = itemController.getItemByID(id);
                    if (item == null) {
                        System.out.println("Item not found.");
                        break;
                    }

                    System.out.print("Enter new name (blank to keep): ");
                    String newName = scanner.nextLine();
                    if (!newName.isBlank())
                        item.setItemName(newName);

                    System.out.print("Enter new price (-1 to keep): ");
                    double newPrice = scanner.nextDouble();
                    if (newPrice >= 0)
                        item.setPrice(newPrice);

                    System.out.print("Enter new quantity (-1 to keep): ");
                    int newQty = scanner.nextInt();
                    if (newQty >= 0)
                        item.setQuantity(newQty);

                    itemController.updateItem(item);
                    System.out.println("Item updated.");
                    scanner.nextLine();
                }
                case 3 -> {
                    // Delete Item
                    System.out.print("Enter item ID to delete: ");
                    int id = scanner.nextInt();
                    itemController.deleteItem(id);
                    System.out.println("Item deleted.");
                    scanner.nextLine();
                }

                case 0 -> System.out.println("Returning to main menu.");
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void queryOptionsMenu(Scanner scanner, ItemController itemController) {
        String queryMenu = """
                Query Options Menu:
                1. View All Items

                (Enter 0 to return to main menu):
                """;

        int input = -1;
        while (input != 0) {
            System.out.println(queryMenu);
            System.out.print("Enter option: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }

            input = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (input) {
                case 1 -> {
                    List<Item> items = itemController.getAllItems();
                    if (items.isEmpty()) {
                        System.out.println("No items found.");
                    } else {
                        System.out.println("All Items in Inventory:");
                        for (Item item : items) {
                            System.out.println("-----------------------------");
                            System.out.println("ID: " + item.getItemID());
                            System.out.println("Name: " + item.getItemName());
                            System.out.println("Price: " + item.getPrice());
                            System.out.println("Quantity: " + item.getQuantity());
                        }
                        System.out.println("-----------------------------");
                    }
                }

                case 0 -> System.out.println("Returning to main menu.");

                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void transactionSaleMenu(Scanner scanner, TransactionController transactionController,
            SaleController saleController, ItemController itemController) {
        Random rand = new Random();

        // 1. Create Transaction
        int transactionID = rand.nextInt(100000);
        Integer memberID = null;

        scanner.nextLine();
        System.out.print("Enter member ID (or leave blank if not applicable): ");
        String memberInput = scanner.nextLine().trim();

        if (!memberInput.isEmpty()) {
            try {
                memberID = Integer.parseInt(memberInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Proceeding with no member ID.");
            }
        }

        System.out.print("Enter transaction type: (membership_purchase,membership_renewal,consumables,merchandise) ");
        String transactionType = scanner.nextLine();

        double totalAmount = 0.0;
        List<Sale> salesList = new ArrayList<>();

        // 2. Add Sale Items
        while (true) {
            System.out.print("Enter item ID to add to sale (or 0 to finish): ");
            int itemID = scanner.nextInt();
            if (itemID == 0)
                break;

            Item item = itemController.getItemByID(itemID);
            if (item == null) {
                System.out.println("Item not found.");
                continue;
            }

            System.out.print("Enter quantity: ");
            int qty = scanner.nextInt();
            scanner.nextLine();

            Sale sale = new Sale();
            sale.setSalesID(rand.nextInt(100000));
            sale.setTransactionID(transactionID);
            sale.setItemID(itemID);
            sale.setQuantity(qty);

            salesList.add(sale);
            totalAmount += item.getPrice() * qty;

            System.out.println("Added: " + item.getItemName() + " x" + qty);
        }

        scanner.nextLine(); // consume newline

        // 3. Save Transaction
        Transaction transaction = new Transaction();
        transaction.setTransactionID(transactionID);
        transaction.setMemberID(memberID);
        transaction.setTransactionDate(new java.sql.Date(System.currentTimeMillis()));
        transaction.setAmount(totalAmount);
        transaction.setTransactionType(transactionType);

        transactionController.addTransaction(transaction);

        // 4. Save Sales
        for (Sale s : salesList) {
            saleController.addSale(s);
        }

        System.out.println("Transaction and sales saved successfully!");
    }

}
