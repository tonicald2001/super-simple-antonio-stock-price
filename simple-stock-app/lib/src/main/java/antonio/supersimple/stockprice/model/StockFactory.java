package antonio.supersimple.stockprice.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import antonio.supersimple.stockprice.model.exceptions.InvalidDividendFixedException;
import antonio.supersimple.stockprice.model.exceptions.InvalidStockTypeException;
/**
 * Stock Factory under is a builder class factory for building stock classes depending on the type
 * @author Antonio Calderon
 * @version 1.0
 
 */
public class StockFactory {
     
   /**
    * Build and return different kinds of Stocks depending on stock type passed as parameter.
    * @param type The stock type represented by a string
    * @return an Stock object
    * @throws InvalidStockTypeException if the stock type  is not defined
    * @throws InvalidDividendFixedException if the fixed dividend is not valid
    */
    public static Stock getStockBySymbol(final String type) throws InvalidStockTypeException, InvalidDividendFixedException{
       
        final StockType stockType=StockType.getStockTypeBySymbol(type);
        
        Stock stock=null;
        
        switch(stockType.getCode()) {
         
        case 0: stock=new CommonStock(StockType.TEA,new BigInteger("0"),new BigInteger("100"));
                break;      
        case 1: stock=new CommonStock(StockType.POP,new BigInteger("8"),new BigInteger("100"));
                break;
        case 2: stock=new CommonStock(StockType.ALE,new BigInteger("23"),new BigInteger("60"));
                break;
        case 3: stock=new PreferredStock(StockType.GIN,new BigInteger("8"),new BigDecimal("2"), new BigInteger("100"));
                break;         
        case 4: stock=new CommonStock(StockType.JOE,new BigInteger("13"),new BigInteger("250"));
                break;
         
        default:
            break;       
                
        }
   
        return stock;
    
 }

    
    
}
