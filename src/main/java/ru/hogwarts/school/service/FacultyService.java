package ru.hogwarts.school.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository repository;

    Logger logger = Logger.getLogger(FacultyService.class.getName());

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Creating faculty: " + faculty);
        return repository.save(faculty);
    }

    public Optional<Faculty> findFaculty(long id) {
        logger.info("Finding faculty with id: " + id);
        return repository.findById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.info("Updating faculty: " + faculty);
        return repository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.info("Deleting faculty with id: " + id);
        repository.deleteById(id);
    }

    public Optional<Faculty> findByStudentId(long id) {
        logger.info("Finding student with id: " + id);
        return repository.findByStudents_Id(id);
    }

    public Optional<Faculty> searchByNameOrColor(String name, String color) {
        logger.info("Finding faculties by color and name: " + name + " and color: " + color);
        return repository.searchByNameOrColor(name, color);
    }

    public String getLongestFacultyName() {
        logger.info("Getting longest faculty name");
        List<Faculty> faculties = repository.findAll();
        return faculties.parallelStream()
                .map(Faculty::getName)
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }
}