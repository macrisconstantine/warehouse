package warehouse;

import javax.swing.*;

/**
 * Shipment details class shows all data about the current shipment.
 */
public class ShipmentDetails extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textArea1;

    /**
     * Again, this dialog only needs a constructor because it only displays data.
     */
    public ShipmentDetails() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(490, 420);
        setLocationRelativeTo(null);
        //setIconImage(new ImageIcon("img.jpg").getImage());

        // creates a local variable to access data about the MainFrame's current shipment
        Shipment shipment = MainFrame.currShipment;

        // a little flourish to set the title to show the appropriate shipment ID
        setTitle("Shipment "+shipment.getShipmentID()+" Pallet Details");
        buttonOK.addActionListener(e -> dispose());

        // text area does not need to be edited for any reason
        textArea1.setEditable(false);

        // sets the first line of text and a nice little line for organization
        textArea1.setText("ID\tTYPE\tBOXES\tSIZE\tSTATUS\n");
        textArea1.append("_________________________________________________" +
                "_____________________________");

        // goes through each stack of pallets in the current shipment and displays the info for each pallet
        for (BookPallet s: shipment.getBookPallets())
        {
            // appends text in a manner that results in aesthetic formatting
            textArea1.append("\n"+s.getPalletID()+"\t"+s.getContents()
                    +"\t"+s.getNumOfBoxes()+"\t"+s.getElementsInBox()+"\t"+s.getStatus());
        }
        for (BagPallet s: shipment.getBagPallets())
        {
            textArea1.append("\n"+s.getPalletID()+"\t"+s.getContents()
                    +"\t"+s.getNumOfBoxes()+"\t"+s.getElementsInBox()+"\t"+s.getStatus());
        }
        for (LeafletPallet s: shipment.getLeafletPallets())
        {
            textArea1.append("\n"+s.getPalletID()+"\t"+s.getContents()
                    +"\t"+s.getNumOfBoxes()+"\t"+s.getElementsInBox()+"\t"+s.getStatus());
        }
        // sets the scroll bar to begin at the top instead of the default of starting at the the bottom
        textArea1.setCaretPosition(0);
        setVisible(true);
    }
}
