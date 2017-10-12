package Constructors;

public class OrderDetails {
  private int ItemCode;
  private int ItemQuantity;


   public OrderDetails() {
      this.ItemCode = 0;
      this.ItemQuantity = 0;

   }

    public OrderDetails(int ItemCode, int ItemQuantity) {

      this.ItemCode = ItemCode;
      this.ItemQuantity = ItemQuantity;

   }

    public int getItemCode() {

        return ItemCode;
    }

    public void setItemCode(int itemCode) {
        ItemCode = itemCode;
    }

    public int getItemQuantity() {

        return ItemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        ItemQuantity = itemQuantity;
    }

}
