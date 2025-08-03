package Controller;

import Model.Member;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class MemberController {
    private Connection conn;

    public MemberController(Connection conn) {
        this.conn = conn;
    }

    // Create
    public int addMember(Member member) {
        String sql = "INSERT INTO members (last_name, first_name, date_of_birth, contact_information, registration_date, signed_waiver, membership_type, membership_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, member.getLastName());
            stmt.setString(2, member.getFirstName());
            stmt.setDate(3, Date.valueOf(member.getDateOfBirth()));
            stmt.setString(4, member.getContactInformation());
            stmt.setDate(5, Date.valueOf(member.getRegistrationDate()));
            stmt.setString(6, member.getSignedWaiver());
            stmt.setString(7, member.getMembershipType());
            stmt.setString(8, member.getMembershipStatus());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedID = generatedKeys.getInt(1);
                        member.setMemberID(generatedID);
                        return generatedID;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    // Read
    public Member getMemberByID(int memberID) {
        String sql = "SELECT * FROM members WHERE memberID = ?";
        Member member = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                member = new Member();

                member.setMemberID(rs.getInt("memberID"));
                member.setLastName(rs.getString("last_name"));
                member.setFirstName(rs.getString("first_name"));
                member.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                member.setContactInformation(rs.getString("contact_information"));
                member.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                member.setSignedWaiver(rs.getString("signed_waiver"));
                member.setMembershipType(rs.getString("membership_type"));
                member.setMembershipStatus(rs.getString("membership_status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return member;
    }

    // Update
    public void updateMember(Member member) {
        String sql = "UPDATE members SET last_name = ?, first_name = ?, date_of_birth = ?, contact_information = ?, registration_date = ?, signed_waiver = ?, membership_type = ?, membership_status = ? WHERE memberID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, member.getLastName());
            stmt.setString(2, member.getFirstName());
            stmt.setDate(3, Date.valueOf(member.getDateOfBirth()));
            stmt.setString(4, member.getContactInformation());
            stmt.setDate(5, Date.valueOf(member.getRegistrationDate()));
            stmt.setString(6, member.getSignedWaiver());
            stmt.setString(7, member.getMembershipType());
            stmt.setString(8, member.getMembershipStatus());
            stmt.setInt(9, member.getMemberID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public boolean deleteMember(int memberID) {
        String sql = "DELETE FROM members WHERE memberID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // returns true if a row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Read all Members
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Member member = new Member();
                member.setMemberID(rs.getInt("memberID"));
                member.setLastName(rs.getString("last_name"));
                member.setFirstName(rs.getString("first_name"));
                member.setContactInformation(rs.getString("contact_information"));
                member.setMembershipType(rs.getString("membership_type"));
                member.setMembershipStatus(rs.getString("membership_status"));

                member.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                member.setRegistrationDate(rs.getDate("registration_date").toLocalDate());
                member.setSignedWaiver(rs.getString("signed_waiver"));

                members.add(member);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    public String createNewMember(String firstName, String lastName, String dobText, String contactInfo,
            String regDateText, String waiver, String type, String status) {
        if (firstName.isBlank() || lastName.isBlank()) {
            return "First name and last name cannot be empty.";
        }

        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate dob = LocalDate.parse(dobText.trim(), formatter);
            LocalDate regDate = LocalDate.parse(regDateText.trim(), formatter);

            Member member = new Member();
            member.setFirstName(firstName.trim());
            member.setLastName(lastName.trim());
            member.setDateOfBirth(dob);
            member.setContactInformation(contactInfo.trim());
            member.setRegistrationDate(regDate);
            member.setSignedWaiver(waiver.trim());
            member.setMembershipType(type.trim());
            member.setMembershipStatus(status.trim());

            int generatedID = addMember(member);
            if (generatedID > 0)
                return "Member created with ID: " + generatedID;
            else
                return "Failed to create member.";

        } catch (DateTimeParseException e) {
            return "Invalid date format. Please use YYYY-MM-DD.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Unexpected error: " + e.getMessage();
        }
    }

    public String updateExistingMember(Member member, String newFirstName, String newLastName, LocalDate dobText,
            String contactInfo, LocalDate regDateText, String waiver, String type, String status) {
        if (!newFirstName.equalsIgnoreCase("KEEP")) {
            member.setFirstName(newFirstName.trim());
        }
        if (!newLastName.equalsIgnoreCase("KEEP")) {
            member.setLastName(newLastName.trim());
        }

        try {
            if (dobText != null) {
                member.setDateOfBirth(dobText);
            }
            if (!contactInfo.equalsIgnoreCase("KEEP")) {
                member.setContactInformation(contactInfo.trim());
            }
            if (regDateText != null) {
                member.setRegistrationDate(regDateText);
            }
            if (!waiver.equalsIgnoreCase("KEEP")) {
                member.setSignedWaiver(waiver.trim());
            }
            if (!type.equalsIgnoreCase("KEEP")) {
                member.setMembershipType(type.trim());
            }
            if (!status.equalsIgnoreCase("KEEP")) {
                member.setMembershipStatus(status.trim());
            }

            updateMember(member);
            return "Member updated successfully.";

        } catch (Exception e) {
            return "Invalid input format.";
        }
    }

    public boolean memberExists(int memberID) {
        String sql = "SELECT 1 FROM members WHERE memberID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    

    
}
