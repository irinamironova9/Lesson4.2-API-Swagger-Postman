package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        logger.info("Was invoked method for find student with id = {}", id);
        try {
            Student student = studentRepository.findById(id).get();
            return student;
        } catch (NoSuchElementException e) {
            logger.error("There is not student with id = {}", id);
            throw new NotFoundException(null);
        }
//        return studentRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException(null));
    }

    public Student updateStudent(Student student) {
        logger.info("Was invoked method for update student");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.info("Was invoked method for delete student with id = {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentsByAge(int age) {
        logger.info("Was invoked method for find students by age of {}", age);
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Was invoked method for find students by age between " +
                "{} and {}", min, max);
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getStudentsFaculty(long id) {
        logger.info("Was invoked method for get student`s faculty, " +
                "student`s id = {}", id);
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .orElseThrow(() -> new NotFoundException(null));
    }

    public int getTotalNumberOfStudents() {
        logger.info("Was invoked method for get total number of students");
        return studentRepository.getTotalNumberOfStudents();
    }

    public int getAverageAgeOfStudents() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAverageAgeOfStudents();
    }

    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getAllNamesStartingWithAInUpperCaseAlphabetical() {
        return studentRepository.findAll()
                .parallelStream()
                .map(Student::getName)
                .filter(s -> s.startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .toList();
    }

    public Long getAverageAge() {
        return Math.round(studentRepository.findAll()
                .parallelStream()
                .mapToInt(Student::getAge)
                .average()
                .getAsDouble());
    }
}
