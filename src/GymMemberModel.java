import java.util.Date;

public class GymMemberModel {

    //Member details
    private int id;
    private String lastName;
    private String firstName;
    private int age;

    private String email;
    private String phoneNumber;

    //Membership details
    private Date memberSinceDate;
    private Date lastRenewDate;
    private String membershipType;
    private String membershipStatus;

    
    //Constructor
    public GymMemberModel(int id, String lastName, String firstName, int age, String email, String phoneNumber,
                          Date memberSinceDate, Date lastRenewDate, String membershipType, String membershipStatus) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.memberSinceDate = memberSinceDate;
        this.lastRenewDate = lastRenewDate;
        this.membershipType = membershipType;
        this.membershipStatus = membershipStatus;
    }

    //Constructor without ID (for new members)
    public GymMemberModel(String lastName, String firstName, int age, String email, String phoneNumber,
                          Date memberSinceDate, Date lastRenewDate, String membershipType, String membershipStatus) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.memberSinceDate = memberSinceDate;
        this.lastRenewDate = lastRenewDate;
        this.membershipType = membershipType;
        this.membershipStatus = membershipStatus;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Date getMemberSinceDate() {
        return memberSinceDate;
    }
    public void setMemberSinceDate(Date memberSinceDate) {
        this.memberSinceDate = memberSinceDate;
    }
    public Date getLastRenewDate() {
        return lastRenewDate;
    }
    public void setLastRenewDate(Date lastRenewDate) {
        this.lastRenewDate = lastRenewDate;
    }
    public String getMembershipType() {
        return membershipType;
    }
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
    public String getMembershipStatus() {
        return membershipStatus;
    }
    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }
    
    @Override
    public String toString() {
        return "GymMemberModel{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", memberSinceDate=" + memberSinceDate +
                ", lastRenewDate=" + lastRenewDate +
                ", membershipType='" + membershipType + '\'' +
                ", membershipStatus='" + membershipStatus + '\'' +
                '}';
    }
    
}
