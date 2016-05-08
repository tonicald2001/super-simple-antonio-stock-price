package antonio.supersimple.stockprice.model.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import antonio.supersimple.stockprice.model.Trade;
/**
 * The StockPricesCalculator is a class commited to calculation  
 * @author Antonio Calderon
 * @version 1.0

 */
public class StockPricesCalculator {

    /**
     * Calculate a division between two big decimals,
     * @param dividend The dividend.
     * @param divider The divider
     * @return a BigDecimal as a result of the division with accurates of 6 decimals and Rounding Half up

     */
    private static BigDecimal divide(final BigDecimal dividend,final BigDecimal divider) {

        if (dividend!=null && divider!=null) {

            if (divider.compareTo(BigDecimal.ZERO) == 0) {
                return null;
            }
            return dividend.divide(BigDecimal.valueOf(divider.longValue()), 6, RoundingMode.HALF_UP);
        }
        return null;

    }
    /**
     * Calculate Stock Price for a Trade List for a given stock,
     * @param listTrades The list of Trades for a definite stock type.
     * @return a BigDecimal as the Stock price for a given stock

     */

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

            return StockPricesCalculator.divide(sumTradeXQuantity,sumQuantity);
        }
        return null;
    }

    /**
     * Calculate time differences time in minutes between the current date and a timnestamp trade date,
     * @param currentDate cuurent date in format yyyy-MM-dd hh:mm:ss
     * @param tradeDate cuurent date
     * @return a long as the time difference in minutes between these dates, and -1 when an exception ocurred or one date is  null.

     */
    public static long calculateDifferencesInMinutes(final String currentDate,final String tradeDate) {

        final long MILLSECS_PER_MIN =  60 * 1000; //Milisegundos per minute 

        if (currentDate!=null && tradeDate!=null) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{

                Date date1 = formatter.parse(currentDate);
                Date date2 = formatter.parse(tradeDate);
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



