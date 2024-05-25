package ru.hogwarts.school.controllerWebMvcTest;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class FacultyControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private AvatarService avatarService;

    @SpyBean
    private StudentService studentService;
    @InjectMocks
    private FacultyController facultyController;

    final long id = 1;
    final String name = "facultyTest";
    final String color = "silver";

    Faculty faculty = new Faculty(id, name, color);

    @Test
    public void getAllFacultyTest() throws Exception {
        List<Faculty> faculties = new ArrayList<>();

        when(facultyRepository.findAll()).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createFacultyTest() throws Exception {

        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);


        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void getFacultyIdTest() throws Exception {

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void updateFacultyTest() throws Exception {
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        when(facultyRepository.save(any())).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty") //send
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        Faculty facultyDelete = new Faculty(777L, "Пропащие", "Gray");
        doNothing().when(studentRepository).deleteById(facultyDelete.getId());
        mockMvc.perform(delete("/facultys/{id}", facultyDelete.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFacultyStudentsTest() throws Exception {

        List<Student> studentsTest = new ArrayList<>(List.of(
                new Student(111L, "Аборигенов", 17),
                new Student(222L, "Дреопитеков", 18)));

        faculty.setStudents(studentsTest);

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));
        when(facultyRepository.getReferenceById(any(Long.class))).thenReturn(faculty);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(111L))
                .andExpect(jsonPath("$[0].name").value("Аборигенов"))
                .andExpect(jsonPath("$[0].age").value(17));
    }

    @Test
    public void getStudentsByAgeRangeTest() throws Exception {
        List<Faculty> facultyesTest = new ArrayList<>(List.of(
                new Faculty(55L, "Левые", "White"),
                new Faculty(66L, "Правые", "Black")));

        when(facultyRepository.findByNameIgnoreCaseContainingOrColorIgnoreCaseContaining(any(String.class),any(String.class))).thenReturn(facultyesTest);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/faculties")
                        .param("value", "White")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(55L))
                .andExpect(jsonPath("$[0].name").value("Левые"))
                .andExpect(jsonPath("$[0].color").value("White"));

        when(facultyRepository.findByNameIgnoreCaseContainingOrColorIgnoreCaseContaining(any(String.class),any(String.class))).thenReturn(facultyesTest);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/faculties")
                        .param("value", "Правые")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].id").value(66L))
                .andExpect(jsonPath("$[1].name").value("Правые"))
                .andExpect(jsonPath("$[1].color").value("Black"));
    }

    @Test
    public void sortedColorFacultyTest() throws Exception {
        List<Faculty> facultyTest = new ArrayList<>(List.of(
                new Faculty(55L, "Левые", "White"),
                new Faculty(66L, "Правые", "Black")));

        when(facultyRepository.findByColorLike(any(String.class))).thenReturn(facultyTest);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/White/sortedColorFaculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

}
