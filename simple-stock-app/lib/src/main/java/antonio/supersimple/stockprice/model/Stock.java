

package antonio.supersimple.stockprice.model;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Stock is the abstract base class for all stocks
 *
 * @author Antonio Calderon
 * @version 1.0
 */


public abstract class Stock {
    
    //Attributes
    
    /**
     * Type of a stock ("TEA","POP,etc..)
     */
    private StockType symbol;
    /**
     * Last Dividend of a share
     */
    
    private BigInteger lastDividend;
    /**
     * Par Value of a share
     */
    private BigInteger parValue;
    
    /**
     * Price of share.
     */
    private BigDecimal price;
    
    
    //Constructors
    /**
     * Default Constructor 
     */
    public Stock() {}

    
    /**
     * Constructor
     * @param symbol This parameter defines the type of a stock
     * @param lastDividend This parameter defines the last Dividend of a  stock
     * @param par Value This parameter defines the Par Value of a stock
     * @param price This parameter defines the price of stock
     */
    public Stock(StockType symbol, BigInteger lastDividend, BigInteger parValue) {
        this.symbol = symbol;
        this.lastDividend = lastDividend;
        this.parValue = parValue;
        this.price = BigDecimal.valueOf(parValue.longValue());
    }

   
    /**
     * @return the symbol
     */
    public StockType getSymbol() {
        return symbol;
    }


    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(StockType symbol) {
        this.symbol = symbol;
    }


    /**
     * @return the lastDividend
     */
    public BigInteger getLastDividend() {
        return lastDividend;
    }


    /**
     * @param lastDividend the lastDividend to set
     */
    public void setLastDividend(BigInteger lastDividend) {
        this.lastDividend = lastDividend;
    }


    /**
     * @return the parValue
     */
    public BigInteger getParValue() {
        return parValue;
    }


    /**
     * @param parValue the parValue to set
     */
    public void setParValue(BigInteger parValue) {
        this.parValue = parValue;
    }


    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }


    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    /**
     * @return the stocks' dividend yield, accurate to 6 decimal digits, 
     * and rounded half way up ({@link RoundingMode}).
     */
    public abstract BigDecimal calculateDividendYield();

    /**
     * @return the stocks' P/E ratio, accurate to 3 decimal digits, and rounded 
     * half way up ({@link RoundingMode}), or {@code null} if undefined/unavailable.
     */
    public abstract BigDecimal calculatePERatio();

}