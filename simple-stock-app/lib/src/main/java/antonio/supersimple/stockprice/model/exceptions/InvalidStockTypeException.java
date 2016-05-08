package antonio.supersimple.stockprice.model.exceptions;


public class InvalidStockTypeException extends Exception {

   
    /**
     *  serial version ID
     */
    private static final long serialVersionUID = 8823239309235909189L;

    /**
     * The InvalidStockTypeException is a subclass of Exception 
     * This class is committed to represent an exception that should be throw by program when try to get a stock type does not exist
     * @author Antonio Calderon
     * @version 1.0
     
     */
   
    public InvalidStockTypeException(String message) {
        super("Invalid value  for " + message);
    }

}
