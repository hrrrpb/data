/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legoscan.stores;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import legoscan.data.product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author yi
 */
public class walmart extends baseStore {
    private ArrayList<product> newList = new ArrayList();
    private ArrayList<product> lowerPriceList= new ArrayList();
    private String baseUrl ="http://www.walmart.com/search/?query=lego&cat_id=4171_4186_1044000&grid=true&facet=brand:LEGO%7C%7Cretailer:Walmart.com&po=1&min_price=0&max_price=500&page=";
    
    
    public walmart(){
        super();
        dataFile = new File("data/Walmart_Data.txt");
        outputFile = new File("data/Walmart_Output.txt");
        readData();
   
    }
    
    @Override
    public void scan(){
        
        
        System.out.println("Walmart Scan :");
        int totalPages = 0;
        String url = baseUrl +"1";
        try {
            Document doc = Jsoup.connect(url).get();
            String results = doc.select("div.result-summary-container").text();
            String[] parts = results.split(" ");
            int total  = Integer.parseInt(parts[parts.length-2]);
            if(total%40 == 0) totalPages = total/40;
            else totalPages= total/40+1;
            
          
        } catch (IOException ex) {
            Logger.getLogger(walmart.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        for (int i= 1; i<=totalPages; i++) {

            fetchDataByPage(i);   
        }

    //update the updates map
        if(!newList.isEmpty()) updates.put("New Items", newList);
        
        if(!lowerPriceList.isEmpty()) updates.put("Price Decreased",lowerPriceList);
        
        //save data
        saveData();

        
        if(!report().isEmpty()){
            saveOutput();
            sendNotification("Walmart 2 hour report", report());
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
        
        List<Element> items = doc.select("li.tile-grid-unit-wrapper");
        System.out.println(items.size());
        String priceStr ="";
        
        for(Element item : items) {
            String itemUrl = "http://www.walmart.com"+item.select("a.js-product-image").attr("href");
            String[] urlParts = itemUrl.split("/");
            String itemNum = urlParts[urlParts.length-1];
            priceStr = item.select("span.price.price-display").text();
            
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

    private void fetchDataByPage(int i) {
        System.out.println("Page : " + i);
        
        String currentUrl = baseUrl+Integer.toString(i);

        try {
            Document doc = Jsoup.connect(currentUrl).get();
            updateData(doc);
          
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    


}
