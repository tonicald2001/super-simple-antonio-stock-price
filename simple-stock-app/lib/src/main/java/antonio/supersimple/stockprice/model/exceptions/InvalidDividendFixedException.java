package antonio.supersimple.stockprice.model.exceptions;


public class InvalidDividendFixedException extends Exception {

   
    /**
     * 
     */
    private static final long serialVersionUID = 639684371384524031L;

    public InvalidDividendFixedException(String message) {
        super("Invalid value for " + message);
    }

}
