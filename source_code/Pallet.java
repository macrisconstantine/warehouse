package warehouse;

/**
 * This class is at the heart of the app
 * All created pallets of all types can be managed and tracked based on their unique IDs generated here
 */
public class Pallet implements java.io.Serializable {

    // very important enumeration distinguishes pallets to be packed from completed pallets
    public enum PalletStatus
    {
        WAITING, PACKED
    }

    // pallet IDs start from a default base of 1000 (first pallet will be 1001)
    public static final int INITIAL_PALLET_ID = 1000;
    private static int palletID = INITIAL_PALLET_ID;
    private PalletStatus status;
    private int elementsInBox;
    private String contents;
    private final int id;
    private int boxes;

    // default constructor initializes status as WAITING and sets default number of boxes and elements
    public Pallet()
    {
        this.status = PalletStatus.WAITING;
        this.elementsInBox = 32;
        this.boxes = 80;
        id = ++palletID;
    }

    // overloading used to provide option of creating a pallet of a specified status
    public Pallet(PalletStatus status)
    {
        this();
        this.status = status;
    }

    // another overloaded constructor for when only status, packed boxes, and total boxes are specified
    public Pallet(PalletStatus status, int packedBoxes, int totalBoxes) {
        this();
        if (packedBoxes==totalBoxes)
            this.status = PalletStatus.PACKED;
        else
            this.status = status;
    }

    /**
     * The complete constructor that allows for the creation of fully custom pallets
     * @param status provide whether a pallet is packed or waiting
     * @param packedBoxes provide the number of packed boxes
     * @param totalBoxes total number of boxes in pallet
     * (the final two params are not really utilized in the app yet, but they are preparation for further development
     */
    public Pallet(PalletStatus status, int packedBoxes, int totalBoxes, int elementsInBox) {
        this();
        if (packedBoxes==totalBoxes)
            this.status = PalletStatus.PACKED;
        else
            this.status = status;
        this.elementsInBox = elementsInBox;
    }

    // below are all the appropriate getters and setters
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getPalletID() {
        return id;
    }

    public PalletStatus getStatus() {
        return status;
    }

    public void setStatus(PalletStatus status) {
        this.status = status;
    }

    public int getElementsInBox() {
        return elementsInBox;
    }

    public void setElementsInBox(int elementsInBox) {
        this.elementsInBox = elementsInBox;
    }

    public int getNumOfBoxes() {
        return boxes;
    }

    public void setNumOfBoxes(int boxes) {
        this.boxes = boxes;
    }

    @Override
    // to string method primarily useful for debugging purposes at this point.
    public String toString() {
        return  "\npalletID=" + id +
                ", status=" + status +
                ", elementsInBox=" + elementsInBox +
                ", boxes=" + boxes +
                '}';
    }
}
