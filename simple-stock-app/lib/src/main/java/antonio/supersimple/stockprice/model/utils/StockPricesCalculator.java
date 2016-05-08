package antonio.supersimple.stockprice.model.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import antonio.supersimple.stockprice.model.Trade;

public class StockPricesCalculator {
    
    
    public static BigDecimal calculateStockPrice(final BigDecimal dividend,final BigDecimal divisor) {
        
        if (dividend!=null && divisor!=null) {
        
            if (divisor.compareTo(BigDecimal.ZERO) == 0) {
                return null;
            }
            return dividend.divide(BigDecimal.valueOf(divisor.longValue()), 6, RoundingMode.HALF_UP);
        }
        return null;
        
    }
   
    public static BigDecimal calculateStockPrice(final List<Trade> listTrades) {
    
        if (listTrades!=null) {
            BigDecimal sumQuantity=new BigDecimal("0");
            BigDecimal sumTradeXQuantity=new BigDecimal("0");
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date actualDate=new Date();
            for (Trade trade:listTrades) {
                String strActualDate=formatter.format(actualDate);
                String strTradeDate=formatter.format(trade.getTimestamp());
                BigDecimal tradeQuantity=new BigDecimal(trade.getQuantity().longValue());
                BigDecimal tradePrice=new BigDecimal(trade.getPrice().longValue());
                long diff=StockPricesCalculator.calculateDifferencesInMinutes(strActualDate, strTradeDate);
                if (diff>=0 && diff<=15) {
                    BigDecimal multiply=tradeQuantity.multiply(tradePrice);
                    sumTradeXQuantity=sumTradeXQuantity.add(multiply);
                    sumQuantity=sumQuantity.add(tradeQuantity);
                 }
                 
            }
            
            return StockPricesCalculator.calculateStockPrice(sumTradeXQuantity,sumQuantity);
        }
        return null;
    }


    public static long calculateDifferencesInMinutes(final String currentDate,final String dateToCompare) {

        final long MILLSECS_PER_MIN =  60 * 1000; //Milisegundos per minute 

        if (currentDate!=null && dateToCompare!=null) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try{

                Date date1 = formatter.parse(currentDate);
                Date date2 = formatter.parse(dateToCompare);
                if(date1.compareTo(date2)>=0) {
                    long difference = ( date1.getTime() - date2.getTime() )/MILLSECS_PER_MIN; 
                    return difference;
                }


            }
            catch(ParseException ex){
                return -1; 
            }



        }  
        return -1; 
    }  

} 
    
    
    
    