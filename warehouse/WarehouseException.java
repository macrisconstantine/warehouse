package warehouse;

// exception class for warehouse
public class WarehouseException extends Exception{

    public WarehouseException(String s)
    {
        // calls super class Exception and passes string argument
        super(s);
    }
}
