package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStudent(Student student) {
        logger.debug("Was invoked method for create student: {}", student);
        if (student.getFaculty() == null) {
            List<Faculty> allFaculty = facultyRepository.findAll();
            if (allFaculty.isEmpty()) {
                throw new IllegalArgumentException("No faculties found in database.");
            }
            Random random = new Random();
            int randomIndex = random.nextInt(allFaculty.size());
            student.setFaculty(allFaculty.get(randomIndex));
        }
        try {
            return studentRepository.save(student);
        } catch (Exception e) {
            logger.error("Error saving student: {}", student, e);
            throw new RuntimeException("Error saving student", e);
        }
    }

    public Student getStudentId(long id) {
        logger.error("There is not student with id = {}", id);
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("can not find student by id = " + id));
    }

    public Student updateStudent(Student student) {
        logger.warn("The student's faculty name is not filled in: {}", student.getFaculty());
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.debug("Was invoked method for delete student: {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getAllStudent() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public List<Student> sortedAgeStudent(int age) {
        logger.info("Was invoked method for sorted students by age: {}", age);
        return studentRepository.findByAge(age);
    }

    public List<Student> sortedAgeStudentBetween(int min, int max) {
        logger.info("Was invoked method for sorted students by between min age: {}", min);
        logger.info("Was invoked method for sorted students by between max age: {}", max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Integer getStudentByCount() {
        logger.info("The method was called to specify the number of students in the database: {}", studentRepository.count());
        return studentRepository.Count();
    }

    public Long getStudentAgeAVG() {
        logger.info("The method was called to indicate the average age of students: {}", studentRepository.ageAVG());
        return studentRepository.ageAVG();
    }

    public List<Student> findLastFiveStudent() {
        logger.info("Was invoked method for find last five students: {}", studentRepository.findLastFiveStudent());
        return studentRepository.findLastFiveStudent();
    }

    public List<Student> getStudentByName(String name) {
        logger.info("The method to find students by name was called: {}", studentRepository.getStudentByName(name));
        return studentRepository.getStudentByName(name);
    }

}
