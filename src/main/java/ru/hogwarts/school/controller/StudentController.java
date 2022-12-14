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
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
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
    public ResponseEntity deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public Collection<Student> findStudentsByAge(@RequestParam int age) {
        return studentService.findStudentsByAge(age);
    }
//
//    @ExceptionHandler(StudentAlreadyExistsException.class)
//    public ResponseEntity<String> handleStudentAlreadyExistsException() {
//        return ResponseEntity.badRequest().body("Студент уже был добавлен ранее");
//    }
//
//    @ExceptionHandler(StudentDoesNotExistException.class)
//    public ResponseEntity<String> handleStudentDoesNotExistException() {
//        return ResponseEntity.badRequest().body("Студент не найден");
//    }
}
