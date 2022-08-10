package warehouse;

import javax.swing.*;

/**
 * This dialog calls a large number of functions to display various kinds of useful statistics about
 * the warehouse as a whole.
 */
public class StatisticsDialog extends JDialog {
    // there are quite a few elements to be constructed for this relatively "simple" little dialog
    private JPanel contentPane;
    private JButton done;
    private JLabel users;
    private JLabel shipments;
    private JLabel pallets;
    private JLabel elements;
    private JLabel cShipments;
    private JLabel uShipments;
    private JLabel aShipments;
    private JLabel wPallets;
    private JLabel pPallets;
    private JLabel uBoxes;
    private JLabel pBoxes;
    private JLabel uElements;
    private JLabel pElements;
    private JLabel boxes;
    private JScrollPane scrollPane;
    private JLabel totalBooks;
    private JLabel waitingBookPal;
    private JLabel waitingBagPal;
    private JLabel waitLeafPal;
    private JLabel packBookPal;
    private JLabel packBagPal;
    private JLabel packLeafPal;
    private JLabel waitBookBox;
    private JLabel waitBagBox;
    private JLabel waitLeafBox;
    private JLabel unpackBook;
    private JLabel unpackBag;
    private JLabel uLeafs;
    private JLabel packedBooks;
    private JLabel packedBags;
    private JLabel packedLeaflets;
    private JLabel packBookBox;
    private JLabel packBagBox;
    private JLabel packLeafBox;

    /**
     * Only a constructor is needed to display all the information.
     */
    public StatisticsDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(done);
        setSize(325,325);
        setLocationRelativeTo(null);
        //setIconImage(new ImageIcon("img.jpg").getImage());
        setTitle("Cumulative Warehouse Numbers");
        done.addActionListener(e -> dispose());

        // creates local variable to access information about the warehouse in its current state
        Warehouse warehouse = MainFrame.theWarehouse;

        // creates local variable only to get the number of users in the system
        Login login = LoginDialog.log;

        // speeds up speed of scroll pane from its frustratingly slow default speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // the following statements utilize functions of the warehouse to set each label to display
        // the calculated statistics described in the GUI of the statistics dialog
        totalBooks.setText(String.valueOf(warehouse.getTotalNumOfPackedBook()+warehouse.getTotalNumOfUnpackedBook()));
        elements.setText(String.valueOf(warehouse.getTotalPackedElements()+warehouse.getTotalUnpackedElements()));
        boxes.setText(String.valueOf(warehouse.getTotalUnpackedBoxes()+warehouse.getTotalPackedBoxes()));
        waitingBookPal.setText(String.valueOf(warehouse.getTotalNumOfUnpackedBookPallets()));
        waitingBagPal.setText(String.valueOf(warehouse.getTotalNumOfUnpackedBagPallets()));
        waitLeafPal.setText(String.valueOf(warehouse.getTotalNumOfUnpackedLeafPallets()));
        packBookPal.setText(String.valueOf(warehouse.getTotalNumOfPackedBookPallets()));
        pPallets.setText(String.valueOf(warehouse.getTotalNumOfPackedPallets()));
        wPallets.setText(String.valueOf(warehouse.getTotalNumOfUnpackedPallets()));
        pallets.setText(String.valueOf(warehouse.getTotalNumOfPallets()));
        pBoxes.setText(String.valueOf(warehouse.getTotalPackedBoxes()));
        uBoxes.setText(String.valueOf(warehouse.getTotalUnpackedBoxes()));
        uElements.setText(String.valueOf(warehouse.getTotalUnpackedElements()));
        pElements.setText(String.valueOf(warehouse.getTotalPackedElements()));
        shipments.setText(String.valueOf(warehouse.numOfShipments()));
        uShipments.setText(String.valueOf(warehouse.numOfUpShipments()));
        cShipments.setText(String.valueOf(warehouse.numOfCompShipments()));
        aShipments.setText(String.valueOf(warehouse.numOfCurrShipments()));
        packBagPal.setText(String.valueOf(warehouse.getTotalNumOfPackedBagPallets()));
        packLeafPal.setText(String.valueOf(warehouse.getTotalNumOfPackedLeafPallets()));
        unpackBook.setText(String.valueOf(warehouse.getTotalNumOfUnpackedBook()));
        unpackBag.setText(String.valueOf(warehouse.getTotalNumOfUnpackedBag()));
        uLeafs.setText(String.valueOf(warehouse.getTotalNumOfUnpackedLeaf()));
        packedBooks.setText(String.valueOf(warehouse.getTotalNumOfPackedBook()));
        packedBags.setText(String.valueOf(warehouse.getTotalNumOfPackedBag()));
        packedLeaflets.setText(String.valueOf(warehouse.getTotalNumOfPackedLeaf()));
        packBookBox.setText(String.valueOf(warehouse.getTotalPackedBookBox()));
        packLeafBox.setText(String.valueOf(warehouse.getTotalPackedLeafBox()));
        packBagBox.setText(String.valueOf(warehouse.getTotalPackedBagBox()));
        waitBookBox.setText(String.valueOf(warehouse.getTotalUnpackedBookBox()));
        waitBagBox.setText(String.valueOf(warehouse.getTotalUnpackedBagBox()));
        waitLeafBox.setText(String.valueOf(warehouse.getTotalUnpackedLeafBox()));

        // this was the only purpose of creating a local copy of the log object
        users.setText(String.valueOf(login.getNumOfUsers()));
        setVisible(true);
    }
}
