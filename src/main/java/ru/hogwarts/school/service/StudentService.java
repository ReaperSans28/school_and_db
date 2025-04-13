package ru.hogwarts.school.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repository;

    public Student addStudent(Student student) {
        return repository.save(student);
    }

    public Optional<Student> findStudent(long id) {
        return repository.findById(id);
    }

    public Student editStudent(Student student) {
        return repository.save(student);
    }

    public void deleteStudent(long id) {
        repository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        return repository.findAll();
    }

    public Collection<Student> findByFacultyId(long id) {
        return repository.findByFacultyId(id);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return repository.findByAgeBetween(min, max);
    }
}
