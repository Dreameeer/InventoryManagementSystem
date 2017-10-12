package Constructors;

public class ItemDetailswithSupplier extends ItemDetails {

    private int ItemCode;
    private String ItemName;
    private int ItemQuantity;
    private String Supplier;

    public ItemDetailswithSupplier() {
        this.ItemCode = 0;
        this.ItemName = "";

        this.ItemQuantity = 0;

        this.Supplier= "";
    }

    @Override
    public int getItemQuantity() {
        return ItemQuantity;
    }

    @Override
    public void setItemQuantity(int itemQuantity) {
        ItemQuantity = itemQuantity;
    }

    @Override
    public String getItemName() {

        return ItemName;
    }

    @Override
    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    @Override
    public int getItemCode() {

        return ItemCode;
    }

    @Override
    public void setItemCode(int itemCode) {
        ItemCode = itemCode;
    }

    public ItemDetailswithSupplier(int ItemCode, String ItemName, String Supplier, int ItemQuantity) {
        this.Supplier=Supplier;
        this.ItemCode = ItemCode;
        this.ItemName = ItemName;
        this.ItemQuantity = ItemQuantity;

    }

    public String getSupplier() {
        return Supplier;
    }

    public void setSupplier(String supplier) {
        Supplier = supplier;
    }


}
