package warehouse;

import javax.swing.*;
import java.awt.event.*;

/**
 * This dialog is used to adjust the pallet details of the current shipment.
 */
public class ShipmentEditDialog extends JDialog {
    private JPanel contentPane;
    private JButton submitChanges;
    private JButton buttonCancel;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;
    private JSpinner spinner4;
    private JSpinner spinner5;
    private JSpinner spinner6;

    /**
     * The constructor creates the GUI layout and builds all the appropriate spinners and labels.
     */
    public ShipmentEditDialog() {
        setSize(575, 170);
        //setIconImage(new ImageIcon("img.jpg").getImage());
        setTitle("Shipment Editor");
        setContentPane(contentPane);
        setModal(true);
        setContentPane(contentPane);
        getRootPane().setDefaultButton(submitChanges);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // creates a local variable to manipulate/access the data of the MainFrame's current shipment
        Shipment editShipment = MainFrame.currShipment;

        // each spinner is set up to eliminate the possibility of any invalid input
        // additionally the spinners have a starting value that reflects the actual current value of
        // those pallet types in that specific shipment
        // this provides excellent convenience in the case of minor number adjustments
        spinner1.setModel(new SpinnerNumberModel(editShipment.getBookPallets().firstElement().getNumOfBoxes(),
                0, 150, 1));
        spinner2.setModel(new SpinnerNumberModel(editShipment.getLeafletPallets().firstElement().getNumOfBoxes(),
                0, 100, 1));
        spinner3.setModel(new SpinnerNumberModel(editShipment.getBagPallets().firstElement().getNumOfBoxes(),
                0, 100, 1));
        spinner4.setModel(new SpinnerNumberModel(editShipment.getBookPallets().firstElement().getElementsInBox(),
                0, 50, 1));
        spinner5.setModel(new SpinnerNumberModel(editShipment.getLeafletPallets().firstElement().getElementsInBox(),
                0, 10000, 100));
        spinner6.setModel(new SpinnerNumberModel(editShipment.getBagPallets().firstElement().getElementsInBox(),
                0, 12000, 100));

        // cancel button disposes the dialog without submitting changes
        buttonCancel.addActionListener(e -> dispose());

        // call dispose() when cross is clicked
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // submit changes button prompts for confirmation before implementing the changes on the current shipment
        submitChanges.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(this,
                    "Commit changes?", "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.YES_OPTION)
            {
                // takes the values from the corresponding spinners and sets the details of the current
                // shipment's pallets accordingly
                editShipment.setNumOfBookBoxes((int)spinner1.getValue());
                editShipment.setNumOfLeafBoxes((int)spinner2.getValue());
                editShipment.setNumOfBagBoxes((int)spinner3.getValue());
                editShipment.setNumOfBooks((int)spinner4.getValue());
                editShipment.setNumOfLeaflets((int)spinner5.getValue());
                editShipment.setNumOfBags((int)spinner6.getValue());

                // disposes on completion
                dispose();

                // after disposing, informative message is issued to signal successful execution
                JOptionPane.showMessageDialog(this, "Shipment details updated!",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
