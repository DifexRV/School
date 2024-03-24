package ru.hogwarts.school.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTest {

    @Mock
    private FacultyRepository facultyRepository;
    private FacultyService facultyService;

    @BeforeEach
    public void setup() {
        facultyService = new FacultyService(facultyRepository);
    }

    @Test
    public void testCreateFaculty() {
        Faculty newFaculty = new Faculty(1L, "Engineering", "Blue");
        when(facultyRepository.save(newFaculty)).thenReturn(newFaculty);

        Faculty createdFaculty = facultyService.createFaculty(newFaculty);

        assertEquals("Engineering", createdFaculty.getName());
        assertEquals("Blue", createdFaculty.getColor());
    }

    @Test
    public void testGetFacultyById() {
        Faculty newFaculty = new Faculty(1L, "Engineering", "Blue");
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(newFaculty));

        Faculty retrievedFaculty = facultyService.getFacultyId(1L);

        assertEquals("Engineering", retrievedFaculty.getName());
        assertEquals("Blue", retrievedFaculty.getColor());
    }

    @Test
    public void testUpdateFaculty() {
        Faculty newFaculty = new Faculty(1L, "Engineering", "Blue");
        when(facultyRepository.save(newFaculty)).thenReturn(newFaculty);

        Faculty updatedFaculty = facultyService.updateFaculty(newFaculty);

        assertEquals("Engineering", updatedFaculty.getName());
        assertEquals("Blue", updatedFaculty.getColor());
    }

    @Test
    public void testDeleteFaculty() {
        doNothing().when(facultyRepository).deleteById(1L);

        facultyService.deleteFaculty(1L);

        verify(facultyRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllFaculty() {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(1L, "Engineering", "Blue"));
        faculties.add(new Faculty(2L, "Science", "Green"));
        when(facultyRepository.findAll()).thenReturn(faculties);

        Collection<Faculty> allFaculties = facultyService.getAllFaculty();

        assertEquals(2, allFaculties.size());
    }

    @Test
    public void testSortedColorFaculty() {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(1L, "Engineering", "Black"));
        faculties.add(new Faculty(2L, "Science", "Blue"));
        when(facultyRepository.findByColorLike("Black")).thenReturn(faculties);

        List<Faculty> sortedFaculties = facultyService.sortedColorFaculty("Black");

        assertEquals(2, sortedFaculties.size());
    }

    @Test
    public void testFindFacultyByNameOrColor() {
        List<Faculty> faculties = new ArrayList<>();
        faculties.add(new Faculty(1L, "Engineering", "Blue"));
        when(facultyRepository.findByNameIgnoreCaseContainingOrColorIgnoreCaseContaining("Engineering", "Engineering")).thenReturn(faculties);

        List<Faculty> foundFaculties = facultyService.findFacultyByNameOrColor("Engineering");

        assertEquals(1, foundFaculties.size());
    }

}
