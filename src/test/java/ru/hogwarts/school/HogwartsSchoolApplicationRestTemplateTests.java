package ru.hogwarts.school;

import net.minidev.json.JSONObject;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HogwartsSchoolApplicationRestTemplateTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void createStudentTest() {
        Faculty faculty = new Faculty();
        faculty.setName("Test Faculty");
        ResponseEntity<Faculty> resultFaculty =
                restTemplate.postForEntity("http://localhost:" + port + "/faculties/add",
                faculty, Faculty.class);
        assertThat(resultFaculty.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultFaculty.getBody()).isNotNull();
        assertThat(resultFaculty.getBody().getId()).isNotNull();
        Faculty created = resultFaculty.getBody();

//        Student student = new Student();
//        student.setName("Test Student");
//        student.setFaculty(created);
        JSONObject student = new JSONObject();
        student.put("name", "Test Student");
        student.put("faculty", created);
        ResponseEntity<Student> result =
                restTemplate.postForEntity("http://localhost:" + port + "/students/add",
                student, Student.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getId()).isNotNull();
    }

    @Test
    public void findStudentTest() {

    }


}
