package it.polito.applied.asti.clan.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import it.polito.applied.asti.clan.pojo.UserMessage;


@Service
public class AsyncUpdaterImpl implements AsyncUpdater {
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Autowired
	JavaMailSender mailSender;
	
	@Value("${mailAddress.from}")
	private String from;
	
	@Value("${mailAddress.to}")
	private String to;
	
	@Value("${domain}")
	private String domain;
	
	@Override
	public void sendEmailFromSite(UserMessage userMessage) {
		
		System.out.println(from+" "+to+" "+userMessage.getEmail());
		
		Runnable r = new SendEmailFromSite(userMessage.getName(), userMessage.getEmail(), userMessage.getMessage());
		taskExecutor.execute(r);	
	}
	
	
	private class SendEmailFromSite implements Runnable{
		
		private String name, email, message;
		
		public SendEmailFromSite(String name, String email, String message) {
			this.name = name;
			this.email = email;
			this.message = message;
		}

		@Override
		public void run() {
			SimpleMailMessage messageToUser = new SimpleMailMessage();
			String text="Gentile "+ name+",\n\n"
					+ "grazie per il suo interesse. Le risponderemo appena possibile.\n\n"
					+ "Intanto le auguriamo una buona giornata.\n\n\nSaluti\nAstiMuesi";
			messageToUser.setFrom(from);
			messageToUser.setTo(email);
			messageToUser.setSubject("AstiMusei.it - Grazie per averci contattato");
			messageToUser.setText(text);
		
			mailSender.send(messageToUser);
			
			SimpleMailMessage messageToAsti = new SimpleMailMessage();
			String textToAsti = "Mittente: "+name+"\n"
					+"Email: "+email+"\n"
					+"Data: "+new Date()+"\n\n"
					+"Messaggio:\n "+message;
			messageToAsti.setFrom(to);
			messageToAsti.setTo(to);
			messageToAsti.setSubject("[ASTIMUSEI.IT] - FORM CONTACT");
			messageToAsti.setText(textToAsti);
		
			mailSender.send(messageToAsti);
		}
		
	}

}
