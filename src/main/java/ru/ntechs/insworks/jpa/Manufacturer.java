package ru.ntechs.insworks.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "manufacturers")
public class Manufacturer {
	@Id
	@NotNull
	@Column(name="id_manufacturer")
	@GeneratedValue(generator = "gen_id_manufacturer")
	@SequenceGenerator(name = "gen_id_manufacturer", sequenceName = "gen_id_manufacturer", allocationSize = 1)
	private Long id;

	private String title;


	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
