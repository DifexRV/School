package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    @Query(value = "select count(*) from student", nativeQuery = true)
    Integer Count();

    @Query(value = "select AVG(age) from student", nativeQuery = true)
    Long ageAVG();

    @Query(value = "select s from Student s ORDER BY s.id desc limit 5")
    List<Student> findLastFiveStudent();

    List<Student> getStudentByName(String name);

}
