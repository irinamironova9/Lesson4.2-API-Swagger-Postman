package ru.hogwarts.school;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;
import org.webjars.NotFoundException;
import ru.hogwarts.school.controller.AvatarController;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwartsSchoolApplicationRestTemplateTests {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    private static Faculty testFaculty;
    private static long testFacultyId;
    private static Student testStudent;
    private static long testStudentId;


    @Test
    void contextLoads() {
        assertThat(studentController).isNotNull();
        assertThat(facultyController).isNotNull();
    }

    @BeforeEach
    void setUp() {
        testFaculty = new Faculty();
        testFaculty.setName("Test");
        testFaculty.setColor("Test");
        testFaculty = facultyController.createFaculty(testFaculty);
        testFacultyId = testFaculty.getId();

        testStudent = new Student();
        testStudent.setName("TEST");
        testStudent.setAge(20);
        testStudent.setFaculty(testFaculty);
        testStudent = studentController.createStudent(testStudent);
        testStudentId = testStudent.getId();
    }

    @AfterEach
    void cleanUp() {
        facultyController.deleteFaculty(testFacultyId);
        studentController.deleteStudent(testStudentId);
    }

    @Test
    void createStudentTest() {
        Student s = new Student();
        s.setName("createStudentTest");
        s.setAge(20);
        s.setFaculty(testFaculty);
        s = studentController.createStudent(s);
        Student finalS = s;
        assertThat(restTemplate.postForObject(
                "http://localhost:" + port + "/students/add",
                s,
                String.class))
                .isNotNull();
        studentController.deleteStudent(s.getId());
        assertThrows(NotFoundException.class, () -> studentController.findStudent(finalS.getId()));
    }

    @Test
    void findStudent() {
        assertThat(restTemplate
                .getForObject("http://localhost:" + port +
                        "/students/" + testStudentId, String.class))
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
