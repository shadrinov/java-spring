package ru.ntechs.insworks.jpa.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ru.ntechs.insworks.jpa.User;

public interface AccountRepository extends CrudRepository<User, Long> {
	// если ты пишешь что findBy_LastName_ то подразумевается что у модели Model есть проперти которая называется lastName с типом String.
	// я добавил в User
	List<User> findByLastName(String lastName);
}