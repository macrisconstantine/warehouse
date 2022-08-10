package warehouse;

import java.util.Stack;

/**
 * The shipment class is the largest class in the project, and it contains most of the functionality
 * necessary to properly manipulate pallet info.
 * Shipments are housed in the warehouse class, and they contain book, bag, and leaflet pallets.
 */
public class Shipment implements java.io.Serializable {

    // shipment status is determined by ratio of packed to unpacked pallets
    // a shipment can either be not yet begun, currently active, or complete
    public enum ShipmentStatus
    {
        UPCOMING, CURRENT, COMPLETED
    }
    private final int shipmentID;
    private ShipmentStatus status;

    // all pallets of a shipment are stored in stacks, which is accurate to real life, since the pallet
    // placed last is the one that is accessible to access/modify (last in, first out)
    private final Stack<BookPallet> bookPallets = new Stack<>();
    private final Stack<LeafletPallet> leafletPallets = new Stack<>();
    private final Stack<BagPallet> bagPallets = new Stack<>();

    /**
     * Non-parameterized constructor generates a default shipment with a preset ID, status, and number of
     * each pallet type.
     * The shipment ID is based on the year, and there is always at least and only 1 shipment/year.
     */
    public Shipment() {
        shipmentID = 2022;
        status = ShipmentStatus.CURRENT;

        // for loop creates 9 new book pallets of status WAITING and adds them to the appropriate stack
        for (int i =0; i<9; i++)
        {
            BookPallet pallet = new BookPallet(Pallet.PalletStatus.WAITING);
            addBookPallet(pallet);
        }
        // the same process is applied to the other pallet types
        for (int i =0; i<2; i++)
        {
            LeafletPallet pallet = new LeafletPallet(Pallet.PalletStatus.WAITING);
            addLeafletPallet(pallet);
        }
        for (int i =0; i<1; i++)
        {
            BagPallet pallet = new BagPallet(Pallet.PalletStatus.WAITING);
            addBagPallet(pallet);
        }

        // for the default constructor only, some already packed pallets are added to the shipment as well
        for (int i =0; i<21; i++)
        {
            BookPallet pallet = new BookPallet(Pallet.PalletStatus.PACKED);
            addBookPallet(pallet);
        }
        for (int i =0; i<4; i++)
        {
            LeafletPallet pallet = new LeafletPallet(Pallet.PalletStatus.PACKED);
            addLeafletPallet(pallet);
        }
        for (int i =0; i<2; i++)
        {
            BagPallet pallet = new BagPallet(Pallet.PalletStatus.PACKED);
            addBagPallet(pallet);
        }
    }

    /**
     * This is the main constructor used in the app to generate new, custom shipments
     * @param shipmentID the year of the shipment is specified
     * @param bookN takes the number of book pallets (sets as waiting by default)
     * @param leafN takes the number of leaflet pallets
     * @param bagN takes the number of bag pallets
     */
    public Shipment(int shipmentID, int bookN, int leafN, int bagN) {

        // for-loop same as in default constructor except it allows custom number of each pallet type
        for (int i =0; i<bookN; i++)
        {
            BookPallet pallet = new BookPallet(Pallet.PalletStatus.WAITING);
            addBookPallet(pallet);
        }
        for (int i =0; i<leafN; i++)
        {
            LeafletPallet pallet = new LeafletPallet(Pallet.PalletStatus.WAITING);
            addLeafletPallet(pallet);
        }
        for (int i =0; i<bagN; i++)
        {
            BagPallet pallet = new BagPallet(Pallet.PalletStatus.WAITING);
            addBagPallet(pallet);
        }
        //new shipments are set to upcoming by default, since all the pallets are unpacked
        this.status = ShipmentStatus.UPCOMING;
        this.shipmentID = shipmentID;
    }
    
    public int getShipmentID()
    {
        return shipmentID;
    }

    /**
     * This function scans over the stack of book pallets and tallies the number of packed pallets
     * @return the number of packed pallets
     */
    public int getNumOfPackedBookPallets()
    {
        // create new int variable to use to sum the number of packed pallets
        int pallets=0;

        // scan through book pallet stack
        for (Pallet p : bookPallets)
        {
            // if status of the pallet is packed, increment the variable
            if (p.getStatus()== Pallet.PalletStatus.PACKED)
                pallets++;
        }

        // returns total number of packed pallets in book pallet stack
        return pallets;
    }

    /**
     * This function scans over the stack of bag pallets and tallies the number of packed pallets
     * @return the number of packed pallets
     */
    public int getNumOfPackedBagPallets()
    {
        int pallets=0;
        for (Pallet p : bagPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.PACKED)
            pallets++;
        }
        return pallets;
    }

    /**
     * This function scans over the stack of leaflet pallets and tallies the number of packed pallets
     * @return the number of packed pallets
     */
    public int getNumOfPackedLeafPallets()
    {
        int pallets=0;
        for (Pallet p : leafletPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.PACKED)
            pallets++;
        }
        return pallets;
    }

    /**
     * @return sum of each type of pallet that has been packed
     */
    public int getNumOfPackedPallets()
    {
        return getNumOfPackedBookPallets()+getNumOfPackedBagPallets()+getNumOfPackedLeafPallets();
    }

    /**
     * @return total number of pallets based on cumulative stack sizes
     */
    public int getNumOfPallets() {
        return bookPallets.size()+leafletPallets.size()+bagPallets.size();
    }

    // basic setters and getters for encapsulation purposes
    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    // the following three functions are not currently useful beyond debugging purposes
    public Stack<BookPallet> getBookPallets() {
        return bookPallets;
    }

    public Stack<LeafletPallet> getLeafletPallets() {
        return leafletPallets;
    }

    public Stack<BagPallet> getBagPallets() {
        return bagPallets;
    }

    /**
     * Takes a specific book pallet and adds it to the stack of book pallets
     * @param pallet takes a pallet of type BookPallet
     */
    public void addBookPallet(BookPallet pallet)
    {
        bookPallets.push(pallet);
    }

    // function is repeated for each pallet type
    public void addLeafletPallet(LeafletPallet pallet)
    {
        leafletPallets.push(pallet);
    }

    public void addBagPallet(BagPallet pallet)
    {
        bagPallets.push(pallet);
    }

    /**
     * Calculates number of packed book boxes by scanning over the book pallet stack and summing the number
     * of boxes in each packed pallet.
     * @return total number of boxes in a packed
     */
    public int getNumOfPackedBookBox()
    {
        int boxes=0;
        for (BookPallet p : bookPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.PACKED)
                boxes+=p.getNumOfBoxes();
        }
        return boxes;
    }

    // same function is repeated for each pallet type (this is a pattern found commonly in this program, since
    // each stack must be manipulated separately)
    public int getNumOfPackedBagBox()
    {
        int boxes=0;
        for (BagPallet p : bagPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.PACKED)
                boxes+=p.getNumOfBoxes();
        }
        return boxes;
    }
    public int getNumOfPackedLeafBox()
    {
        int boxes=0;
        for (LeafletPallet p : leafletPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.PACKED)
                boxes+=p.getNumOfBoxes();
        }
        return boxes;
    }

    // returns sum of packed boxes by calling the relevant functions and summing together
    public int totalNumOfPackedBoxes()
    {
        return getNumOfPackedBagBox()+getNumOfPackedBookBox()+getNumOfPackedLeafBox();
    }

    // mostly the same function as the one used to calculate number of packed book boxes, but for unpacked
    public int getNumOfUnpackedBookBox()
    {
        int boxes=0;
        for (BookPallet p : bookPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.WAITING)
                boxes+=p.getNumOfBoxes();
        }
        return boxes;
    }
    public int getNumOfUnpackedBagBox()
    {
        int boxes=0;
        for (BagPallet p : bagPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.WAITING)
                boxes+=p.getNumOfBoxes();
        }
        return boxes;
    }
    public int getNumOfUnpackedLeafBox()
    {
        int boxes=0;
        for (LeafletPallet p : leafletPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.WAITING)
                boxes+=p.getNumOfBoxes();
        }
        return boxes;
    }

    // same as corresponding function for packed boxes
    public int totalNumOfUnpackedBoxes()
    {
        return getNumOfUnpackedBagBox()+getNumOfUnpackedBookBox()+getNumOfUnpackedLeafBox();
    }

    // the whole set of functions is repeated, but these are used to calculate the total number of elements
    // in the stack (based on the sum of the number of elements per box * number of boxes per pallet * number
    // of pallets in stack
    public int getNumOfUnpackedBookElement()
    {
        int boxes=0;
        for (BookPallet p : bookPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.WAITING)
                boxes+=p.getTotalElements();
        }
        return boxes;
    }
    public int getNumOfUnpackedBagElement()
    {
        int boxes=0;
        for (BagPallet p : bagPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.WAITING)
                boxes+=p.getTotalElements();
        }
        return boxes;
    }
    public int getNumOfUnpackedLeafElement()
    {
        int boxes=0;
        for (LeafletPallet p : leafletPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.WAITING)
                boxes+=p.getTotalElements();
        }
        return boxes;
    }
    public int totalNumOfUnpackedElements()
    {
        return getNumOfUnpackedBagElement()+getNumOfUnpackedBookElement()+getNumOfUnpackedLeafElement();
    }
    public int getNumOfPackedBookElement()
    {
        int boxes=0;
        for (BookPallet p : bookPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.PACKED)
                boxes+=p.getTotalElements();
        }
        return boxes;
    }
    public int getNumOfPackedBagElement()
    {
        int boxes=0;
        for (BagPallet p : bagPallets)
        {
            if (p.getStatus()==Pallet.PalletStatus.PACKED)
                boxes+=p.getTotalElements();
        }
        return boxes;
    }
    public int getNumOfPackedLeafElement()
    {
        int boxes=0;
        for (LeafletPallet p : leafletPallets)
        {
            if (p.getStatus()== Pallet.PalletStatus.PACKED)
                boxes+=p.getTotalElements();
        }
        return boxes;
    }

    // the absurd number of separate functions are almost all used for calculating statistics in the app
    public int totalNumOfPackedElements()
    {
        return getNumOfPackedBagElement()+getNumOfPackedBookElement()+getNumOfPackedLeafElement();
    }

    /**
     * Based on the inputted integer, a given number of book pallets with status WAITING are set to status PACKED.
     * @param n takes the number of book pallets to be packed.
     */
    public void packBookPallets(int n)
    {
        // only executes when an integer above zero is provided
        if (n>0) {

            // loops through book pallets stack
            for (BookPallet p : bookPallets) {

                // for each unpacked pallet in the stack, status is set to packed and n is decremented
                if (p.getStatus() == BookPallet.PalletStatus.WAITING) {
                    p.setStatus(BookPallet.PalletStatus.PACKED);
                    n--;

                    // stops packing pallets when specified number of pallets have been packed
                    if (n < 1) break;
                }
            }
        }
    }

    // same function is repeated for each stack of the different pallet types
    public void packLeafPallets(int n)
    {
        if (n>0) {
            for (LeafletPallet p : leafletPallets) {
                if (p.getStatus() == LeafletPallet.PalletStatus.WAITING) {
                    p.setStatus(LeafletPallet.PalletStatus.PACKED);
                    n--;
                    if (n < 1) break;
                }
            }
        }
    }
    public void packBagPallets(int n)
    {
        if (n>0) {
            for (BagPallet p : bagPallets) {
                if (p.getStatus() == BagPallet.PalletStatus.WAITING) {
                    p.setStatus(BagPallet.PalletStatus.PACKED);
                    n--;
                    if (n < 1) break;
                }
            }
        }
    }

    /**
     * Calculates the number of packed book pallets.
     * @return increments variable once for each packed book pallet and returns the sum
     */
    public int getPackedBookNum()
    {
        int n=0;
        for (BookPallet p : bookPallets) {
            if (p.getStatus() == BagPallet.PalletStatus.PACKED) {
                n++;
            }
        }
        return n;
    }

    // function is again repeated for each pallet type
    public int getPackedLeafNum()
    {
        int n=0;
        for (LeafletPallet p : leafletPallets) {
            if (p.getStatus() == LeafletPallet.PalletStatus.PACKED) {
                n++;
            }
        }
        return n;
    }

    public int getPackedBagNum()
    {
        int n=0;
        for (BagPallet p : bagPallets) {
            if (p.getStatus() == BagPallet.PalletStatus.PACKED) {
                n++;
            }
        }
        return n;
    }

    /**
     * Used to alter the number of boxes in a pallet of type book in a shipment
     * @param n takes the number to be set as the new number of boxes per pallet
     */
    public void setNumOfBookBoxes(int n)
    {
        for (Pallet p: bookPallets)
        {
            p.setNumOfBoxes(n);
        }
    }

    // again, function must be applied to each pallet type
    public void setNumOfLeafBoxes(int n)
    {
        for (Pallet p: leafletPallets)
        {
            p.setNumOfBoxes(n);
        }
    }

    public void setNumOfBagBoxes(int n)
    {
        for (Pallet p: bagPallets)
        {
            p.setNumOfBoxes(n);
        }
    }

    /**
     * Set of pallet-altering functions is repeated but for the number of elements per box.
     * This allows for full editing of the pallets of a given shipment, down to the level of elements per box.
     * @param n takes the number to be set as the new number of elements per box in each pallet in the shipment.
     */
    public void setNumOfBooks(int n)
    {
        for (Pallet p: bookPallets)
        {
            p.setElementsInBox(n);
        }
    }
    public void setNumOfLeaflets(int n)
    {
        for (Pallet p: leafletPallets)
        {
            p.setElementsInBox(n);
        }
    }
    public void setNumOfBags(int n)
    {
        for (Pallet p: bagPallets)
        {
            p.setElementsInBox(n);
        }
    }

    /**
     * Method used as a shortcut to pack all pallets.
     */
    public void packPallets()
    {
        for (BookPallet pallet: bookPallets)
        {
            pallet.setStatus(Pallet.PalletStatus.PACKED);
        }
        for (LeafletPallet pallet: leafletPallets)
        {
            pallet.setStatus(Pallet.PalletStatus.PACKED);
        }
        for (BagPallet pallet: bagPallets)
        {
            pallet.setStatus(Pallet.PalletStatus.PACKED);
        }
    }

    /**
     * Method used as a shortcut to reset all pallets of a shipment to status unpacked.
     */
    public void resetPallets()
    {
        for (BookPallet pallet: bookPallets)
        {
            pallet.setStatus(Pallet.PalletStatus.WAITING);
        }
        for (LeafletPallet pallet: leafletPallets)
        {
            pallet.setStatus(Pallet.PalletStatus.WAITING);
        }
        for (BagPallet pallet: bagPallets)
        {
            pallet.setStatus(Pallet.PalletStatus.WAITING);
        }
    }

    /**
     * Search function used to find a specified pallet in a shipment using id
     * @param id takes pallet ID
     * @return pallet with given ID
     */
    public Pallet findPallet(int id)
    {
        // initializes pallet variable to store found pallet
        Pallet palletFound=null;
        for (BookPallet pallet: bookPallets)
        {
            // loops through pallet stack, finds matching ID, and assigns to palletFound variable
            if (pallet.getPalletID()==id)
            {
                palletFound = pallet;
            }
        }
        for (LeafletPallet pallet: leafletPallets)
        {
            if (pallet.getPalletID()==id)
            {
                palletFound = pallet;
            }
        }
        for (BagPallet pallet: bagPallets)
        {
            if (pallet.getPalletID()==id)
            {
                palletFound = pallet;
            }
        }

        // returns pallet with matching ID after scanning all stacks
        return palletFound;
    }

    /**
     * Simple function that sets the status of the shipment according to the progress of pallet packing.
     */
    public void updateShipmentStatus()
    {
        // if all pallets in a shipment are packed, set the shipment status as completed
        if (getNumOfPackedPallets()==getNumOfPallets())
            setStatus(ShipmentStatus.COMPLETED);
        // if no pallets in a shipment are packed, set shipment status to upcoming
        else if (getNumOfPackedPallets()==0)
            setStatus(ShipmentStatus.UPCOMING);
        // if shipment has both packed and unpacked pallets, set shipment status to current
        else setStatus(ShipmentStatus.CURRENT);
    }

    @Override
    // use of toString method limited to debugging, since app functionality uses other means of
    // accessing the values of a shipment
    public String toString() {
        return "\nShipment{" +
                "shipmentID=" + shipmentID +
                ", \nstatus=" + status +
                ", \nnumOfWaitingPallets=" + (getNumOfPallets()-getNumOfPackedPallets()) +
                ", \nnumOfPackedPallets=" + getNumOfPackedPallets() +
                ", \nnumOfPallets=" + getNumOfPallets() +
                ", \nbookPallets=" + bookPallets +
                ", \nleafletPallets=" + leafletPallets +
                ", \nbagPallets=" + bagPallets +
                '}';
    }
}
