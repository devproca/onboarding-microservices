package ca.devpro.core.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @Column(name = "user_id")
    @Type(type = "uuid-char")
    @Setter(AccessLevel.NONE)
    private UUID userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "password")
    private String password;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Column(name = "created_timestamp")
    private Instant createdTimestamp;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @LastModifiedDate
    @Column(name = "updated_timestamp")
    private Instant updatedTimestamp;

    @Version
    @Column(name = "version")
    private long version;

    public static User newInstance(String emailAddress, String password) {
        User user = new User();
        user.userId = UUID.randomUUID();
        user.emailAddress = emailAddress;
        user.password = password;
        return user;
    }
}
