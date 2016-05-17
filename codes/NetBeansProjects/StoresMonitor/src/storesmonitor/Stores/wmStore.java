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
public class wmStore extends abstractStore {
    public void isInStock(){
        Iterator<productLink> iter = links.iterator();
        while(iter.hasNext()){
	        	
	    productLink tmpLink = iter.next();     	
	    String tmpUrl = tmpLink.getUrlLink();
            
            try{
                Document doc = Jsoup.connect(tmpUrl).userAgent("Mozilla").get();
                if(tmpLink.getTitle().isEmpty()) tmpLink.setTitle(doc.title());
                String html = doc.html();
                int index = html.indexOf("\"adContextJSON\"");
                String testStr = html.substring(index,index+200);
                
                if (doc.select("div.product-seller").text().contains("Walmart.com")&&
                    (testStr.contains("\"online\":true"))) {
                    
                    System.out.println(tmpLink.getTitle() + " : IN STOCK ALERT.");
                    if(!tmpLink.getInStock()) tmpLink.setInStock(true);
                    
                }
                
                else {
                    System.out.println(tmpLink.getTitle() + " : OOS.");
                    if(tmpLink.getInStock()) tmpLink.setInStock(false);
                }
                
            }
                
           
            catch(Exception ex){   
                
                ex.printStackTrace();
                
            }
            
        }
    }
    
    
    
}
