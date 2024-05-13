package ru.hogwarts.school.controllerTestRestTemplate;


import org.assertj.core.api.Assertions;
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
        Faculty facultyTest = this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/1", Faculty.class);
        Assertions.assertThat(facultyTest.getId())
                .isEqualTo(1)
                .isNotNull();
    }

    @Test
    public void testCreateFaculty() throws Exception {
        Faculty facultyTest = new Faculty();
        facultyTest.setId(111);
        facultyTest.setName("Тестендор");
        facultyTest.setColor("White or Black");

        assertThat(this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, String.class))
                .isNotNull();

        assertThat(facultyTest.getName())
                .isEqualTo("Тестендор");

        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + facultyTest.getId());
    }

    @Test
    public void deleteFacultyTest() throws Exception {

        Faculty facultyTest = new Faculty();
        facultyTest.setId(111);
        facultyTest.setName("Тестендор");
        facultyTest.setColor("White or Black");

        Long id = this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty", facultyTest, Faculty.class).getId();
        testRestTemplate.delete("http://localhost:" + port + "/faculty/" + id);

        Assertions.assertThat((this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/" + id, String.class)))
                .toString().contains("500");
    }

    @Test
    public void getFacultyStudentsTest() throws Exception {
        String expectedJson = "[{\"id\":4,\"name\":\"Hermione G\",\"age\":20,\"faculty\":{\"id\":1,\"name\":\"Gryffindor\",\"color\":\"Scarlet\"}},{\"id\":1,\"name\":\"Harry Potter\",\"age\":22,\"faculty\":{\"id\":1,\"name\":\"Gryffindor\",\"color\":\"Scarlet\"}},{\"id\":12,\"name\":\"Ronald Weasley\",\"age\":21,\"faculty\":{\"id\":1,\"name\":\"Gryffindor\",\"color\":\"Scarlet\"}}]";

        String actualJson = this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/1/students", String.class);

        Assertions.assertThat(actualJson).isNotNull();
        Assertions.assertThat(actualJson).isEqualTo(expectedJson);
    }

    @Test
    public void testGetFaculty() throws Exception {
        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty", String.class))
                .isNotNull();

        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty?color=1", String.class))
                .isNotNull();

        Assertions.assertThat(this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty?name=1", String.class))
                .isNotNull();

    }

}
