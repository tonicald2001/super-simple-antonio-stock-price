package antonio.supersimple.stockprice.model;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
/**
 * A {@link Stock} without Fixed Dividend
 * @author Antonio Calderon
 * @version 1.0
 */
public class CommonStock extends Stock {

    //Constructors
    /**
     * Default Constructor 
     */
    public CommonStock() {
        super();
    }
    /**
     * Constructor
     * @param symbol This parameter defines the type of a stock
     * @param lastDividend This parameter defines the last Dividend of a  stock
     * @param parValue This parameter defines the Par Value of a stock
     */
    public CommonStock(StockType symbol, BigInteger lastDividend, BigInteger parValue) {
        super(symbol, lastDividend, parValue);
    }

    
    //Methods
    /**
     * Calculate the PE/Ratio.
     * @return the stocks' dividend yield, accurate to 6 decimal digits, 
     * and rounded half way up ({@link RoundingMode}).
     */
    @Override
    public BigDecimal calculatePERatio() {
        if (getLastDividend().compareTo(BigInteger.ZERO) == 0) {
            return null;
        }
        return getPrice().divide(BigDecimal.valueOf(getLastDividend().longValue()), 3, RoundingMode.HALF_UP);
    }
    
    /**
     * Calculate the Dividend yield
     * @return the stocks' dividend yield, accurate to 6 decimal digits, 
     * and rounded half way up ({@link RoundingMode}).
     */
    
    @Override
    public BigDecimal calculateDividendYield() {
        if (getPrice().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(getLastDividend().longValue()).
                divide(getPrice(), 6, RoundingMode.HALF_UP);
    }

    
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CommonStock");
        sb.append("{symbol='").append(getSymbol()).append('\'');
        sb.append(", lastDividend=").append(getLastDividend());
        sb.append(", parValue=").append(getParValue());
        sb.append(", price=").append(getPrice());
        sb.append('}');
        return sb.toString();
    }

}