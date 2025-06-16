import java.util.*;

public class MyApp {
    
    //Menu strings
    private final String menuString = """
        Welcome to MyApp! Please select an option from the menu below.
        1. Option 1
        2. Option 2
        3. Option 3

        (Enter 0 to exit):
        """;
    private final String op1MenuString = """
        Option 1 Menu:
        1. Sub-option 1
        2. Sub-option 2
        3. Sub-option 3

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
}