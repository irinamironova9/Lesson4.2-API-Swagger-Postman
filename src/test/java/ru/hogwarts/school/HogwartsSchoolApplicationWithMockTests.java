package ru.hogwarts.school;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwartsSchoolApplicationWithMockTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private AvatarController avatarController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
        assertThat(facultyController).isNotNull();
        assertThat(avatarController).isNotNull();
    }

    @Test
    void testCreateStudent() {

    }

    @Test
    void findStudent() {
        assertThat(restTemplate
                .getForObject("http://localhost:" + port + "/students/1", String.class))
                .isNotNull();
    }

//    @PutMapping("/update")
//    public Student updateStudent(@RequestBody Student student) {
//        return studentService.updateStudent(student);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteStudent(@PathVariable("id") long id) {
//        studentService.deleteStudent(id);
//        return ResponseEntity.ok().body("Студент успешно удалён");
//    }
//
//    @GetMapping("/filter/age")
//    public Collection<Student> findStudentsByAge(@RequestParam int age) {
//        return studentService.findStudentsByAge(age);
//    }
//
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
