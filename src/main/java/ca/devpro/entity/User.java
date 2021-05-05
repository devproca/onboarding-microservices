package ca.devpro.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "usr")
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

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name="phone_id")
    private List<Phone> phones;

    public static User newInstance(String username) {
        User user = new User();
        user.userId = UUID.randomUUID();
        user.username = username;
        user.phones = new ArrayList<>();
        return user;
    }

}
