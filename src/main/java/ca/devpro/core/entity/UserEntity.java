package ca.devpro.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Entity
@Table(name = "USER")
@Getter
@ToString
public class UserEntity {

	@Id
	@Column(name = "USER_ID")
	@Type(type = "uuid-char")
	@Setter()
	private UUID userId;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
}

