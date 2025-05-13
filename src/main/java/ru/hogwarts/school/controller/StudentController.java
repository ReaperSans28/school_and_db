package ru.hogwarts.school.controller;

import java.util.Optional;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Optional<Student>> retrieveStudent(@PathVariable long studentId) {
        Optional<Student> studentDetails = service.findStudent(studentId);
        return studentDetails.isPresent() ? ResponseEntity.ok(studentDetails) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Student> addNewStudent(@RequestBody Student student) {
        Student addedStudent = service.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedStudent);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updatedStudent = service.editStudent(student);
        return updatedStudent != null ? ResponseEntity.ok(updatedStudent) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<Void> removeStudent(@PathVariable long studentId) {
        service.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<Collection<Student>> getStudentFaculty(@PathVariable long id) {
        Collection<Student> students = service.findByFacultyId(id);
        if (students.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getStudents(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
        if (min != null && max != null) {
            return ResponseEntity.ok(service.findByAgeBetween(min, max));
        }
        return ResponseEntity.ok(service.getAllStudents());
    }
    
    @GetMapping("/count")
    public ResponseEntity<Integer> getTotalCount() {
        return ResponseEntity.ok(service.getTotalCount());
    }
    
    @GetMapping("/average-age")
    public ResponseEntity<Double> getAverageAge() {
        return ResponseEntity.ok(service.getAverageAge());
    }
    
    @GetMapping("/last-five")
    public ResponseEntity<List<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(service.getLastFiveStudents());
    }

    @GetMapping("/a-names")
    public ResponseEntity<List<String>> getNamesStartingWithA() {
        List<String> names = service.getNamesStartingWithA();
        return ResponseEntity.ok(names);
    }

    @GetMapping("/sum")
    public ResponseEntity<Long> getSum() {
        int n = 1_000_000;
        long sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return ResponseEntity.ok(sum);
    }
}