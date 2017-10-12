package Constructors;

import javafx.beans.property.StringProperty;

public class ItemDetails {
    private int ItemCode;
    private String ItemName;
    private String ItemDescription;
    private int ItemQuantity;

    public ItemDetails() {
        this.ItemCode = 0;
        this.ItemName = "";
        this.ItemDescription = "";
        this.ItemQuantity = 0;
    }

    public ItemDetails(int ItemCode, String ItemName, String ItemDescription, int ItemQuantity) {
        this.ItemCode = ItemCode;
        this.ItemName = ItemName;
        this.ItemDescription = ItemDescription;
        this.ItemQuantity = ItemQuantity;
    }

    public int getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        ItemQuantity = itemQuantity;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public void setItemDescription(String itemDescription) {
        ItemDescription = itemDescription;
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

