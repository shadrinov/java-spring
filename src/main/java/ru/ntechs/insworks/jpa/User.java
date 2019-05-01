package ru.ntechs.insworks.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "users")
// вот lombok
@Data
public class User {
	@Id
	@NotNull
	@Column(name="id_user")
	@GeneratedValue(generator = "gen_id_user")
	@SequenceGenerator(name = "gen_id_user", sequenceName = "gen_id_user", allocationSize = 1)
	private Long id;

	private String username;
	private String password;
	private String lastName;
	
	/**
	 * с lombok это не надо
	 */
	
}
