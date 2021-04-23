package ca.devpro.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Locale;
import java.util.UUID;

@Entity
@Table(name = "phone")
@Getter
@Setter
@Accessors(chain = true)
public class Phone {

    @Id
    @Column(name ="phone_id")
    @Setter(AccessLevel.NONE)
    @Type(type = "uuid-char")
    private UUID phoneId;

    @Column(name ="usr_id")
    @Setter(AccessLevel.NONE)
    @Type(type = "uuid-char")
    private UUID userId;

    @Column(name = "phone_num")
    private String phoneNumber;

    @Column(name = "phone_type")
    private String phoneType;

    public static Phone newPhone(UUID userId){
        Phone entity = new Phone();
        entity.userId = userId;
        entity.phoneId = UUID.randomUUID();
        return entity;
    }
}
