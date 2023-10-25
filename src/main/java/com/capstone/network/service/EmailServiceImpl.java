package com.capstone.network.service;

import java.io.File;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.capstone.network.entities.Incident; 
//import com.capstone.network.entities.EmailDetails;
import com.capstone.network.repository.IncidentRepository;
import com.google.common.base.Optional;


@Service
public class EmailServiceImpl   {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private IncidentRepository incidentRepository; // Inject the repository to fetch incident details

    public String sendResolutionMail(Long incidentId, String userEmail, String resolution) {
        try {
        	
            java.util.Optional<Incident> optionalIncident = incidentRepository.findById(incidentId);
            if (optionalIncident.isPresent()) {
                Incident incident = optionalIncident.get();

                // Create the email content
                String subject = "Incident Resolution";
                String body = buildResolutionEmailBody(incident);

                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

                helper.setFrom(sender);
                helper.setTo(userEmail);
                helper.setSubject(subject);
                helper.setText(body, true);

                // Attach any files if needed
                // helper.addAttachment("attachmentName", new FileSystemResource(new File("path/to/file")));

                javaMailSender.send(mimeMessage);

                return "Mail Sent Successfully...";
            } else {
                return "Incident not found for ID: " + incidentId;
            }
        } catch (Exception e) {
            return "Error while Sending Mail: " + e.getMessage();
        }
    }
// 
//    private String buildResolutionEmailBody(Incident incident) {
//        // Customize the email body as per your requirements
//        return "Dear " + incident.getUser() + "," + System.lineSeparator() + System.lineSeparator() +
//                "We are writing to inform you that the incident with ID " + incident.getId() + " has been resolved." + System.lineSeparator() + System.lineSeparator() +
//                "Incident Details:" + System.lineSeparator() +
//                "- Description: " + incident.getDescription() + System.lineSeparator() +
//                "- Resolution: " + incident.getResolution() + System.lineSeparator() + System.lineSeparator() +
//                "Thank you for your understanding." + System.lineSeparator() + System.lineSeparator() +
//                "Best regards," + System.lineSeparator() +
//                "[Your Company Name]";
//    }

    private String buildResolutionEmailBody(Incident incident) {
        // Customize the email body as per your requirements
        return "Dear " + incident.getUser() + ",<br><br>" +
                "We are writing to inform you that the incident with ID " + incident.getId() + " has been resolved.<br><br>" +
                "Incident Details:<br>" +
                "- Description: " + incident.getDescription() + "<br>" +
                "- Resolution: " + incident.getResolution() + "<br><br>" +
                "Thank you for your understanding.<br><br>" +
                "Best regards,<br>" +
                "[Your Company Name]";
    }

//
//    private String buildResolutionEmailBody(Incident incident) {
//        // Customize the email body as per your requirements
//        return "Dear " + incident.getUser() + ",\n\n"
//                + "We are writing to inform you that the incident with ID " + incident.getId() + " has been resolved.\n\n"
//                + "Incident Details:\n"
//                + "- Description: " + incident.getDescription() + "\n"
//                + "- Resolution: " + incident.getResolution() + "\n\n"
//                + "Thank you for your understanding.\n\n"
//                + "Best regards,\n"
//                + "[Your Company Name]";
//    }
// 
}


//@Service 
//public class EmailServiceImpl implements EmailService {
//	@Autowired
//	private JavaMailSender javaMailSender;
//
//	@Value("${spring.mail.username}")
//	private String sender;
//
//
//	public String sendSimpleMail(String username,String resolution) {
//		try {
//
//			SimpleMailMessage mailMessage = new SimpleMailMessage();             
//			mailMessage.setFrom(sender);
//			mailMessage.setTo(username);
//			mailMessage.setText(resolution);
//			mailMessage.setSubject("Resolution to the problem"); 
//			javaMailSender.send(mailMessage);
//			return "Mail Sent Successfully...";
//		}
//
//		catch (Exception e) {
//			return "Error while Sending Mail: " + e.getMessage();
//		}
//	}
//
//	public String sendMailWithAttachment(EmailDetails details)
//	{ 
//		MimeMessage mimeMessage
//		= javaMailSender.createMimeMessage();
//		MimeMessageHelper mimeMessageHelper;
//
//		try { 
//			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//			mimeMessageHelper.setFrom(sender);
//			mimeMessageHelper.setTo(details.getRecipient());
//			mimeMessageHelper.setText(details.getMsgBody());
//			mimeMessageHelper.setSubject(
//					details.getSubject());
//
//			FileSystemResource file	= new FileSystemResource(new File(details.getAttachment()));
//
//			mimeMessageHelper.addAttachment(file.getFilename(), file);
//			javaMailSender.send(mimeMessage);
//			return "Mail sent Successfully";
//		}
//
//		catch (MessagingException e) { 
//			return "Error while sending mail!!!";
//		}
//	}
 
