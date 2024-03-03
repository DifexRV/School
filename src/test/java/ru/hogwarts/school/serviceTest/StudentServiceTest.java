package ru.hogwarts.school.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    private StudentService studentService;

    @BeforeEach
    public void setup() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void testCreateStudent() {
        Student newStudent = new Student(1L, "Alice", 20);
        Mockito.when(studentRepository.save(newStudent)).thenReturn(newStudent);

        Student createdStudent = studentService.createStudent(newStudent);

        assertEquals("Alice", createdStudent.getName());
        assertEquals(20, createdStudent.getAge());
    }

    @Test
    public void testGetStudentById() {
        Student student = new Student(1L, "Bob", 22);
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student foundStudent = studentService.getStudentId(1L);

        assertEquals("Bob", foundStudent.getName());
        assertEquals(22, foundStudent.getAge());
    }

    @Test
    public void testUpdateStudent() {
        Student student = new Student(1L, "Charlie", 25);
        Mockito.when(studentRepository.save(student)).thenReturn(student);

        Student updatedStudent = studentService.updateStudent(student);

        assertEquals("Charlie", updatedStudent.getName());
        assertEquals(25, updatedStudent.getAge());
    }

    @Test
    public void testDeleteStudent() {
        studentService.deleteStudent(1L);

        Mockito.verify(studentRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "David", 23));
        students.add(new Student(2L, "Eve", 24));
        Mockito.when(studentRepository.findAll()).thenReturn(students);

        Collection<Student> allStudents = studentService.getAllStudent();

        assertEquals(2, allStudents.size());
    }

    @Test
    public void testSortedAgeStudent() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "Frank", 22));
        students.add(new Student(2L, "Grace", 22));
        Mockito.when(studentRepository.findByAge(22)).thenReturn(students);

        List<Student> sortedStudents = studentService.sortedAgeStudent(22);

        assertEquals(2, sortedStudents.size());
    }

    @Test
    public void testSortedAgeStudentBetween() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1L, "Helen", 20));
        students.add(new Student(2L, "Isaac", 25));
        Mockito.when(studentRepository.findByAgeBetween(20, 25)).thenReturn(students);

        List<Student> sortedStudents = studentService.sortedAgeStudentBetween(20, 25);

        assertEquals(2, sortedStudents.size());
    }

}
