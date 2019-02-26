package ru.ntechs.insworks;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.ntechs.insworks.jpa.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
	// если ты пишешь что findBy_LastName_ то подразумевается что у модели Model есть проперти которая называется lastName с типом String.
	List<Model> findByLastName(String model);
}
