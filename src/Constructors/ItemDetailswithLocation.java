package Constructors;

import Constructors.ItemDetails;

public class ItemDetailswithLocation extends ItemDetails {
    private int ItemCode;
    private String ItemName;

    private String ItemLocation;

    public ItemDetailswithLocation() {
        this.ItemCode = 0;
        this.ItemName = "";

        this.ItemLocation="";
    }

    public String getItemLocation() {
        return ItemLocation;
    }

    public void setItemLocation(String itemLocation) {
        ItemLocation = itemLocation;
    }

    public ItemDetailswithLocation(int ItemCode, String ItemName, String ItemLocation) {
        this.ItemCode = ItemCode;
        this.ItemName = ItemName;

        this.ItemLocation=ItemLocation;

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

