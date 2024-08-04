package ru.hogwarts.school.controller;

import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final RestTemplateAutoConfiguration restTemplateAutoConfiguration;

    public StudentController(StudentService studentService, RestTemplateAutoConfiguration restTemplateAutoConfiguration) {
        this.studentService = studentService;
        this.restTemplateAutoConfiguration = restTemplateAutoConfiguration;
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudent() {
        return ResponseEntity.ok(studentService.getAllStudent());
    }

    @GetMapping(path = "/age/{age}")
    public ResponseEntity<?> sortedAgeStudent(@PathVariable int age) {
        return ResponseEntity.ok(studentService.sortedAgeStudent(age));
    }

    @GetMapping("/students")
    public ResponseEntity<?> getStudentsByAgeRange(@RequestParam int minAge, @RequestParam int maxAge) {
        return ResponseEntity.ok(studentService.sortedAgeStudentBetween(minAge, maxAge));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createStudent);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentId(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        if (updateStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}/faculty")
    public ResponseEntity<?> getStudentFaculty(@PathVariable Long studentId) {
        Student student = studentService.getStudentId(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student.getFaculty());
    }

    @GetMapping("/count")
    public ResponseEntity<?> getStudentByCount() {
        Integer count = studentService.getStudentByCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/age-AVG")
    public ResponseEntity<?> getStudentAvg() {
        Long avg = studentService.getStudentAgeAVG();
        return ResponseEntity.ok(avg);
    }

    @GetMapping("/lastFive")
    public List<Student> getLastFiveStudent() {
        return studentService.findLastFiveStudent();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Student>> getStudentByName(@PathVariable String name) {
        List<Student> student = studentService.getStudentByName(name);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/find_name")
    public ResponseEntity<List<String>> findStudentByHalfName(@RequestParam(required = false) String symbol) {
        List<String> student = studentService.findStudentByHalfName(symbol);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/find_AVG_age")
    public ResponseEntity<?> findStudentAvgAge() {
        double avg = studentService.findStudentAvgAge();
        return ResponseEntity.ok(avg);
    }

    @GetMapping("/print-parallel")
    public ResponseEntity<?> printStudent() {
        List<String> students = studentService.printStudent();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/print-synchronized")
    public ResponseEntity<?> printStudentSynchronized() {
        List<String> students = studentService.printStudentSynchronized();
        return ResponseEntity.ok(students);
    }

}

