package warehouse;

import org.jetbrains.annotations.NotNull;
import java.util.HashMap;

/**
 * Warehouse class is instantiated only once in the app. One warehouse stores all shipments.
 * Warehouse is responsible for managing shipments/pallets/boxes/elements and calculating cumulative statistics.
 *
 * Source of custom icon:
 * <a href="https://www.flaticon.com/free-icons/pallet"
 * title="pallet icons">Pallet icons created by ultimatearm - Flaticon</a>
 */
public class Warehouse implements java.io.Serializable {

    // hashmap used to match shipments with their IDs
    // not public because other classes need access to shipments and current shipment
    protected HashMap<Integer, Shipment> shipments = new HashMap<>();

    // current shipment will be accessed by MANY classes and functions
    // most of the displayed data in the menu is based on the current shipment
    protected Shipment currentShipment;

    /**
     * Only one constructor necessary.
     * Default warehouse builds and adds one default shipment and sets it to current shipment.
     */
    public Warehouse() {
        try{
            currentShipment = new Shipment();
            addShipment(currentShipment);
        }
        catch (WarehouseException ignored)
        {}
    }

    /**
     *
     * @param shipment shipment object of type Shipment is added to
     * @throws WarehouseException if a shipment with the supplied ID already exists.
     * There can be only one shipment per year (ID).
     */
    public void addShipment(@NotNull Shipment shipment) throws WarehouseException
    {
        // if shipments hashmap already has a shipment stored with the supplied ID, throws exception
        if (shipments.containsKey(shipment.getShipmentID()))
            throw new WarehouseException("Shipment already registered in warehouse.");

        // if exception not thrown, value shipment and key shipment ID are added to the hashmap
        shipments.put(shipment.getShipmentID(), shipment);
    }

    /**
     * Deletes a shipment from the warehouse.
     * @param shipmentId takes ID of the shipment to be deleted
     * @throws WarehouseException if the supplied shipment ID does not exist in warehouse
     */
    public void removeShipment(int shipmentId) throws WarehouseException
    {
        // if shipment not in warehouse, throws exception
        if (!shipments.containsKey(shipmentId))
            throw new WarehouseException("Selected shipment does not exist.");

        // deletes shipment value associated with shipment ID
        shipments.remove(shipmentId);
    }

    /**
     * @return number of shipments
     */
    public int numOfShipments()
    {
        // number of shipments found using size function to calculate number of key-value mappings in hashmap shipments.
        return shipments.size();
    }

    /**
     * Counts number of current shipments
     * @return shipments with status == current
     */
    public int numOfCurrShipments()
    {
        int n=0;

        // for each shipment value in shipments hashmap
        for (Shipment s : shipments.values())
        {
            // increment n for each shipment with status CURRENT
            if (s.getStatus()==Shipment.ShipmentStatus.CURRENT)
            n++;
        }
        return n;
    }

    /**
     * Same as previous function but for upcoming shipments
     * @return upcoming shipments
     */
    public int numOfUpShipments()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            // increment n for each shipment with status UPCOMING
            if (s.getStatus()==Shipment.ShipmentStatus.UPCOMING)
                n++;
        }
        return n;
    }

    /**
     * Same as previous function but for completed shipments
     * @return completed shipments
     */
    public int numOfCompShipments()
    {
        currentShipment.updateShipmentStatus();
        int n=0;
        for (Shipment s : shipments.values())
        {
            // increment n for each shipment with status COMPLETED
            if (s.getStatus()==Shipment.ShipmentStatus.COMPLETED)
                n++;
        }
        return n;
    }

    /**
     * Sets supplied shipment object as current shipment
     * @param currShip shipment to make current shipment
     */
    public void updateCurrShipment(Shipment currShip)
    {
        currentShipment = currShip;
    }

    /**
     * Finds a pallet in the warehouse using supplied ID.
     * @param n ID of searched pallet
     * @return pallet associated with given pallet ID
     */
    public Pallet findPalletInWarehouse(int n)
    {
        // initialize variable to hold searched pallet
        Pallet p = null;

        // for each shipment in the warehouse
        for (Shipment ship: shipments.values())
        {
            // execute Shipment function find pallet on each shipment
            p = ship.findPallet(n);

            // stop searching as soon as the pallet is found
            if (p!=null) break;
        }

        // return the searched pallet object
        return p;
    }

    /**
     * Applies shipment functions to each shipment to calculate total packed boxes
     * @return total number of packed boxes in the warehouse
     */
    public int getTotalPackedBoxes()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            // adds number of packed boxes of each shipment
            n+=s.totalNumOfPackedBoxes();
        }

        // total sum of all packed boxes in all pallets in all shipments
        return n;
    }

    // same as previous method, but for unpacked boxes
    public int getTotalUnpackedBoxes()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.totalNumOfUnpackedBoxes();
        }
        return n;
    }

    // same as previous methods, but for total packed elements
    public int getTotalPackedElements()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.totalNumOfPackedElements();
        }
        return n;
    }

    // same as previous methods, but for total unpacked elements
    public int getTotalUnpackedElements()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.totalNumOfUnpackedElements();
        }
        return n;
    }

    // same as previous methods, but more specific (used for statistics) for total unpacked book boxes
    public int getTotalUnpackedBookBox()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfUnpackedBookBox();
        }
        return n;
    }

    // same as previous methods, but more specific (used for statistics) for total unpacked bag boxes
    public int getTotalUnpackedBagBox()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfUnpackedBagBox();
        }
        return n;
    }

    // same as previous methods, but for total unpacked leaflet boxes
    public int getTotalUnpackedLeafBox()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfUnpackedLeafBox();
        }
        return n;
    }

    // same as previous methods, but for total packed book boxes
    public int getTotalPackedBookBox()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPackedBookBox();
        }
        return n;
    }

    // same as previous methods, but for total packed bag boxes
    public int getTotalPackedBagBox()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPackedBagBox();
        }
        return n;
    }

    // same as previous methods, but for total packed leaflet boxes
    public int getTotalPackedLeafBox()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPackedLeafBox();
        }
        return n;
    }

    // same as previous methods, but for total pallets
    public int getTotalNumOfPallets()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPallets();
        }
        return n;
    }

    // same as previous methods, but for total unpacked pallets
    public int getTotalNumOfUnpackedPallets()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=(s.getNumOfPallets()-s.getNumOfPackedPallets());
        }
        return n;
    }

    // same as previous methods, but for total packed pallets
    public int getTotalNumOfPackedPallets()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPackedPallets();
        }
        return n;
    }

    // same as previous methods, but more specific, for total packed book pallets
    public int getTotalNumOfPackedBookPallets()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPackedBookPallets();
        }
        return n;
    }

    // same as previous methods, but for total packed bag pallets
    public int getTotalNumOfPackedBagPallets()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPackedBagPallets();
        }
        return n;
    }

    // same as previous methods, but for total packed leaflet pallets
    public int getTotalNumOfPackedLeafPallets()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPackedLeafPallets();
        }
        return n;
    }

    // same as previous methods, but for total unpacked book pallets
    public int getTotalNumOfUnpackedBookPallets()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            // unpacked book pallets calculated as: size of book pallets stack - number of packed book pallets
            n+=(s.getBookPallets().size()-s.getNumOfPackedBookPallets());
        }
        return n;
    }

    // same as previous methods, but for total unpacked bag pallets
    public int getTotalNumOfUnpackedBagPallets()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=(s.getBagPallets().size()-s.getNumOfPackedBagPallets());
        }
        return n;
    }

    // same as previous methods, but for total unpacked leaflet pallets
    public int getTotalNumOfUnpackedLeafPallets()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=(s.getLeafletPallets().size()-s.getNumOfPackedLeafPallets());
        }
        return n;
    }

    // same as previous methods, but for total packed individual books
    public int getTotalNumOfPackedBook()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPackedBookElement();
        }
        return n;
    }

    // same as previous methods, but for total packed individual bags
    public int getTotalNumOfPackedBag()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPackedBagElement();
        }
        return n;
    }

    // same as previous methods, but for total packed individual leaflets
    public int getTotalNumOfPackedLeaf()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPackedLeafElement();
        }
        return n;
    }

    // same as previous methods, but for total unpacked individual books
    public int getTotalNumOfUnpackedBook()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfUnpackedBookElement();
        }
        return n;
    }

    // same as previous methods, but for total unpacked individual bags
    public int getTotalNumOfUnpackedBag()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfUnpackedBagElement();
        }
        return n;
    }

    // same as previous methods, but for total unpacked individual leaflets
    public int getTotalNumOfUnpackedLeaf()
    {
        int n=0;
        for (Shipment s : shipments.values())
        {
            n+=s.getNumOfPackedLeafElement();
        }
        return n;
    }

    // toString method prints out everything contained in the warehouse for debugging purposes
    public String toString() {
        return "\n\nWarehouse Shipments: \n" + shipments;
    }
}
