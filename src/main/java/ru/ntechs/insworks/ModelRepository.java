package ru.ntechs.insworks;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.ntechs.insworks.jpa.Model;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
	List<Model> findByLastName(String model);
}
