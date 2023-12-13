package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.rabbitmq.model.MailModel;
import com.socialmedia.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailConsumer {

    private final MailSenderService mailSenderService;

    @RabbitListener(queues = "${rabbitmq.mail-queue}")
    public void sendActivationMail(MailModel mailModel){
        mailSenderService.sendMail(mailModel);
    }



}
