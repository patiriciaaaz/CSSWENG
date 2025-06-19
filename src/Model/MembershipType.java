package Model;

public class MembershipType {
    private String membershipType;
    private double price;

    // Getters
    public String getMembershipType() {
         return membershipType; 
    }
    
    public double getPrice() {
        return price; 
    }

    // Setters
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType; 
    }

    public void setPrice(double price) {
        this.price = price; 
    }
}
