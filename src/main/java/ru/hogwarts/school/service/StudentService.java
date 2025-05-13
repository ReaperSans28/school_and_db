package ru.hogwarts.school.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

@Service
public class StudentService {

    java.util.logging.Logger logger = Logger.getLogger(StudentService.class.getName());

    @Autowired
    private StudentRepository repository;

    public Student addStudent(Student student) {
        logger.info("Creating student: " + student);
        return repository.save(student);
    }

    public Optional<Student> findStudent(long id) {
        logger.info("Finding student with ID: " + id);
        return repository.findById(id);
    }

    public Student editStudent(Student student) {
        logger.info("Editing student: " + student);
        return repository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Deleting student with ID: " + id);
        repository.deleteById(id);
    }

    public Collection<Student> getAllStudents() {
        logger.info("Retrieving all students");
        return repository.findAll();
    }

    public Collection<Student> findByFacultyId(long id) {
        logger.info("Finding students by faculty ID: " + id);
        return repository.findByFacultyId(id);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Finding students with age between " + min + " and " + max);
        return repository.findByAgeBetween(min, max);
    }

    public Integer getTotalCount() {
        logger.info("Getting total count of students");
        return repository.getTotalCount();
    }

    public Double getAverageAge() {
        logger.info("Calculating average age of students");
        return repository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        logger.info("Retrieving last five students");
        return repository.getLastFiveStudents();
    }

    public List<String> getNamesStartingWithA() {
        return repository.findAll().stream()
                .map(Student::getName)
                .filter(name -> name != null && name.toUpperCase().startsWith("A"))
                .map(String::toUpperCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}