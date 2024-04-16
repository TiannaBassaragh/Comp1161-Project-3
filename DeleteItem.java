import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteItem extends JFrame {
    private JTextField txtName;       //name
    private JTextField txtNewQuant;        //quantity
    private JButton cmdSave;
    private JButton cmdClose;
    private JButton cmdClearAll;

    private JTextField txtCat;     //category
    private JPanel pnlCommand;
    private JPanel pnlDisplay;

    private DeleteItem dele;
    private Inventory i;
    private Store s;

    public DeleteItem(Inventory stock) {
        dele = this; //Gives reference to specific instance of PersonEntry
        setTitle("Entering new item");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        pnlDisplay.add(new JLabel("Name:"));
        txtName = new JTextField(20);
        pnlDisplay.add(txtName);
        pnlDisplay.add(new JLabel("New Quantity:"));
        txtNewQuant = new JTextField(3);
        pnlDisplay.add(txtNewQuant);
        pnlDisplay.add(new JLabel("Category"));
        txtCat = new JTextField(20);
        pnlDisplay.add(txtCat);
        pnlDisplay.setLayout(new GridLayout(3, 4));

        //Creates and sets colours of buttons
        cmdSave = new JButton("Save");
        cmdSave.setBackground(Color.GREEN);
        cmdClose = new JButton("Close");
        cmdClose.setBackground(Color.RED);

        //Adds functionality to buttons
        cmdClose.addActionListener(new CloseButtonListener());
        cmdSave.addActionListener(new SaveButtonListener());

        pnlCommand.add(cmdSave);
        pnlCommand.add(cmdClose);
        add(pnlDisplay, BorderLayout.CENTER);
        add(pnlCommand, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        this.i = stock;
    }

    //Button Listeners contain functions to be carried out by buttons
    private class CloseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dele.setVisible(false);
        }
    }

    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = dele.txtName.getText();
            String cat = dele.txtCat.getText();
            String error = "";
            try {
                int quauntity = Integer.parseInt(dele.txtNewQuant.getText());
            } catch (NumberFormatException ne) {
                error += "Quantity must be in numeral format";
            }
            if (!error.equals("")) {
                JFrame frame = new JFrame("An error has occurred");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //Create and set up the content pane.
                ExceptionPopUp epu = new ExceptionPopUp(error);
                epu.setOpaque(true); //content panes must be opaque
                frame.setContentPane(epu);
                //Display the window.
                frame.pack();
                frame.setVisible(true);

            } else {
                i.productList.remove(new Item(name, Integer.parseInt(dele.txtNewQuant.getText()), cat));
//REMOVE FROM FILE
                dele.setVisible(false);
            }
        }
    }
}
