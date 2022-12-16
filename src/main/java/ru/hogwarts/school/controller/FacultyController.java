package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping("/add")
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable("id") long id) {
        Faculty f = facultyService.findFaculty(id);
        if (f == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(f);
    }

    @PutMapping("/update")
    public Faculty updateFaculty(@RequestBody Faculty faculty) {
        return facultyService.updateFaculty(faculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable("id") long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().body("Факультет успешно удалён");
    }

    @GetMapping("/filter/color")
    public Collection<Faculty> findFacultiesByColor(@RequestParam String color) {
        return facultyService.findFacultiesByColor(color);
    }

    @GetMapping("/filter/name-or-color")
    public Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(@RequestParam(required = false) String name,
                                                                     @RequestParam(required = false) String color) {
        return facultyService.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getFacultyStudents(@PathVariable("id") long id) {
        Collection<Student> students = facultyService.getFacultyStudents(id);
        if (students == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(students);
    }
}
