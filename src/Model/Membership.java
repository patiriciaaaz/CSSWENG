package Model;

public class Membership {
    private int membershipID;
    private int memberID;
    private String membershipType;

    // Getters
    public int getMembershipID() { 
        return membershipID; 
    }

    public int getMemberID() { 
        return memberID; 
    }

    public String getMembershipType() { 
        return membershipType; 
    }


    // Setters
    public void setMembershipID(int membershipID) {
         this.membershipID = membershipID; 
    }

    public void setMemberID(int memberID) {
         this.memberID = memberID; 
    }

    public void setMembershipType(String membershipType) {
         this.membershipType = membershipType; 
    }

}
