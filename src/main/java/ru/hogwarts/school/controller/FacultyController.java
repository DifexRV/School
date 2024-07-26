package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RequestMapping("faculty")
@RestController
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService, StudentService studentService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculty());
    }

    @GetMapping(path = "{color}/sortedColorFaculty")
    public ResponseEntity<?> sortedColorFaculty(@PathVariable String color) {
        return ResponseEntity.ok(facultyService.sortedColorFaculty(color));
    }

    @GetMapping("/faculties")
    public ResponseEntity<?> findFacultyByNameOrColor(@RequestParam String value) {
        return ResponseEntity.ok(facultyService.findFacultyByNameOrColor(value));
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        Faculty createFaculty = facultyService.createFaculty(faculty);
        return ResponseEntity.ok(createFaculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable long id) {
        Faculty faculty = facultyService.getFacultyId(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@RequestBody Faculty faculty) {
        Faculty updateFaculty = facultyService.updateFaculty(faculty);
        if (updateFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteFaculty(@PathVariable long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{facultyId}/students")
    public ResponseEntity<?> getFacultyStudents(@PathVariable Long facultyId) {
        Faculty faculty = facultyService.getFacultyId(facultyId);
        if(faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty.getStudents());
    }

    @GetMapping("/nameAndColor/name/{name}/color/{color}")
    public ResponseEntity<List<Faculty>> getFacultyByNameAndColor(@PathVariable String name, @PathVariable String color) {
        List<Faculty> faculty = facultyService.getFacultyByNameAndColor(name, color);
        return ResponseEntity.ok(faculty);
    }

}