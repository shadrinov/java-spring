package ru.ntechs.insworks.jpa.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ru.ntechs.insworks.jpa.User;

public interface AccountRepository extends CrudRepository<User, Long> {
	List<User> findByLastName(String lastName);
}