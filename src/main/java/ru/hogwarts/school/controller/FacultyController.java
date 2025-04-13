package ru.hogwarts.school.controller;

import java.util.Optional;

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
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService service;

    public FacultyController(FacultyService service) {
        this.service = service;
    }

    @GetMapping("{facultyId}")
    public ResponseEntity<Optional<Faculty>> retrieveFaculty(
            @PathVariable long facultyId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String color) {
        if (name != null && !name.isEmpty() && color != null && !color.isEmpty()) {
            return ResponseEntity.ok(service.searchByNameOrColor(name, color));
        }
        Optional<Faculty> facultyDetails = service.findFaculty(facultyId);
        return facultyDetails.isPresent() ? ResponseEntity.ok(facultyDetails) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Faculty> addNewFaculty(@RequestBody Faculty faculty) {
        Faculty addedFaculty = service.addFaculty(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedFaculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updatedFaculty = service.editFaculty(faculty);
        return updatedFaculty != null ? ResponseEntity.ok(updatedFaculty) : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("{facultyId}")
    public ResponseEntity<Void> removeFaculty(@PathVariable long facultyId) {
        service.deleteFaculty(facultyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Optional<Faculty>> getStudentsFaculty(@PathVariable long id) {
        Optional<Faculty> faculty = service.findByStudentId(id);
        if (faculty.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(faculty);
    }
}
