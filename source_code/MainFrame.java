package warehouse;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * The big, main GUI class that runs and displays virtually everything in the app.
 */
public class MainFrame extends JFrame{
    // declaration of all the appropriate GUI components
    private JPanel mainPanel;
    private JButton editPallets;
    private JLabel welcome;
    private JProgressBar progressBar1;
    private JLabel shipNo;
    private JButton foreButton;
    private JButton backButton;
    private JButton newShipment;
    private JButton statistics;
    private JButton addPackedBookBoxes;
    private JSpinner spinner1;
    private JSpinner spinner2;
    private JSpinner spinner3;
    private JButton addPackedLeafBox;
    private JButton addPackedBagBox;
    private JLabel UnpackedBook;
    private JLabel UnpackedLeaf;
    private JLabel UnpackedBag;
    private JLabel packedBook;
    private JLabel packedLeaf;
    private JLabel packedBag;
    private JButton packShipment;
    private JButton resetShipment;
    private JButton dropShipment;
    private JSpinner searchSpinner;
    private JButton findPallet;
    private JButton shipDetails;

    // following variables are made static to be accessed from the other classes
    protected static Warehouse theWarehouse;
    protected static Shipment currShipment;
    protected static Pallet searchedPallet;

    public MainFrame() {
        // all the basic dialog configuration and GUI component setup
        setSize(750, 575);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setIconImage(new ImageIcon("img.jpg").getImage());
        setTitle("WarehouseManager");
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');
        menuBar.add(menu);
        JMenuItem jmiLoad = new JMenuItem("Load");
        jmiLoad.setMnemonic('L');
        JMenuItem jmiSave = new JMenuItem("Save");
        jmiSave.setMnemonic('S');
        JMenuItem jmiExit = new JMenuItem("Exit");
        jmiExit.setMnemonic('E');
        JMenuItem jmiDebug = new JMenuItem("DEBUG");
        jmiDebug.setMnemonic('D');
        menu.add(jmiLoad);
        menu.add(jmiSave);
        menu.add(jmiDebug);
        menu.add(jmiExit);

        // forward and backward icons to navigate through shipments
        foreButton.setText("▶");
        backButton.setText("◀");

        // custom color to accentuate navigation buttons
        foreButton.setForeground(new Color(199, 92, 8));
        backButton.setForeground(new Color(199, 92, 8));

        // spinner models avoid the need for input validation by allowing only values
        // from 1 to 100, in steps of 1
        // if any garbage values/characters are input into the spinner, it is simply ignored
        spinner1.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        spinner2.setModel(new SpinnerNumberModel(1, 1, 100, 1));
        spinner3.setModel(new SpinnerNumberModel(1, 1, 100, 1));

        // progress bar was a creative idea for additional functionality of my choice
        // it displays the progress of the current shipment as a result of packed pallets/total pallets*100%
        // custom color was applied here as well for nice visual contrast
        progressBar1.setForeground(new Color(199, 92, 8));

        // the main warehouse is created
        // this same warehouse will be used all around the program to manipulate/access/display data
        theWarehouse = new Warehouse();

        // the default current shipment is assigned
        currShipment = theWarehouse.currentShipment;

        // the login object created and saved in LoginDialog is stored in a local object here
        Login theLogin = LoginDialog.log;

        // welcome text welcomes user by username
        welcome.setText("Welcome, "+ theLogin.getCurrUser());

        // page is updated
        updatePage();

        // file chooser is called for load functionality
        jmiLoad.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int i = fileChooser.showOpenDialog(this);
            if (i==JFileChooser.APPROVE_OPTION)
            {
                File f = fileChooser.getSelectedFile();
                String path = f.getPath();
                load(path);
            }
            // current shipment is re-assigned and page updated to reflect newly loaded state
            currShipment = theWarehouse.currentShipment;
            updatePage();
        });

        // file chooser is called for save functionality
        jmiSave.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int i = fileChooser.showSaveDialog(this);
            if (i==JFileChooser.APPROVE_OPTION)
            {
                File f = fileChooser.getSelectedFile();
                String path = f.getPath();
                save(path);
            }
        });

        // debug prints the entire warehouse to the console for analysis
        jmiDebug.addActionListener(e -> System.out.println(theWarehouse));

        // exit calls a confirmation dialog and if confirmed closes the program
        jmiExit.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit?", "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer==JOptionPane.YES_OPTION)
                System.exit(0);
        });

        // back button checks whether there is an older shipment, and if it exists,
        // it is assigned to the current shipment variable, and the page is updated
        backButton.addActionListener(e -> {
            if (theWarehouse.shipments.containsKey(currShipment.getShipmentID()-1)){
                currShipment = theWarehouse.shipments.get(currShipment.getShipmentID()-1);
                updatePage();
            }
        });

        // same as the back button, but it checks if there is a shipment in the year after the current shipment
        foreButton.addActionListener(e -> {
            if (theWarehouse.shipments.containsKey(currShipment.getShipmentID()+1)){
                currShipment = theWarehouse.shipments.get(currShipment.getShipmentID()+1);
                updatePage();
            }
        });

        // edit pallets button opens shipment edit dialog
        editPallets.addActionListener(e -> new ShipmentEditDialog());

        // pressing submit for a "pack pallet" actually sets the submitted number of waiting pallets
        // of the specified pallet type to status "packed", and updates the displayed values
        // and progress bar accordingly
        addPackedBookBoxes.addActionListener(e -> {
            // packs the specified number of book pallets
            currShipment.packBookPallets((int)spinner1.getValue());

            // sets the labels to display the new number of packed book pallets
            packedBook.setText(String.valueOf(currShipment.getPackedBookNum()));

            // sets the labels to display the new number of unpacked book pallets
            UnpackedBook.setText(String.valueOf(currShipment.getBookPallets().size()-currShipment.getPackedBookNum()));
            setProgressBar();
        });

        // same function as the previous, but for leaflet pallets
        addPackedLeafBox.addActionListener(e -> {
            currShipment.packLeafPallets((int)spinner2.getValue());
            packedLeaf.setText(String.valueOf(currShipment.getPackedLeafNum()));
            UnpackedLeaf.setText(String.valueOf(currShipment.getLeafletPallets().size()-currShipment.getPackedLeafNum()));
            setProgressBar();
        });

        // same function as the previous, but for bag pallets
        addPackedBagBox.addActionListener(e -> {
            currShipment.packBagPallets((int)spinner3.getValue());
            packedBag.setText(String.valueOf(currShipment.getPackedBagNum()));
            UnpackedBag.setText(String.valueOf(currShipment.getBagPallets().size()-currShipment.getPackedBagNum()));
            setProgressBar();
        });

        // provides a shortcut to pack all pallets of the current shipment and updates page
        packShipment.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(this,
                    "Pack all pallets in this shipment?", "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer==JOptionPane.YES_OPTION) {
                currShipment.packPallets();
                updatePage();
            }
        });

        // provides a means of "undoing" a mistaken pack action, and resets the shipment
        resetShipment.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(this,
                    "Reset all pallets in this shipment?", "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer==JOptionPane.YES_OPTION) {
                currShipment.resetPallets();
                updatePage();
            }
        });

        // creates a new add shipments dialog and updates the page upon addition of new shipment
        newShipment.addActionListener(e -> {
            new AddShipmentsDialog();
            updatePage();
        });

        // after confirmation dialog, this button allows for the removal of a shipment
        dropShipment.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(this,
                    "Proceed with removal of shipment?", "Confirmation",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer==JOptionPane.YES_OPTION)
            {
                // if there is a shipment before the current shipment, current shipment is set to the subsequent
                // shipment, and then the original current shipment (now the previous shipment) is removed
                if (!theWarehouse.shipments.containsKey(currShipment.getShipmentID() - 1)) {
                    try {
                        // current shipment is moved one spot forward in the hashmap of shipments
                        currShipment = theWarehouse.shipments.get(currShipment.getShipmentID() + 1);

                        // the shipment we just moved away from is removed
                        theWarehouse.removeShipment(currShipment.getShipmentID() - 1);
                    } catch (WarehouseException ex) {
                        JOptionPane.showMessageDialog(null, "The shipment could not be removed.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                // same idea as the first part of the if clause, except it checks if there is a shipment
                // AFTER, rather than before
                // this ensures that currentShipment not deleted and then called, which would cause a null
                // pointer exception error
                else if (!theWarehouse.shipments.containsKey(currShipment.getShipmentID() + 1)) {
                    try {
                        currShipment = theWarehouse.shipments.get(currShipment.getShipmentID() - 1);
                        theWarehouse.removeShipment(currShipment.getShipmentID() + 1);
                    } catch (WarehouseException ex) {
                        JOptionPane.showMessageDialog(null, "The shipment could not be removed.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            // page updated to reflect changes
            updatePage();
        });

        // search function looks through entire warehouse to find pallet with specified ID
        findPallet.addActionListener(e -> {
            if (theWarehouse.findPalletInWarehouse((int)searchSpinner.getValue())!=null)
            {
                // gets search ID value from search spinner, given it is not null
                searchedPallet = theWarehouse.findPalletInWarehouse((int)searchSpinner.getValue());
                new SearchPalletDialog();
            } else {
                JOptionPane.showMessageDialog(null, "Pallet "+
                        searchSpinner.getValue()+" does not exist.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            updatePage();
        });

        // shipment details button executes a simple call of the shipment details dialog
        shipDetails.addActionListener(e -> new ShipmentDetails());

        // statistics button updates the page to ensure all numbers are up-to-date before calling statistics dialog
        statistics.addActionListener(e -> {
            updatePage();
            new StatisticsDialog();
        });
        setVisible(true);
    }

    /**
     * This method sets the main shipment progress bar to display the correct value
     */
    public void setProgressBar()
    {
        try {
            // divides the number of packed pallets in the current shipment by the number of
            // total pallets in the shipment, multiplies it by 100, and displays the resulting
            // percentage as a string and as a progress-meter
            progressBar1.setValue((int)((currShipment.getNumOfPackedPallets())
                    /(float)currShipment.getNumOfPallets()*100));
        }
        catch (Exception ArithmeticException)
        {
            // this shouldn't happen, but in the case of an arithmetic error like divide by 0, error message
            // is created to notify user that something went wrong
            JOptionPane.showMessageDialog(null, "Hmm...the math doesn't add up.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This function, called by the buttons, holds all the update functionality in one location for ease of maintenance
     */
    public void updatePage()
    {
        // progress bar is updated to reflect any changes in numbers
        // this ensures the data shown by progress bar is always relevant and accurate
        setProgressBar();

        // current shipment is refreshed to ensure accurate numbers
        theWarehouse.updateCurrShipment(currShipment);

        // update status function is called to ensure the status of the current shipment accurately reflects
        // the state of all the pallets in the shipment
        currShipment.updateShipmentStatus();

        // updates shipping id to match current shipment
        shipNo.setText("Shipment of "+currShipment.getShipmentID());

        // updates the numbers of packed and unpacked pallets
        packedBook.setText(String.valueOf(currShipment.getPackedBookNum()));
        UnpackedBook.setText(String.valueOf(currShipment.getBookPallets().size()-currShipment.getPackedBookNum()));
        packedLeaf.setText(String.valueOf(currShipment.getPackedLeafNum()));
        UnpackedLeaf.setText(String.valueOf(currShipment.getLeafletPallets().size()-currShipment.getPackedLeafNum()));
        packedBag.setText(String.valueOf(currShipment.getPackedBagNum()));
        UnpackedBag.setText(String.valueOf(currShipment.getBagPallets().size()-currShipment.getPackedBagNum()));

        // as an extra flourish of design and input validation, buttons are enabled/disabled according to
        // whether or not the actions can be executed
        foreButton.setEnabled(true);
        newShipment.setEnabled(false);
        dropShipment.setEnabled(false);

        // disables forward key when there is not a shipment in the following year
        if (!theWarehouse.shipments.containsKey(currShipment.getShipmentID()+1)) {
            foreButton.setEnabled(false);
            newShipment.setEnabled(true);
            dropShipment.setEnabled(true);
        }
        backButton.setEnabled(true);

        // disables backward key when there is not a shipment in the previous year
        if (!theWarehouse.shipments.containsKey(currShipment.getShipmentID()-1))
        {
            backButton.setEnabled(false);
            newShipment.setEnabled(true);
            dropShipment.setEnabled(true);
        }

        // if the current shipment is the only shipment in the warehouse,
        // the drop shipment button is disabled
        if (!theWarehouse.shipments.containsKey(currShipment.getShipmentID()-1)
                &&!theWarehouse.shipments.containsKey(currShipment.getShipmentID()+1))
            dropShipment.setEnabled(false);

        // disables pallet packing buttons when all pallets are packed
        if (currShipment.getNumOfPackedPallets()==currShipment.getNumOfPallets()) {
            addPackedBookBoxes.setEnabled(false);
            addPackedLeafBox.setEnabled(false);
            addPackedBagBox.setEnabled(false);
            spinner1.setEnabled(false);
            spinner2.setEnabled(false);
            spinner3.setEnabled(false);
        }
        // in all other cases ensures that buttons are refreshed to active
        else {
            addPackedBookBoxes.setEnabled(true);
            addPackedLeafBox.setEnabled(true);
            addPackedBagBox.setEnabled(true);
            spinner1.setEnabled(true);
            spinner2.setEnabled(true);
            spinner3.setEnabled(true);
        }
        // pallet search spinner value is set to begin at the top of the book pallets stack
        // this is done for convenience and because it is realistically practical for my real-world needs
        // minimum value is the first possibly created pallet
        searchSpinner.setModel(new SpinnerNumberModel(currShipment.getBookPallets().peek().getPalletID(),
                1001, 20000, 1));

        // once again the spinner editor is used to display appropriate format
        searchSpinner.setEditor(new JSpinner.NumberEditor(searchSpinner, "#"));
    }

    /**
     * Save method calls file chooser and allows for the creation of a save file
     * @param fileName takes user filename and creates save file accordingly
     */
    private void save(String fileName){
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // saves the state of object "theWarehouse"
            out.writeObject(theWarehouse);
            out.close();
            fileOut.close();
            System.out.println("Saved in :"+fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Again activates file chooser and allows user to load a file of their choice
     * @param fileName provided to the load function via file chooser
     */
    private void load(String fileName) {
        FileInputStream fileIn;
        try {
            fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            theWarehouse = (Warehouse) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Loading from :" + fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.err.println("Warehouse class not found");
            c.printStackTrace();
        }
    }
}
