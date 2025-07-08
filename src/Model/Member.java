package Model;

import java.time.LocalDate;

public class Member {
    private Integer memberID;
    private String lastName;
    private String firstName;
    private LocalDate dateOfBirth;
    private String contactInformation;
    private LocalDate registrationDate;
    private String signedWaiver; // "TRUE" or "FALSE"
    private String membershipType;
    private String membershipStatus;

    // Getters
    public Integer getMemberID() {
         return memberID; 
    }

    public String getLastName() {
         return lastName; 
    }

    public String getFirstName() {
         return firstName; 
    }

    public LocalDate getDateOfBirth() {
         return dateOfBirth; 
    }

    public String getContactInformation() {
         return contactInformation; 
    }

    public LocalDate getRegistrationDate() {
         return registrationDate; 
    }

    public String getSignedWaiver() {
         return signedWaiver; 
    }

    public String getMembershipType() {
         return membershipType; 
    }

    public String getMembershipStatus() 
    {
         return membershipStatus;
    }


    // Setters
    public void setMemberID(Integer memberID) {
         this.memberID = memberID; 
    }
    
    public void setLastName(String lastName) {
         this.lastName = lastName; 
    }
    
    public void setFirstName(String firstName) {
         this.firstName = firstName; 
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
         this.dateOfBirth = dateOfBirth; 
    }
    
    public void setContactInformation(String contactInformation) {
         this.contactInformation = contactInformation; 
    }
    
    public void setRegistrationDate(LocalDate registrationDate) {
         this.registrationDate = registrationDate; 
    }
    
    public void setSignedWaiver(String signedWaiver) {
         this.signedWaiver = signedWaiver; 
    }
    
    public void setMembershipType(String membershipType) {
         this.membershipType = membershipType; 
    }
    
    public void setMembershipStatus(String membershipStatus) {
         this.membershipStatus = membershipStatus; 
    }
    
}
