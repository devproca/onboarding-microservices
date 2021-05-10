package ca.devpro.entity;

import ca.devpro.api.ChangeHistoryDto;
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
@Table(name ="changehistory")
@Getter
@Setter
@Accessors(chain = true)
public class ChangeHistory {

    @Id
    @Column(name = "version_id")
    @Type(type = "uuid-char")
    @Setter(AccessLevel.NONE)
    private UUID versionId;

    @Column(name = "usr_id")
    @Type(type = "uuid-char")
    @Setter(AccessLevel.NONE)
    private UUID userId;

    @Column(name = "previous_firstname")
    private String previousFirstName;

    @Column(name = "updated_firstname")
    private String updatedFirstName;

    @Column(name = "previous_lastname")
    private String previousLastName;

    @Column(name = "updated_lastname")
    private String updatedLastName;

    @Column(name = "previous_username")
    private String previousUsername;

    @Column(name = "updated_username")
    private String updatedUsername;

    public static ChangeHistory newInstance(ChangeHistoryDto dto) {
        ChangeHistory changeHistory = new ChangeHistory();

        changeHistory.versionId = UUID.randomUUID();
        changeHistory.userId = dto.getUserId();
        changeHistory.previousUsername = dto.getPreviousUsername();
        changeHistory.previousFirstName = dto.getPreviousFirstName();
        changeHistory.previousLastName = dto.getPreviousLastName();
        changeHistory.updatedUsername = dto.getUpdatedUsername();
        changeHistory.updatedFirstName = dto.getUpdatedFirstName();
        changeHistory.updatedLastName = dto.getUpdatedLastName();

        return changeHistory;
    }



}
