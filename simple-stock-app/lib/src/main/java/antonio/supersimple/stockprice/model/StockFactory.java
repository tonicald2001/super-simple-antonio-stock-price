package antonio.supersimple.stockprice.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import antonio.supersimple.stockprice.model.exceptions.InvalidDividendFixedException;
import antonio.supersimple.stockprice.model.exceptions.InvalidStockTypeException;

public class StockFactory {
    
   private static StockFactory instance=null;
   
   private StockFactory() {}
   
   public static StockFactory getInstance(){
       if (instance==null) {
           instance=new StockFactory();
           return instance;
       }else {
           
           return instance;
       }
        
   }
     
   
    public Stock getStockBySymbol(final String symbol) throws InvalidStockTypeException, InvalidDividendFixedException{
       
        final StockType stockType=StockType.getStockTypeBySymbol(symbol);
        
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
