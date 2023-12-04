package com.socialmedia.entity;

import com.socialmedia.entity.enums.ERole;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.utility.CodeGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "users")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    @Builder.Default
    private String activationCode = CodeGenerator.generateCode();

    private ERole role;

    @Builder.Default
    private EStatus status = EStatus.PENDING;

}
