package antonio.supersimple.stockprice.model;

/**
 * Enum class represents Order Type
 * @author Antonio Calderon
 * @version 1.0
 
 */

public enum OrderType {
    BUY, SELL;
    
   
    @Override
    public String toString() {
        return name().toLowerCase();
    }


}
