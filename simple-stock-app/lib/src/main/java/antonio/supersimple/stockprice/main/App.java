package antonio.supersimple.stockprice.main;




import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
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
       
   
    private static void calculateDividendYield(Scanner sc) {
        System.out.println(DASHES_SEPARATOR);
        System.out.println("View Dividend Yield");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter stock symbol:");
        String symbol = sc.nextLine();
        StockFactory stockFactory = StockFactory.getInstance();
        Stock stock=null; 
        try {
           stock=stockFactory.getStockBySymbol(symbol);
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
     
    
    
    
    
    /*
    
    private static void viewTradeInfo(Scanner sc) {
        System.out.println(DASHES_SEPARATOR);
        System.out.println("View Trade");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter trade id:");
        long tradeId = sc.nextLong();

        ExchangeClient exchange = new ExchangeClient();
        Trade trade = exchange.getTrade(tradeId);

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Trade " + trade.getId());
        System.out.println(DASHES_SEPARATOR);
        Date timestamp = trade.getTimestamp();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("Date: " + formatter.format(timestamp));
        System.out.println("Stock: " + trade.getStock());
        System.out.println("Price: " + trade.getPrice());
        System.out.println("Quantity: " + trade.getQuantity());
    }

    private static void viewOrderInfo(Scanner sc) {
        System.out.println(DASHES_SEPARATOR);
        System.out.println("View Order");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter order id:");
        BigInteger orderId = sc.nextBigInteger();

        ExchangeClient exchange = new ExchangeClient();
        Order order = exchange.getOrder(orderId);

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Order " + order.getId());
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Type: " + order.getType());
        System.out.println("Stock: " + order.getStock());
        System.out.println("Price: " + order.getPrice());
        System.out.println("Quantity: " + order.getQuantity());

        long tradeId = order.getTrade();
        String tradeOutput = (tradeId != 0) ? String.valueOf(tradeId) : "-";
        System.out.println("Trade: " + tradeOutput);
    }

    private static void viewIndex() {
        ExchangeClient exchange = new ExchangeClient();
        BigDecimal allShareIndex = exchange.getAllShareIndex();

        String pattern = "#####.###";
        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(Locale.UK);
        decimalFormat.applyPattern(pattern);

        System.out.println(DASHES_SEPARATOR);
        System.out.println("All Share Index: " + 
                decimalFormat.format(allShareIndex));
        System.out.println(DASHES_SEPARATOR);
    }

    private static void placeOrder(Scanner sc) {
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Placing New Order");
        System.out.println(DASHES_SEPARATOR);

        Stock stock = viewStockInfo(sc);

        Order order = new Order();
        order.setStock(stock.getSymbol());

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Order Type");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter order type:");
        System.out.println("1: Buy");
        System.out.println("2: Sell");
        OrderType orderType = null;
        switch (sc.nextInt()) {
        case 1:
            orderType = OrderType.BUY;
            break;
        case 2:
            orderType = OrderType.SELL;
            break;

        default:
            break;
        }
        order.setType(orderType);

        sc.nextLine();

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Price");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter " + orderType + " price in pennies:");
        BigInteger price = BigInteger.valueOf(Integer.valueOf(sc.nextLine()));
        order.setPrice(price);

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Quantity");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter number of shares to " + orderType + ":");
        BigInteger quantity = BigInteger.valueOf(Integer.valueOf(sc.nextLine()));
        order.setQuantity(quantity);

        ExchangeClient exchange = new ExchangeClient();
        Order placedOrder = exchange.placeOrder(order);

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Placed Order");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Order ID: " + placedOrder.getId());
    }

    private static Stock viewStockInfo(Scanner sc) {
        System.out.println(DASHES_SEPARATOR);
        System.out.println("View Stock");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter stock symbol:");
        String symbol = sc.nextLine();

        ExchangeClient exchange = new ExchangeClient();
        Stock stock = exchange.getStockBySymbol(symbol);

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
        System.console().writer().println("Price: " + 
                priceFormat.format(penniesToPounds(stock.getPrice())));

        System.console().writer().println("Par Value: " + 
                priceFormat.format(penniesToPounds(stock.getParValue())));

        System.console().writer().println("Last Dividend: " + 
                priceFormat.format(penniesToPounds(stock.getLastDividend())));

        if (stock instanceof PreferredStock) {
            BigDecimal fixedDividend = ((PreferredStock) stock).getFixedDividend();
            BigDecimal percentDividend = fixedDividend.divide(BigDecimal.valueOf(100));
            System.console().writer().println("Fixed Dividend: " + 
                    NumberFormat.getPercentInstance(Locale.UK).format(percentDividend));
        }
        System.console().writer().println("Dividend Yield: " + 
                NumberFormat.getInstance().format(stock.calculateDividendYield()));

        String peRatio = "";
        if (stock.calculatePERatio() == null) {
            peRatio = "-";
        } else {
            peRatio = NumberFormat.getInstance().format(stock.calculatePERatio());
        }
        System.console().writer().println("P/E Ratio: " + peRatio);

        return stock;
    }
  
    */
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    public static void main( String[] args ) {

        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        do {
            System.out.println(DASHES_SEPARATOR);
            System.out.println("GBCE Exchange Client");
            System.out.println(DASHES_SEPARATOR);
            System.out.println("Choose a number to select function:");
            System.out.println("1: View stock");
            System.out.println("2: View order");
            System.out.println("3: View trade");
            System.out.println("4: Place order");
            System.out.println("5: View GBCE All Share Index");
            System.out.println("6: Exit program");


            int function = sc.nextInt();
            sc.nextLine();

            switch (function) {
            case 1:
                viewStockInfo(sc);
                break;
            case 2:
                viewOrderInfo(sc);
                break;
            case 3:
                viewTradeInfo(sc);
                break;
            case 4:
                placeOrder(sc);
                break;
            case 5:
                viewIndex();
                break;
            case 6:
                exit = true;
                break;

            default:
                break;
            }

        } while (!exit);

        sc.close();
    }

    private static void viewTradeInfo(Scanner sc) {
        System.out.println(DASHES_SEPARATOR);
        System.out.println("View Trade");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter trade id:");
        long tradeId = sc.nextLong();

        ExchangeClient exchange = new ExchangeClient();
        Trade trade = exchange.getTrade(tradeId);

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Trade " + trade.getId());
        System.out.println(DASHES_SEPARATOR);
        Date timestamp = trade.getTimestamp();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("Date: " + formatter.format(timestamp));
        System.out.println("Stock: " + trade.getStock());
        System.out.println("Price: " + trade.getPrice());
        System.out.println("Quantity: " + trade.getQuantity());
    }

    private static void viewOrderInfo(Scanner sc) {
        System.out.println(DASHES_SEPARATOR);
        System.out.println("View Order");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter order id:");
        BigInteger orderId = sc.nextBigInteger();

        ExchangeClient exchange = new ExchangeClient();
        Order order = exchange.getOrder(orderId);

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Order " + order.getId());
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Type: " + order.getType());
        System.out.println("Stock: " + order.getStock());
        System.out.println("Price: " + order.getPrice());
        System.out.println("Quantity: " + order.getQuantity());

        long tradeId = order.getTrade();
        String tradeOutput = (tradeId != 0) ? String.valueOf(tradeId) : "-";
        System.out.println("Trade: " + tradeOutput);
    }

    private static void viewIndex() {
        ExchangeClient exchange = new ExchangeClient();
        BigDecimal allShareIndex = exchange.getAllShareIndex();

        String pattern = "#####.###";
        DecimalFormat decimalFormat = (DecimalFormat)
                NumberFormat.getNumberInstance(Locale.UK);
        decimalFormat.applyPattern(pattern);

        System.out.println(DASHES_SEPARATOR);
        System.out.println("All Share Index: " + 
                decimalFormat.format(allShareIndex));
        System.out.println(DASHES_SEPARATOR);
    }

    private static void placeOrder(Scanner sc) {
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Placing New Order");
        System.out.println(DASHES_SEPARATOR);

        Stock stock = viewStockInfo(sc);

        Order order = new Order();
        order.setStock(stock.getSymbol());

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Order Type");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter order type:");
        System.out.println("1: Buy");
        System.out.println("2: Sell");
        OrderType orderType = null;
        switch (sc.nextInt()) {
        case 1:
            orderType = OrderType.BUY;
            break;
        case 2:
            orderType = OrderType.SELL;
            break;

        default:
            break;
        }
        order.setType(orderType);

        sc.nextLine();

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Price");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter " + orderType + " price in pennies:");
        BigInteger price = BigInteger.valueOf(Integer.valueOf(sc.nextLine()));
        order.setPrice(price);

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Quantity");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter number of shares to " + orderType + ":");
        BigInteger quantity = BigInteger.valueOf(Integer.valueOf(sc.nextLine()));
        order.setQuantity(quantity);

        ExchangeClient exchange = new ExchangeClient();
        Order placedOrder = exchange.placeOrder(order);

        System.out.println(DASHES_SEPARATOR);
        System.out.println("Placed Order");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Order ID: " + placedOrder.getId());
    }

    private static Stock viewStockInfo(Scanner sc) {
        System.out.println(DASHES_SEPARATOR);
        System.out.println("View Stock");
        System.out.println(DASHES_SEPARATOR);
        System.out.println("Enter stock symbol:");
        String symbol = sc.nextLine();

        ExchangeClient exchange = new ExchangeClient();
        Stock stock = exchange.getStockBySymbol(symbol);

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
        System.console().writer().println("Price: " + 
                priceFormat.format(penniesToPounds(stock.getPrice())));

        System.console().writer().println("Par Value: " + 
                priceFormat.format(penniesToPounds(stock.getParValue())));

        System.console().writer().println("Last Dividend: " + 
                priceFormat.format(penniesToPounds(stock.getLastDividend())));

        if (stock instanceof PreferredStock) {
            BigDecimal fixedDividend = ((PreferredStock) stock).getFixedDividend();
            BigDecimal percentDividend = fixedDividend.divide(BigDecimal.valueOf(100));
            System.console().writer().println("Fixed Dividend: " + 
                    NumberFormat.getPercentInstance(Locale.UK).format(percentDividend));
        }
        System.console().writer().println("Dividend Yield: " + 
                NumberFormat.getInstance().format(stock.calculateDividendYield()));

        String peRatio = "";
        if (stock.calculatePERatio() == null) {
            peRatio = "-";
        } else {
            peRatio = NumberFormat.getInstance().format(stock.calculatePERatio());
        }
        System.console().writer().println("P/E Ratio: " + peRatio);

        return stock;
    }
*/
    private static BigDecimal penniesToPounds(BigDecimal pennies) {
        return pennies.divide(BigDecimal.valueOf(100));
    }

    private static BigDecimal penniesToPounds(BigInteger pennies) {
        BigDecimal decimalPennies = BigDecimal.valueOf(pennies.longValue());
        return penniesToPounds(decimalPennies);
    }

}

