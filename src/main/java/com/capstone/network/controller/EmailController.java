//package com.capstone.network.controller;
// 
// 
//import com.capstone.network.entities.EmailDetails;
//import com.capstone.network.service.EmailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
////Annotation
//@RestController
////Class
//public class EmailController {
//
//	@Autowired
//	private EmailService emailService;
// 
//	@PostMapping("/sendMail")
//	public String sendMail()
//	{
//		String status= emailService.sendSimpleMail(String username,String resolution);
//
//		return status;
//	}
// 
//	@PostMapping("/sendMailWithAttachment")
//	public String sendMailWithAttachment(
//		@RequestBody EmailDetails details)
//	{
//		String status=emailService.sendMailWithAttachment(details);
//
//		return status;
//	}
//}
