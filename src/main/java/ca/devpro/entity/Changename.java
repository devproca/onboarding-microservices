package ca.devpro.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Table(name = "changename")
@Entity
@Getter
@Setter
@Accessors(chain = true)
public class Changename {
    @Id
    @Column(name = "changename_id")
    @Type(type = "uuid-char")
    @Setter(AccessLevel.NONE)
    private UUID changenameId;

    @Column(name = "usr_id")
    @Type(type = "uuid-char")
    @Setter(AccessLevel.NONE)
    private UUID userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "created_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp createdDate;

    public static Changename newInstance(UUID userId) {
        Changename changename = new Changename();
        changename.changenameId = UUID.randomUUID();
        changename.userId = userId;
        return changename;
    }
}