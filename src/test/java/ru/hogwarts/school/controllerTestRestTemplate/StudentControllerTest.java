package ru.hogwarts.school.controllerTestRestTemplate;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

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
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/student/1", Student.class))
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


}
