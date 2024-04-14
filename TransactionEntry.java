import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TransactionEntry extends JFrame
{
    private JTextField  txtName;       //name
    private JTextField  txtQuant;        //quantity
    private JButton     cmdSave;
    private JButton     cmdClose;
    private JButton     cmdClearAll;

    private JTextField   txtCat;     //category
    private JPanel      pnlCommand;
    private JPanel      pnlDisplay;

    private TransactionEntry tEnt;
    private Transactions t ;
    private Store s ;
    public TransactionEntry(Transactions trade)
    {
        tEnt = this; //Gives reference to specific instance of PersonEntry
        setTitle("Entering new item");
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();
        pnlDisplay.add(new JLabel("Name:"));
        txtName = new JTextField(20);
        pnlDisplay.add(txtName);
        pnlDisplay.add(new JLabel("Quantity:"));
        txtQuant = new JTextField(3);
        pnlDisplay.add(txtQuant);
        pnlDisplay.add(new JLabel("Category"));
        txtCat= new JTextField(20);
        pnlDisplay.add(txtCat);
        pnlDisplay.setLayout(new GridLayout(3,4));

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
        this.t = trade;
    }
    //Button Listeners contain functions to be carried out by buttons
    private class CloseButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            tEnt.setVisible(false);
        }
    }
    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = tEnt.txtName.getText();
            String cat = tEnt.txtCat.getText();
            String error = "";
            try {
                int quauntity = Integer.parseInt(tEnt.txtQuant.getText());
            }
            catch (NumberFormatException ne) {
                error += "Quantity must be in numeral format";
            }
            if (!error.equals("")){
                ExceptionPopUp ex = new ExceptionPopUp(error);
            }
            else{
                t.addTransaction(new TransactionBase(name, Integer.parseInt(tEnt.txtQuant.getText()), cat));
                tEnt.setVisible(false);
            }
        }
    }
}