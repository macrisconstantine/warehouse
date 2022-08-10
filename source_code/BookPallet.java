package warehouse;

// BookPallet class inherits from superclass Pallet
public class BookPallet extends Pallet implements java.io.Serializable {

    /**
     * BookPallet constructor creates new bag pallets with the default number of boxes
     * and bags per box
     * @param status of pallet allows for creation of either packed or waiting pallets
     */
    public BookPallet(PalletStatus status) {
        super(status);
        int defaultBoxes=80;
        int defaultElements = 32;
        setElementsInBox(defaultElements);
        setNumOfBoxes(defaultBoxes);
        setContents("BOOKS");
    }

    /**
     *
     * @return calculates total number of books in a book pallet
     */
    public int getTotalElements() {
        return getElementsInBox()*getNumOfBoxes();
    }

    @Override
    // simple toString method only for debugging purposes, not app functionality
    public String toString() {
        return "\nBookPallet{" + super.toString();
    }
}
