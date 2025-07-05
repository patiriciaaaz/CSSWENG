package Controller;

import Model.Member;
import java.sql.*;

public class MemberController {
    private Connection conn;

    public MemberController(Connection conn) {
        this.conn = conn;
    }

    //Create
    public void addMember(Member member) {
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

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

}
