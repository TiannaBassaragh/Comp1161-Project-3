public class Item{
    private String name;
    public int quantity;
    private String category;

    public Item(String name, int quantity, String category){
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName(){
        return this.name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory(){
        return category;
    }

    public void decQuantity(int quantity) {
            this.quantity = this.quantity - quantity;
    }
    public void incQuantity(int quantity) {
        this.quantity = this.quantity + quantity;
    }
}
