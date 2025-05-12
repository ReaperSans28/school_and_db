package ru.hogwarts.school.service;

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
}