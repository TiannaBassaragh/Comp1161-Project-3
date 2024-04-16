import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExceptionPopUp extends JPanel {
    private JButton cmdClose;
    private JPanel pnlCommand;
    private JPanel pnlDisplay;
    private ExceptionPopUp thisExceptionPopUp;

    public ExceptionPopUp(String errorMessage) {
        //super(new GridLayout(5,6));
        thisExceptionPopUp = this;
        //setTitle("An exception error has occurred."); // Set the title for the JFrame.
        pnlCommand = new JPanel();
        pnlDisplay = new JPanel();

        pnlDisplay.add(new JLabel(errorMessage)); //Display the error message.

        //Added close button.
        cmdClose = new JButton("Close");

        //Adds functionality to the close button.
        cmdClose.addActionListener(new CloseButtonListener());

        //Make the close button appear in the user interface.
        pnlCommand.add(cmdClose);

        add(pnlDisplay);
        add(pnlCommand);
    }

    private class CloseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(thisExceptionPopUp);
            if (frame != null) {
                frame.dispose(); // Close the JFrame
            }
        }
    }
}


