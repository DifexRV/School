package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    Map<Long, Student> students = new HashMap<>();
    private long generateStudentId = 0;

    public Student createStudent(Student student) {
        student.setId(++generateStudentId);
        students.put(generateStudentId, student);
        return student;
    }

    public Student getStudentId(long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        students.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    public Collection<Student> getAllStudent() {
        return students.values();
    }

    public List<Student> sortedAgeStudent(int age) {
        return getAllStudent().stream()
                .filter(student -> student.getAge() == age)
                .sorted(Comparator.comparingInt(students -> students.getAge()))
                .collect(Collectors.toList());
    }

}
