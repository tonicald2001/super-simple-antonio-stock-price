package antonio.supersimple.stockprice.model;



public enum OrderType {
    BUY, SELL;
    
   
    @Override
    public String toString() {
        return name().toLowerCase();
    }


}
