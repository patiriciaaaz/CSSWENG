import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class TransactionModel {
    private int id;
    private Date transactionDate;
    private String transactionType; // Membership/Product/Studio
    private List<TransactionItem> items;
    private double totalAmount;
    private double amountPaid;
    private String paymentMethod; // Cash/GCash/Credit/Debit/BankTransfer
    private Integer memberId; // Nullable for non-member transactions
    private String receiptNumber;
    private String staffName;
    private String status; // Completed/Refunded/Voided

    // Nested class for line items
    public static class TransactionItem {
        private String itemId;
        private String description;
        private int quantity;
        private double unitPrice;
        private String itemType; // Membership/Product/Service

        // Getters
        public String getItemId() {
            return itemId;
        }

        public String getDescription() {
            return description;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public String getItemType() {
            return itemType;
        }

        // Setters
        public void setItemId(String itemId) {
            this.itemId = itemId != null ? itemId.trim() : "";
        }

        public void setDescription(String description) {
            this.description = description != null ? description.trim() : "";
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity > 0 ? quantity : 1;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice >= 0 ? unitPrice : 0;
        }

        public void setItemType(String itemType) {
            if (itemType != null && Arrays.asList("Membership", "Product", "Service").contains(itemType)) {
                this.itemType = itemType;
            } else {
                this.itemType = "Product";
            }
        }

        // Business Logic
        public double getLineTotal() {
            return quantity * unitPrice;
        }

        @Override
        public String toString() {
            return String.format("%s x%d @ ₱%.2f = ₱%.2f",
                    description, quantity, unitPrice, getLineTotal());
        }
    }

    // Constructors
    public TransactionModel() {
        this.items = new ArrayList<>();
        this.transactionDate = new Date();
        this.status = "Completed";
    }

    public TransactionModel(int id, Date transactionDate, String transactionType,
            List<TransactionItem> items, String paymentMethod,
            Integer memberId, String staffName) {
        this();
        this.id = id;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
        this.items = items != null ? items : new ArrayList<>();
        this.paymentMethod = paymentMethod;
        this.memberId = memberId;
        this.staffName = staffName;
        calculateTotal();
    }

    // Getters
    public int getId() {
        return id;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public List<TransactionItem> getItems() {
        return new ArrayList<>(items); // Return copy for encapsulation
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate != null ? transactionDate : new Date();
    }

    public void setTransactionType(String transactionType) {
        if (transactionType != null && Arrays.asList("Membership", "Product", "Studio").contains(transactionType)) {
            this.transactionType = transactionType;
        } else {
            this.transactionType = "Product";
        }
    }

    public void setItems(List<TransactionItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        calculateTotal();
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid >= 0 ? amountPaid : 0;
    }

    public void setPaymentMethod(String paymentMethod) {
        if (paymentMethod != null &&
                Arrays.asList("Cash", "GCash", "Credit", "Debit", "BankTransfer").contains(paymentMethod)) {
            this.paymentMethod = paymentMethod;
        } else {
            this.paymentMethod = "Cash";
        }
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber != null ? receiptNumber.trim() : "";
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName != null ? staffName.trim() : "";
    }

    public void setStatus(String status) {
        if (status != null && Arrays.asList("Completed", "Refunded", "Voided").contains(status)) {
            this.status = status;
        }
    }

    // Business Logic Methods
    public void addItem(TransactionItem item) {
        if (item != null) {
            items.add(item);
            calculateTotal();
        }
    }

    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            calculateTotal();
        }
    }

    private void calculateTotal() {
        totalAmount = items.stream()
                .mapToDouble(TransactionItem::getLineTotal)
                .sum();
    }

    public double getChangeDue() {
        return amountPaid - totalAmount;
    }

    public boolean isPaymentComplete() {
        return amountPaid >= totalAmount;
    }

    public boolean canBeRefunded() {
        return status.equals("Completed") &&
                transactionDate.after(new Date(System.currentTimeMillis() - (30L * 24 * 60 * 60 * 1000))); // Within 30
                                                                                                           // days
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Transaction #%d (%s)\n", id, transactionType));
        sb.append(String.format("Date: %s | Staff: %s\n", transactionDate.toString(), staffName));
        sb.append(String.format("Payment: %s | Status: %s\n", paymentMethod, status));

        if (memberId != null) {
            sb.append(String.format("Member ID: %d\n", memberId));
        }

        sb.append("\nItems:\n");
        for (TransactionItem item : items) {
            sb.append("  ").append(item.toString()).append("\n");
        }

        sb.append(String.format("\nTotal: ₱%.2f | Paid: ₱%.2f | Change: ₱%.2f",
                totalAmount, amountPaid, getChangeDue()));

        return sb.toString();
    }
}