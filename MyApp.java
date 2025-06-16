import java.util.*;

public class MyApp {
    
    //Menu strings
    private final String menuString = """
        Welcome to MyApp! Please select an option from the menu below.
        1. Update Database
        2. Query Options
        3. View Reports

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
        app.setUserMenuInput(-1); // Initialize to an invalid option
        
        do {
            System.out.println("-----------------------------------");
            System.out.print(app.menuString);

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }

            app.setUserMenuInput(scanner.nextInt());

            switch (app.getUserMenuInput()) {
                case 1:
                    System.out.println("Option 1 selected.");
                    if (app.correctPassword("dummyPassword")) {
                        app.updateDatabaseMenu(scanner);
                    }
                    else {
                        System.out.println("Incorrect password. Returning to main menu.");
                    }
                    break;
                case 2:
                    System.out.println("Option 2 selected.");
                    break;
                case 3:
                    System.out.println("Option 3 selected.");
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
        //TODO: Password validation logic
        return true;
    }
    //menus
    private void updateDatabaseMenu(Scanner scanner) {
        System.out.println(op1MenuString);
        int op1MenuInput = -1;

        while (op1MenuInput != 0) {
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear the invalid input
            }
            op1MenuInput = scanner.nextInt();

            switch (op1MenuInput) {
                case 1:
                    System.out.println("Creating new entry...");
                    break;
                case 2:
                    System.out.println("Updating existing entry...");
                    break;
                case 3:
                    System.out.println("Deleting entry...");
                    break;
                case 0:
                    System.out.println("Returning to main menu.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}