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
 * @author yi
 */
public class msStore extends abstractStore{
    public void isInStock(){
        Iterator<productLink> iter = links.iterator();
        
			        
	while(iter.hasNext()){
            productLink tmpLink = iter.next();     	
	    String tmpUrl = tmpLink.getUrlLink();
           
	    try {
                URL tmpURL = new URL(tmpUrl);
			        	
                URLConnection conn = tmpURL.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(
				                conn.getInputStream(), "UTF-8"));
                
                String inputLine;
		String title="";
		boolean inStock=true;
				        
		while ((inputLine = in.readLine()) != null) {
                    if(inputLine.contains("lpAddVars")){
                        if(inputLine.contains("ProductName")) {
                            String[] parseLines = inputLine.split("'");
                            title = parseLines[parseLines.length-2];
			}
                        if(inputLine.contains("Out of stock"))
                            inStock = false;
                        }
                    
                }
                in.close();
                
                if(!title.isEmpty()){
                    if(tmpLink.getTitle().isEmpty()) tmpLink.setTitle(title);
                    
                    if(inStock==false) {
					        	
                        System.out.println(title+ " : " + " is OOS at Microsoft Store ");
					        	
                        if(tmpLink.getInStock()) tmpLink.setInStock(false);
					        	
					        	
                    }

                    else{
                        System.out.println(title+ " : " + " In Stock Alert at Microsoft Store! " );
						         
			if(!tmpLink.getInStock()) tmpLink.setInStock(true);
                        
                    }
					        	
                        
                }
                else {
                    System.out.println("did not retreive data from microsoft store correctly.");
                    
                }
                    
                       
            }
            catch (Exception ex) {
                ex.printStackTrace();
					
                System.out.println("Microsoftstore scan error, ignore and pass \n");
                
            }
            
        }
    }
				        		
				        		
	
    
}
