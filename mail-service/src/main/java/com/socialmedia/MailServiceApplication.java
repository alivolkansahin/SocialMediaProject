package com.socialmedia;

import com.socialmedia.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class MailServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MailServiceApplication.class);
    }

//    @Autowired
//    private MailSenderService mailSenderService;

//    @EventListener(ApplicationReadyEvent.class) // program başladığı an çalışmasını belirten anotasyon
//    public void sendMail(){
//        mailSenderService.sendMail("a45e852");
//    }

}