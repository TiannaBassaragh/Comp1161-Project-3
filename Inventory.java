import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.Collections;

public class Inventory extends JPanel{
    private int quantity;
    protected ArrayList <Item> productList;
    private Inventory thisScreen;

    private JButton     cmdAddItem;
    private JButton     cmdClose;
    private JButton     cmdSortName;
    private JButton     cmdSortCategory;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;

    private  JScrollPane scrollPane;

    private JTable table;
    private DefaultTableModel model;
    private Store storeFront;



    public Inventory (Store s){
        super(new GridLayout(5,6));
        thisScreen = this;

        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        productList = loadItems("stock.dat");
        System.out.print(productList);
        String[] columnNames=  {"Product",
                "Quantity",
                "Category"};
        model=new DefaultTableModel(columnNames,0);
        table = new JTable(model);
        showTable(productList);

        table.setPreferredScrollableViewportSize(new Dimension(500, productList.size()*15 +50));
        table.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(table);
        add(scrollPane);

        pnlDisplay.setLayout(new GridLayout(2,1));
        //Creates and sets colours of buttons
        cmdAddItem  = new JButton("Add Item");
        cmdAddItem.setBackground(Color.GREEN);
        cmdSortName = new JButton("Sort by Item Name");
        cmdSortName.setBackground(Color.ORANGE);
        cmdSortCategory = new JButton("Sort by Category");
        cmdSortCategory.setBackground(Color.BLUE);
        cmdClose = new JButton("Close");
        cmdClose.setBackground(Color.RED);
        //Adds functionality to buttons
        cmdClose.addActionListener(new CloseButtonListener());
        cmdAddItem.addActionListener(new AddItemButtonListener());
        cmdSortName.addActionListener(new SortCategoryButtonListener());
        cmdSortCategory.addActionListener(new SortNameButtonListener());
        //Makes buttons appear in user interface
        pnlCommand.add(cmdAddItem);
        pnlCommand.add(cmdClose);
        pnlCommand.add(cmdSortName);
        pnlCommand.add(cmdSortCategory);
        add(pnlCommand);
    }

    private void showTable(ArrayList<Item> productList)
    {
        if (productList.size()>0){
            for(int p=0; p<productList.size(); p++){
                addToTable(productList.get(p));
            }
        }
    }

    private void addToTable(Item i)
    {

        String[] item={i.getName(),""+ i.getQuantity(), i.getCategory()};
        model.addRow(item);

    }

    public void addItem(Item i){
        productList.add(i);
        addToTable(i);
    }

    private ArrayList<Item> loadItems(String ifile){

        ArrayList<Item> pList = new ArrayList<>();
        try
        {
           Scanner iscan  = new Scanner(new File(ifile));
            while(iscan.hasNext())
            {
                String [] nextLine = iscan.nextLine().split(",");
                String name = nextLine[0];
                int quantity = Integer.parseInt(nextLine[1]);
                String cat = nextLine[2];
                Item i = new Item(name, quantity, cat);
                pList.add(i);
            }

            iscan.close();
        }
        catch(IOException e)
        {e.printStackTrace(); }
        return pList;
    }


    private class CompareCat implements Comparator <Item>{
        public int compare(Item i1, Item i2){
            return i1.getCategory().compareTo(i2.getCategory());
        }
    }
    private class CompareName implements Comparator <Item> {
        public int compare(Item i1, Item i2) {
            return i1.getName().compareTo(i2.getName());
        }
    }
    public int getInventoryQuant(){
        int total = 0;
        for(Item p:productList){
            total+=p.getQuantity();
        }
        return total;
    }

    public ArrayList<Item> getProductList() {
        return productList;
    }

    private class AddItemButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
                ItemEntry ie = new ItemEntry(thisScreen);
        }
    }
    private class CloseButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            thisScreen.setVisible(false);
        }
    }
    private class SortNameButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            model.setRowCount(0);
            Collections.sort(productList, new CompareName());
            showTable(productList);
        }
    }
    private class SortCategoryButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            model.setRowCount(0);
            Collections.sort(productList, new CompareCat());
            showTable(productList);
        }
    }

}