package antonio.supersimple.stockprice.model;

import java.math.BigInteger;
import java.util.Date;
/**
 * The Trade class is the representation of a trade record.
 * @author Antonio Calderon
 * @version 1.0
 
 */
public class Trade {
    /**
     * ID for a trade record
     */ 
    private int id;
    /**
     * TimeStamp for a trade record
     */ 
    private Date timestamp;
    
    /**
     * Order type for a trade record
     */
    private OrderType type;
    
    /**
     * Stock type for a trade record
     */
    private StockType stockType;
    
    /**
     * Price for a trade record
     */
    
    private BigInteger price;
    /**
     * Quantity for a trade record
     */
    private BigInteger quantity;
    
  //Constructors
    /**
     * Default private Constructor 
     */
    public Trade() {}

    /**
     * Constructor
     * @param id This parameter defines the ID 
     * @param timestamp This parameter defines the timestamp 
     * @param stockType This parameter defines the stock type
     * @param price This parameter defines the price
     * @param price This parameter defines the quantity
     */
    public Trade(int id,Date timestamp, StockType stockType, BigInteger price, BigInteger quantity) {
        this.id=id;
        this.timestamp = timestamp;
        this.stockType = stockType;
        this.price = price;
        this.quantity = quantity;
    }

    //setter and getter methods
    
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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Trade [id=");
        builder.append(id);
        builder.append(", timestamp=");
        builder.append(timestamp);
        builder.append(", type=");
        builder.append(type);
        builder.append(", stockType=");
        builder.append(stockType);
        builder.append(", price=");
        builder.append(price);
        builder.append(", quantity=");
        builder.append(quantity);
        builder.append("]");
        return builder.toString();
    }


   

}