package Controller;

import Model.Member;
import java.sql.*;
import java.time.LocalDate;

public class MemberController {
    private Connection conn;

    public MemberController(Connection conn) {
        this.conn = conn;
    }

    //Create
    public int addMember(Member member) {
        String sql = "INSERT INTO members (memberID, lastName, firstName, dateOfBirth, contactInformation, registrationDate, signedWaiver, membershipType, membershipStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, member.getMemberID());
            stmt.setString(2, member.getLastName());
            stmt.setString(3, member.getFirstName());
            stmt.setDate(4, Date.valueOf(member.getDateOfBirth()));
            stmt.setString(5, member.getContactInformation());
            stmt.setDate(6, Date.valueOf(member.getRegistrationDate()));
            stmt.setString(7, member.getSignedWaiver());
            stmt.setString(8, member.getMembershipType());
            stmt.setString(9, member.getMembershipStatus());

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

    //Read
    public Member getMemberByID(int memberID) {
        String sql = "SELECT * FROM members WHERE memberID = ?";
        Member member = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                member.setMemberID(rs.getInt("memberID"));
                member.setLastName(rs.getString("lastName"));
                member.setFirstName(rs.getString("firstName"));
                member.setDateOfBirth(rs.getDate("dateOfBirth").toLocalDate());
                member.setContactInformation(rs.getString("contactInformation"));
                member.setRegistrationDate(rs.getDate("registrationDate").toLocalDate());
                member.setSignedWaiver(rs.getString("signedWaiver"));
                member.setMembershipType(rs.getString("membershipType"));
                member.setMembershipStatus(rs.getString("membershipStatus"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return member;
    }

    //Update
    public void updateMember(Member member) {
        String sql = "UPDATE members SET lastName = ?, firstName = ?, dateOfBirth = ?, contactInformation = ?, registrationDate = ?, signedWaiver = ?, membershipType = ?, membershipStatus = ? WHERE memberID = ?";

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

    //Delete
    public void deleteMember(int memberID) {
        String sql = "DELETE FROM members WHERE memberID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, memberID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String createNewMember(String firstName, String lastName, String dobText, String contactInfo, String regDateText, String waiver, String type, String status) {
        if (firstName.isBlank() || lastName.isBlank()) {
            return "First name and last name cannot be empty.";
        }

        if (memberNameExists(firstName, lastName, null)) {
            return "Member with the same name already exists.";
        }

        try {
            LocalDate dob = LocalDate.parse(dobText.trim());
            LocalDate regDate = LocalDate.parse(regDateText.trim());

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

        } catch (Exception e) {
            return "Invalid date format. Use YYYY-MM-DD.";
        }
    }

    public String updateExistingMember(Member member, String newFirstName, String newLastName, String dobText, String contactInfo, String regDateText, String waiver, String type, String status) {
        if (!newFirstName.equalsIgnoreCase("KEEP")) {
            member.setFirstName(newFirstName.trim());
        }
        if (!newLastName.equalsIgnoreCase("KEEP")) {
            member.setLastName(newLastName.trim());
        }

        if (memberNameExists(member.getFirstName(), member.getLastName(), member.getMemberID())) {
            return "Another member with this name already exists.";
        }

        try {
            if (!dobText.equalsIgnoreCase("KEEP")) {
                member.setDateOfBirth(LocalDate.parse(dobText.trim()));
            }
            if (!contactInfo.equalsIgnoreCase("KEEP")) {
                member.setContactInformation(contactInfo.trim());
            }
            if (!regDateText.equalsIgnoreCase("KEEP")) {
                member.setRegistrationDate(LocalDate.parse(regDateText.trim()));
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

    public boolean memberNameExists(String firstName, String lastName, Integer excludeID) {
        String sql = "SELECT COUNT(*) FROM members WHERE LOWER(firstName) = ? AND LOWER(lastName) = ?";
        if (excludeID != null) {
            sql += " AND memberID != ?";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName.toLowerCase().trim());
            stmt.setString(2, lastName.toLowerCase().trim());

            if (excludeID != null) {
                stmt.setInt(3, excludeID);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
