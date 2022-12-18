package ru.hogwarts.school;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.webjars.NotFoundException;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwartsSchoolApplicationRestTemplateTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
        assertThat(facultyController).isNotNull();
        assertThat(studentRepository).isNotNull();
    }

    @Test
    void createStudentTest() {
        Student testStudent = studentController.findStudent(0);
        assertThat(restTemplate.postForObject(
                "http://localhost:" + port + "/students/add",
                testStudent, String.class))
                .isNotNull();
    }

    @Test
    void findStudentTest() {
        assertThat(restTemplate.getForObject(
                "http://localhost:" + port + "/students/0", String.class))
                .isNotNull();
    }

    @Test
    void updateStudentTest() {
        Student testStudent = studentController.findStudent(0);
        assertThat(restTemplate.postForObject(
                "http://localhost:" + port + "/students/update",
                testStudent, String.class))
                .isNotNull();
    }

    @Test
    void deleteStudentTest() {
        Faculty faculty = facultyController.findFaculty(0);
        Student student = new Student();
        student.setName("deleteStudentTest");
        student.setFaculty(faculty);
        long testId = this.studentRepository.save(student).getId();
        restTemplate.delete(
                "http://localhost:" + port + "/students/" + testId);
        assertThrows(NotFoundException.class,
                () -> restTemplate.getForObject(
                        "http://localhost:" + port + "/students/0",
                        String.class));
    }

//    @GetMapping("/filter/age")
//    public Collection<Student> findStudentsByAge(@RequestParam int age) {
//        return studentService.findStudentsByAge(age);
//    }

//    @GetMapping("/filter/age-gap")
//    public Collection<Student> findByAgeBetween(@RequestParam int min, @RequestParam int max) {
//        return studentService.findByAgeBetween(min, max);
//    }
//
//    @GetMapping("/{id}/faculty")
//    public Faculty getStudentsFaculty(@PathVariable("id") long id) {
//        return studentService.getStudentsFaculty(id);
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity handleNotFoundException() {
//        return ResponseEntity.badRequest().build();
//    }
}
