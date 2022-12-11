package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.FacultyAlreadyExistsException;
import ru.hogwarts.school.exception.FacultyDoesNotExistException;
import ru.hogwarts.school.model.Faculty;
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
    public Faculty addFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
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
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable("id") long id) {
        Faculty f = facultyService.deleteFaculty(id);
        if (f == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(f);
    }

    @GetMapping("/filter")
    public Collection<Faculty> getFacultiesOfColor(@RequestParam String color) {
        return facultyService.getFacultiesOfColor(color);
    }

    @ExceptionHandler(FacultyAlreadyExistsException.class)
    public ResponseEntity<String> handleFacultyAlreadyExistsException() {
        return ResponseEntity.badRequest().body("Факультет уже был добавлен ранее");
    }

    @ExceptionHandler(FacultyDoesNotExistException.class)
    public ResponseEntity<String> handleFacultyDoesNotExistException() {
        return ResponseEntity.badRequest().body("Факультет не найден");
    }
}
