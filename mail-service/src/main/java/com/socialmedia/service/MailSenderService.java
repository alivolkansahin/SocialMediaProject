package com.socialmedia.service;

import com.socialmedia.rabbitmq.model.MailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender; // yml'ı confige taşıdık diye IDE görmüyor ve hata veriyor, IDE'nin kafasının karışması bu sadece, programda sorun yok.

    public void sendMail(MailModel model){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("mail");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Hesap Oluşturma Başarılı......");
        mailMessage.setText("code: " + model.getActivationCode());
        mailMessage.setCc("mail");

        javaMailSender.send(mailMessage);
    }

}
