package antonio.supersimple.stockprice.model;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import antonio.supersimple.stockprice.model.exceptions.InvalidDividendFixedException;



/**
 * A {@link Stock} with fixed dividend.
 * Fixed dividend is in percent format.
 *
 */
public class PreferredStock extends Stock {

    private BigDecimal fixedDividend;

    public PreferredStock() {
        super();
    }

    public PreferredStock(StockType symbol, BigInteger lastDividend, BigDecimal fixedDividend, BigInteger parValue) throws InvalidDividendFixedException {
        super(symbol, lastDividend, parValue);

        validateFixedDividend(fixedDividend);
        this.fixedDividend = fixedDividend;
    }

    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }

    public void setFixedDividend(BigDecimal fixedDividend) throws InvalidDividendFixedException {
        validateFixedDividend(fixedDividend);
        this.fixedDividend = fixedDividend;
    }

    private void validateFixedDividend(BigDecimal fixedDividend) throws InvalidDividendFixedException {
        if (fixedDividend.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidDividendFixedException("fixedDividend");
        }
    }

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

    @Override
    public BigDecimal calculatePERatio() {
        if (getLastDividend().compareTo(BigInteger.ZERO) == 0) {
            return null;
        }
        return getPrice().divide(BigDecimal.valueOf(getLastDividend().longValue()), 3, RoundingMode.HALF_UP);

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
