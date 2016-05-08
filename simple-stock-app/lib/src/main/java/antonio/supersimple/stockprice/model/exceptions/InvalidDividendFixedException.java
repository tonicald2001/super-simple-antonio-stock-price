package antonio.supersimple.stockprice.model.exceptions;


public class InvalidDividendFixedException extends Exception {

   
    /**
     * The InvalidDividendFixedException is a subclass of Exception 
     * This class is committed to represent an exception that should be throw by program when aninvalid fixed dividend is found
     * @author Antonio Calderon
     * @version 1.0
     
     */
    private static final long serialVersionUID = 639684371384524031L;

    public InvalidDividendFixedException(String message) {
        super("Invalid value for " + message);
    }

}
