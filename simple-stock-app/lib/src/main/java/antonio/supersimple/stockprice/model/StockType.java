package antonio.supersimple.stockprice.model;

import org.apache.log4j.Logger;

import antonio.supersimple.stockprice.model.exceptions.InvalidStockTypeException;

public enum StockType {
    
    
    TEA(0), POP(1),ALE(2),GIN(3),JOE(4);

    private static final Logger LOGGER = Logger.getLogger(StockType.class);
    
    @Override
    public String toString() {
        return name().toUpperCase();
    }
    private int code;
    private StockType(int code) {
        this.code = code;
      }

     public int getCode() {
        return code;
    }
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
