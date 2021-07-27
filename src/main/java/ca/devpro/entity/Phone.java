package ca.devpro.entity;

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

@Table(name = "phone")
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Phone {
    @Id
    @Column(name = "phone_id")
    @Type(type = "uuid-char")
    @Setter(AccessLevel.NONE)
    private UUID phoneId;

    @Column(name = "usr_id")
    @Type(type = "uuid-char")
    private UUID userId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_verified")
    private Boolean isVerified = false;

    @Column(name = "code_verifier")
    private String codeVerify;


    public static Phone newInstance(UUID userId) {
        Phone phone = new Phone();
        phone.phoneId = UUID.randomUUID();
        phone.userId = userId;
        return phone;
    }

}

