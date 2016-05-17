/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author yi
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
                String url = "http://www.target.com/p/lego-city-t-junction-curve-7281/-/A-15930421#prodSlot=medium_1_40&term=lego";
                Document doc = Jsoup.connect(url).get();
                
                System.out.println(doc.title());
                
//		URL obj = new URL("http://www.target.com/p/lego-disney-princess-palace-pets-royal-castle-41142/-/A-49121188#prodSlot=medium_1_11&term=lego");
//		URLConnection con = obj.openConnection();
//
//		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
//                String inputLine;
//                
//                while ((inputLine = in.readLine()) != null) {
//                    
//                    System.out.println(inputLine);
                    //if (inputLine.contains("itemMap[0]")) {
//                        if (inputLine.contains("IN_STOCK")&&tmpLink.getInStock()==false)
//                            tmpLink.setInStock(true);
//                        else{
//                            if(tmpLink.getInStock()==true) tmpLink.setInStock(false);
//                        }
//                       
//                    }
//                    
//                    if (inputLine.contains("<title>")){
//                        
//                        title = inputLine.replace("<title>","").replace("</title>","").split("-",2)[0].trim();
//                        tmpLink.setTitle(title);
//                        
//                    }
//                        
                 
                
                    
                
//                in.close();
//                if(tmpLink.getInStock()) System.out.println(tmpLink.getTitle() +" is in stock now at toysrus. ");
//                else System.out.println(tmpLink.getTitle() +" is oos at toysrus. ");
	    	    	
            }	
	    catch(Exception e){
                e.printStackTrace();
	    	System.out.println("Error, pass "+"\n");
	    	    	
	    }
	    	     
		
		  
	        
        }
		
		
		
		  
	        
   

		
        
        
    
    
}
