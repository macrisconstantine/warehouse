package warehouse;

import javax.swing.*;

/**
 * This is a simple dialog to display the information about a searched pallet.
 */
public class SearchPalletDialog extends JDialog {
    private JPanel contentPane;
    private JButton done;
    private JLabel pID;
    private JLabel pContents;
    private JLabel numBoxes;
    private JLabel numElements;
    private JLabel pStatus;

    // there is only really a constructor in this class, as the dialog's purpose is only to display information
    public SearchPalletDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(done);
        setSize(300,225);
        setLocationRelativeTo(null);
        //setIconImage(new ImageIcon("img.jpg").getImage());
        setTitle("Pallet Search Results");

        // creates a local object of the Mainframe's searched pallet
        Pallet pallet = MainFrame.searchedPallet;

        // sets the values of the labels to show the appropriate data for each piece of data about the pallet
        pID.setText(String.valueOf(pallet.getPalletID()));
        pContents.setText(pallet.getContents());
        numBoxes.setText(String.valueOf(pallet.getNumOfBoxes()));
        numElements.setText(String.valueOf(pallet.getElementsInBox()));

        // checks the status enum to determine whether to label the pallet PACKED or WAITING
        if (pallet.getStatus()==Pallet.PalletStatus.PACKED)
            pStatus.setText("PACKED");
        else pStatus.setText("WAITING");

        // done button to dispose the dialog
        done.addActionListener(e -> dispose());
        setVisible(true);
    }
}
