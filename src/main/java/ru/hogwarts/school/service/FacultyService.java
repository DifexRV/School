package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacultyService {

    Map<Long, Faculty> facultys = new HashMap<>();
    private long generateFacultysId = 0;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++generateFacultysId);
        facultys.put(generateFacultysId, faculty);
        return faculty;
    }

    public Faculty getFacultyId(long id) {
        return facultys.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        facultys.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        return facultys.remove(id);
    }

    public Collection<Faculty> getAllFaculty() {
        return facultys.values();
    }

    public List<Faculty> sortedColorFaculty(String color) {
        return getAllFaculty().stream()
                .filter(faculty -> faculty.getColor() == color)
                .sorted(Comparator.comparing(facultys -> facultys.getColor().equals(color)))
                .collect(Collectors.toList());
    }

}
