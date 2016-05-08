package antonio.supersimple.stockprice.model;

import org.apache.log4j.Logger;

import antonio.supersimple.stockprice.model.exceptions.InvalidStockTypeException;
/**
 * Enum class represents Stock Type
 * @author Antonio Calderon
 * @version 1.0
 
 */
public enum StockType {
    
    //values
    TEA(0), POP(1),ALE(2),GIN(3),JOE(4);

    private static final Logger LOGGER = Logger.getLogger(StockType.class);
    
    @Override
    public String toString() {
        return name().toUpperCase();
    }
    
    /**
     * code of Stock Type
     */
    private int code;
    private StockType(int code) {
        this.code = code;
      }

     public int getCode() {
        return code;
    }
   
     /**
      * Obtain Stock Type by its name
      * @param type The name of the stock type
      * @return The  Stock type object that corresponds to that name
      * @throws InvalidStockTypeException if the name passed as parameter is not valid
       */
     public static StockType getStockTypeBySymbol(final String symbol) throws InvalidStockTypeException {
        
        StockType type;
        try  {
             type=StockType.valueOf(symbol);
             return type;
        }
        catch(IllegalArgumentException e){
           LOGGER.error("Invalid Stock Type", e); 
           throw new InvalidStockTypeException("stock type");
            
        }
            
    }
    
    
   
     
}
