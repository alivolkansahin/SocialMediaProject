package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "${rabbitmq.activation-queue}")
    public void activateUser(Long authId){
        userProfileService.activation(authId);
    }

}
