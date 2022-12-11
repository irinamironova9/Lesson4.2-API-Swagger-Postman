package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.StudentAlreadyExistsException;
import ru.hogwarts.school.exception.StudentDoesNotExistException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentService {

    private static long counter;
    private final Map<Long, Student> students = new HashMap<>();

    public Student addStudent(Student student) {
        if (students.containsValue(student)) {
            throw new StudentAlreadyExistsException();
        }
        student.setId(++counter);
        return students.put(student.getId(), student);
    }

    public Student findStudent(long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        if (!students.containsValue(student)) {
            throw new StudentDoesNotExistException();
        }
        return students.replace(student.getId(), student);
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }
}
