public class TransactionBase{
    protected int tranID  ;
    private String product;
    private int quantity;

    private String date;

    public TransactionBase(String itemName, int quantity, String date){
        this.product = itemName;
        this.quantity = quantity;
        this.date = date;
    }
    public String getItemName(){
        return product;
    }

    public int getTranId(){
        return this.tranID;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public String getDate(){
        return this.date;
    }
}