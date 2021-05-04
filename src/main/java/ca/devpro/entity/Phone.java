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

    @Column(name = "phonenumber")
    private String phoneNumber;


    public static Phone newInstance(String phoneNumber) {
        Phone phone = new Phone();
        phone.phoneId = UUID.randomUUID();
        phone.phoneNumber = phoneNumber;
        return phone;
    }

}
