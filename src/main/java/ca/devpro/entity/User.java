package ca.devpro.entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "usr")
@Getter
@Setter
@EqualsAndHashCode(of = "userId")
@Accessors(chain = true)
public class User {

    @Id
    @Type(type = "uuid-char")
    @Column(name="usr_id")
    @Setter(AccessLevel.NONE)
    private UUID userId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="username")
    @Setter(AccessLevel.NONE)
    private String username;

    public static User newInstance(String username) {
        User entity = new User();
        entity.userId = UUID.randomUUID();
        entity.username = username;
        return entity;
    }
}


