package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

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

    public List<String> findStudentByHalfName(String symbol) {
        logger.debug("The method for searching for a student by letter is called: {}", symbol);
        return studentRepository.findAll()
                .stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.toUpperCase().startsWith(symbol.toUpperCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    public double findStudentAvgAge() {
        logger.info("The method was designed to indicate the average age (via stream) of students: {}", studentRepository.ageAVG());
        return BigDecimal.valueOf(studentRepository.findAll()
                        .stream()
                        .mapToInt(Student::getAge)
                        .average()
                        .orElse(0.0))
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public List<String> printStudent() {

        List<String> students = studentRepository.findAll().stream().map(Student::getName).sorted().toList();

        List<String> processedStudents = new ArrayList<>();
        processedStudents.add(students.get(0));
        processedStudents.add(students.get(1));
        processedStudents.add(students.get(2));
        processedStudents.add(students.get(3));
        processedStudents.add(students.get(4));
        processedStudents.add(students.get(5));

        System.out.println(processedStudents.get(0));
        System.out.println(students.get(1));

        Thread thread1 = new Thread(() -> {
            System.out.println(students.get(2));
            System.out.println(students.get(3));
        });

        Thread thread2 = new Thread(() -> {
            System.out.println(students.get(4));
            System.out.println(students.get(5));
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return processedStudents;
    }

    public List<String> printStudentSynchronized() {

        List<String> students = studentRepository.findAll().stream().map(Student::getName).sorted().toList();

        List<String> processedStudents = new ArrayList<>();
        processedStudents.add(students.get(0));
        processedStudents.add(students.get(1));
        processedStudents.add(students.get(2));
        processedStudents.add(students.get(3));
        processedStudents.add(students.get(4));
        processedStudents.add(students.get(5));

        System.out.println(processedStudents.get(0));
        System.out.println(processedStudents.get(1));

        Thread thread1 = new Thread(() -> {
            synchronized (processedStudents) {
                System.out.println(processedStudents.get(2));
                System.out.println(processedStudents.get(3));
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (processedStudents) {
                System.out.println(processedStudents.get(4));
                System.out.println(processedStudents.get(5));
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return processedStudents;

    }

}
