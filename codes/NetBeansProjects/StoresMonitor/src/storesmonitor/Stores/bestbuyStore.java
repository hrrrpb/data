/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storesmonitor.Stores;

import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import storesmonitor.data.productLink;

/**
 *
 * @author yi
 */
public class bestbuyStore extends abstractStore {
    public void isInStock(){
        Iterator<productLink> iter = links.iterator();
        while(iter.hasNext()){
            productLink tmpLink = iter.next();     	
	    String tmpUrl = tmpLink.getUrlLink();
            
            
            try{
                Document doc = Jsoup.connect(tmpUrl).userAgent("Mozilla").get();
                if(tmpLink.getTitle().isEmpty()) tmpLink.setTitle(doc.title());
                
                if(doc.select("div.ship-time-message").text().contains("Shipping: Not Available")){
                    System.out.println(tmpLink.getTitle()+ " : " + " is OOS ");
                    if(tmpLink.getInStock()) tmpLink.setInStock(false);
                    
                }
                else{
                    System.out.println(tmpLink.getTitle()+ " : " + " In Stock Alert! " );	    	    		
                    if(!tmpLink.getInStock()) tmpLink.setInStock(true);
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
