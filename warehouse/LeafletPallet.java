package warehouse;

// LeafletPallet class inherits from superclass Pallet
public class LeafletPallet extends Pallet implements java.io.Serializable {

    /**
     * LeafletPallet constructor creates new bag pallets with the default number of boxes
     * and bags per box
     * @param status of pallet allows for creation of either packed or waiting pallets
     */
    public LeafletPallet(PalletStatus status) {
        super(status);
        int defaultElements = 450;
        int defaultBoxes = 12;
        setElementsInBox(defaultElements);
        setNumOfBoxes(defaultBoxes);
        setContents("LEAFLETS");
    }

    /**
     *
     * @return calculates total number of leaflets in a leaflet pallet
     */
    public int getTotalElements() {
        return getElementsInBox()*getNumOfBoxes();
    }

    @Override
    // simple toString method only for debugging purposes, not app functionality
    public String toString() {
        return "\nLeafletPallet{" + super.toString();
    }
}
