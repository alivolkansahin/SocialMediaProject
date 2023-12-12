package com.socialmedia.entity;

import com.socialmedia.entity.enums.ERole;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.utility.CodeGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "tblauths")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 2, max = 20, message = "Username must be between {min} and {max} characters long!")
    private String username;

    @Column(nullable = false)
    @Email(message = "Check your email address for inappropriate characters!")
    private String email;

    @Column(nullable = false)
    @Size(min = 2, max = 20, message = "Password must be between {min} and {max} characters long!")
    private String password;

    @Builder.Default
    private String activationCode = CodeGenerator.generateCode();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ERole role = ERole.USER;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EStatus status = EStatus.PENDING;

}
