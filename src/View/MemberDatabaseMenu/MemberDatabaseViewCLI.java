package View.MemberDatabaseMenu;

import Controller.MemberController;
import Controller.DBConnector;
import Model.Member;

import java.sql.Connection;
import java.util.Scanner;


public class MemberDatabaseViewCLI {
    public static void main(String[] args) {
        // Initialize DB connection and controller
        DBConnector db = new DBConnector();
        Connection conn = db.getConnection();
        MemberController memberController = new MemberController(conn);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Member Management CLI ---");
            System.out.println("1. Add Member");
            System.out.println("2. Get Member by ID");
            System.out.println("3. Update Member");
            System.out.println("4. Delete Member");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("First Name: ");
                    String firstName = sc.nextLine();
                    System.out.print("Last Name: ");
                    String lastName = sc.nextLine();
                    System.out.print("Date of Birth (YYYY-MM-DD): ");
                    String dob = sc.nextLine();
                    System.out.print("Contact Information: ");
                    String contact = sc.nextLine();
                    System.out.print("Registration Date (YYYY-MM-DD): ");
                    String regDate = sc.nextLine();
                    System.out.print("Signed Waiver (TRUE / FALSE): ");
                    String waiver = sc.nextLine();
                    System.out.print("Membership Type (NORMAL / ? / ?): ");
                    String type = sc.nextLine();
                    System.out.print("Membership Status (ACTIVE / INACTIVE): ");
                    String status = sc.nextLine();

                    String result = memberController.createNewMember(firstName, lastName, dob, contact, regDate, waiver, type, status);
                    System.out.println(result);
                    break;

                case "2":
                    System.out.print("Enter Member ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    Member member = memberController.getMemberByID(id);
                    if (member != null) {
                        System.out.println("ID: " + member.getMemberID());
                        System.out.println("Name: " + member.getFirstName() + " " + member.getLastName());
                        System.out.println("DOB: " + member.getDateOfBirth());
                        System.out.println("Contact: " + member.getContactInformation());
                        System.out.println("Registered: " + member.getRegistrationDate());
                        System.out.println("Waiver: " + member.getSignedWaiver());
                        System.out.println("Type: " + member.getMembershipType());
                        System.out.println("Status: " + member.getMembershipStatus());
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;

                case "3":
                    System.out.print("Enter Member ID to update: ");
                    int updateID = Integer.parseInt(sc.nextLine());
                    Member memberToUpdate = memberController.getMemberByID(updateID);
                    if (memberToUpdate == null) {
                        System.out.println("Member not found.");
                        break;
                    }

                    System.out.println("Enter new values or type 'KEEP' to retain current value.");
                    System.out.print("First Name: ");
                    String newFirst = sc.nextLine();
                    System.out.print("Last Name: ");
                    String newLast = sc.nextLine();
                    System.out.print("Date of Birth (YYYY-MM-DD): ");
                    String newDob = sc.nextLine();
                    System.out.print("Contact Information: ");
                    String newContact = sc.nextLine();
                    System.out.print("Registration Date (YYYY-MM-DD): ");
                    String newRegDate = sc.nextLine();
                    System.out.print("Signed Waiver: ");
                    String newWaiver = sc.nextLine();
                    System.out.print("Membership Type: ");
                    String newType = sc.nextLine();
                    System.out.print("Membership Status: ");
                    String newStatus = sc.nextLine();

                    String updateResult = memberController.updateExistingMember(memberToUpdate, newFirst, newLast, newDob, newContact, newRegDate, newWaiver, newType, newStatus);
                    System.out.println(updateResult);
                    break;

                case "4":
                    System.out.print("Enter Member ID to delete: ");
                    int deleteID = Integer.parseInt(sc.nextLine());
                    memberController.deleteMember(deleteID);
                    System.out.println("Member deleted.");
                    break;

                case "5":
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }
}