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
public class targetStore extends abstractStore{
    public void isInStock(){
        Iterator<productLink> iter = links.iterator();
        
	        
	while(iter.hasNext()){
	        	
	    productLink tmpLink = iter.next();     	
	    String tmpUrl = tmpLink.getUrlLink();
            
	    
	    try {
                
		Document doc = Jsoup.connect(tmpUrl).userAgent("Mozilla").get();
                
                if (tmpLink.getTitle().isEmpty()) 
                    tmpLink.setTitle (doc.title());
                
                String html = doc.html();
                int index = html.indexOf("Target.globals.refreshItems");
                String testStr = html.substring(index, index+4000);
                //System.out.println(testStr);
                int inventoryIndex = testStr.indexOf("\"inventory\"");
                String judgeStr = testStr.substring(inventoryIndex, inventoryIndex+200);
                
                if(judgeStr.contains("\"status\":\"in stock\"")){
                    
                    if (tmpLink.getInStock()==false)
                            tmpLink.setInStock(true);
                    System.out.println(tmpLink.getTitle()+ " : " + " is Back In Stock");
                }
                else {
                    
                    System.out.println(tmpLink.getTitle()+ " : " + " is OOS");
                    if(tmpLink.getInStock())
                        tmpLink.setInStock(false);
                        
                     
                }
                        
 	    	
            }	
	    catch(Exception e){
                e.printStackTrace();
	    	System.out.println("Error, pass "+"\n");
	    	    	
	    }
	    	     
		
		  
	        
        }
		
		
		
		  
	        
    }

		
		
		
		  
	        
    
    
}
