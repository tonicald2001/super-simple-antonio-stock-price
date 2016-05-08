package antonio.supersimple.stockprice.model;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import antonio.supersimple.stockprice.model.exceptions.InvalidDividendFixedException;



/**
 * A {@link Stock} with fixed dividend.
 * Fixed dividend is in percent format.
 * @author Antonio Calderon
 * @version 1.0
 
 */
public class PreferredStock extends Stock {
    
    //Attributes
    
    /**
     * Fixed Dividend
     */
    private BigDecimal fixedDividend;

   //Constructors
    /**
     * Default Constructor 
     */
    public PreferredStock() {
        super();
    }
    /**
     * Constructor
     * @param symbol This parameter defines the type of a stock
     * @param lastDividend This parameter defines the last Dividend of a  stock
     * @param fixedDividend This parameter defines the fixed dividend as a percentage.
     * @param parValue This parameter defines the Par Value of a stock
     */
    public PreferredStock(StockType symbol, BigInteger lastDividend, BigDecimal fixedDividend, BigInteger parValue) throws InvalidDividendFixedException {
        super(symbol, lastDividend, parValue);

        validateFixedDividend(fixedDividend);
        this.fixedDividend = fixedDividend;
    }

    
    
    //Methods
    /**
     * Validate fixed Dividend
     * @param fixedDividend Fixed Dividend
     * @throws InvalidDividendFixedException if fixed dividend is less than 0 

     */
    private void validateFixedDividend(final BigDecimal fixedDividend) throws InvalidDividendFixedException {
        if (fixedDividend.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidDividendFixedException("fixedDividend");
        }
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

        BigDecimal fixedDividendPercentRate = 
                fixedDividend.divide(BigDecimal.valueOf(100));
        BigDecimal dividend = fixedDividendPercentRate.multiply(BigDecimal.valueOf(
                getParValue().longValue()));
        return dividend.divide(getPrice(), 6, RoundingMode.HALF_UP);
    }
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
    //Set and get methods
    
    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(BigDecimal fixedDividend) throws InvalidDividendFixedException {
        validateFixedDividend(fixedDividend);
        this.fixedDividend = fixedDividend;
    }
    

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PreferredStock");
        sb.append("{symbol='").append(getSymbol()).append('\'');
        sb.append(", lastDividend=").append(getLastDividend());
        sb.append(", fixedDividend=").append(getFixedDividend());
        sb.append(", parValue=").append(getParValue());
        sb.append(", price=").append(getPrice());
        sb.append('}');
        return sb.toString();
    }

}
