package Model;

public class Item {
    private int itemID;
    private String itemName;
    private double price;
    private int quantity;

    // Getters
    public int getItemID() {
         return itemID; 
    }

    public String getItemName() {
         return itemName; 
    }

    public double getPrice() {
         return price; 
    }

    public int getQuantity() {
         return quantity; 
    }

    // Setters
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
    
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
