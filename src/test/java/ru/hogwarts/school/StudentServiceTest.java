package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentServiceTest {

    StudentService studentService = new StudentService();
    Student newStudent = new Student(0, "Harry Potter", 14);
    Student newStudent1 = new Student(1, "Tom Marvolo Riddle", 33);
    Student newStudent2 = new Student(1, "Albus Dumbledore", 55);

    @Test
    public void testCreateStudent() {
        Student addStudent = studentService.createStudent(newStudent);
        assertEquals(newStudent, addStudent);
        assertTrue(studentService.getAllStudent().contains(newStudent));
    }

    @Test
    public void testGetStudentId() {
        Student studentToFind = studentService.createStudent(newStudent);
        assertEquals(newStudent, studentService.getStudentId(studentToFind.getId()));
    }

    @Test
    public void testUpdateStudent() {

        studentService.updateStudent(newStudent);
        Student studentUpdate = studentService.updateStudent(newStudent);
        assertEquals(newStudent, studentUpdate);
    }

    @Test
    public void testDeleteStudent() {
        studentService.createStudent(newStudent);
        Student deleteStudent = studentService.deleteStudent(1);
        assertEquals(newStudent, deleteStudent);
    }

    @Test
    public void testGetAllStudent() {

        studentService.createStudent(newStudent);
        studentService.createStudent(newStudent1);
        studentService.createStudent(newStudent2);

        Collection<Student> allStudents = studentService.getAllStudent();

        assertEquals(3, allStudents.size());
        assertTrue(allStudents.contains(newStudent));
        assertTrue(allStudents.contains(newStudent1));
        assertTrue(allStudents.contains(newStudent2));
    }

    @Test
    public void testSortedAgeStudent() {
        studentService.createStudent(newStudent);

        List<Student> sortedStudents = studentService.sortedAgeStudent(14);

        assertEquals(1, sortedStudents.size());
    }

}
