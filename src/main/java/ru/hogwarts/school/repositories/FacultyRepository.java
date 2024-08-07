package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    List<Faculty> findByColorLike(String color);

    List<Faculty> findByNameIgnoreCaseContainingOrColorIgnoreCaseContaining(String name, String color);

    List<Faculty> getByNameAndColor(String name, String color);

}
