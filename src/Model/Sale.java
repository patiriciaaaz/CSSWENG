package Model;

public class Sale {
    private int salesID;
    private int transactionID;
    private int itemID;
    private int quantity;

    // Getters
    public int getSalesID() { 
        return salesID;
    }
    
    public int getTransactionID() {
         return transactionID; 
    }

    public int getItemID() {
         return itemID; 
    }

    public int getQuantity() {
         return quantity; 
    }

    // Setters
    public void setSalesID(int salesID) {
         this.salesID = salesID;
    }

    public void setTransactionID(int transactionID) {
         this.transactionID = transactionID;
    }

    public void setItemID(int itemID) {
         this.itemID = itemID;
    }

    public void setQuantity(int quantity) {
         this.quantity = quantity;
    }

}
