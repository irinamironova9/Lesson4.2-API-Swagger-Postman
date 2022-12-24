package ru.hogwarts.school.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(null));
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    public Collection<Student> findStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getStudentsFaculty(long id) {
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .orElseThrow(() -> new NotFoundException(null));
    }

    public int getTotalNumberOfStudents() {
        return studentRepository.getTotalNumberOfStudents();
    }

    public int getAverageAgeOfStudents() {
        return studentRepository.getAverageAgeOfStudents();
    }

    public Collection<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}
