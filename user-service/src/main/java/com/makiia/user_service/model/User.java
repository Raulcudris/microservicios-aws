package com.makiia.user_service.model;

import com.makiia.user_service.enums.Active;
import com.makiia.user_service.enums.Role;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Entity(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false, updatable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Active active;

    @Embedded
    private UserDetails userDetails;
}