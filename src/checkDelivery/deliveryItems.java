package checkDelivery;

public class deliveryItems{
    int ItemCode;
    String ItemName;
    int OrderQuantity;
    int ReceivedQuantity;
    int Difference;
    int IDOfOrder;



    public deliveryItems() {
        this.ItemCode = 0;
        this.ItemName = "";
        int OrderQuantity=0;
        int ReceivedQuantity=0;
        int Difference=0;
        int IDOfOrder=0;


    }
    public deliveryItems(int IDOfOrder){
        this.IDOfOrder=IDOfOrder;
    }
    public deliveryItems(int ItemCode, String ItemName, int OrderQuantity, int ReceivedQuantity, int Difference) {
        this.ItemCode = ItemCode;
        this.ItemName = ItemName;
        this.OrderQuantity=OrderQuantity;
        this.ReceivedQuantity=ReceivedQuantity;
        this.Difference=Difference;
    }

    public int getDifference() {
        return Difference;
    }

    public void setDifference(int difference) {
        Difference = difference;
    }

    public int getReceivedQuantity() {

        return ReceivedQuantity;
    }

    public void setReceivedQuantity(int receivedQuantity) {
        ReceivedQuantity = receivedQuantity;
    }

    public int getOrderQuantity() {

        return OrderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        OrderQuantity = orderQuantity;
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
    public void setIDOfOrder(int IDOfOrder) {
        this.IDOfOrder = IDOfOrder;
    }

    public int getIDOfOrder() {
        return IDOfOrder;
    }
}
