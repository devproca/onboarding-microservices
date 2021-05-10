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

@Entity
@Table(name = "phone")
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
    @Setter(AccessLevel.NONE)
    private UUID userId;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "phoneType")
    private String phoneType;

    @Column(name = "isVerified")
    private Boolean isVerified;

    @Column(name = "verificationKey")
    private String verificationKey;

    public static Phone newInstance(UUID userId) {
        Phone phone = new Phone();
        phone.phoneId = UUID.randomUUID();
        phone.userId = userId;
        return phone;
    }

}
