/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storesmonitor.Stores;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import storesmonitor.data.productLink;

/**
 *
 * @author Yi
 */
public class truStore extends abstractStore {
    public void isInStock(){
        Iterator<productLink> iter = links.iterator();
        
	        
	while(iter.hasNext()){
	        	
	    productLink tmpLink = iter.next();     	
	    String tmpUrl = tmpLink.getUrlLink();
            String title="";
	    
	    try {
                
		URL obj = new URL(tmpUrl);
		URLConnection con = obj.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String inputLine;
                
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains("itemMap[0]")) {
                        if (inputLine.contains("IN_STOCK")&&tmpLink.getInStock()==false)
                            tmpLink.setInStock(true);
                        else{
                            if(tmpLink.getInStock()==true) tmpLink.setInStock(false);
                        }
                       
                    }
                    
                    if (inputLine.contains("<title>")){
                        
                        title = inputLine.replace("<title>","").replace("</title>","").split("-",2)[0].trim();
                        tmpLink.setTitle(title);
                        
                    }
                        
                 
                }
                    
                
                in.close();
                if(tmpLink.getInStock()) System.out.println(tmpLink.getTitle() +" is in stock now at toysrus. ");
                else System.out.println(tmpLink.getTitle() +" is oos at toysrus. ");
	    	    	
            }	
	    catch(Exception e){
                e.printStackTrace();
	    	System.out.println("Error, pass "+"\n");
	    	    	
	    }
	    	     
		
		  
	        
        }
		
		
		
		  
	        
    }
       
    
}
