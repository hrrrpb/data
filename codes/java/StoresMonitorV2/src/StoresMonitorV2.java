import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gargoylesoftware.htmlunit.BrowserVersion;


public class StoresMonitorV2 {
	private String msgsubject;
	private String msgbody;
	private ArrayList<String> emailList = new ArrayList<String>();
	private int interval; 
	private File data; 
	private ArrayList<link> walmartList = new ArrayList<link>();
	private ArrayList<link> toysrusList = new ArrayList<link>();
	private ArrayList<link> targetList = new ArrayList<link>();
	private ArrayList<link> bestbuyList = new ArrayList<link>();
	private ArrayList<link> amazonList = new ArrayList<link>();
	private ArrayList<link> msList = new ArrayList<link>();
	private ArrayList<link> legoList = new ArrayList<link>();
	
	private BrowserVersion bv = new BrowserVersion("Netscape", "blablabla", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1468.0 Safari/537.36", 0);
	
	
	public void InitData()
	{
		data = new File("MonitorData");
		
		if(!data.exists()) {
			System.exit(0);
		}
		
		else { //read in email data and scan interval
		
			File email = new File(data+"\\alert-emails.txt");
			File scaninterval = new File(data+"\\storesmonitor-interval.txt");
			File Items = new File(data+"\\storesmonitor-items.txt");
			try {
				
		        
		        Scanner scEmail = new Scanner(email);
		        Scanner scInterval = new Scanner(scaninterval);
		        Scanner scItems = new Scanner(Items);
		        interval = scInterval.nextInt();
		        scInterval.close();
		        
		        while(scEmail.hasNextLine()){
		        	emailList.add(scEmail.nextLine());
		        }
		        scEmail.close();
		        
		        while(scItems.hasNextLine()){
		        	String temp = scItems.nextLine();
		        	
		        	if(temp.contains("walmart.com")) {
		        		
		        		walmartList.add(new link(temp));
		        	}
		        	
		        	if(temp.contains("amazon")) {
		        		
		        		amazonList.add(new link(temp));
		        	}
		        	
		        	if(temp.contains("microsoftstore.com")) {
		        		
		        		msList.add(new link(temp));
		        	}
		        	
		        	if(temp.contains("target.com")) {
		        		targetList.add(new link(temp));
		        	}
		        	
		        	if(temp.contains("toysrus.com")) {
		        		toysrusList.add(new link(temp));
		        	}
		        	
		        	if(temp.contains("bestbuy.com")) {
		        		bestbuyList.add(new link(temp));
		        	}
		        	
		        	if(temp.contains("shop.lego.com")) {
		        		legoList.add(new link(temp));
		        	}
		     
		        	
		        	
		        }
		        scItems.close();
		        
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		
			
		}
	
		
	}

	public void Start(){
		
		InitData();
		
	
		if(!walmartList.isEmpty()) {
			new Thread(()-> {
				
				ScanWalmart();
					

			}).start();
			
		}
		
		if(!msList.isEmpty()) {
			new Thread(()-> {
				
				ScanMS();
					

			}).start();
			
		}
		
		if(!amazonList.isEmpty()) {
			new Thread(()-> {
				
				ScanAmazon();
					

			}).start();
			
		}
		
		if(!legoList.isEmpty()) {
			new Thread(()-> {
				
				ScanLego();
					

			}).start();
			
		}
		
		if(!(targetList.isEmpty())) {
			new Thread(()-> {
				
				ScanTarget();
					

			}).start();
			
		}
		
		if(!(bestbuyList.isEmpty())) {
			new Thread(()-> {
				
				ScanBestbuy();
					

			}).start();
			
		}
		
		
		if(!toysrusList.isEmpty()) {
			new Thread(()-> {
				
				ScanToysrus();
					

			}).start();
			
		}
		
	
		

	}
	
	
	
	private void ScanBestbuy(){
		
		while(true) {
			
			
			if (bestbuyList.isEmpty()) {
				
				System.out.println("bestbuy does not have links to scan !\n");
				
				break;
				
				
			}
			
			else{
				
				
				try {
					WebDriver driver = new HtmlUnitDriver(bv);
					
					System.out.println("\nWalmart Scan : "+LocalDateTime.now()+"\n");
			        
				
			        Iterator<link> iter = bestbuyList.iterator();
			        
			        while(iter.hasNext()){
			        	
			        	link tmpLink = iter.next();
			        	String tmpUrl = tmpLink.url;
			        	
			        	
			        	driver.get(tmpUrl);
			        	
			        	
			    	     
			    	    try {
			    	    	String instockmsg = "Shipping: Usually leaves our warehouse in 1 business day";
			    	    	if (driver.findElement(By.className("ship-time-message")).getText().contains(instockmsg)) {
			    	    		System.out.println(driver.getTitle()+ " : " + " In Stock Alert! "  +"\n");
						         
		
					     		if(tmpLink.inStock==false) {
					     			msgsubject = driver.getTitle() + " Now In Stock";
						     		msgbody = tmpUrl;
					     			
					     			SendEmail(emailList);
					     			tmpLink.inStock =true;
					     		}
					     		
					     		
					     		
			    	    		
			    	    	}
			    	    	else{
			    	    		System.out.println(driver.getTitle()+ " : " + " is OOS "+"\n");
			    	    		if(tmpLink.inStock ==true) {tmpLink.inStock=false;}
			    	    			
			    	    	}
			    	    	
			    	    	
			    	    	
			    	    	
			    	    }
			    	    catch(NoSuchElementException e){
			    	    	System.out.println("Bestbuy scan error" +"\n");
			    	    }
			    	     
				
				    }
			        
			        driver.close();
					driver.quit();
					Thread.sleep(interval*60*1000);
				}
				catch (Exception ex){
					ex.printStackTrace();
					
					System.out.println("bestbuy scan error, ignore and pass \n");
			                
			       
					
				}
		        
			}
			
			
		  }
		
		
		 
    	
		
	}
	
	
	private void ScanLego(){
		
		while(true) {
			
			
			if (legoList.isEmpty()) {
				
				System.out.println("lego does not have links to scan !\n");
				
				break;
				
				
			}
			
			else{
				
				
				try {
					WebDriver driver = new HtmlUnitDriver();
					System.out.println("\nLego Scan : "+LocalDateTime.now()+"\n");
			        
				
			        Iterator<link> iter = legoList.iterator();
			        
			        while(iter.hasNext()){
			        	
			        	link tmpLink = iter.next();
			        	String tmpUrl = tmpLink.url;
			        	
			        	
			        	driver.get(tmpUrl);
			        	
			        	
			    	     
			    	    try {
			    	    	driver.findElement(By.id("addToCart"));
			    	    	
			    	    	
			    	    	System.out.println(driver.getTitle()+ " : " + " In Stock Alert! "  +"\n");
			    	    	
			    	    	if(tmpLink.inStock==false) {
				     			msgsubject = driver.getTitle() + " Now In Stock";
					     		msgbody = tmpUrl;
				     			
				     			SendEmail(emailList);
				     			tmpLink.inStock =true;
				     		}
						         
		
					    
			    	    }
			    	    catch(NoSuchElementException e){
			    	    	System.out.println(driver.getTitle()+ " : " + " is OOS "+"\n");
		    	    		if(tmpLink.inStock ==true) {tmpLink.inStock=false;}
			    	    	
			    	    }
			    	     
				
				    }
			        
			        driver.close();
					driver.quit();
					Thread.sleep(interval*60*1000);
				}
				catch (Exception ex){
					ex.printStackTrace();
					
					System.out.println("Lego scan error, ignore and pass \n");
			                
			       
					
				}
		        
			}
			
			
		  }
		
		
		 
    	
		
	}
		
	
	private void ScanMS(){
		
		
		
		while(true) {
			
			
			if (msList.isEmpty()) {
				
				System.out.println("Microsoftstore does not have links to scan !\n");
				
				break;
				
				
			}
			
			else{
				
				
				try {
					System.out.println("\nMicrosoftstore Scan : "+LocalDateTime.now()+"\n");
			        
					
			        Iterator<link> iter = msList.iterator();
			       
			        
			        while(iter.hasNext()){
			        	
			        	link tmpLink = iter.next();
			        	
			        	
			        	URL tmpUrl = new URL(tmpLink.url);
			        	
			           
				        URLConnection conn = tmpUrl.openConnection();
				        BufferedReader in = new BufferedReader(new InputStreamReader(
				                conn.getInputStream(), "UTF-8"));
				        String inputLine;
				        String title="";
						boolean inStock=true;
				        
				        while ((inputLine = in.readLine()) != null)
				        	if(inputLine.contains("lpAddVars")){
				        		if(inputLine.contains("ProductName")) {
				        			String[] parseLines = inputLine.split("'");
				        			title = parseLines[parseLines.length-2];
				        			
				        		}
				        		
				        		if(inputLine.contains("Out of stock"))
				        			inStock = false;
				        		
				        			
				        		
				        	}
				        	
				        	
				       
				        in.close();
				        
				        
				        
				        if(!title.isEmpty()){
				        	
				        	if(inStock==false) {
					        	System.out.println(title+ " : " + " is OOS "+"\n");
					        	if(tmpLink.inStock==true) {tmpLink.inStock=false;}
					        	
					        	
					        }

					        else{
					        	System.out.println(title+ " : " + " In Stock Alert! "  +"\n");
						         
					        	if(tmpLink.inStock==false) {
					        		msgsubject = title + " Now In Stock";
						     		msgbody = tmpLink.url;
						     		SendEmail(emailList);
						     		tmpLink.inStock =true;
					        		
					        	}
					     		
					        }
				        	
				        	
				        	
				        }
				        
				       else System.out.println("did not retreive data correctly.");
				     
				        	
				        
				        
			        }
			       Thread.sleep(interval*60*1000);
				
				}
				catch(Exception ex){
					ex.printStackTrace();
					System.out.println("Microsoftstore scan error, ignore and pass \n");
				}
					
			}
				
		}
				
		        
	}
			
	
	
        private void ScanWalmart(){
		while(true) {
			
			
			if (walmartList.isEmpty()) {
				
				System.out.println("walmart does not have links to scan !\n");
		               
				
				break;
				
				
			}
			
			else{
				
				
				try {
					WebDriver driver = new HtmlUnitDriver(bv);
					System.out.println("\nWalmart Scan : "+LocalDateTime.now()+"\n");
			        
					
				
			        Iterator<link> iter = walmartList.iterator();
			        
			        while(iter.hasNext()){
			        	
			        	link tmpLink = iter.next();
			        	String tmpUrl = tmpLink.url;
			        	
			        	driver.get(tmpUrl);
			        	
			        	
			    	     
			    	    try {
			    	    	
			    	    	driver.findElement(By.id("WMItemAddToCartBtn"));
			    	    	
			    	    	System.out.println(driver.getTitle()+ " : " + " In Stock Alert! "  +"\n");
					         
			    	    	if(tmpLink.inStock==false) {
			    	    		msgsubject = driver.getTitle() + " Now In Stock";
					     		msgbody = tmpUrl;
					     		SendEmail(emailList);
					     	    tmpLink.inStock=true;
			    	    		
			    	    	}
				     		
			    	    }
			    	    catch(NoSuchElementException e){
			    	    	System.out.println(driver.getTitle()+ " : " + " is OOS "+"\n");
			    	    	if(tmpLink.inStock==true) {tmpLink.inStock=false;}
			    	    }
			    	     
				
				    }
			        
			        driver.close();
					driver.quit();
					Thread.sleep(interval*60*1000);
				}
				catch (Exception ex){
					ex.printStackTrace();
					
					System.out.println("walmart scan error, ignore and pass \n");
			               
					
				}
		        
			}
			
			
		  }
		
	
	}
	
	private void ScanTarget(){
		
		while(true) {
			
			
			if (targetList.isEmpty()) {
				
				System.out.println("target does not have links to scan !\n");
		                
		        
				break;
				
				
			}
			
			else{
				
				
				try {
					 DesiredCapabilities caps = new DesiredCapabilities();
				     caps.setJavascriptEnabled(true);                //< not really needed: JS enabled by default
				     caps.setCapability("takesScreenshot", true);    //< yeah, GhostDriver haz screenshotz!
				     caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
				                data+"\\phantomjs\\bin\\phantomjs.exe"
				            
				        );
				     ArrayList<String> cliArgsCap = new ArrayList<String>();
				     cliArgsCap.add("--webdriver-loglevel=NONE");
				        
				     caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
				     
				     WebDriver driver = new PhantomJSDriver(caps);
					
					
					System.out.println("\nTarget Scan : "+LocalDateTime.now()+"\n");
			    
				
			        Iterator<link> iter = targetList.iterator();
			        
			        while(iter.hasNext()){
			        	
			        	link tmpLink = iter.next();
			        	
			        	String tmpUrl = tmpLink.url;
			        	
			        	
			        	
			    	     
			    	    try {
			    	    	
			    	    	driver.get(tmpUrl);
			    	    	if(driver.findElement(By.className
			    	    			("freeShippingPromo")).getText().equals("not available")) {
			    	    		System.out.println(driver.getTitle()+ " : " + " is OOS "+"\n");
				    	    	if(tmpLink.inStock==true) {tmpLink.inStock=false;}
			    	    	}
			    	    	else{
			    	    		System.out.println(driver.getTitle()+ " : " + " In Stock Alert! "  +"\n");
				    	    	
				    	    	if(tmpLink.inStock==false) {
				    	    		msgsubject = driver.getTitle() + " Now In Stock";
						     		msgbody = tmpUrl;
						     		SendEmail(emailList);
						     		tmpLink.inStock=true;
				    	    	}
			    	    		
			    	    	}
			    	    	
			    	    	
			    	    	
			    	    	
					         
				     		
			    	    }
			    	    catch(NoSuchElementException e){
			    	    	System.out.println("Page loading error "+"\n");
			    	    	
			    	    }
			    	     
				
				    }
			        
			      
					
					Thread.sleep(interval*60*1000);
				}
				catch (Exception ex){
					ex.printStackTrace();
					
					System.out.println("Target scan error, ignore and pass \n");
			                
			        
				}
		        
			}
			
			
		  }
		
		
		
		
		
	}
	
	
	private void ScanToysrus(){
		while(true) {
			
			
			if (toysrusList.isEmpty()) {
				
				System.out.println("toysrus does not have links to scan !\n");
		                
		       
				
				break;
				
				
			}
			
			else{
				
				
				try {
					WebDriver driver = new HtmlUnitDriver(bv);
					System.out.println("\nToysrus Scan : "+LocalDateTime.now()+"\n");
			                
			        
					
				
			        Iterator<link> iter = toysrusList.iterator();
			        
			        while(iter.hasNext()){
			        	
			        	link tmpLink = iter.next();
			        	String tmpUrl = tmpLink.url;
			        	
			        	driver.get(tmpUrl);
			        	
			        	
			    	     
			    	    try {
			    	    	
			    	    	driver.findElement(By.id("cartAddition"));
			    	    	
			    	    	System.out.println(driver.getTitle()+ " : " + " In Stock Alert! "  +"\n");
			    	    	
			    	    	if(tmpLink.inStock==false) {
			    	    		msgsubject = driver.getTitle() + " Now In Stock";
					     		msgbody = tmpUrl;
					     		SendEmail(emailList);
					     		tmpLink.inStock=true;
			    	    	}
					         
				     		
				     		
			    	    }
			    	    catch(NoSuchElementException e){
			    	    	System.out.println(driver.getTitle()+ " : " + " is OOS "+"\n");
			    	    	if(tmpLink.inStock==true) {tmpLink.inStock=false;}
			    	    }
			    	     
				
				    }
			        
			        driver.close();
					driver.quit();
					Thread.sleep(interval*60*1000);
				}
				catch (Exception ex){
					ex.printStackTrace();
					
					System.out.println("Toysrus scan error, ignore and pass \n");
			                
			       
					
				}
		        
			}
			
			
		  }
		        
		
	}
	
	private void ScanAmazon(){
		
		while(true) {
			
			
			if (amazonList.isEmpty()) {
				
				System.out.println("Amazon does not have links to scan !\n");
				
				break;
				
				
			}
			
			else{
				
				
				try {
					WebDriver driver = new HtmlUnitDriver();
					System.out.println("\nAmazon Scan : "+LocalDateTime.now()+"\n");
			        
				
			        Iterator<link> iter = amazonList.iterator();
			        
			        while(iter.hasNext()){
			        	
			        	link tmpLink = iter.next();
			        	String tmpUrl = tmpLink.url;
			        	
			        	driver.get(tmpUrl);
			        	
			        	
			    	     
			    	    try {
			    	    	String instockmsgjp = "������Ʒ�ϡ�Amazon.co.jp";
			    	    	String instockmsg = "Ships from and sold by Amazon.com";
			    	    	String instockmsguk ="Dispatched from and sold by Amazon";
			    	    	
			    	    	if (driver.findElement(By.id("merchant-info")).getText().contains(instockmsgjp) ||
			    	    			driver.findElement(By.id("merchant-info")).getText().contains(instockmsg) ||
			    	    			driver.findElement(By.id("merchant-info")).getText().contains(instockmsguk)) {
			    	    		System.out.println(driver.getTitle()+ " : " + " In Stock Alert! "  +"\n");
			    	    		
			    	    		if(tmpLink.inStock==false) {
			    	    			msgsubject = driver.getTitle() + " Now In Stock";
						     		msgbody = tmpUrl;
						     		SendEmail(emailList);
						     		tmpLink.inStock=true;
			    	    			
			    	    		}
						         
	
					     		
			    	    		
			    	    	}
			    	    	else{
			    	    		System.out.println(driver.getTitle()+ " : " + " is OOS "+"\n");
			    	    		if(tmpLink.inStock==true) {tmpLink.inStock=false;}
			    	    		
			    	    	}
			    	    	
			    	    	
			    	    	
			    	    	
			    	    }
			    	    catch(NoSuchElementException e){
			    	    	System.out.println("Amazon scan error" +"\n");
			    	    }
			    	     
				
				    }
			        
			        driver.close();
					driver.quit();
					Thread.sleep(interval*60*1000);
				}
				catch (Exception ex){
					ex.printStackTrace();
					
					System.out.println("Amazon scan error, ignore and pass \n");
			                
			       
					
				}
		        
			}
			
			
		  }
		
		
		 
    	
		
	}
	
	private void SendEmail(ArrayList<String> emails) {
		
		
		StringBuilder sb = new StringBuilder();
		for(String tmp:emails) {
			sb.append(tmp);
			sb.append(",");
		}
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("gobetterkx", "Bnmrc123");
					}
				  });
		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("gobetterkx@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(sb.toString(), true));
			message.setSubject(msgsubject);
			message.setText(msgbody);
	 
			Transport.send(message);
	 
			System.out.println("Notifications Sent! \n");
	 
		} 
		catch (Exception ex) {
			System.out.println("Notification failure ! \n\n");
		}
	
	
		
		
	}
	
	public static  void  main( String[] args ) {
		
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		Logger.getLogger(PhantomJSDriverService.class.getName()).setLevel(Level.OFF);
		
		StoresMonitorV2 monitor = new StoresMonitorV2();
		monitor.Start();
		
	}
}
