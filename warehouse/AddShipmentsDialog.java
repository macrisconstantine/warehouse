package warehouse;

import javax.swing.*;
import java.awt.event.*;

/**
 * Mini-dialog pops up from button in main frame for creating a new shipment.
 */
public class AddShipmentsDialog extends JDialog {
    // declaration of encapsulated GUI components
    private JPanel contentPane;
    private JButton createShip;
    private JButton buttonCancel;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;
    private JSpinner spinner4;

    /**
     * Constructor for dialog sets custom image, and constructs all the GUI components.
     */
    public AddShipmentsDialog() {
        // setting icon image as little warehouse logo
        //setIconImage(new ImageIcon("img.jpg").getImage());

        // setting appropriate title
        setTitle("Shipment Registration");
        setSize(370, 225);
        setContentPane(contentPane);
        setLocationRelativeTo(null);

        // set modal so action must be taken before disposing and returning focus to main frame
        setModal(true);
        getRootPane().setDefaultButton(createShip);

        // creates a new local variable to manipulate the static MainFrame "theWarehouse" object
        // this is done to keep all data manipulation saved in the serializable class MainFrame, which
        // can then be saved
        Warehouse wareHouse = MainFrame.theWarehouse;

        // spinner is set up to eliminate the need for input-validation while also making it convenient
        // to add a new year's shipment in the future or to add a past shipment
        // The shipment IDs in the shipments hashmap MUST be consecutive for the menu navigation system to work
        // This is ensured by allowing the spinner to only increment or decrement the current shipment ID by 1
        // for the new shipment.
        spinner1.setModel(new SpinnerNumberModel(wareHouse.currentShipment.getShipmentID(),
                wareHouse.currentShipment.getShipmentID()-1,
                wareHouse.currentShipment.getShipmentID()+1, 1));

        // editor is used here to display date in 2022 format instead of 2,022
        spinner1.setEditor(new JSpinner.NumberEditor(spinner1, "#"));

        // new shipment spinner values are set to the appropriate and realistic default values for convenience
        spinner2.setModel(new SpinnerNumberModel(20, 1, 200, 1));
        spinner3.setModel(new SpinnerNumberModel(6, 0, 90, 1));
        spinner4.setModel(new SpinnerNumberModel(8, 0, 70, 1));

        // create shipment button makes and adds a new custom shipment using the values from the spinners
        createShip.addActionListener(e -> {
            try {
                wareHouse.addShipment(new Shipment((int)spinner1.getValue(), (int)spinner2.getValue(),
                        (int)spinner3.getValue(), (int)spinner4.getValue()));

                // disposes after finishing
                dispose();

                // notifies user of successful shipment addition
                JOptionPane.showMessageDialog(this, "New shipment added successfully!",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
            } catch (WarehouseException ex) {

                // notifies user if shipment ID already exists in warehouse
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setVisible(true);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
