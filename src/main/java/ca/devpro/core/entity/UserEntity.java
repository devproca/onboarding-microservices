package ca.devpro.core.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Entity
@Table(name = "USER")
@Data
@Accessors(chain = true)
@Getter
@ToString
public class UserEntity {

	@Id
	@Column(name = "USER_ID")
	@Type(type = "uuid-char")
	@Setter(AccessLevel.PRIVATE)
	private UUID userId;
	@Column(name = "USERNAME")
	@Setter
	private String username;
	@Column(name = "FIRST_NAME")
	@Setter
	private String firstName;
	@Column(name = "LAST_NAME")
	@Setter
	private String lastName;

	public static UserEntity newInstance(){
		return new UserEntity()
				.setUserId(UUID.randomUUID());
	}

}

