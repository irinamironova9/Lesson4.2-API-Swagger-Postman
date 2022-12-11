package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentAlreadyExistsException;
import ru.hogwarts.school.exception.StudentDoesNotExistException;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private static long counter;
    private final Map<Long, Student> students = new HashMap<>();

    public Student addStudent(Student student) {
        if (students.containsValue(student)) {
            throw new StudentAlreadyExistsException();
        }
        student.setId(++counter);
        students.put(student.getId(), student);
        return student;
    }

    public Student findStudent(long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        if (!students.containsValue(student)) {
            throw new StudentDoesNotExistException();
        }
        students.replace(student.getId(), student);
        return student;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    public Collection<Student> getStudentsOfAge(int age) {
        return students.values().stream()
                .filter(s -> s.getAge() == age)
                .collect(Collectors.toList());
    }
}
