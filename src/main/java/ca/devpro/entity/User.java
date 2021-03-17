package ca.devpro.entity;

import liquibase.pro.packaged.U;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.UUID;

@Table(name = "usr")
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class User {

    @Id
    @Column(name = "usr_id")
    @Type(type = "uuid-char")
    @Setter(AccessLevel.NONE)
    private UUID userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    @Setter(AccessLevel.NONE)
    private String username;

    public static User newInstance(String username) {
        User user = new User();
        user.userId = UUID.randomUUID();
        user.username = username;
        return user;
    }
}
