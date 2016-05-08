package antonio.supersimple.stockprice.main;




import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;

import antonio.supersimple.stockprice.model.CommonStock;
import antonio.supersimple.stockprice.model.OrderType;
import antonio.supersimple.stockprice.model.PreferredStock;
import antonio.supersimple.stockprice.model.Stock;
import antonio.supersimple.stockprice.model.StockFactory;
import antonio.supersimple.stockprice.model.StockTradeStore;
import antonio.supersimple.stockprice.model.StockType;
import antonio.supersimple.stockprice.model.Trade;
import antonio.supersimple.stockprice.model.exceptions.InvalidDividendFixedException;
import antonio.supersimple.stockprice.model.exceptions.InvalidStockTypeException;
import antonio.supersimple.stockprice.model.utils.StockPricesCalculator;

/**
 * The App is the client main class.  
 * @author Antonio Calderon
 * @version 1.0

 */

public class App 
{
    private static final String DASHES_SEPARATOR = "-----------------------" + 
            "--------------------------------------------------------";
    private static final Logger LOGGER = Logger.getLogger(App.class);

    public static void main( String[] args ) {

        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        do {
            System.out.println(DASHES_SEPARATOR);
            System.out.println("Choose a number to select function:");
            System.out.println("1: View dividend yield and PE/Ratio");
            System.out.println("2: Record a trade");
            System.out.println("3: View stock prices");
            System.out.println("4: View GBCE All Share Index");
            System.out.println("5: Exit program");


            int function = sc.nextInt();
            sc.nextLine();

            switch (function) {
            case 1:
                calculateDividendYield(sc);
                break;
            case 2:
                recordATrade(sc);
                break;
            case 3:
                calculateStockPrice(sc);
                break;
            case 4:
                calculateGeometricMean();
                break;

            case 5:
                exit = true;
                break;

            default:
                break;
            }

        } while (!exit);

        sc.close();
    }

    /**
     * Method that obtains Geometric Mean for all stocks.
     * For each given stock type calculate the stock price.Based in these stock prices, the method gets the geometric mean,
    */
    private static void calculateGeometricMean() {

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Calculate Geometric mean");
        StockTradeStore stockTradeStore = StockTradeStore.getInstance();
        Set<StockType> setStockTypes=stockTradeStore.getKeys();
        BigDecimal product=new BigDecimal("1.0");
        int count=0;
        for (StockType stockType:setStockTypes) {
            final List<Trade> listTrades=stockTradeStore.getListTradesByStockType(stockType);
            if (!listTrades.isEmpty()){
                final BigDecimal stockPrice=StockPricesCalculator.calculateStockPrice(listTrades);
                product=product.multiply(stockPrice);
                count++;
            }
        }

        BigDecimal bdGeometricMean=null;
        if (count>0) {
            double dProduct=product.doubleValue();
            double dGeometricMean=Math.pow(dProduct,1.0 / count);
            bdGeometricMean=new BigDecimal(dGeometricMean).divide(new BigDecimal("1.0"),3,RoundingMode.HALF_UP);

        } 
        String geometricMean = "";
        if (bdGeometricMean == null) {
            geometricMean = "-";
        } else {
            geometricMean = NumberFormat.getCurrencyInstance(Locale.UK).format(penniesToPounds(bdGeometricMean));
        }

        System.out.println("GeometricMean: " +  geometricMean);



    }    




    /**
     * Method that obtains  and shows the stock price for a given stock type.
     * param sc .This param is a Scanner object from console input
    */

    private static void calculateStockPrice(Scanner sc) {

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Calculate stock price");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Stock Type");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter stock symbol:");
        String symbol = sc.nextLine();
        StockTradeStore stockTradeStore = StockTradeStore.getInstance();

        StockType stockType=null;
        try {
            stockType=StockType.getStockTypeBySymbol(symbol);

        } catch (final InvalidStockTypeException e) {
            // TODO Auto-generated catch block
            System.out.println(DASHES_SEPARATOR);
            System.out.println("Stock type is not a valid argument'"+symbol+"'");
            System.out.println(DASHES_SEPARATOR);
            return;
        }

        //get all trades for a concrete type stockType
        final List<Trade> listTrades=stockTradeStore.getListTradesByStockType(stockType);
        BigDecimal bdStockPrice=StockPricesCalculator.calculateStockPrice(listTrades);
        String stockPrice = "";
        if (bdStockPrice == null) {
            stockPrice = "-";
        } else {
            stockPrice = NumberFormat.getCurrencyInstance(Locale.UK).format(penniesToPounds(bdStockPrice));
        }

        System.out.println("Stock Price: " + 
                stockPrice);

    }  



    /**
     * Method that stores a trade record in memory inside a collection in HashMap values given by stock type
     * param sc .This param is a Scanner object from console input
    */
    private static void recordATrade(Scanner sc) {

        Trade trade=new Trade();
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Record a trade");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Operation Type");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter operation type:");
        System.out.println("1: Buy");
        System.out.println("2: Sell");
        OrderType orderType = null;
        int opSymbol=0;
        try {
            opSymbol=sc.nextInt();
        }catch (final NumberFormatException e) {
            // TODO Auto-generated catch block
            LOGGER.error("Operation type  format exception",e);
            System.out.println(DASHES_SEPARATOR);
            System.out.println("Operation type should be an integer'"+opSymbol+"'");
            System.out.println(DASHES_SEPARATOR);
            return;
        }
        switch (opSymbol) {
        case 1:
            orderType = OrderType.BUY;
            break;
        case 2:
            orderType = OrderType.SELL;
            break;

        default: 
            break;
        }

        if (orderType==null) {

            System.out.println(DASHES_SEPARATOR);
            System.out.println("Order type is not a valid argument");
            System.out.println(DASHES_SEPARATOR);
            return; 
        }

        trade.setType(orderType);
        sc.nextLine();
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Stock Type");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter stock symbol:");
        String symbol = sc.nextLine();
        StockTradeStore stockTradeStore = StockTradeStore.getInstance();
        StockType stockType=null;
        try {
            stockType=StockType.getStockTypeBySymbol(symbol);
            trade.setStockType(stockType);
        } catch (final InvalidStockTypeException e) {
            // TODO Auto-generated catch block
            System.out.println(DASHES_SEPARATOR);
            System.out.println("Stock type is not a valid argument'"+symbol+"'");
            System.out.println(DASHES_SEPARATOR);
            return;
        }

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Price");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter " + orderType + " price in pennies:");
        BigInteger price=null;
        symbol = sc.nextLine();
        try {
            price = BigInteger.valueOf(Integer.valueOf(symbol));
            trade.setPrice(price);
        } catch (final NumberFormatException e) {
            // TODO Auto-generated catch block
            LOGGER.error("Price number format exception",e);

            System.out.println(DASHES_SEPARATOR);
            System.out.println("Price should be an integer'"+symbol+"'");
            System.out.println(DASHES_SEPARATOR);
            return;
        }

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Quantity");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter number of shares to " + orderType + ":");
        BigInteger quantity=null;
        symbol = sc.nextLine();
        try {
            quantity = BigInteger.valueOf(Integer.valueOf(symbol));
            trade.setQuantity(quantity);
        } catch (final NumberFormatException e) {
            // TODO Auto-generated catch block

            LOGGER.error("Quantity number format exception",e);
            System.out.println(DASHES_SEPARATOR);
            System.out.println("Quantity should be an integer'"+symbol+"'");
            System.out.println(DASHES_SEPARATOR);
            return;
        }
        System.out.println(DASHES_SEPARATOR);
        trade.setId(stockTradeStore.nextId());
        Date timestamp =new Date();
        trade.setTimestamp(timestamp);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("Record data to save");
        System.out.println("Trade ID: " + trade.getId());
        System.out.println("Order Type: " + trade.getType());
        System.out.println("Date: " + formatter.format(timestamp));
        System.out.println("Stock Type: " + trade.getStockType());
        System.out.println("Price in pennies: " + trade.getPrice());
        System.out.println("Quantity: " + trade.getQuantity());
        System.out.println(DASHES_SEPARATOR);
        final List<Trade> listTrades=stockTradeStore.getListTradesByStockType(stockType);
        listTrades.add(trade);

    }  


    /**
     * Method that obtain dividend yield and PE/RAtio
     * param sc .This param is a Scanner object from console input
    */
    private static void calculateDividendYield(Scanner sc) {
        System.out.println(DASHES_SEPARATOR);
        System.out.println("View Dividend Yield");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter stock symbol:");
        String symbol = sc.nextLine();
        
        Stock stock=null; 
        try {
            stock=StockFactory.getStockBySymbol(symbol);
        } catch (final InvalidStockTypeException e) {
            // TODO Auto-generated catch block
            System.out.println(DASHES_SEPARATOR);
            System.out.println("Stock type is not a valid argument'"+symbol+"'");
            System.out.println(DASHES_SEPARATOR);
            return;
        } catch(final InvalidDividendFixedException e) {
            System.out.println(DASHES_SEPARATOR);
            System.out.println("Fixed dividend is not a valid argument'"+symbol+"'");
            System.out.println(DASHES_SEPARATOR);
            return;

        }
        System.out.println(DASHES_SEPARATOR);
        System.out.println(stock.getSymbol());
        System.out.println(DASHES_SEPARATOR);

        String type = "";
        if (stock instanceof PreferredStock) {
            type = "Preferred";
        } else if (stock instanceof CommonStock) {
            type = "Common";
        }
        System.out.println("Type: " + type);

        NumberFormat priceFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        System.out.println("Price: " + 
                priceFormat.format(penniesToPounds(stock.getPrice())));

        System.out.println("Par Value: " + 
                priceFormat.format(penniesToPounds(stock.getParValue())));

        System.out.println("Last Dividend: " + 
                priceFormat.format(penniesToPounds(stock.getLastDividend())));

        if (stock instanceof PreferredStock) {
            BigDecimal fixedDividend = ((PreferredStock) stock).getFixedDividend();
            BigDecimal percentDividend = fixedDividend.divide(BigDecimal.valueOf(100));
            System.out.println("Fixed Dividend: " + 
                    NumberFormat.getPercentInstance(Locale.UK).format(percentDividend));
        }
        System.out.println("Dividend Yield: " + 
                NumberFormat.getInstance().format(stock.calculateDividendYield()));


        String peRatio = "";
        if (stock.calculatePERatio() == null) {
            peRatio = "-";
        } else {
            peRatio = NumberFormat.getInstance().format(stock.calculatePERatio());
        }
        System.out.println("P/E Ratio: " + peRatio);


    }

    /**
     * Method that transforms amount in pennis to amount in Pounds 
     * param pennies .Number of pennies to convert in Pounds
     * @return amount in pounds.
    */
    private static BigDecimal penniesToPounds(BigDecimal pennies) {
        return pennies.divide(BigDecimal.valueOf(100));
    }

    /**
     * Method that transforms amount in pennis to amount in pounds 
     * param pennies .Number of pennies to convert in Pounds
    */
    private static BigDecimal penniesToPounds(BigInteger pennies) {
        BigDecimal decimalPennies = BigDecimal.valueOf(pennies.longValue());
        return penniesToPounds(decimalPennies);
    }

}

