import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;
import java.io.IOException;
import javax.swing.table.*;

public class Store extends JPanel{
    private JButton     cmdTransactions;
    private JButton     cmdInventory;

    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;

    private JLabel      backgroundLabel;
    private ImageIcon   backgroundImage;

    private Store thisStore;

    public Store() {
        super(new GridLayout(2,4));
        thisStore = this;


        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        pnlDisplay.add(new JLabel("Welcome to the #1 Beauty Supply Store Database. CLICK an option to continue"));

        //Creates the buttons
        cmdTransactions = new JButton("View Transactions");
        cmdTransactions.setBackground(Color.decode("#d7bf8c"));
        cmdTransactions.addActionListener(new TransactionButtonListener());
        cmdInventory = new JButton("View Inventory");
        cmdInventory.setBackground(Color.decode("#da775d"));
        cmdInventory.addActionListener(new InventoryButtonListener());

        //Creates image and adds it to the UI
        backgroundImage = new ImageIcon("backgroundImage.jpg");
        backgroundLabel = new JLabel("", backgroundImage, JLabel.CENTER);
        pnlDisplay.add(backgroundLabel);
        
        //Makes buttons appear in the UI
        pnlCommand.add(cmdTransactions);
        pnlCommand.add(cmdInventory);

        add(pnlDisplay);
        add(pnlCommand);

    }

    private static void startUpGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Welcome To Our Store!");
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
            JFrame frame = new JFrame("Transactions");
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
            JFrame frame = new JFrame("Inventory");
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
