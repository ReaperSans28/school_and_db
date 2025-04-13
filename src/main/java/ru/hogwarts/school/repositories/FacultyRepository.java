package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.hogwarts.school.model.Faculty;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> searchByNameOrColor(String name, String color);
    Optional<Faculty> findByStudents_Id(long id);
}
