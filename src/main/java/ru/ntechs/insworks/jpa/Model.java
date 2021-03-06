package ru.ntechs.insworks.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "models")

public class Model {
	@Id
	@NotNull
	@Column(name="id_model")
	@GeneratedValue(generator = "gen_id_model")
	@SequenceGenerator(name = "gen_id_model", sequenceName = "gen_id_model", allocationSize = 1)
	private Long id;

	private String title;
	private Integer yearstart;
	private Integer yearend;

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYearstart() {
		return yearstart;
	}

	public void setYearstart(Integer yearStart) {
		this.yearstart = yearStart;
	}

	public Integer getYearend() {
		return yearend;
	}

	public void setYearend(Integer yearEnd) {
		this.yearend = yearEnd;
	}
}
