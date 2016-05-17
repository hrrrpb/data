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
 * @author Yi
 */
public class amazonStore extends abstractStore{
    
    public void isInStock(){
        Iterator<productLink> iter = links.iterator();
        while(iter.hasNext()){
	        	
	    productLink tmpLink = iter.next();     	
	    String tmpUrl = tmpLink.getUrlLink();
            
            try{
                Document doc = Jsoup.connect(tmpUrl).userAgent("Mozilla").get();
                if(tmpLink.getTitle().isEmpty()) tmpLink.setTitle(doc.title());
           
                String instockmsgjp = "������Ʒ�ϡ�Amazon.co.jp";
                String instockmsg = "Ships from and sold by Amazon.com";
                String instockmsguk ="Dispatched from and sold by Amazon";
			    	    	
                    if (doc.select("div#merchant-info").text().contains(instockmsgjp) ||
			doc.select("div#merchant-info").text().contains(instockmsg) ||
			doc.select("div#merchant-info").text().contains(instockmsguk)) {
                        
                        
			System.out.println(tmpLink.getTitle()+ " : " + " In Stock Alert! " );
			    	    		
			if(!tmpLink.getInStock()) tmpLink.setInStock(true);
						         
                    }
		
                    else{
			System.out.println(tmpLink.getTitle()+ " : " + " is OOS ");
			if(tmpLink.getInStock()) tmpLink.setInStock(false);
			    	    		
                    }
			    	    	
            }
			    	    	

            catch(Exception e){
                e.printStackTrace();
                System.out.println("Amazon scan error" +"\n");
            }
			    	     
	  
	        
        }
		
		
		
		  

    }
    
 	
    
}
