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

public class Transactions extends JPanel{

    private int quantity;
    private ArrayList<TransactionBase> transactionList;
    private Transactions thisScreen;

    private JButton     cmdAddTransaction;
    private JButton     cmdClose;
    private JButton     cmdSortTranId;
    private JButton     cmdSortCategory;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;

    private  JScrollPane scrollPane;

    private JTable table;
    private DefaultTableModel model;
    private Store storeFront;
    private Inventory thisStock;


    public Transactions (Store s){
        super(new GridLayout(5,6));
        thisScreen = this;

        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        transactionList = loadTransactions("sales.dat");
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
        cmdSortCategory = new JButton("Sort by Category");
        cmdSortCategory.setBackground(Color.ORANGE);
        cmdClose   = new JButton("Close");
        cmdClose.setBackground(Color.RED);
        //Adds functionality to buttons
        cmdClose.addActionListener(new CloseButtonListener());
        cmdAddTransaction.addActionListener(new AddTransactionButtonListener());
        cmdSortTranId.addActionListener(new SortTranIDButtonListener());
        cmdSortCategory.addActionListener(new SortCategoryButtonListener());
        //Makes buttons appear in user interface
        pnlCommand.add(cmdAddTransaction);
        pnlCommand.add(cmdClose);
        pnlCommand.add(cmdSortTranId);
        pnlCommand.add(cmdSortCategory);

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

    public void addTransaction(TransactionBase tb) {
        ArrayList<Item> pl = thisStock.getProductList();
        for (Item p : pl) {
            if (tb.getItemName().equals(p.getName())){
                    p.decQuantity(tb.getQuantity());
            }
            else{
                ExceptionPopUp e = new ExceptionPopUp("Item not Found");
            }

        }
    }

    private ArrayList<TransactionBase> loadTransactions(String ifile){
        Scanner tscan = null;
        ArrayList<TransactionBase> transactionList = new ArrayList<>();
        try
        {
            tscan  = new Scanner(new File(ifile));
            while(tscan.hasNext())
            {
                String [] nextLine = tscan.nextLine().split(",");
                String name = nextLine[0];
                int quantity = Integer.parseInt(nextLine[1]);
                String date = nextLine[2];
                TransactionBase t = new TransactionBase(name, quantity, date);
                transactionList.add(t);
            }
            tscan.close();
        }
        catch(IOException e)
        {}
        return transactionList;
    }

    private class CloseButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            thisScreen.setVisible(false);
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
            Collections.sort(transactionList, new Transactions.CompareID());
            showTable(transactionList);
        }
    }

    private class SortCategoryButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            model.setRowCount(0);
            Collections.sort(transactionList, new Transactions.CompareName());
            showTable(transactionList);
        }
    }
}