package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Collection;
import java.util.List;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.debug("Was invoked method for create faculty: {}", faculty);
        if (faculty == null) {
            List<Faculty> facultyList = facultyRepository.findAll();
            if (facultyList.isEmpty()) {
                throw new IllegalArgumentException("The faculty field is empty. Faculty information must be filled in.");
            }
        }
        try {
            return facultyRepository.save(faculty);
        } catch (Exception e) {
            logger.error("Failed to save faculty: {}", faculty);
            throw new RuntimeException("Error saving faculty: " + faculty);
        }
    }

    public Faculty getFacultyId(long id) {
        logger.debug("Was invoked method for getFacultyId: {}", id);
        return facultyRepository.findById(id).orElseThrow(() -> new RuntimeException("can not find faculty by id = " + id));
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.debug("Was invoked method for updateFaculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.debug("Was invoked method for deleteFaculty: {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculty() {
        logger.debug("Was invoked method for getAllFaculty");
        return facultyRepository.findAll();
    }

    public List<Faculty> sortedColorFaculty(String color) {
        logger.debug("Was invoked method for sortedColorFaculty: {}", color);
        return facultyRepository.findByColorLike(color);
    }

    public List<Faculty> findFacultyByNameOrColor(String value) {
        logger.debug("Was invoked method for findFacultyByNameOrColor: {}", value);
        return facultyRepository.findByNameIgnoreCaseContainingOrColorIgnoreCaseContaining(value, value);
    }

    public List<Faculty> getFacultyByNameAndColor(String name, String color) {
        logger.debug("Was invoked method for getFacultyByNameAndColor: name - {}, color - {}", name, color);
        return facultyRepository.getByNameAndColor(name, color);
    }

}
