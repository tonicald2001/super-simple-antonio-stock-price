package antonio.supersimple.stockprice.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * StockTradeStore is based in a singleton class and is commited for collecting and maintain data trade records in the memory using a hashmap. 
 * @author Antonio Calderon
 * @version 1.0
 
 */
public class StockTradeStore {
   
   //Attributes
    
    /**
     * Unique instance of the class
     */
   private static StockTradeStore instance=null;
   //count ids
  
   /**
    * ID for a trade record
    */ 
   private int id=0;
   
   /**
    * Map used for collect the record trades. The keys are stock types and values are a List of trade records which belongs to this stock type.  
    */ 
   private Map<StockType, List<Trade>> hashMapStockType= Collections.synchronizedMap(new HashMap<StockType, List<Trade>>());
   
   //Constructors
   /**
    * Default private Constructor 
    */
   private StockTradeStore() {}
   
   
   //Methods
   /**
    * Get an instance of this class
    * @return an instance that will be the unique for this class.

    */
   public static StockTradeStore getInstance(){
       if (instance==null) {
           instance=new StockTradeStore();
           return instance;
       }else {
           
           return instance;
       }
        
   }
    
   /**
    * Get a synchronized set from HashMap.
    * @return a Set of StockType elements.

    */
   public  Set<StockType> getKeys() {
       return Collections.synchronizedSet(hashMapStockType.keySet());
       
   }
   
   
  
   
   /**
    * Get a  collection of Trade elements whose stock type corresponds to the value passed as parameter,
    * @param stockType The stock type.
    * @return the List of Trade elements which belongs to this kind of stock type.

    */
   
   public  List<Trade> getListTradesByStockType(final StockType stockType) {
       
       if (stockType!=null) {
           
           List<Trade> listTradeByStockType=hashMapStockType.get(stockType);
           if (listTradeByStockType==null) {
               listTradeByStockType=Collections.synchronizedList(new ArrayList<Trade>());
               hashMapStockType.put(stockType,listTradeByStockType);
           }
           return listTradeByStockType;
           
       }
       
       return null;
   }
   
   /**
    * Obtain the next ID for a Trade record.
    * @param stockType The stock type.
    * @return the List of Trade elements which belongs to this kind of stock type.

    */
   public synchronized int nextId(){
       return ++id;
   }
   
  
    
    
    /**
     * @return the hashMapStockType
     */
    public Map<StockType, List<Trade>> getHashMapStockType() {
        return hashMapStockType;
    }

    /**
     * @param hashMapStockType the hashMapStockType to set
     */
    public void setHashMapStockType(HashMap<StockType, List<Trade>> hashMapStockType) {
        this.hashMapStockType = hashMapStockType;
    }
}
