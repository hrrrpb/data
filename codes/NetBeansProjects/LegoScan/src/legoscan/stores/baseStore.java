/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legoscan.stores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import legoscan.data.product;
import legoscan.emailSender;

/**
 *
 * @author yi
 */
public class baseStore {
    protected File dataFile;
    protected File outputFile;
    protected HashMap dataMap;
    
    //it will have "new", "historyLow", "lowerPrice", "higherPrice"
    protected Map<String, List<product>> updates;
    
    
    public baseStore() {
        dataMap = new HashMap();
        updates = new HashMap();
        
    }
    
    public void readData() {
        try {
            //data format
            // Num : url : current : hisHigh: hisLow
            Scanner dataScanner = new Scanner(dataFile);
            
            while(dataScanner.hasNextLine()){
                String line = dataScanner.nextLine();
                String[] parts = line.split("::");
                product temp = new product(parts[0], parts[1], 
                                           Float.valueOf(parts[2]), Float.valueOf(parts[3]), Float.valueOf(parts[4]));
                dataMap.put(temp.getItemNum(), temp);
                
            }       
           System.out.println("Saved Data read in...");

        } catch (FileNotFoundException ex) {
            // no need to do things here.
        }
        
    }
    
    public String report(){
        
        StringBuilder sb = new StringBuilder();
        //it will have "new","lowerPrice"
     
     
        String[] keywords = {"New Items", "Price Decreased"};
     
        for (String keyword : keywords) {
            if (updates.get(keyword)!=null) {
                sb.append(keyword +": \n");
                for(product temp : updates.get(keyword)){
                    sb.append(temp.getItemUrl()+"\n");  
                    sb.append("Current Price: $" + temp.getCurrent()+"\n");
                    sb.append("History Low: $" + temp.getHistoryLow()+"\n");
                    sb.append("History High: $" + temp.getHistoryHigh()+"\n");
                 
                }
         
            }
     
     
        }
     
        return sb.toString();
    
    
    }
    
    public void saveData(){
        
        try{
            FileWriter fw = new FileWriter(dataFile);
            for(Object value: dataMap.values()) {
            
                product temp = (product)value;
                fw.write(temp.getItemNum()+"::" + temp.getItemUrl() +"::"+
                    temp.getCurrent()+"::" + temp.getHistoryHigh()+"::"+temp.getHistoryLow() +"\n");
            }
        
            fw.close();
            
        }
        catch(IOException ex) {
            System.out.println("I/O Error writting data file");
        }
        
        
    }
    
    public void saveOutput(){
        try{
            FileWriter fw = new FileWriter(outputFile);
            fw.write(report());
            fw.close();
            
        }
        catch(IOException ex) {
            System.out.println("I/O Error writting data file");
        }
        
    }
    
    
    public void sendNotification(String subject, String body){
        emailSender sender = new emailSender();
        sender.setSender("gobetterkx");
        sender.setSenderPwd("Bnmrc123");
        sender.setMsgbody(body);
        sender.setMsgsubject(subject);
        ArrayList<String> emails = new ArrayList();
        emails.add("hrrrpb@gmail.com");
        sender.setEmails(emails);
        sender.SendEmail();
    }
    
    public void scan(){
        
    }
    
    
    
    
}
