package ru.hogwarts.school.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final List<Long> studentIds = new ArrayList<>();
    private final List<Long> facultyIds = new ArrayList<>();

    @Test
    void testRetrieveStudent() {
        Student student = new Student();
        ResponseEntity<Student> responseCreate = restTemplate.postForEntity("/students", student, Student.class);
        long studentId = Objects.requireNonNull(responseCreate.getBody()).getId();
        studentIds.add(studentId);

        ResponseEntity<Optional> responseRetrieve = restTemplate.getForEntity("/students/" + studentId, Optional.class);
        assertEquals(HttpStatus.OK, responseRetrieve.getStatusCode());
    }

    @Test
    void testAddNewStudent() {
        Student student = new Student();
        ResponseEntity<Student> response = restTemplate.postForEntity("/students", student, Student.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        studentIds.add(Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void testUpdateStudent() {
        Student student = new Student();
        ResponseEntity<Student> responseCreate = restTemplate.postForEntity("/students", student, Student.class);
        long studentId = Objects.requireNonNull(responseCreate.getBody()).getId();
        studentIds.add(studentId);

        Student updatedStudent = new Student();
        updatedStudent.setId(studentId);
        ResponseEntity<Student> response = restTemplate.exchange("/students", HttpMethod.PUT, new HttpEntity<>(updatedStudent), Student.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRemoveStudent() {
        Student student = new Student();
        ResponseEntity<Student> responseCreate = restTemplate.postForEntity("/students", student, Student.class);
        long studentId = Objects.requireNonNull(responseCreate.getBody()).getId();
        studentIds.add(studentId);

        ResponseEntity<Void> response = restTemplate.exchange("/students/{studentId}", HttpMethod.DELETE, null, Void.class, studentId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetStudentFaculty() {
        Faculty faculty = new Faculty();
        ResponseEntity<Faculty> facultyResponse = restTemplate.postForEntity("/faculties", faculty, Faculty.class);
        long facultyId = Objects.requireNonNull(facultyResponse.getBody()).getId();
        facultyIds.add(facultyId);

        Student student = new Student();
        student.setFaculty(facultyResponse.getBody());
        ResponseEntity<Student> studentResponse = restTemplate.postForEntity("/students", student, Student.class);
        studentIds.add(Objects.requireNonNull(studentResponse.getBody()).getId());

        ResponseEntity<Collection> response = restTemplate.getForEntity("/students/faculty/" + facultyId, Collection.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetStudents() {
        ResponseEntity<Collection> response = restTemplate.getForEntity("/students?min=18&max=25", Collection.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @AfterEach
    void cleanUp() {
        for (Long studentId : studentIds) {
            restTemplate.exchange("/students/{studentId}", HttpMethod.DELETE, null, Void.class, studentId);
        }
        for (Long facultyId : facultyIds) {
            restTemplate.exchange("/faculties/{facultyId}", HttpMethod.DELETE, null, Void.class, facultyId);
        }
    }
}
