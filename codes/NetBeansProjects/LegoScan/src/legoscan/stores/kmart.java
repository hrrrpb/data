/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legoscan.stores;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author yi
 */
public class kmart extends baseStore {
    
    public kmart(){
        super();
//        dataFile = new File("data/Kmart_Data.txt");
//        outputFile = new File("data/Kmart_Output.txt");
//        readData();
   
    }
    
    
    @Override
    public void scan(){
        String url = "http://www.kmart.com/search=lego?catalogId=10104&pageNum=9&levels=Toys%20%26%20Games_Blocks%20%26%20Building%20Sets_Building%20Sets";
        try {
            Document doc = Jsoup.connect(url).get();
            List<Element> items = doc.select("div.card-container");
            System.out.println(items.size());
        } catch (IOException ex) {
            Logger.getLogger(kmart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
