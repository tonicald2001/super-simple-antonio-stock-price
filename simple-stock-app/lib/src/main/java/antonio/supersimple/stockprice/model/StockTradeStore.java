package antonio.supersimple.stockprice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class StockTradeStore {
    
   private static StockTradeStore instance=null;
   //count ids
   private int id=0;
   private HashMap<StockType, List<Trade>> hashMapStockType=new HashMap<StockType, List<Trade>>();
   private StockTradeStore() {}
   
   public static StockTradeStore getInstance(){
       if (instance==null) {
           instance=new StockTradeStore();
           return instance;
       }else {
           
           return instance;
       }
        
   }
     
   public Set<StockType> getKeys() {
       return hashMapStockType.keySet();
       
   }
   
   
   
   public List<Trade> getListTradesByStockType(final StockType stockType) {
       
       if (stockType!=null) {
           
           List<Trade> listTradeByStockType=hashMapStockType.get(stockType);
           if (listTradeByStockType==null) {
               listTradeByStockType=new ArrayList<Trade>();
               hashMapStockType.put(stockType,listTradeByStockType);
           }
           return listTradeByStockType;
           
       }
       
       return null;
   }
   
   
   public int nextId(){
       return ++id;
   }
   
  
    
    
    /**
     * @return the hashMapStockType
     */
    public HashMap<StockType, List<Trade>> getHashMapStockType() {
        return hashMapStockType;
    }

    /**
     * @param hashMapStockType the hashMapStockType to set
     */
    public void setHashMapStockType(HashMap<StockType, List<Trade>> hashMapStockType) {
        this.hashMapStockType = hashMapStockType;
    }
}
