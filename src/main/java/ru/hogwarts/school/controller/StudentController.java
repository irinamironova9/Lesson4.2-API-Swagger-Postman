package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exception.StudentAlreadyExistsException;
import ru.hogwarts.school.exception.StudentDoesNotExistException;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudent(@PathVariable("id") long id) {
        Student s = studentService.findStudent(id);
        if (s == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(s);
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestBody Student student) {
        return studentService.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") long id) {
        Student s = studentService.deleteStudent(id);
        if (s == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(s);
    }

    @GetMapping("/filter")
    public Collection<Student> getStudentsOfAge(@RequestParam int age) {
        return studentService.getStudentsOfAge(age);
    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<String> handleStudentAlreadyExistsException() {
        return ResponseEntity.badRequest().body("Студент уже был добавлен ранее");
    }

    @ExceptionHandler(StudentDoesNotExistException.class)
    public ResponseEntity<String> handleStudentDoesNotExistException() {
        return ResponseEntity.badRequest().body("Студент не найден");
    }
}
