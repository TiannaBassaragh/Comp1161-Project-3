import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import java.awt.Color;

public class Store extends JPanel{
    private JButton     cmdTransactions;
    private JButton     cmdInventory;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;

    private Store thisStore;

    public Store() {
        super(new GridLayout(5,6));
        thisStore = this;


        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        pnlDisplay.add(new JLabel("Welcome to the #1 Beauty Supply Store Database. CLICK an option to continue"));

        //Creates and sets colours of buttons
        cmdTransactions  = new JButton("View Transactions");
        cmdTransactions.setBackground(Color.GREEN);
        cmdInventory  = new JButton("View Inventory");
        cmdInventory.setBackground(Color.BLUE);

        //Adds functionality to buttons
        //cmdClose.addActionListener(new CloseButtonListener());
        cmdTransactions.addActionListener(new TransactionButtonListener());
        cmdInventory.addActionListener(new InventoryButtonListener());

        //Makes buttons appear in user interface
        pnlCommand.add(cmdTransactions);
        pnlCommand.add(cmdInventory);

        add(pnlDisplay);
        add(pnlCommand);

    }

    private static void startUpGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Welcome");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        Store viewStorePane = new Store();
        viewStorePane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(viewStorePane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                startUpGUI();
            }
        });
    }

    private class TransactionButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Welcome to Transactions");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Create and set up the content pane.
            Transactions t = new Transactions(thisStore);
            t.setOpaque(true); //content panes must be opaque
            frame.setContentPane(t);

            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }
    }

    private class InventoryButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JFrame frame = new JFrame("Welcome To Inventory");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Create and set up the content pane.
            Inventory i = new Inventory(thisStore);
            i.setOpaque(true); //content panes must be opaque
            frame.setContentPane(i);

            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }
    }
}
