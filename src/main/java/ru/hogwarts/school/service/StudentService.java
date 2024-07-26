package ru.hogwarts.school.service;


import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student getStudentId(long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("can not find student by id = " + id));
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public List<Student> sortedAgeStudent(int age) {
        return studentRepository.findByAge(age);
    }

    public List<Student> sortedAgeStudentBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Integer getStudentByCount() {
        return studentRepository.Count();
    }

    public Long getStudentAgeAVG() {
        return studentRepository.ageAVG();
    }

    public List<Student> findLastFiveStudent() {
        return studentRepository.findLastFiveStudent();
    }

}
