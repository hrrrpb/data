import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class DealsMonitor {
	
	private ArrayList<String> emailList = new ArrayList<String>();
	private static int interval; 
	private ArrayList<String> urlList = new ArrayList<String>();
	//private ArrayList<String> keywords = new ArrayList<String>();
	private String msgsubject;
	private StringBuilder msgbody = new StringBuilder();
	
	private ArrayList<String> dealseaLog = new ArrayList<String>();
	private ArrayList<String> sdLog = new ArrayList<String>();
	
	private File dir = new File("monitordata"); 
	
	
	//constructor
	public DealsMonitor(){
		//initialize urls
		urlList.add("http://dealsea.com/");
		urlList.add("http://slickdeals.net/forums/forumdisplay.php?f=9&pp=50&order=desc&sort=threadstarted");
		urlList.add("http://slickdeals.net/forums/forumdisplay.php?f=30&order=desc&sort=threadstarted");
		
		//set up interval
		
		
		if(!dir.exists()) {
			System.exit(0);
		}
		else { //read in scan data and contact data

			try {
				
		        
		        Scanner scEmail = new Scanner(new File("MonitorData/emails.txt"));
		        Scanner scInterval = new Scanner(new File("MonitorData/deals-interval.txt"));
		        Scanner scDealSea = new Scanner(new File("MonitorData/DealSeaData.txt"));
		        Scanner scSlickdeal = new Scanner(new File("MonitorData/SlickDealData.txt"));
	        	
	        	
		        interval = scInterval.nextInt();
		        scInterval.close();
		        
		        while(scEmail.hasNextLine()){
		        	emailList.add(scEmail.nextLine());
		        }
		        scEmail.close();
		        
		        while(scDealSea.hasNextLine()){
		        	dealseaLog.add(scDealSea.nextLine());
		        }
	        	scDealSea.close();
	        	
	        	while(scSlickdeal.hasNextLine()){
		        	sdLog.add(scSlickdeal.nextLine());
		        }
	        	scSlickdeal.close();
	        	
		        
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		
			
		}
	}
	
	
	
	private void ScanSD(){
		
		WebDriver driver = new HtmlUnitDriver();
		
		//forum9 hotdeal
		driver.get(urlList.get(1));
		
		String maxid = sdLog.get(0);
		
		List<WebElement> dealsf9 = driver.findElement(By.id("threadbits_forum_9")).findElements(By.tagName("tr"));
		for(WebElement tr: dealsf9) {
			
			String id = tr.getAttribute("id");
			String title = tr.findElement(By.className("threadtitleline")).findElement(By.xpath("a")).getText();
			String link = tr.findElement(By.className("threadtitleline")).findElement(By.xpath("a")).getAttribute("href");
			if(id.compareTo(maxid)>0) {
				maxid = id; 
			}
			
			if(id.compareTo(sdLog.get(0))<=0) {
				
			}
			else{
				System.out.println("New SD Hotdeal: "+ title);
				msgbody.append(title+"\n"+link+"\n");
			}
			
		}
		//update new value
		sdLog.set(0, maxid);
		
		//forum30 dealtalk
		
		driver.get(urlList.get(2));
		
		maxid = sdLog.get(1);
		
		List<WebElement> dealsf30 = driver.findElement(By.id("threadbits_forum_30")).findElements(By.tagName("tr"));
		for(WebElement tr: dealsf30) {
			String id = tr.getAttribute("id");
			String title = tr.findElement(By.className("threadtitleline")).findElement(By.xpath("a")).getText();
			String link = tr.findElement(By.className("threadtitleline")).findElement(By.xpath("a")).getAttribute("href");
			if(id.compareTo(maxid)>0) {
				maxid = id; 
			}
			
			if(id.compareTo(sdLog.get(1))<=0) {
				
			}
			else{
				System.out.println("New SD Dealtalk: " + title); 
				msgbody.append(title+"\n"+link+"\n");
			}
			
		}
		//update new value
		sdLog.set(1, maxid);
		
		//notify new deals and update the log info.
		if(msgbody != null && (!msgbody.toString().equals(""))) {
			msgsubject = "Alert : New Slickdeal deals";
			SendEmail(emailList);
					
			//clean the msgbody, and update the dealsea log
			msgbody.setLength(0);
		}
			
		driver.close();
		driver.quit();
		
		try {
			PrintWriter pw = new PrintWriter(new File("MonitorData/SlickDealData.txt"));
			for(String tmp:sdLog) {
				pw.write(tmp+System.getProperty("line.separator"));
				
			}
			pw.close();
		}	

		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
	
	private void InitDealSea(){
		WebDriver driver = new HtmlUnitDriver();
		driver.get(urlList.get(0));
		List<WebElement> deals = driver.findElements(By.className("dealbox"));
		
		for(int i =0; i<3; i++) {
			dealseaLog.add(deals.get(i).findElement(By.tagName("a")).getAttribute("href"));
			
		}
		
		
		try {
			PrintWriter pw = new PrintWriter(new File(dir+"\\DealSeaData.txt"));
			for(String tmp:dealseaLog) {
				pw.write(tmp+System.getProperty("line.separator"));
				
			}
			pw.close();
		}	

		catch (Exception ex) {
			ex.printStackTrace();
		}
	

	}
	
	private void ScanDealsea(){
		WebDriver driver = new HtmlUnitDriver();
		driver.get(urlList.get(0));
		
    	//java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
        //java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
        
        
        List<WebElement> deals = driver.findElements(By.className("dealbox"));
        
        //check for new deals 
		for(WebElement deal: deals) {
			String title = deal.findElement(By.tagName("Strong")).getText();
			String link = deal.findElement(By.tagName("a")).getAttribute("href");
			
			if(dealseaLog.contains(link)) {
				break;
			}
			else {
				System.out.println("New Dealsea deal : " + title);
				msgbody.append(title+"\n"+link+"\n");
			
			}
			
		}
		
		//notify new deals and update the log info.
		if(msgbody != null && (!msgbody.toString().equals(""))) {
			msgsubject = "Alert : New DealSea deals";
			SendEmail(emailList);
			
			//clean the msgbody, and update the dealsea log
			msgbody.setLength(0);
			dealseaLog.clear();
			for(int i =0; i<3; i++) {
				dealseaLog.add(deals.get(i).findElement(By.tagName("a")).getAttribute("href"));
				
			}			
		}
		
		
		driver.close();
		driver.quit();
		
		try {
			PrintWriter pw = new PrintWriter(new File("MonitorData/DealSeaData.txt"));
			for(String tmp:dealseaLog) {
				pw.write(tmp+System.getProperty("line.separator"));
				
			}
			pw.close();
		}	

		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		
	}
	
	private void PrintUpdate(){
		System.out.println("****************************************");
		System.out.println(sdLog);
		System.out.println(dealseaLog);
		System.out.println("****************************************");
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
						return new PasswordAuthentication("waldealor", "yaoki!978916");
					}
				  });
		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("waldealor@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(sb.toString(), true));
			message.setSubject(msgsubject);
			message.setText(msgbody.toString());
	 
			Transport.send(message);
	 
			System.out.println("Notifications Sent!");
	 
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException{ 
		
		//turn off log info from htmlUnit
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		
                //TEST RUN
                
                
		DealsMonitor monitor = new DealsMonitor();
		while(true){
			System.out.println(LocalDateTime.now());
			
			try {
				monitor.ScanSD();
				monitor.ScanDealsea();
				monitor.PrintUpdate();
			}
			catch (Exception ex) {
				System.out.println("scanning exceptions, pass");
			}
			
			
			Thread.sleep(interval*60*1000);
		}
		
	
		
    
    }  

}
