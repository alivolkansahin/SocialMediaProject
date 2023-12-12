package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "${rabbitmq.register-queue}")
    public void newUserCreate(RegisterModel registerModel){
        userProfileService.createNewUserWithRabbit(registerModel);
    }



}
