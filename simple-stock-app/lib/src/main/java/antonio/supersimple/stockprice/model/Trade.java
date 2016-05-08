package antonio.supersimple.stockprice.model;

import java.math.BigInteger;
import java.util.Date;

public class Trade {

    private int id;
    private Date timestamp;
    private OrderType type;
    private StockType stockType;
    private BigInteger price;
    private BigInteger quantity;

    public Trade() {}

   
    public Trade(int id,Date timestamp, StockType stockType, BigInteger price, BigInteger quantity) {
        this.id=id;
        this.timestamp = timestamp;
        this.stockType = stockType;
        this.price = price;
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public StockType getStockType() {
        return stockType;
    }

    public void setStockType(StockType stockType) {
        this.stockType = stockType;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public BigInteger getQuantity() {
        return quantity;
    }

    public void setQuantity(BigInteger quantity) {
        this.quantity = quantity;
    }
    
    /**
     * @return the type
     */
    public OrderType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(OrderType type) {
        this.type = type;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Trade");
        /*sb.append("{symbol='").append(getSymbol()).append('\'');
        sb.append(", lastDividend=").append(getLastDividend());
        sb.append(", fixedDividend=").append(getFixedDividend());
        sb.append(", parValue=").append(getParValue());
        sb.append('}');*/
        return sb.toString();
    }

}