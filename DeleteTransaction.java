import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeleteTransaction extends JFrame {
    private JTextField txtName;       //name
    private JTextField txtQuant;        //quantity
    private JButton cmdSave;
    private JButton cmdClose;
    private JButton cmdClearAll;

    private JTextField txtDate;     //category
    private JPanel pnlCommand;
    private JPanel pnlDisplay;

    private DeleteTransaction del;
    private Transactions t;
    private Store s;

    public DeleteTransaction(Transactions sale) {
        del = this; //Gives reference to specific instance of PersonEntry
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
        txtDate = new JTextField(20);
        pnlDisplay.add(txtDate);
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
        this.t = sale;
    }

    //Button Listeners contain functions to be carried out by buttons
    private class CloseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            del.setVisible(false);
        }
    }

    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String name = del.txtName.getText();
            String date = del.txtDate.getText();
            String error = "";
            try {
                int quauntity = Integer.parseInt(del.txtQuant.getText());
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

                t.transactionList.remove(new TransactionBase(name, Integer.parseInt(del.txtQuant.getText()), date));
//
                del.setVisible(false);
            }
        }
    }
}
