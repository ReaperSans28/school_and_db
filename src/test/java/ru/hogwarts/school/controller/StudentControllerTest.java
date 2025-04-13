package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    @Test
    void testRetrieveStudent() throws Exception {
        when(studentService.findStudent(1)).thenReturn(Optional.of(new Student(1, "Bill Weasley", 25)));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(studentService).findStudent(1);
    }

    @Test
    void testAddNewStudent() throws Exception {
        String studentJson = "{\"name\":\"Neville Longbottom\",\"age\":20}";
        Student student = new Student(1, "Neville Longbottom", 20);
        when(studentService.addStudent(Mockito.any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(studentService).addStudent(Mockito.any(Student.class));
    }

    @Test
    void testUpdateStudent() throws Exception {
        String studentJson = "{\"id\":1,\"name\":\"Lavender Brown\",\"age\":22}";
        Student updatedStudent = new Student(1, "Lavender Brown", 22);
        when(studentService.editStudent(Mockito.any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(studentService).editStudent(Mockito.any(Student.class));
    }

    @Test
    void testRemoveStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(1);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());

        verify(studentService).deleteStudent(1);
    }

    @Test
    void testGetStudentsByAge() throws Exception {
        when(studentService.findByAgeBetween(18, 25))
                .thenReturn(Arrays.asList(new Student(1, "Ron Weasley", 19)));

        mockMvc.perform(get("/students?min=18&max=25"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(studentService).findByAgeBetween(18, 25);
    }
}