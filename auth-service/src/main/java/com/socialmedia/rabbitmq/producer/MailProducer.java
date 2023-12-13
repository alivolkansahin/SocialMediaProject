package com.socialmedia.rabbitmq.producer;

import com.socialmedia.rabbitmq.model.MailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth-exchange}")
    private String directExchange;

    @Value("${rabbitmq.mail-bindingKey}")
    private String mailBindingKey;

    public void sendActivationMail(MailModel mailModel){
        rabbitTemplate.convertAndSend(directExchange, mailBindingKey, mailModel);
    }


}
