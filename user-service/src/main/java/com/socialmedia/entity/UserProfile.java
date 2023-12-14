package com.socialmedia.entity;

import com.socialmedia.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "tbluserprofiles")
public class UserProfile implements Serializable { // redis için ekledik.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long authId;

    private String username;

    private String email;

    private String phone;

    private String avatar;

    private String address;

    private String about;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EStatus status = EStatus.PENDING;

}
