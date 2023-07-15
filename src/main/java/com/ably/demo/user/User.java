package com.ably.demo.user;

import com.ably.demo.common.BaseEntity;
import com.ably.demo.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Setter(value = AccessLevel.PRIVATE)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    public static User createUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setName(userRequestDto.getName());

        return user;
    }

    public void modifyLastLoginDateTime(LocalDateTime now) {
        this.lastLogin = now;
    }
}