import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.swing.table.*;
import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.Collections;
import java.util.Random;

public class Transactions extends JPanel{

    private int quantity;
    private ArrayList<TransactionBase> transactionList;
    private Transactions thisScreen;

    private JButton     cmdAddTransaction;
    private JButton     cmdClose;
    private JButton     cmdSortTranId;
    private JButton     cmdSortName;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;

    private  JScrollPane scrollPane;

    private JTable table;
    private DefaultTableModel model;
    private Store storeFront;
    private ArrayList<Item> itemList;


    public Transactions (Store s){
        super(new GridLayout(5,6));
        thisScreen = this;

        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        transactionList = loadTransactions("sales.txt");
        String[] columnNames=  {"Transaction ID",
                "Product",
                "Quantity",
                "Date"};
        model=new DefaultTableModel(columnNames,0);
        table = new JTable(model);
        showTable(transactionList);

        table.setPreferredScrollableViewportSize(new Dimension(500, transactionList.size()*15 +50));
        table.setFillsViewportHeight(true);

        scrollPane = new JScrollPane(table);

        add(scrollPane);
        cmdAddTransaction  = new JButton("Add Transaction");
        cmdAddTransaction.setBackground(Color.GREEN);
        cmdSortTranId  = new JButton("Sort by Transaction ID");
        cmdSortTranId.setBackground(Color.BLUE);
        cmdSortName = new JButton("Sort by Name");
        cmdSortName.setBackground(Color.ORANGE);
        cmdClose   = new JButton("Close");
        cmdClose.setBackground(Color.RED);
        //Adds functionality to buttons
        cmdClose.addActionListener(new CloseButtonListener());
        cmdAddTransaction.addActionListener(new AddTransactionButtonListener());
        cmdSortTranId.addActionListener(new SortTranIDButtonListener());
        cmdSortName.addActionListener(new SortCategoryButtonListener());
        //Makes buttons appear in user interface
        pnlCommand.add(cmdAddTransaction);
        pnlCommand.add(cmdClose);
        pnlCommand.add(cmdSortTranId);
        pnlCommand.add(cmdSortName);

        add(pnlCommand);

    }

    private void showTable(ArrayList<TransactionBase> tL)
    {
        if (transactionList.size()>0){
            for(int p=0; p<transactionList.size(); p++){
                addToTable(transactionList.get(p));
            }
        }
    }

    private void addToTable(TransactionBase t)
    {
        String[] item={""+t.getTranId(), t.getItemName(),""+ t.getQuantity(), t.getDate()};
        model.addRow(item);

    }

    public void addToFile(TransactionBase t) {
        try {
            String newadd = t.getTranId()+" "+t.getItemName()+" "+t.getQuantity()+" "+t.getDate() + "\n"; // Add newline character
            BufferedWriter writer = new BufferedWriter(new FileWriter("sales.txt", true)); // Append to the file
            writer.write(newadd);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace(); // Properly handle the exception, print the stack trace for debugging
        }
    }

    public void addTransaction(TransactionBase tb) {
        itemList = loadItems("stock.txt");
        ArrayList<String> nameList =new ArrayList<String>();
        for (Item p : itemList) {
            nameList.add(p.getName());
        }
        if (nameList.contains(tb.getItemName())) {
            for (Item i : itemList) {
                if (i.getName().equals(tb.getItemName())) {
                    i.decQuantity(tb.getQuantity());
                    unloadItems(itemList);
                    tb.tranID = SearchandSetId(transactionList);
                    transactionList.add(tb);
                    addToTable(tb);
                    addToFile(tb);
                }
            }
        }
        else{
            JFrame frame = new JFrame("An error has occurred");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //Create and set up the content pane.
            ExceptionPopUp e = new ExceptionPopUp("Item not Found");
            e.setOpaque(true); //content panes must be opaque
            frame.setContentPane(e);
                //Display the window.
            frame.pack();
            frame.setVisible(true);

            }

        }

    public int randomnum(){
        int min = 10000000;
        int max = 99999999;
        Random  r = new Random();
        int newId = r.nextInt(max-min)+min;
        return newId;
    }
    public int SearchandSetId(ArrayList<TransactionBase> tb){
        ArrayList<Integer> ids = new ArrayList<Integer>();
        for (TransactionBase t: tb){
            ids.add(t.getTranId());
        }
        int id = randomnum();

        if (!ids.contains(id)){
            id = id;
        }
        else{
            id = SearchandSetId(tb);
        }
        return id;
    }
    private void unloadItems(ArrayList <Item> list){
        try {
            String updatedStock = "";
            for (Item i:list){
                updatedStock = updatedStock.concat(i.getName() + " " + i.getQuantity() + " " + i.getCategory() + "\n"); // Add newline character

            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("stock.txt"));
            writer.write(updatedStock);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace(); // Properly handle the exception, print the stack trace for debugging
        }
    }
    private ArrayList<Item> loadItems(String ifile){
        Scanner iscan = null;
        ArrayList<Item> pList = new ArrayList<>();
        try
        {
            iscan  = new Scanner(new File(ifile));
            while(iscan.hasNext())
            {
                String [] nextLine = iscan.nextLine().split(" ");
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

    private ArrayList<TransactionBase> loadTransactions(String ifile){
        Scanner tscan = null;
        ArrayList<TransactionBase> transactionList = new ArrayList<>();
        try
        {
            tscan  = new Scanner(new File(ifile));
            while(tscan.hasNext())
            {
                String [] nextLine = tscan.nextLine().split(" ");
                String name = nextLine[1];
                int quantity = Integer.parseInt(nextLine[2]);
                String date = nextLine[3];
                TransactionBase t = new TransactionBase(name, quantity, date);
                t.tranID = Integer.parseInt(nextLine[0]);
                transactionList.add(t);
            }
            tscan.close();
        }
        catch(IOException e)
        {}
        return transactionList;
    }

    private class CloseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            thisScreen.setVisible(false);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(thisScreen);
            if (frame != null) {
                frame.dispose(); // Close the JFrame
            }
        }
    }
    private class AddTransactionButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
                    TransactionEntry te = new TransactionEntry(thisScreen);
        }
    }

    private class CompareID implements Comparator <TransactionBase>{
        public int compare (TransactionBase t1, TransactionBase t2){
            return t1.getTranId() - (t2.getTranId());
        }
    }
    private class CompareName implements Comparator<TransactionBase>{
        @Override
        public int compare(TransactionBase t1, TransactionBase t2) {
            return t1.getItemName().compareTo(t2.getItemName());
        }
    }
    private class SortTranIDButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            model.setRowCount(0);
            Collections.sort(transactionList, new CompareID());
            showTable(transactionList);
        }
    }

    private class SortCategoryButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            model.setRowCount(0);
            Collections.sort(transactionList, new CompareName());
            showTable(transactionList);
        }
    }
}