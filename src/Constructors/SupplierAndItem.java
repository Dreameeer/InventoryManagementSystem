package Constructors;

public class SupplierAndItem {
    private int ItemCode;
    private String ItemName;
    private String SupplierName;
    private int SupplierID;

    public SupplierAndItem() {
        this.ItemCode = 0;
        this.ItemName = "";
        this.SupplierName = "";
        this.SupplierID = 0;
    }

    public SupplierAndItem(int ItemCode, String ItemName , int SupplierID, String SupplierName) {
        this.ItemCode = ItemCode;
        this.ItemName = ItemName;
        this.SupplierName = SupplierName;
        this.SupplierID = SupplierID;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(int supplierID) {
        SupplierID = supplierID;
    }

    public String getSupplierName() {

        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getItemName() {

        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public int getItemCode() {

        return ItemCode;
    }

    public void setItemCode(int itemCode) {
        ItemCode = itemCode;
    }
}
