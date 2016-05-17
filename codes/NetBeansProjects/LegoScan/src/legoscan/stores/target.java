/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legoscan.stores;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import legoscan.data.product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author yi
 */
public class target extends baseStore{
    
    private ArrayList<product> newList = new ArrayList();
    private ArrayList<product> lowerPriceList= new ArrayList();
    private String baseUrl ="http://www.target.com/s?searchTerm=lego&category=4694&resultsPerPage=120&sort=relevance&customPrice=true&minPrice=#&maxPrice=??";
    
    public target(){
        super();
        dataFile = new File("data/Target_Data.txt");
        outputFile = new File("data/Target_Output.txt");
        readData();
   
    }
    
    @Override
    public void scan(){
        
        
        System.out.println("Target Scan :");
        int[] priceRange = {0, 10, 15, 20, 30, 60, 500};
        
        for (int index = 0; index< priceRange.length-1; index++) {
            
            fetchDataByPrice(priceRange[index], priceRange[index+1]);   
        }
        
        //update the updates map
        if(!newList.isEmpty()) updates.put("New Items", newList);
        
        if(!lowerPriceList.isEmpty()) updates.put("Price Decreased",lowerPriceList);
        
        //save data
        saveData();
        
        
        if(!report().isEmpty()){
            saveOutput();
            sendNotification("Target 2 hour report", report());
        }
        else{
            System.out.println("No new updates");
        }
        
        
        //clear updates
        newList = new ArrayList();
        lowerPriceList = new ArrayList();
        updates = new HashMap();
        
    }
    
    
    private void updateData(Document doc) {
        List<Element> items = doc.select("li.tile.standard");
        
        for(Element item : items) {
            String priceStr="";
            String itemUrl = item.select("div.tileImage>a").attr("href");
            int startIndex = itemUrl.indexOf("/A-");
            int stopIndex = itemUrl.indexOf("#");
            String itemNum = itemUrl.substring(startIndex+"/A-".length(), stopIndex);
            
            if (item.select("p.price.price-label").text()!= null) 
                    priceStr= item.select("p.price.price-label").text();
            
            if(!priceStr.isEmpty()) {
                if(dataMap.get(itemNum)!=null&&priceStr.charAt(0)=='$') {
                
                
                product tempProduct = (product)dataMap.get(itemNum);
                float price = Float.parseFloat(priceStr.substring(1));
                if (tempProduct.getCurrent()> price) lowerPriceList.add(tempProduct);
                tempProduct.setCurrent(price);
                if (price > tempProduct.getHistoryHigh()) tempProduct.setHistoryHigh(price);
                if (price < tempProduct.getHistoryLow())   tempProduct.setHistoryLow(price);
           
            }
                
            if(dataMap.get(itemNum)==null&&priceStr.charAt(0)=='$') {
                    
                    float price = Float.parseFloat(priceStr.substring(1));
                    product newProduct = new product(itemNum, itemUrl, price, price, price);
                    dataMap.put(itemNum, newProduct);
                    newList.add(newProduct);
         
                
            }
                
            }
            
            
                
                    
               

        
        }

    }
    
    private void fetchDataByPrice(int lower, int higher) {
        
        System.out.println("Price range : " + lower +" to "+ higher);
        
        String currentUrl = baseUrl.replace("#", Integer.toString(lower))
                    .replace("??", Integer.toString(higher));

        try {
            Document doc = Jsoup.connect(currentUrl).get();
            int total = Integer.parseInt(doc.select("span#countMsg").text().split(" ")[0]);
            
            if (total<=120) {
                System.out.println("Total count :" + total);
                updateData(doc);
            }
            else {
                System.out.println("Spliting price range now...");
                int mid;
                if (lower==60) mid =100;
                else mid = (lower+higher)/2;
                fetchDataByPrice(lower,mid);
                fetchDataByPrice(mid, higher);
                 
            }
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       
        
    }
    
    
    
}
