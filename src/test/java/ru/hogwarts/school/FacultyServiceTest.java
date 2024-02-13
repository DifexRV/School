package ru.hogwarts.school;

        import org.junit.jupiter.api.Test;
        import ru.hogwarts.school.model.Faculty;
        import ru.hogwarts.school.model.Student;
        import ru.hogwarts.school.service.FacultyService;

        import java.util.Collection;
        import java.util.List;

        import static org.junit.jupiter.api.Assertions.assertEquals;
        import static org.junit.jupiter.api.Assertions.assertTrue;

public class FacultyServiceTest {

    FacultyService facultyService = new FacultyService();
    Faculty newFaculty = new Faculty(0, "Gryffindor", "Scarlet");
    Faculty newFaculty1 = new Faculty(1, "Ravenclaw", "Blue");
    Faculty newFaculty2 = new Faculty(1, "Hufflepuff", "Yellow");
    Faculty newFaculty3 = new Faculty(1, "Slytherin", "green");

    @Test
    public void testCreateStudent() {
        Faculty addStudent = facultyService.createFaculty(newFaculty);
        assertEquals(newFaculty, addStudent);
        assertTrue(facultyService.getAllFaculty().contains(newFaculty));
    }

    @Test
    public void testGetStudentId() {
        Faculty studentToFind = facultyService.createFaculty(newFaculty);
        assertEquals(newFaculty, facultyService.getFacultyId(studentToFind.getId()));
    }

    @Test
    public void testUpdateStudent() {

        facultyService.updateFaculty(newFaculty);
        Faculty studentUpdate = facultyService.updateFaculty(newFaculty);
        assertEquals(newFaculty, studentUpdate);
    }

    @Test
    public void testDeleteStudent() {
        facultyService.createFaculty(newFaculty);
        Faculty deleteStudent = facultyService.deleteFaculty(1);
        assertEquals(newFaculty, deleteStudent);
    }

    @Test
    public void testGetAllStudent() {

        facultyService.createFaculty(newFaculty);
        facultyService.createFaculty(newFaculty1);
        facultyService.createFaculty(newFaculty2);

        Collection<Faculty> allFaculty = facultyService.getAllFaculty();

        assertEquals(3, allFaculty.size());
        assertTrue(allFaculty.contains(newFaculty));
        assertTrue(allFaculty.contains(newFaculty1));
        assertTrue(allFaculty.contains(newFaculty2));
    }

    @Test
    public void testSortedAgeStudent() {
        facultyService.createFaculty(newFaculty);

        List<Faculty> sortedStudents = facultyService.sortedColorFaculty("Scarlet");

        assertEquals(1, sortedStudents.size());
    }

}
