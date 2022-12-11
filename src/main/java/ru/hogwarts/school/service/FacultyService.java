package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.FacultyAlreadyExistsException;
import ru.hogwarts.school.exception.FacultyDoesNotExistException;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.Map;

@Service
public class FacultyService {

    private static long counter;
    private final Map<Long, Faculty> faculties = new HashMap<>();

    public Faculty addFaculty(Faculty faculty) {
        if (faculties.containsValue(faculty)) {
            throw new FacultyAlreadyExistsException();
        }
        faculty.setId(++counter);
        return faculties.put(faculty.getId(), faculty);
    }

    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        if (!faculties.containsValue(faculty)) {
            throw new FacultyDoesNotExistException();
        }
        return faculties.replace(faculty.getId(), faculty);
    }

    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }
}
