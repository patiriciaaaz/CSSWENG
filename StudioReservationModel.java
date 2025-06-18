import java.util.Date;
import java.util.List;

public class StudioReservationModel {
    private int id;
    private int memberId; // Links to GymMemberModel
    private String customerName; // For non-members
    private String contactNumber;
    private String eventName;
    private String eventType; // Class/Training/Rehearsal/Event
    private Date startDateTime;
    private Date endDateTime;
    private double fee;
    private String paymentMethod; // Cash/GCash/BankTransfer
    private boolean isPaid;
    private String status; // Pending/Confirmed/Cancelled/Completed
    private String notes;
    private Date bookingDate; // When reservation was made
    private String staffName; // Who processed the booking

    // Constructors
    public StudioReservationModel() {
    }

    public StudioReservationModel(int id, int memberId, String customerName, String contactNumber,
            String eventName, String eventType, Date startDateTime, Date endDateTime,
            double fee, String paymentMethod, boolean isPaid, String status,
            String notes, Date bookingDate, String staffName) {
        this.id = id;
        this.memberId = memberId;
        this.customerName = customerName;
        this.contactNumber = contactNumber;
        this.eventName = eventName;
        this.eventType = eventType;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.fee = fee;
        this.paymentMethod = paymentMethod;
        this.isPaid = isPaid;
        this.status = status;
        this.notes = notes;
        this.bookingDate = bookingDate;
        this.staffName = staffName;
    }

    // Getters (with consistent spacing)
    public int getId() {
        return id;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public double getFee() {
        return fee;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public String getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public String getStaffName() {
        return staffName;
    }

    // Setters (with consistent spacing)
    public void setId(int id) {
        this.id = id;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName != null ? customerName.trim() : "";
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber != null ? contactNumber.trim() : "";
    }

    public void setEventName(String eventName) {
        this.eventName = eventName != null ? eventName.trim() : "";
    }

    public void setEventType(String eventType) {
        if (eventType != null && List.of("Class", "Training", "Rehearsal", "Event").contains(eventType)) {
            this.eventType = eventType;
        } else {
            this.eventType = "Class"; // Default value
        }
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime != null ? startDateTime : new Date();
    }

    public void setEndDateTime(Date endDateTime) {
        if (endDateTime != null && endDateTime.after(this.startDateTime)) {
            this.endDateTime = endDateTime;
        }
    }

    public void setFee(double fee) {
        this.fee = fee >= 0 ? fee : 0;
    }

    public void setPaymentMethod(String paymentMethod) {
        if (paymentMethod != null && List.of("Cash", "GCash", "BankTransfer").contains(paymentMethod)) {
            this.paymentMethod = paymentMethod;
        } else {
            this.paymentMethod = "Cash"; // Default value
        }
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public void setStatus(String status) {
        if (status != null && List.of("Pending", "Confirmed", "Cancelled", "Completed").contains(status)) {
            this.status = status;
        } else {
            this.status = "Pending"; // Default value
        }
    }

    public void setNotes(String notes) {
        this.notes = notes != null ? notes.trim() : "";
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate != null ? bookingDate : new Date();
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName != null ? staffName.trim() : "";
    }

    // Business Logic Methods
    public boolean isOverlapping(Date newStart, Date newEnd) {
        return !newEnd.before(this.startDateTime) && !newStart.after(this.endDateTime);
    }

    public long getDurationHours() {
        if (endDateTime == null || startDateTime == null)
            return 0;
        return (endDateTime.getTime() - startDateTime.getTime()) / (1000 * 60 * 60);
    }

    public boolean canBeCancelled() {
        return !status.equals("Completed") && !status.equals("Cancelled");
    }

    @Override
    public String toString() {
        return String.format(
                "Reservation #%d: %s (%s) from %s to %s | Status: %s | Paid: %s",
                id,
                eventName,
                eventType,
                startDateTime.toString(),
                endDateTime.toString(),
                status,
                isPaid ? "Yes" : "No");
    }
}