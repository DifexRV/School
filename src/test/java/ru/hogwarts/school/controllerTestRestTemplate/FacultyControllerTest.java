package ru.hogwarts.school.controllerTestRestTemplate;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    public void testGetAllFaculty() throws Exception {
        assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotNull();
    }

    @Test
    public void testGetFacultyId() throws Exception {
        assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/Faculty/1", Faculty.class))
                .isNotNull();
    }

    @Test
    public void testCreateFaculty() {
        Faculty facultyTest = new Faculty();
        facultyTest.setName("Тестендор");
        facultyTest.setColor("White or Black");

        assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class))
                .isNotNull();

        this.testRestTemplate.delete("http://localhost:" + port + "/faculty/" + facultyTest.getId());

    }

}
