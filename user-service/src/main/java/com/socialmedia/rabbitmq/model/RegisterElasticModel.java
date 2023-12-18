package com.socialmedia.rabbitmq.model;

import com.socialmedia.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterElasticModel {

    private Long userProfileId;

    private Long authId;

    private String username;

    private String email;

    private String phone;

    private String avatar;

    private String address;

    private String about;

    private EStatus status;
}
