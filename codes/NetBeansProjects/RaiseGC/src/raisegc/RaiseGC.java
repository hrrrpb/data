/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raisegc;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 *
 * @author yi
 */
public class RaiseGC {
    
    private ArrayList<String> emails = new ArrayList();
    private String sender;
    private String senderPwd;
    private int interval;
    private Map<String, Double> links = new HashMap();

    
    
    private void init() {
        File data = new File("data");
              
		
	if(!data.exists()) {
		System.out.println("Error: folder monitordata does not exist!");
		System.exit(0);
	}
		
	else { //read in email data and scan interval
		
		File email = new File("data/alert-emails.txt");
		File scaninterval = new File("data/interval.txt");
		File from = new File("data/sender.txt");
                File GCs = new File("data/giftcards.txt");
		try {
				
		        
		        Scanner scEmail = new Scanner(email);
		        Scanner scInterval = new Scanner(scaninterval);
		        Scanner scSender = new Scanner(from);
		        Scanner scGC = new Scanner(GCs);
		        interval = scInterval.nextInt();
		        scInterval.close();
		        
		        while(scEmail.hasNextLine()){
		        	emails.add(scEmail.nextLine().trim());
		        }
		        scEmail.close();
                        
                        String baseUrl = "https://www.raise.com/buy-STORE-gift-cards";
                        while(scGC.hasNextLine()){
                            String temp = scGC.nextLine();
                            String[] parts = temp.split(":");
                            String store = parts[0].trim().replace(" ", "-");
                            String url = baseUrl.replace("STORE", store);
                            String percent = parts[1].trim().replace("%", "");
                            Double limit =Double.parseDouble(percent);
                            links.put(url, limit);
                        	
		        }
		        scEmail.close();
                        
                        sender = scSender.next();
                        senderPwd = scSender.next();
                        
                        
		        
		        
			}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
			
	}
    }
    
    public RaiseGC(){
        init();
        
    }
    
    
    
    public void checkGC() {
        while(true) {
            try {
                System.out.println("\nRaise GC Scan : "+LocalDateTime.now()+"\n");
                for (String url : links.keySet()) {
                    checkGC(url, links.get(url));
                }
                Thread.sleep(interval*60*1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        
        }
    }
    
    private void checkGC(String url, Double limit) {
        try {
                
		Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
                String percent = doc.select("tbody").select("tr").get(0).select("td").get(3).text();
                Double pnt = Double.parseDouble(percent.trim().replace("%", ""));
                String info = doc.title() +" : " + percent;
                System.out.println(info);
                if (pnt >= limit){
                    
                    SendEmail(info, url);
                }
 	    	
            }	
	    catch(Exception e){
                e.printStackTrace();
	    	System.out.println("Error, pass "+"\n");
	    	    	
	    }
        
    }
    
    public void SendEmail(String msgsubject, String msgbody) {
		
		
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
						return new PasswordAuthentication(sender, senderPwd);
					}
				  });
		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender+"@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(sb.toString(), true));
			message.setSubject(msgsubject);
			message.setText(msgbody);
	 
			Transport.send(message);
	 
			System.out.println("Notifications Sent! ");
	 
		} 
		catch (Exception ex) {
			System.out.println("Notification failure ! ");
		}
	
	
		
		
	}
    
    
    
    public static void main(String[] args) {
        
        RaiseGC scan = new RaiseGC();
        
        scan.checkGC();
        
    }
    
}
