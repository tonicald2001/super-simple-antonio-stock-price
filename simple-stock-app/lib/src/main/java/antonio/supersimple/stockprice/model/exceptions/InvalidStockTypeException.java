package antonio.supersimple.stockprice.model.exceptions;


public class InvalidStockTypeException extends Exception {

   
    /**
     * 
     */
    private static final long serialVersionUID = 639684371384524031L;

    public InvalidStockTypeException(String message) {
        super("Invalid value  for " + message);
    }

}
