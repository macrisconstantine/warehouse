package warehouse;

// BagPallet class inherits from superclass Pallet
public class BagPallet extends Pallet implements java.io.Serializable {

    /**
     * BagPallet constructor creates new bag pallets with the default number of boxes
     * and bags per box
     * @param status of pallet allows for creation of either packed or waiting pallets
     */
    public BagPallet(PalletStatus status) {
        super(status);
        int defaultBoxes=8;
        int defaultElements = 800;
        setElementsInBox(defaultElements);
        setNumOfBoxes(defaultBoxes);
        setContents("BAGS");
    }

    /**
     *
     * @return calculates total number of bags in a bag pallet
     */
    public int getTotalElements() {
        return getElementsInBox()*getNumOfBoxes();
    }

    @Override
    // simple toString method only for debugging purposes, not app functionality
    public String toString() {
        return "\nBagPallet{" + super.toString();
    }
}
