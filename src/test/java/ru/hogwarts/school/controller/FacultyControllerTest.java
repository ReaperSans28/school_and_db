package ru.hogwarts.school.controller;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FacultyControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private List<Long> facultyIds = new ArrayList<>();
    private List<Long> studentIds = new ArrayList<>();

    @Test
    void testRetrieveFaculty() {
        Faculty faculty = new Faculty();
        ResponseEntity<Faculty> responseCreate = restTemplate.postForEntity("/faculties", faculty, Faculty.class);
        long facultyId = Objects.requireNonNull(responseCreate.getBody()).getId();
        facultyIds.add(facultyId);

        ResponseEntity<Optional> responseRetrieve = restTemplate.getForEntity("/faculties/" + facultyId, Optional.class);
        assertEquals(HttpStatus.OK, responseRetrieve.getStatusCode());
    }

    @Test
    void testAddNewFaculty() {
        Faculty faculty = new Faculty();
        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculties", faculty, Faculty.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        facultyIds.add(Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    void testUpdateFaculty() {
        Faculty faculty = new Faculty();
        ResponseEntity<Faculty> responseCreate = restTemplate.postForEntity("/faculties", faculty, Faculty.class);
        long facultyId = Objects.requireNonNull(responseCreate.getBody()).getId();
        facultyIds.add(facultyId);

        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setId(facultyId);
        ResponseEntity<Faculty> response = restTemplate.exchange("/faculties", HttpMethod.PUT, new HttpEntity<>(updatedFaculty), Faculty.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRemoveFaculty() {
        Faculty faculty = new Faculty();
        Faculty savedFaculty = restTemplate.postForObject("/faculties", faculty, Faculty.class);
        facultyIds.add(savedFaculty.getId());

        ResponseEntity<Void> response = restTemplate.exchange("/faculties/{facultyId}", HttpMethod.DELETE, null, Void.class, savedFaculty.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetStudentsFaculty() {
        Faculty faculty = new Faculty();
        ResponseEntity<Faculty> facultyResponse = restTemplate.postForEntity("/faculties", faculty, Faculty.class);
        assertEquals(HttpStatus.CREATED, facultyResponse.getStatusCode());
        long facultyId = Objects.requireNonNull(facultyResponse.getBody()).getId();
        facultyIds.add(facultyId);

        Student student = new Student();
        student.setFaculty(facultyResponse.getBody());
        ResponseEntity<Student> studentResponse = restTemplate.postForEntity("/students", student, Student.class);
        assertEquals(HttpStatus.CREATED, studentResponse.getStatusCode());
        long studentId = Objects.requireNonNull(studentResponse.getBody()).getId();
        studentIds.add(studentId);

        ResponseEntity<List> response = restTemplate.getForEntity("/students/faculty/" + facultyId, List.class);
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
