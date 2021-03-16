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
public class PhoneNumber {

    @Id
    @Column(name = "phone_id")
    @Type(type = "uuid-char")
    @Setter(AccessLevel.NONE)
    private UUID phoneId;

    @Column(name = "usr_id")
    @Type(type = "uuid-char")
    @Setter(AccessLevel.NONE)
    private UUID userId;

    @Column(name = "phone_number")
    private String phoneNumber;

    public static PhoneNumber newInstance(UUID userId) {
        PhoneNumber phone = new PhoneNumber();
        phone.phoneId = UUID.randomUUID();
        phone.userId = userId;

        return phone;
    }

}
