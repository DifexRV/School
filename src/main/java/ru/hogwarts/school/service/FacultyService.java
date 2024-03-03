package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty getFacultyId(long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new RuntimeException("can not find faculty by id = " + id));
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public List<Faculty> sortedColorFaculty(String color) {
        return facultyRepository.findByColorLike(color);
    }

    public List<Faculty> findFacultyByNameOrColor(String value) {
        return facultyRepository.findByNameIgnoreCaseContainingOrColorIgnoreCaseContaining(value, value);
    }

}
