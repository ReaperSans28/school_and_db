package ru.hogwarts.school.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacultyService facultyService;

    @Test
    void testRetrieveFaculty() throws Exception {
        Faculty faculty = new Faculty(1, "Gryffindor", "Red");
        when(facultyService.findFaculty(1)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculties/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(facultyService).findFaculty(1);
    }

    @Test
    void testAddNewFaculty() throws Exception {
        String facultyJson = "{\"name\":\"Gryffindor\",\"color\":\"Red\"}";
        Faculty faculty = new Faculty(1, "Gryffindor", "Red");
        when(facultyService.addFaculty(Mockito.any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(facultyJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(facultyService).addFaculty(Mockito.any(Faculty.class));
    }

    @Test
    void testUpdateFaculty() throws Exception {
        String facultyJson = "{\"id\":1,\"name\":\"Slytherin\",\"color\":\"Green\"}";
        Faculty updatedFaculty = new Faculty(1, "Slytherin", "Green");
        when(facultyService.editFaculty(Mockito.any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(put("/faculties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(facultyJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(facultyService).editFaculty(Mockito.any(Faculty.class));
    }

    @Test
    void testRemoveFaculty() throws Exception {
        doNothing().when(facultyService).deleteFaculty(1);

        mockMvc.perform(delete("/faculties/1"))
                .andExpect(status().isOk());

        verify(facultyService).deleteFaculty(1);
    }

    @Test
    void testGetStudentsFaculty() throws Exception {
        Faculty faculty = new Faculty(1, "Hufflepuff", "Yellow");
        when(facultyService.findByStudentId(1)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculties/students/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(facultyService).findByStudentId(1);
    }
}
