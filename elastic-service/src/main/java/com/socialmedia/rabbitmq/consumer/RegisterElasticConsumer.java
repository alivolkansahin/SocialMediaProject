package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.rabbitmq.model.RegisterElasticModel;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterElasticConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "${rabbitmq.registerElastic-queue}")
    public void newUserCreate(RegisterElasticModel registerElasticModel){
        userProfileService.save(IUserMapper.INSTANCE.modelToUserProfile(registerElasticModel));
    }



}
