package ru.hogwarts.school.controllerTestRestTemplate;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        org.assertj.core.api.Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void testGetAllStudent() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotNull();
    }

    @Test
    public void testGetStudentId() throws Exception {

        Student studentTest = this.testRestTemplate.getForObject("http://localhost:" + port + "/student/1", Student.class);
        Assertions.assertThat(studentTest.getId())
                .isEqualTo(1)
                .isNotNull();
    }

    @Test
    public void testCreateStudent() throws Exception {
        Student studentTest = new Student();
        studentTest.setId(3);
        studentTest.setName("Тестов Тест Тестович");
        studentTest.setAge(2000);

        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student", studentTest, String.class))
                .isNotNull();

        this.testRestTemplate.delete("http://localhost:" + port + "/student/" + studentTest.getId());
    }

    @Test
    public void testSortedAgeStudent() {
        List<Student> expectedStudents = new ArrayList<>();

        Student student1 = new Student();
        student1.setId(666);
        student1.setName("Иванов Иван Иванович");
        student1.setAge(20);
        expectedStudents.add(student1);

        Student student2 = new Student();
        student2.setId(777);
        student2.setName("Павлов Павел Павлович");
        student2.setAge(20);
        expectedStudents.add(student2);

        Assertions.assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/student/age/20", expectedStudents, String.class))
                .isNotNull();

        Assertions.assertThat(ResponseEntity.ok(expectedStudents));

    }

    @Test
    public void testSortedAgeStudentEmptyList() {
        ResponseEntity<Student[]> response = testRestTemplate.getForEntity("/student/age/{age}", Student[].class, 99);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().length);

    }

}
