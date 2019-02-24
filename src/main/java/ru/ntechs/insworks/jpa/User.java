package ru.ntechs.insworks.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
	@Id
	@NotNull
	@Column(name="id_user")
	@GeneratedValue(generator = "gen_id_user")
	@SequenceGenerator(name = "gen_id_user", sequenceName = "gen_id_user", allocationSize = 1)
	private Long id;

	private String username;
	private String password;


	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
