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
import ru.hogwarts.school.controller.StudentController;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private AvatarService avatarService;

    @SpyBean
    private FacultyService facultyService;
    @InjectMocks
    private StudentController studentController;

    final String name = "Барбара";
    final int age = 33;
    final long id = 1;

    Student student = new Student(id, name, age);

    @Test
    public void getAllStudentTest() throws Exception {
        List<Student> students = new ArrayList<>();

        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createStudentTest() throws Exception {

        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);


        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void getStudentIdTest() throws Exception {

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void updateStudentTest() throws Exception {
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", name);
        studentObject.put("age", age);

        when(studentRepository.save(any())).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student") //send
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //receive
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    public void deleteStudentTest() throws Exception {
        Student studentDelete = new Student(666L, "Обреченный", 22);
        doNothing().when(studentRepository).deleteById(studentDelete.getId());
        mockMvc.perform(delete("/students/{id}", studentDelete.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getStudentFacultyTest() throws Exception {
        Long facultyId = 11L;
        String facultyName = "OldHunters";
        String facultyColor = "Brown";

        Faculty facultyTest = new Faculty(facultyId, facultyName, facultyColor);

        student.setFaculty(facultyTest);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));
        when(studentRepository.getReferenceById(any(Long.class))).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/1/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(facultyId))
                .andExpect(jsonPath("$.name").value(facultyName))
                .andExpect(jsonPath("$.color").value(facultyColor));

    }

    @Test
    public void getStudentsByAgeRangeTest() throws Exception {
        List<Student> studentsTest = new ArrayList<>(List.of(
                new Student(13L, "Монардыч", 15),
                new Student(14L, "Экспандыч", 16)));

        when(studentRepository.findByAgeBetween(anyInt(), anyInt())).thenReturn(studentsTest);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/students")
                        .param("minAge", "15")
                        .param("maxAge", "16")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(13L))
                .andExpect(jsonPath("$[0].name").value("Монардыч"))
                .andExpect(jsonPath("$[0].age").value(15))
                .andExpect(jsonPath("$[1].id").value(14L))
                .andExpect(jsonPath("$[1].name").value("Экспандыч"))
                .andExpect(jsonPath("$[1].age").value(16));
    }

    @Test
    public void sortedAgeStudentTest() throws Exception {
        List<Student> studentsTest = new ArrayList<>(List.of(
                new Student(13L, "Алфитыч", 22),
                new Student(14L, "Сфгиттыч", 23)));

        when(studentRepository.findByAge(any(int.class))).thenReturn(studentsTest);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/age/22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

}
