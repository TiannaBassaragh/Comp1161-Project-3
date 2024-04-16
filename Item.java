import javax.swing.*;

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
        if (this.quantity >= quantity){
            this.quantity = this.quantity - quantity;
        }
        else{
            JFrame frame = new JFrame("An error has occurred");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Create and set up the content pane.
            ExceptionPopUp epu = new ExceptionPopUp("Not enough " + getName()+ " in stock, " + getQuantity() +" remaining");
            epu.setOpaque(true); //content panes must be opaque
            frame.setContentPane(epu);
            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }

    }
    public void incQuantity(int quantity) {
        this.quantity = this.quantity + quantity;
    }
    public void setQuanity(int quantity){
        this.quantity = quantity;
    }
}
