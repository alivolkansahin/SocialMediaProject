package com.socialmedia.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.auth-exchange}")
    private String exchange;

    @Value("${rabbitmq.register-queue}")
    private String registerQueueName;

    @Value("${rabbitmq.activation-queue}")
    private String activationQueueName;

    @Value("${rabbitmq.registerElastic-queue}")
    private String registerElasticQueueName;

    @Value("${rabbitmq.register-bindingKey}")
    private String registerBindingKey;

    @Value("${rabbitmq.activation-bindingKey}")
    private String activationBindingKey;

    @Value("${rabbitmq.registerElastic-bindingKey}")
    private String registerElasticBindingKey;


    @Bean
    DirectExchange exchangeAuth(){
        return new DirectExchange(exchange);
    }
    @Bean
    Queue registerQueue(){
        return new Queue(registerQueueName);
    }
    @Bean
    Queue activationQueue(){
        return new Queue(activationQueueName);
    }
    @Bean
    Queue registerElasticQueue(){
        return new Queue(registerElasticQueueName);
    }
    @Bean
    public Binding bindingRegister(Queue registerQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(registerQueue).to(exchangeAuth).with(registerBindingKey);
    }

    @Bean
    public Binding bindingActivation(Queue activationQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(activationQueue).to(exchangeAuth).with(activationBindingKey);
    }

    @Bean
    public Binding bindingRegisterElastic(Queue registerElasticQueue, DirectExchange exchangeAuth){
        return BindingBuilder.bind(registerElasticQueue).to(exchangeAuth).with(registerElasticBindingKey);
    }

    @Bean
    MessageConverter messageConverter(){
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory factory){
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }

}
