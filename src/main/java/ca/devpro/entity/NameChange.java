package ca.devpro.entity;

import ca.devpro.api.NameChangeDto;
import ca.devpro.api.UserDto;
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
@Table(name = "namechange")
@Getter
@Setter
@Accessors(chain = true)
public class NameChange {

    @Id
    @Column(name = "namechange_id")
    @Type(type = "uuid-char")
    @Setter(AccessLevel.NONE)
    private UUID nameChangeId;

    @Column(name = "usr_id")
    @Type(type = "uuid-char")
    @Setter(AccessLevel.NONE)
    private UUID userId;

    @Column(name = "previous_username")
    private String previousUsername;

    @Column(name = "updated_username")
    private String updatedUsername;

    @Column(name = "previous_firstname")
    private String previousFirstName;

    @Column(name = "updated_firstname")
    private String updatedFirstName;

    @Column(name = "previous_lastname")
    private String previousLastName;

    @Column(name = "updated_lastname")
    private String updatedLastName;

    public static NameChange newInstance(NameChangeDto dto) {
        NameChange nameChange = new NameChange();

        nameChange.nameChangeId = UUID.randomUUID();
        nameChange.userId = dto.getUserId();
        nameChange.previousUsername = dto.getPreviousUsername();
        nameChange.previousFirstName = dto.getPreviousFirstName();
        nameChange.previousLastName = dto.getPreviousLastName();
        nameChange.updatedUsername = dto.getUpdatedUsername();
        nameChange.updatedFirstName = dto.getUpdatedFirstName();
        nameChange.updatedLastName = dto.getUpdatedLastName();

        return nameChange;
    }
}
