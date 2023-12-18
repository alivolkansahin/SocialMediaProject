package com.socialmedia.rabbitmq.producer;

import com.socialmedia.rabbitmq.model.RegisterElasticModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterElasticProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.auth-exchange}")
    private String directExchange;

    @Value("${rabbitmq.registerElastic-bindingKey}")
    private String registerElasticBindingKey;

    public void sendNewUser(RegisterElasticModel registerElasticModel){
        rabbitTemplate.convertAndSend(directExchange, registerElasticBindingKey, registerElasticModel);
    }

}
