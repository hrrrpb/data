package storesmonitor.Stores;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import storesmonitor.data.productLink;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yi
 */
public class staplesStore extends abstractStore {
    public void isInStock(){
        Iterator<productLink> iter = links.iterator();
        
			        
	while(iter.hasNext()){
            productLink tmpLink = iter.next();     	
	    String tmpUrl = tmpLink.getUrlLink().replace("http://www","http://m");
            
            
           
	    try {
                URL tmpURL = new URL(tmpUrl);
			        	
                URLConnection conn = tmpURL.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
				                conn.getInputStream(), "UTF-8"));
                
                String inputLine;
		String title="";
		boolean inStock=false;
				        
		while ((inputLine = in.readLine()) != null) {
                    
                    if(inputLine.contains("pdpResponse.properties")){
                        String temp = inputLine.split(" ")[4];
                        title = temp.substring(1, temp.length()-1).replace("-", " ");
                        
                                
                        if(inputLine.contains("\"instock\":\"true\""))
                            inStock = true;
                        
                        break;
                        }
                    
                }
                in.close();
                
                if(!title.isEmpty()){
                    if(tmpLink.getTitle().isEmpty()) tmpLink.setTitle(title);
                    
                    if(inStock==false) {
					        	
                        System.out.println(title+ " : " + " is OOS at Staples ");
					        	
                        if(tmpLink.getInStock()) tmpLink.setInStock(false);
					        	
					        	
                    }

                    else{
                        System.out.println(title+ " : " + " In Stock Alert at Staples! " );
						         
			if(!tmpLink.getInStock()) tmpLink.setInStock(true);
                        
                    }
					        	
                        
                }
                else {
                    System.out.println("did not retreive data from staples store correctly.");
                    
                }
                    
                       
            }
            catch (Exception ex) {
                ex.printStackTrace();
					
                System.out.println("Staples scan error, ignore and pass \n");
                
            }
            
        }
    }
    
}
