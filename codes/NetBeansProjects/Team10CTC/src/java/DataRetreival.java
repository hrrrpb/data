/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 *
 * @author yi
 */
@Named(value = "dataRetreival")
@SessionScoped

public class DataRetreival implements Serializable{
    
    private ArrayList<Country> data = new ArrayList();
    private static String appid = "6KJ9YT-WWEYRV72P2";
    private int index = 0;

    
    public DataRetreival() {
        
        
        
    }
    
    //fetch country data and population data
   public String fetchData() {
        //retrieve gold medal rank info
        String input ="Olympic Gold Medals";
        try {
            String url = "http://api.wolframalpha.com/v2/query?appid=6KJ9YT-WWEYRV72P2&input=Olympic+Gold+Medals&format=plaintext&async=false&reinterpret=true";
            Document doc = Jsoup.connect(url).get();
            
            String[] lines = doc.text().split("\n");
            for (int i = 1; i< lines.length; i++) {
               
               
               int mid = lines[i].indexOf("|");
               String country = lines[i].substring(0, mid-1).trim();
               Country current = new Country(country);
               Integer rank;
               if (i==15){
                   String temp = lines[i].substring(mid+1).trim().split(" ")[0];
                   rank = Integer.parseInt(temp);
                   current.setRank(rank);
                   
               }
               else{
                   rank = Integer.parseInt(lines[i].substring(mid+1).trim()) ;
                   current.setRank(rank);
               }
                
               
               data.add(current);
               System.out.println(country +" added");
                
            }
               
              

        }
        
        catch(IOException ex){
            ex.printStackTrace();
            System.out.println("failed");
        }
            
        //retrieve country info
        
        try {
            
            
            String baseUrl = "http://api.wolframalpha.com/v2/query?appid=6KJ9YT-WWEYRV72P2&input=yourInput&format=plaintext&async=false&reinterpret=true";
            
            
            for(Country country: data) {
                
                String countryInput = country.getName()+"+demographics";
                String url = baseUrl.replace("yourInput", countryInput);
                Document doc = Jsoup.connect(url).get();
                String[] lines = doc.text().split("\n");
                
                
                System.out.println(country);
                //retrieve data
                for(String line: lines){
                    if (country.getName().equals("Union of Soviet Socialist Republics")){
                        country.setPopulation(287.0);
                        country.setLifeExpectancy(69.9);
                        
                        
                    }
                    else{
                        if (line.contains("demographics population")) {
                            String searchStr  = "demographics population |";
                            int index = line.indexOf(searchStr) + searchStr.length();
                            Double population = Double.parseDouble(line.substring(index).trim().split(" ")[0]);
                            country.setPopulation(population);
                        }
                    
                        if (line.contains("life expectancy")) {
                            String searchStr  = "|";
                            int index = line.indexOf(searchStr);
                            Double lifeExpectancy = Double.parseDouble(line.substring(index+1).trim().split(" ")[0]);
                            country.setLifeExpectancy(lifeExpectancy);
                        }
                        
                    }
                    
   
                    
                }
                
                
               
              
            }
            
            
        }
        
        catch(IOException ex){
            System.out.println("failed");
        }
        
        return "table";
     }

    public ArrayList<Country> getData() {
        return data;
    }

    public void setData(ArrayList<Country> data) {
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    
   
   


    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        DataRetreival.appid = appid;
    }
   

 
    
}
