/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storesmonitor.util;

/**
 *
 * @author Yi
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class emailSender {
	
	private String msgsubject;
	private String msgbody;
	private ArrayList<String> emails;
        private String sender;
        private String senderPwd;
	
	public emailSender(String msgsubject, String msgbody, ArrayList<String> emails){
		this.msgsubject=msgsubject;
		this.msgbody=msgbody;
		this.emails = emails;
                File senderFile = new File("monitordata/sender.txt");
                try{
                    Scanner scSender = new Scanner(senderFile);
                    sender = scSender.next();
                    senderPwd = scSender.next();
                    
                    
                }
                catch(Exception ex){
                    System.out.println("Cannot find the file sender.txt, or invalid info for gmail account");
                    System.exit(0);
                }
                
                
                
	}

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    

    public String getSenderPwd() {
        return senderPwd;
    }

    public void setSenderPwd(String senderPwd) {
        this.senderPwd = senderPwd;
    }
	
	
        
        
	public void SendEmail() {
		
		
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

}