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
public class toysrus extends baseStore {
    private ArrayList<product> newList = new ArrayList();
    private ArrayList<product> lowerPriceList= new ArrayList();
    private String baseUrl = "http://www.toysrus.com/family/index.jsp?categoryId=31151746&ppg=96&page=";
    
    public toysrus(){
        super();
        dataFile = new File("data/Toysrus_Data.txt");
        outputFile = new File("data/Toysrus_Output.txt");
        readData();
   
    }
    
    @Override
    public void scan(){
        
        System.out.println("Toysrus scan : ");
        String url = baseUrl +"1";
        Document doc;
        int totalPages = 0;
        //get total products and the total number of pages
        try {
            doc = Jsoup.connect(url).get();
            String result = doc.select("div.showingText").get(0).text();
            String[] parts = result.split(" ");
            int total = Integer.parseInt(parts[parts.length-2]);
            
            if (total%96==0) totalPages = total/96;
            else totalPages = total/96+1;
        
        } catch (IOException ex) {
            Logger.getLogger(toysrus.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i =1; i<=totalPages; i++) {
            fetchDataByPage(i);
        }
        
        //update the updates map
        if(!newList.isEmpty()) updates.put("New Items", newList);
        if(!lowerPriceList.isEmpty()) updates.put("Price Decreased",lowerPriceList);
        
        //save data
        saveData();
        
        //save and email report
        if(!report().isEmpty()){
            saveOutput();
            sendNotification("Toysrus 2 hour report", report());
        }
        else{
            System.out.println("No new updates");
        }
      
        //clear updates
        newList = new ArrayList();
        lowerPriceList = new ArrayList();
        updates = new HashMap();
        
   
    }
    
    private void updateData(Document doc){
        
        List<Element> items = doc.select("div.prodloop_float");
            System.out.println(items.size());
            for(Element item : items) {
                String link = item.select("a.prodtitle").attr("href");
                String name = item.select("a.prodtitle").text();
                int start = link.indexOf("productId=")+"productid=".length();
                int stop = link.indexOf("&");
                
                String itemNum = link.substring(start, stop);
                String itemUrl = "http://www.toysrus.com/product/index.jsp?productId="+itemNum
                        +"|" + name;
                
                String priceStr= item.select("span.ourPrice2").text();
                
                if(!priceStr.isEmpty()){
                    
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
    
    
    @Override
    public String report(){
        StringBuilder sb = new StringBuilder();
        //it will have "new","lowerPrice"
     
     
        String[] keywords = {"New Items", "Price Decreased"};
     
        for (String keyword : keywords) {
            if (updates.get(keyword)!=null) {
                sb.append(keyword +": \n");
                for(product temp : updates.get(keyword)){
                    int breakpoint = temp.getItemUrl().indexOf("|");
                    
                    sb.append(temp.getItemUrl().substring(0,breakpoint)+"\n");  
                    sb.append(temp.getItemUrl().substring(breakpoint+1)+"\n");  
                    sb.append("Current Price: $" + temp.getCurrent()+"\n");
                    sb.append("History Low: $" + temp.getHistoryLow()+"\n");
                    sb.append("History High: $" + temp.getHistoryHigh()+"\n");
                 
                }
         
            }
     
     
        }
     
        return sb.toString();
    }
    
}
 