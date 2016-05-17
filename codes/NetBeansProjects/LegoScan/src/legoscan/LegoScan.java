/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legoscan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import legoscan.stores.kmart;
import legoscan.stores.target;
import legoscan.stores.toysrus;
import legoscan.stores.walmart;



/**
 *
 * @author yi
 */
public class LegoScan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)   {
        
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        
        
        target scanTarget = new target();
        toysrus truScan = new toysrus();
        walmart scanWalmart = new walmart();
        while(true) {
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            scanTarget.scan();
            truScan.scan();
            scanWalmart.scan();
            try {
                Thread.sleep(1000 * 60 * 60 * 2);
            } catch (InterruptedException ex) {}
            
        }

         
        
    }

}
        
    

