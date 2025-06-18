import java.util.Date;

public class InventoryItemModel {
    private int id;
    private String name;
    private String category; // Supplement/Drink/Merchandise
    private String barcode;
    private double purchasePrice;
    private double sellingPrice;
    private int currentStock;
    private int minimumStockLevel;
    private Date lastRestockDate;
    private String supplier;

    // Constructors
    public InventoryItemModel() {
    }

    public InventoryItemModel(int id, String name, String category, String barcode,
            double purchasePrice, double sellingPrice, int currentStock,
            int minimumStockLevel, Date lastRestockDate, String supplier) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.barcode = barcode;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.currentStock = currentStock;
        this.minimumStockLevel = minimumStockLevel;
        this.lastRestockDate = lastRestockDate;
        this.supplier = supplier;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getMinimumStockLevel() {
        return minimumStockLevel;
    }

    public void setMinimumStockLevel(int minimumStockLevel) {
        this.minimumStockLevel = minimumStockLevel;
    }

    public Date getLastRestockDate() {
        return lastRestockDate;
    }

    public void setLastRestockDate(Date lastRestockDate) {
        this.lastRestockDate = lastRestockDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    // Business Logic Methods
    public boolean needsRestock() {
        return currentStock <= minimumStockLevel;
    }

    public double calculateMarkup() {
        return sellingPrice - purchasePrice;
    }

    public double calculateMarkupPercentage() {
        return ((sellingPrice - purchasePrice) / purchasePrice) * 100;
    }

    public void addStock(int quantity) {
        this.currentStock += quantity;
        this.lastRestockDate = new Date(); // Update to current date/time
    }

    public boolean reduceStock(int quantity) {
        if (this.currentStock >= quantity) {
            this.currentStock -= quantity;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "InventoryItemModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", barcode='" + barcode + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", sellingPrice=" + sellingPrice +
                ", currentStock=" + currentStock +
                ", minimumStockLevel=" + minimumStockLevel +
                ", lastRestockDate=" + lastRestockDate +
                ", supplier='" + supplier + '\'' +
                '}';
    }
}