package ru.ntechs.insworks.jpa.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.ntechs.insworks.jpa.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
	List<Manufacturer> findByLastName(String manufacturer);
}
