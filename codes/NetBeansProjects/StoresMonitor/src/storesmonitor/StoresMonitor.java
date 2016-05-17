/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storesmonitor;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import storesmonitor.Stores.amazonStore;
import storesmonitor.Stores.bestbuyStore;
import storesmonitor.Stores.msStore;
import storesmonitor.Stores.staplesStore;
import storesmonitor.Stores.targetStore;
import storesmonitor.Stores.truStore;
import storesmonitor.Stores.wmStore;
import storesmonitor.data.productLink;
import storesmonitor.util.emailSender;


/**
 *
 * @author Yi
 */
public class StoresMonitor {

    private String msgsubject;
	private StringBuilder msgbody;
	private ArrayList<String> emailList = new ArrayList<String>();
	private File data; 
	private int interval;
	private ArrayList<productLink> toysrusList = new ArrayList<productLink>();
	private ArrayList<productLink> walmartList = new ArrayList<productLink>();
	private ArrayList<productLink> amazonList = new ArrayList<productLink>();
	private ArrayList<productLink> msList = new ArrayList<productLink>();
        private ArrayList<productLink> bbList = new ArrayList<productLink>();
        private ArrayList<productLink> targetList = new ArrayList();
        private ArrayList<productLink> staplesList = new ArrayList();
	
	
	private void InitData()
	{
		data = new File("monitordata");
                
		
		if(!data.exists()) {
			System.out.println("Error: folder monitordata does not exist!");
			System.exit(0);
		}
		
		else { //read in email data and scan interval
		
			File email = new File("monitordata/alert-emails.txt");
			File scaninterval = new File("monitordata/storesmonitor-interval.txt");
			File Items = new File("monitordata/storesmonitor-items.txt");
			try {
				
		        
		        Scanner scEmail = new Scanner(email);
		        Scanner scInterval = new Scanner(scaninterval);
		        Scanner scItems = new Scanner(Items);
		        
		        interval = scInterval.nextInt();
		        scInterval.close();
		        
		        while(scEmail.hasNextLine()){
		        	emailList.add(scEmail.nextLine().trim());
		        }
		        scEmail.close();
		        
		        while(scItems.hasNextLine()){
		        	String temp = scItems.nextLine();
		        	if(temp.startsWith("#")) continue;
		        	
		        	if(temp.contains("walmart.com")) {
		        		
		        		walmartList.add(new productLink(temp));
		        		continue;
		        	}
		        	
		        	if(temp.contains("amazon")) {
		        		
		        		amazonList.add(new productLink(temp));
		        		continue;
		        	}
		        	
                                if(temp.contains("target.com")) {
		        		
		        		targetList.add(new productLink(temp));
		        		continue;
		        	}
		        	
		        	
		        	
		   
		        	if(temp.contains("toysrus.com")) {
		        		toysrusList.add(new productLink(temp));
		        		continue;
		        	}
		        	
		        	if(temp.contains("microsoftstore.com")) {
		        		msList.add(new productLink(temp));
		        		continue;
		        	}
                                
                                if(temp.contains("bestbuy.com")) {
		        		bbList.add(new productLink(temp));
		        		continue;
		        	}
		        	
                                if(temp.contains("staples.com")) {
                                        staplesList.add(new productLink(temp));
		        		continue;
                                }
		        	
		     
		        	
		        	
		        }
		        scItems.close();
		        
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		
			
		}
	
		
	}
	
	
	private void report() {
		
		
		if(!(bbList.isEmpty())) {
			msgbody=new StringBuilder("");
			
			for(productLink tmp: bbList) {
				if(tmp.getInStock()) {
					
					msgbody.append(tmp.getTitle()+System.lineSeparator());
					msgbody.append(tmp.getUrlLink()+System.lineSeparator()+System.lineSeparator());
					
				}
			   
				
			}
			
			if(!msgbody.toString().isEmpty()) {
				msgsubject="Bestbuy monitored items back in stock";
				new emailSender(msgsubject, msgbody.toString(), emailList).SendEmail();
			}
			
			
		}
                
                if(!(targetList.isEmpty())) {
			msgbody=new StringBuilder("");
			
			for(productLink tmp: targetList) {
				if(tmp.getInStock()) {
					
					msgbody.append(tmp.getTitle()+System.lineSeparator());
					msgbody.append(tmp.getUrlLink()+System.lineSeparator()+System.lineSeparator());
					
				}
			   
				
			}
			
			if(!msgbody.toString().isEmpty()) {
				msgsubject="Target monitored items back in stock";
				new emailSender(msgsubject, msgbody.toString(), emailList).SendEmail();
			}
			
			
		}
		
		if(!(toysrusList.isEmpty())) {
			msgbody=new StringBuilder("");
			
			for(productLink tmp: toysrusList) {
				
				if(tmp.getInStock()) {
					
					msgbody.append(tmp.getTitle()+System.lineSeparator());
					msgbody.append(tmp.getUrlLink()+System.lineSeparator()+System.lineSeparator());
					
				}
				
			}
			
			if(!msgbody.toString().equals("")) {
				msgsubject="Toysrus monitored items back in stock";
				new emailSender(msgsubject, msgbody.toString(), emailList).SendEmail();
			}
			
			
		}
		
		if(!(amazonList.isEmpty())) {
			msgbody=new StringBuilder("");
			
			for(productLink tmp: amazonList) {
				
				if(tmp.getInStock()) {
					
					msgbody.append(tmp.getTitle()+System.lineSeparator());
					msgbody.append(tmp.getUrlLink()+System.lineSeparator()+System.lineSeparator());
					
				}
				
			}
			
			if(!msgbody.toString().equals("")) {
				msgsubject="Amazon monitored items back in stock";
				new emailSender(msgsubject, msgbody.toString(), emailList).SendEmail();
			}
			
			
		}
                
                if(!(walmartList.isEmpty())) {
			msgbody=new StringBuilder("");
			
			for(productLink tmp: walmartList) {
				
				if(tmp.getInStock()) {
					
					msgbody.append(tmp.getTitle()+System.lineSeparator());
					msgbody.append(tmp.getUrlLink()+System.lineSeparator()+System.lineSeparator());
					
				}
				
			}
			
			if(!msgbody.toString().equals("")) {
				msgsubject="Walmart monitored items back in stock";
				new emailSender(msgsubject, msgbody.toString(), emailList).SendEmail();
			}
			
			
		}
                
                if(!(msList.isEmpty())) {
			msgbody=new StringBuilder("");
			
			for(productLink tmp: msList) {
				
				if(tmp.getInStock()) {
					
					msgbody.append(tmp.getTitle()+System.lineSeparator());
					msgbody.append(tmp.getUrlLink()+System.lineSeparator()+System.lineSeparator());
					
				}
				
			}
			
			if(!msgbody.toString().equals("")) {
				msgsubject="Microsoft store monitored items back in stock";
				new emailSender(msgsubject, msgbody.toString(), emailList).SendEmail();
			}
			
			
		}
                
                if(!(staplesList.isEmpty())) {
			msgbody=new StringBuilder("");
			
			for(productLink tmp: staplesList) {
				if(tmp.getInStock()) {
					
					msgbody.append(tmp.getTitle()+System.lineSeparator());
					msgbody.append(tmp.getUrlLink()+System.lineSeparator()+System.lineSeparator());
					
				}
			   
				
			}
			
			if(!msgbody.toString().isEmpty()) {
				msgsubject="Staples monitored items back in stock";
				new emailSender(msgsubject, msgbody.toString(), emailList).SendEmail();
			}
			
			
		}
		
		
	}
	
	
	public void start() {
		InitData();
		
		bestbuyStore bbStore = new bestbuyStore();
                truStore truStore = new truStore();
		amazonStore amazonStore = new amazonStore();
		wmStore wmStore = new wmStore();
                msStore msStore = new msStore();
                targetStore targetStore = new targetStore();
                staplesStore staplesStore = new staplesStore();
		
		if(!(bbList.isEmpty())) {
			
			bbStore.setLinks(bbList);
			
		}
		
		if(!(toysrusList.isEmpty())) {
			
			truStore.setLinks(toysrusList);
			
			
		}       
                
                if(!(targetList.isEmpty())) {
			
			targetStore.setLinks(targetList);
			
			
		}      
		
		if(!(amazonList.isEmpty())) {
			
			amazonStore.setLinks(amazonList);
			
			
		}     
                
                if(!(walmartList.isEmpty())) {
			
			wmStore.setLinks(walmartList);
			
			
		}     
                
                if(!(msList.isEmpty())) {
			
			msStore.setLinks(msList);
			
			
		}     
		
		if(!(staplesList.isEmpty())) {
			
			staplesStore.setLinks(staplesList);
			
			
		}     
		
		try{
			while(true) {
				System.out.println("\nMonitor Scan : "+LocalDateTime.now()+"\n");
				if(!(bbList.isEmpty())) {
					
					bbStore.isInStock();
					
				}
				
				if(!(toysrusList.isEmpty())) {
					
					truStore.isInStock();
					
				}    
                                
                                if(!(targetList.isEmpty())) {
					
					targetStore.isInStock();
					
				}    
				
				if(!(amazonList.isEmpty())) {
					
					amazonStore.isInStock();
					
				}   
                                
                                if(!(walmartList.isEmpty())) {
					
					wmStore.isInStock();
					
				}   
                                
                                if(!(msList.isEmpty())) {
					
					msStore.isInStock();
					
				}   
                                
                                if(!(staplesList.isEmpty())) {
					
					staplesStore.isInStock();
					
				}   
				
				report();
				Thread.sleep(interval*60*1000);
                                
			}
			
		}
		catch(InterruptedException ex) {
			System.out.println("error in scaning");
                        ex.printStackTrace();
			
		}
		
	}

	
	
	
	public static  void  main( String[] args ) {
		
		
		StoresMonitor monitor = new StoresMonitor();
		monitor.start();

           
	
	}
	

}


