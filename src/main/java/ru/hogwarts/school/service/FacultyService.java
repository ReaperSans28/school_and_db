package ru.hogwarts.school.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository repository;

    public Faculty addFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public Optional<Faculty> findFaculty(long id) {
        return repository.findById(id);
    }

    public Faculty editFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    public void deleteFaculty(long id) {
        repository.deleteById(id);
    }

    public Optional<Faculty> findByStudentId(long id) {
        return repository.findByStudents_Id(id);
    }

    public Optional<Faculty> searchByNameOrColor(String name, String color) {
        return repository.searchByNameOrColor(name, color);
    }
}