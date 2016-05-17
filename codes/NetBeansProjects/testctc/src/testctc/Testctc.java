/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testctc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author yi
 */
public class Testctc {
    
    
    Map<String, Map> data = new HashMap();
    private static String appid = "6KJ9YT-WWEYRV72P2";


    
    //fetch country data and population data
    public void fetchData() {
        //retrieve gold medal rank info
        String input ="Olympic Gold Medals";
        try {
            String url = "http://api.wolframalpha.com/v2/query?appid=6KJ9YT-WWEYRV72P2&input=Olympic+Gold+Medals&format=plaintext&async=false&reinterpret=true";
            Document doc = Jsoup.connect(url).get();
            
            String[] lines = doc.text().split("\n");
            for (int i = 1; i< lines.length; i++) {
               
               
               int mid = lines[i].indexOf("|");
               String country = lines[i].substring(0, mid-1).trim();
               Integer rank;
               if (i==15){
                   String temp = lines[i].substring(mid+1).trim().split(" ")[0];
                   rank = Integer.parseInt(temp);
               }
               else{
                   rank = Integer.parseInt(lines[i].substring(mid+1).trim()) ;
               }
                
               
               Map detail = new HashMap();
               detail.put("Medal rank", rank);
               data.put(country, detail);
                
            }
               
              

        }
        
        catch(IOException ex){
            System.out.println("failed");
        }
            
        //retrieve country info
        
        try {
            
            
            String baseUrl = "http://api.wolframalpha.com/v2/query?appid=6KJ9YT-WWEYRV72P2&input=yourInput&format=plaintext&async=false&reinterpret=true";
            
            for(String country: data.keySet()) {
                String countryInput = country+"+demographics";
                String url = baseUrl.replace("yourInput", countryInput);
                Document doc = Jsoup.connect(url).get();
                String[] lines = doc.text().split("\n");
                
                Map detail = data.get(country);
                System.out.println(country);
                //retrieve data
                for(String line: lines){
                    if (country.equals("Union of Soviet Socialist Republics")){
                        detail.put("population", 287.0);
                        detail.put("life expectancy", 69.9);
                        
                    }
                    else{
                        if (line.contains("demographics population")) {
                            String searchStr  = "demographics population |";
                            int index = line.indexOf(searchStr) + searchStr.length();
                            Float population = Float.parseFloat(line.substring(index).trim().split(" ")[0]);
                            detail.put("population", population);
                            System.out.println("population:" + population);
                        }
                    
                        if (line.contains("life expectancy")) {
                            String searchStr  = "|";
                            int index = line.indexOf(searchStr);
                            Float lifeExpectancy = Float.parseFloat(line.substring(index+1).trim().split(" ")[0]);
                            detail.put("life expectancy", lifeExpectancy);
                            System.out.println("life expectancy" + lifeExpectancy);
                        }
                        
                    }
                    
                    
                    
                    
                    
                }
                
                
                
              
            }
            
            
        }
        
        catch(IOException ex){
            System.out.println("failed");
        }
        
        
     }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Testctc test = new Testctc();
        test.fetchData();
        
        
        
    }
    
}
